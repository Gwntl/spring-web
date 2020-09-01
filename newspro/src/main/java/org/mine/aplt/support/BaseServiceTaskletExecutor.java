package org.mine.aplt.support;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步作业执行基类
 * @filename BaseServiceTaskletExecutor.java
 * @author wzaUsers
 * @date 2019年11月28日上午11:29:19 
 * @version v1.0
 */
public abstract class BaseServiceTaskletExecutor implements ExcutorTask, GroupTask {
	protected static final Logger logger = LoggerFactory.getLogger(BaseServiceTaskletExecutor.class);
	
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
					beforeProcessor(input);
					resultMap = executor(input);
					//若如果逻辑代码内存在sleep等方法调用,需要在异常处理内重新执行interrupt方法, 重设置中断状态.
					if(Thread.interrupted()){
						throw GitWebException.GIT1001("the current step is cancelled.");
					}
					afterProcessor(input, resultMap);
				} catch (Throwable e) {
					logger.error("JobDetailProvider运行异常: {}", MineBizException.getStackTrace(e));
					throw e;
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>执行器返回信息: {}", CommonUtils.toString(resultMap));
				return resultMap;
			}}, map);
	}

	public List<Map<String, Object>> grouping(Map<String, Object> input) {
		List<Map<String, Object>> maps = doGrouping(input);
		if(CommonUtils.isNotEmpty(maps)){
			for(Map<String, Object> map : maps){
				CommonUtils.putAll(map, input);
			}
		} else {
			maps = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.putAll(input);
			maps.add(map);
		}
		return maps;
	}
	
	
	/**
	 * <p>Description: 分组执行方法</p>
	 * <p>Title: doGrouping</p>
	 * @param input
	 * @return
	 * @see org.mine.aplt.support.GroupTask#doGrouping(Map<String, Object>)
	 */
	@Override
	public List<Map<String, Object>> doGrouping(Map<String, Object> input) {
		return null;
	}

	/**
	* hook method. service before calling the executor method.
	* Developers can customize the implementations to user for special processing.
	* @param map
	* @return: void
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	protected void beforeProcessor(Map<String, Object> map){}
	/**
	* hook method. service after calling the executor method.
	* Developers can customize the implementations to user for special processing.
	* @param map
	* @param resultMap
	* @return: void
	* @Author: wntl
	* @Date: 2020/8/17
	*/
	protected void afterProcessor(Map<String, Object> map, Map<String, Object> resultMap){}

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
