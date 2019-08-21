package org.mine.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.exception.MineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Controller类异常处理
 * 使用(@ControllerAdvice+@ExceptionHandler)注解来完成
 * 
 * @filename ControllerExceptionDealing.java 
 * @author wzaUsers
 * @date 2019年8月21日下午5:19:46 
 * @version v1.0
 */
@ControllerAdvice
public class ControllerExceptionDealing {

	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionDealing.class);
	
	private static final String CALLBACK_RESULT = "result";
	private static final String CALLBACK_CLASS = "className";
	private static final String CALLBACK_METHOD = "methodName";
	private static final String CALLBACK_ERROR_CODE = "errorcode";
	private static final String CALLBACK_INFO = "errorinfo";
	
	/**
	 * 处理java异常(非自定义)
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Map<String,String> processingThrowable(HandlerMethod handler, Throwable ex){
		logger.debug(">>>>>>>>>handler.getMethod() : " + handler.getMethod());
		Map<String,String> map = new HashMap<>();
		//控制器默认返回json数据格式
//		ResponseBody body = AnnotationUtils.findAnnotation(handler.getMethod(), ResponseBody.class);
		StringBuffer errmsg = new StringBuffer();
		//空指针,数组越界,类文件不存在,数学运算异常,方法的参数错误,访问权限,不兼容的类变化,实例化错误,链接错误
		if(ex instanceof NullPointerException || ex instanceof ArrayIndexOutOfBoundsException
				|| ex instanceof ClassNotFoundException || ex instanceof ArithmeticException
				|| ex instanceof IllegalArgumentException || ex instanceof IllegalAccessException
				|| ex instanceof IncompatibleClassChangeError || ex instanceof InstantiationError
				|| ex instanceof LinkageError){
			errmsg.append("系统处理异常[AppErr]");
		} else if(ex instanceof StackOverflowError || ex instanceof OutOfMemoryError){
			errmsg.append("内存不足");
		} else {
			errmsg.append("System_Error");
		}
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>Throwable堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "System_Error");
		map.put(CALLBACK_RESULT, errmsg.toString());
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		return map;
	}
	
	/**
	 * 处理Sql异常
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(SQLException.class)
	@ResponseBody
	public Map<String, String> processingSQLException(HandlerMethod handler, SQLException ex){
		Map<String,String> map = new HashMap<>();
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>SQLException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "SQL");
		map.put(CALLBACK_RESULT, "数据库SQL访问异常");
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
	
	/**
	 * 处理数据库权限异常
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	public Map<String, String> processingDataAccessException(HandlerMethod handler, DataAccessException ex){
		Map<String,String> map = new HashMap<>();
		StringBuffer errmsg = new StringBuffer();
		if (ex instanceof DataAccessException) {
			if (ex instanceof DuplicateKeyException) {
				errmsg.append("插入或更新时记录重复");
			} else if (ex instanceof DeadlockLoserDataAccessException) {
				errmsg.append("数据库死锁异常");
			} else if (ex instanceof CannotAcquireLockException) {
				errmsg.append("数据库忙");
			} else {
				errmsg.append("数据库访问异常");
			}
		}
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>DataAccessException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "SQL");
		map.put(CALLBACK_RESULT, errmsg.toString());
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
	
	/**
	 * 处理自定义异常
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MineException.class)
	@ResponseBody
	public Map<String,String> processingMineException(HandlerMethod handler, MineException ex){
		Map<String,String> map = new HashMap<>();
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>MineException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, ex.getError_code());
		map.put(CALLBACK_RESULT, ex.getError_msg());
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
	
	/**
	 * 请求参数无效抛出的异常
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> processingMethodArgumentNotValidException(HandlerMethod handler, MethodArgumentNotValidException ex){
		Map<String,String> map = new HashMap<>();
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>MineException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "request_err");
		map.put(CALLBACK_RESULT, "request参数传递错误");
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
	
	/**
	 * 方法请求参数类型不匹配异常
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Map<String,String> processingMethodArgumentTypeMismatchException(HandlerMethod handler, MethodArgumentTypeMismatchException ex){
		Map<String,String> map = new HashMap<>();
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>MineException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "request_err");
		map.put(CALLBACK_RESULT, "request参数传递错误");
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
	
	/**
	 * 请求参数绑定到controller请求参数时的异常
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Map<String,String> processingBindException(HandlerMethod handler, BindException ex){
		Map<String,String> map = new HashMap<>();
		String error_infos = MineException.getStackTrace(ex);
		logger.error(">>>>>MineException堆栈错误信息>>>> " + error_infos);
		map.put(CALLBACK_ERROR_CODE, "request_err");
		map.put(CALLBACK_RESULT, "request参数传递错误");
		map.put(CALLBACK_INFO, error_infos);
		map.put(CALLBACK_METHOD, handler.getMethod().getName());
		map.put(CALLBACK_CLASS, handler.getClass().getName());
		return map;
	}
}
