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
	 * 数据库访问异常.错误信息为:%s
	 * @param msg
	 * @param a
	 * @return
	 */
	public static MineException GIT_DB_DATA_ACCESS(String msg, Throwable a) {
		return newException(PROPERTIES, "GIT_DB_DATA_ACCESS", new Object[]{msg, getStackTrace(a)});
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

	/**
	* 表[%S]数据异常.具体数据信息: [%s].
	* @param tableName
	* @param data
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/11
	*/
	public static MineException GIT_TABLE_DATA_ERROR(String tableName, String data){
		return newException(PROPERTIES, "GIT_TABLE_DATA_ERROR", new Object[]{tableName, data});
	}

	/**
	 * 克隆对象出错
	 * @return
	 */
	public static MineException GIT_OBJECT_CLONE_ERROR(){
		return newException(PROPERTIES, "GIT_OBJECT_CLONE_ERROR", new Object[]{});
	}

	/**
	* %s[%s]实例不存在
	* @param message
	* @param descriptor
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/21
	*/
	public static MineException GIT_BATCH_INSTANCE(String descriptor, String message){
		return newException(PROPERTIES, "GIT_BATCH_INSTANCE", new Object[]{descriptor, message});
	}
	/**
	* %s[%s]实例冲突, 请检查程序是否存在问题.
	* @param message
	* @param descriptor
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/21
	*/
	public static MineException GIT_BATCH_CONFLICT_INSTANCE(String descriptor, String message){
		return newException(PROPERTIES, "GIT_BATCH_CONFLICT_INSTANCE", new Object[]{descriptor, message});
	}
	/**
	* 任务[%s]未指定并发数.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/22
	*/
	public static MineException GIT_SPECIFIED_CONCURRENT_NUMBER(String message){
		return newException(PROPERTIES, "GIT_SPECIFIED_CONCURRENT_NUMBER", new Object[]{message});
	}
	/**
	* 依赖出错.错误信息: [%s].
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/23
	*/
	public static MineException GIT_DEPEND_ERROR(String message){
		return newException(PROPERTIES, "GIT_DEPEND_ERROR", new Object[]{message});
	}
	/**
	* 当前任务状态不为[%s], 不允许重启.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/24
	*/
	public static MineException GIT_TASK_STATUS_RESTART_ERROR(String message) {
		return newException(PROPERTIES, "GIT_TASK_STATUS_RESTART_ERROR", new Object[]{message});
	}
	/**
	* 当前作业状态不为[%s], 不允许重启.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/24
	*/
	public static MineException GIT_JOB_STATUS_RESTART_ERROR(String message) {
		return newException(PROPERTIES, "GIT_JOB_STATUS_RESTART_ERROR", new Object[]{message});
	}
	/**
	* 任务内[%s]不存在待重启的数据.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/24
	*/
	public static MineException GIT_RESTART_TASK_NO_DATA(String message) {
		return newException(PROPERTIES, "GIT_RESTART_TASK_NO_DATA", new Object[]{message});
	}
	/**
	* 作业内[%s]不存在待重启的数据.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/24
	*/
	public static MineException GIT_RESTART_JOB_NO_DATA(String message) {
		return newException(PROPERTIES, "GIT_RESTART_JOB_NO_DATA", new Object[]{message});
	}
	/**
	* %s数据初始化异常.
	* @param message
	* @return: org.mine.aplt.exception.MineException
	* @Author: wntl
	* @Date: 2020/9/25
	*/
	public static MineException GIT_DATA_INIT_ERROR(String message){
		return newException(PROPERTIES, "GIT_DATA_INIT_ERROR", new Object[]{message});
	}
}
