package org.mine.aplt.exception;

import java.util.Properties;

public class GitWebException extends MineBizException{

	private static final long serialVersionUID = 709133615026825495L;

	private static final Properties PROPERTIES = loadProperties("config/properties/error/error.properties");
	
	private GitWebException(String error_code, String error_msg) {
		super(error_code,error_msg);
	}
	
	/**
	 * %s
	 * @param errmsg
	 * @return
	 */
	public static MineBizException GIT1001(String error_msg){
		return newException(PROPERTIES, "GIT1001", new Object[]{error_msg});
	}
	
	/**
	 * [%s]不能为空
	 * @param error_msg
	 * @return
	 */
	public static MineBizException GIT1002(String error_msg){
		return newException(PROPERTIES, "GIT1002", new Object[]{error_msg});
	}
	
	/**
	 * [%s]文件不存在
	 * @return
	 */
	public static MineBizException GIT1003(String error_msg){
		return newException(PROPERTIES, "GIT1003", new Object[]{error_msg});
	}
	
	public static MineBizException GIT_NOTFOUNT(String table_name, String message){
		return newException(PROPERTIES, "GIT_NOTFOUNT", new Object[]{table_name, message});
	}
	
	
}
