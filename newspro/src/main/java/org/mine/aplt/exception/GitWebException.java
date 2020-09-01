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
	 * @param error_msg
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
	
	/**
	 * 该用户{%s}已存在
	 * @param error_msg
	 * @return
	 */
	public static MineBizException GIT1004(String error_msg){
		return newException(PROPERTIES, "GIT1004", new Object[]{error_msg});
	}

	/**
	* {%s}不存在
	* @param error_msg
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/8/10
	*/
	public static MineException GIT1005(String error_msg){
		return newException(PROPERTIES, "GIT1005", new Object[]{error_msg});
	}

	/**
	* {%s}已存在
	* @param error_msg
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/8/10
	*/
	public static MineException GIT1006(String error_msg){
		return newException(PROPERTIES, "GIT1006", new Object[]{error_msg});
	}
	
	/**
	 * 查询数据不存在
	 * @param table_name
	 * @param message
	 * @return
	 */
	public static MineBizException GIT_NOTFOUNT(String table_name, String message){
		return newException(PROPERTIES, "GIT_NOTFOUNT", new Object[]{table_name, message});
	}
	
	/**
	 * 类型解析错误
	 * @param errmsg
	 * @return
	 */
	public static MineBizException GIT_PARSE(String errmsg){
		return newException(PROPERTIES, "GIT_PARSE", new Object[]{errmsg});
	}
	
	/**
	 * 参数配置错误
	 * @param table 表名
	 * @param errmsg
	 * @return
	 */
	public static MineBizException GIT_CONFIGUARTION(String table, String column, String errmsg){
		return newException(PROPERTIES, "GIT_CONFIGUARTION", new Object[]{table, column, errmsg});
	}
	
	/**
	 * 系统对外异常
	 * @param errmsg
	 * @return
	 */
	public static MineBizException GIT_APPRUNEXC(String errmsg){
		return newException(PROPERTIES, "GIT_APPRUNEXC", new Object[]{errmsg});
	}
	
	/**
	 * Trigger加载失败
	 * @return
	 */
	public static MineBizException GIT_TRIGGER(){
		return newException(PROPERTIES, "GIT_TRIGGER", new Object[]{});
	}
	
	/**
	 * 定时触发器设置不合法
	 * @return
	 */
	public static MineBizException GIT_CRONEXPRESSION(String expression){
		return newException(PROPERTIES, "GIT_CRONEXPRESSION", new Object[]{expression});
	}
	
	/**
	 * 队列赋值失败,请技术人员尽快查看错误信息!!!
	 * @return
	 */
	public static MineBizException GIT_PUTQUEUE(){
		return newException(PROPERTIES, "GIT_PUTQUEUE", new Object[]{});
	}
	
	/**
	 * 数据库连接失败
	 * @param msg
	 * @return
	 */
	public static MineBizException GIT_DB_CONNECT(String msg){
		return newException(PROPERTIES, "GIT_DB_CONNECT", new Object[]{msg});
	}
	
	/**
	 * 数据库操作失败
	 * @param msg
	 * @return
	 */
	public static MineBizException GIT_DB_OPERATOR(String msg){
		return newException(PROPERTIES, "GIT_DB_OPERATOR", new Object[]{msg});
	}
	
	/**
	 * 对象为空
	 * @param msg
	 * @return
	 */
	public static MineBizException GIT_EMPTY(String msg){
		return newException(PROPERTIES, "GIT_EMPTY", new Object[]{msg});
	}

	/**
	* 表[%s]设计异常
	* @param err_msg
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/8/11
	*/
	public static MineException GIT_DB_CREATE(String err_msg){
		return newException(PROPERTIES, "GIT_DB_CREATE", new Object[]{err_msg});
	}

	/**
	* 系统错误
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/8/25
	*/
	public static MineException GIT_APP_ERROR() {
		return newException(PROPERTIES, "GIT_APP_ERROR", new Object[]{});
	}
}
