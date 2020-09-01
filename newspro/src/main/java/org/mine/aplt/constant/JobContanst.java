package org.mine.aplt.constant;

public class JobContanst {
	/**
	 * 队列命名前缀
	 */
	public static String QUEUE_ID_PREFIX = "QUEUE_";
	/**
	 * 执行队列命名前缀
	 */
	public static String EXECUTE_PREFIX = "EXECUTE_";
	/**
	 * 任务命名前缀
	 */
	public static String TASK_ID_PREFIX = "TASK_";
	/**
	 * 作业命名前缀
	 */
	public static String JOB_ID_PREFIX = "JOB_";
	/**
	 * 步骤命名前缀
	 */
	public static String STEP_ID_PREFIX = "STEP_";
	/**
	 * 触发器前缀
	 */
	public static String TRIGGER_ID_PREFIX = "TRIGGER_";
	/**
	 * 字段位数
	 */
	public static int NAME_DIGITS = 10;
	/**
	 * 填充符
	 */
	public static char FILL_STR = '0';
	/**
	 * 执行JOB日志
	 */
	public static String EXECUTOR_PREFIX = "EXECUTOR_";
	/**
	 * 执行TASK日志
	 */
	public static String EXECUTOR_TASK_PREFIX = "EXECUTOR_TASK_";
	/**
	 * 日志实例前缀
	 */
	public static String TIMING_JOB_PREFIX = "TIMING_JOB_";
	/**
	 * 日志实例前缀
	 */
	public static String TIMING_TASK_PREFIX = "TIMING_TASK_";
	/**
	 * 日志实例前缀
	 */
	public static String TIMING_QUEUE_PREFIX = "TIMING_QUEUE_";
	/**
	 * 保存日志 0-是
	 */
	public static final int SAVE_LOG_0 = 0;
	/**
	 * 保存日志 1-否
	 */
	public static final int SAVE_LOG_1 = 1;
	/**
	 * 有效状态. 0-正常
	 */
	public static final String VALID_STATUS_0 = "0";
	/**
	 * 有效状态. 1-失效
	 */
	public static final String VALID_STATUS_1 = "1";
	/**
	 * 有效状态. D-废弃
	 */
	public static final String VALID_STATUS_D = "D";
	/**
	 * 一次性任务 0-是
	 */
	public static final int ONE_TIME_0 = 0;
	/**
	 * 一次性任务 1-否
	 */
	public static final int ONE_TIME_1 = 1;
	/**
	 * 任务事务性. CONSISTENCY-一致
	 */
	public static final String TASK_TRANSACTIONS_CONSISTENCY = "CONSISTENCY";
	/**
	 * 任务事务性. ISOLATIO-隔离
	 */
	public static final String TASK_TRANSACTIONS_ISOLATIO = "ISOLATIO";
}
