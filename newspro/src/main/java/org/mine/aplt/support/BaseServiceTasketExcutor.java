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
import org.mine.aplt.util.CommonUtils;
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
public abstract class BaseServiceTasketExcutor implements ExcutorTask, GroupTask{
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceTasketExcutor.class);
	
	public Map<String, String> context;
	
	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	@Override
	public Map<String, Object> excutor(Map<String, Object> map) {
		try {
			call(map);
		} catch (Throwable e) {
			logger.error("异步job运行异常: {}", MineBizException.getStackTrace(e));
			throw GitWebException.GIT_APPRUNEXC(parseException(e));
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> grouping(Map<String, Object> map) {
		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String, Object> mapDefault = new HashMap<String, Object>();
		mapDefault.put("default", "0");
		mapDefault.put("tranDate", CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		maps.add(mapDefault);
		return maps;
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
