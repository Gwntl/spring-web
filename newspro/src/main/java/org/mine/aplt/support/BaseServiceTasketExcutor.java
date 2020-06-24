package org.mine.aplt.support;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.quartz.dto.GroupInputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * 异步作业执行基类
 * @filename BaseServiceTasketExcutor.java 
 * @author wzaUsers
 * @date 2019年11月28日上午11:29:19 
 * @version v1.0
 */
public abstract class BaseServiceTasketExcutor implements ExcutorTask, GroupTask {
	protected static final Logger logger = LoggerFactory.getLogger(BaseServiceTasketExcutor.class);
	
	public Map<String, String> context;
	
	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	@Override
	public Map<String, Object> handler(Map<String, Object> map) {
		return GitContext.doIndependentTransActionControl(new BatchOperator<Map<String, Object>, Map<String, Object>>() {
			@Override
			public Map<String, Object> call(Map<String, Object> input) {
				Map<String, Object> resultMap = null;
				try {
					resultMap =  excutor(input);
					//若如果逻辑代码内存在sleep等方法调用,需要在异常处理内重新执行interrupt方法, 重设置中断状态.
					if(Thread.interrupted()){
						throw GitWebException.GIT1001("the current step is cancelled.");
					}
				} catch (Throwable e) {
					logger.error("JobdetailProvider运行异常: {}", MineBizException.getStackTrace(e));
					throw e;
				}
				return resultMap;
			}}, map);
	}

	public List<Map<String, Object>> grouping(GroupInputDto input) {
		List<Map<String, Object>> maps = new ArrayList<>();
		maps = doGrouping(input);
		if(CommonUtils.isNotEmpty(maps)){
			for(Map<String, Object> map : maps){
				map.put("default", "0");
				map.put("tranDate", CommonUtils.dateToString(new Date(), "yyyyMMdd"));
				maps.add(map);
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("default", "0");
			map.put("tranDate", CommonUtils.dateToString(new Date(), "yyyyMMdd"));
			maps.add(map);
		}
		return maps;
	}
	
	
	/**
	 * <p>Description: 分组执行方法</p>
	 * <p>Title: doGrouping</p>
	 * @param input
	 * @return
	 * @see org.mine.aplt.support.GroupTask#doGrouping(org.mine.quartz.dto.GroupInputDto)
	 */
	@Override
	public List<Map<String, Object>> doGrouping(GroupInputDto input) {
		return null;
	}

	public String parseException(Throwable e){
		String message = "";
		if(e instanceof MineException){
			message = String.format("%s-%s", ((MineException)e).getError_code(), ((MineException)e).getError_msg());
		} else if(e instanceof SQLException || e instanceof DataAccessException ){
			message = String.format("数据库处理异常,错误信息: [%s]", e.getMessage());
		} else if(e instanceof StackOverflowError || e instanceof OutOfMemoryError){
			message = "系统处理异常,内存不足";
		} else if(e instanceof IllegalArgumentException){
			message = "参数非法";
		} else if(e instanceof IndexOutOfBoundsException || e instanceof StringIndexOutOfBoundsException ){
			message = "索引溢出";
		} else {
			message = "系统应用异常";
		}
		return message;
	}
}
