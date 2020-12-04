package org.mine.aplt.constant;

public class JobConstant {
	/**
	 * 队列命名前缀
	 */
	public static final String QUEUE_ID_PREFIX = "QUEUE_";
	/**
	 * 执行队列命名前缀
	 */
	public static final String EXECUTE_PREFIX = "EXECUTE_";
	/**
	 * 任务命名前缀
	 */
	public static final String TASK_ID_PREFIX = "TASK_";
	/**
	 * 作业命名前缀
	 */
	public static final String JOB_ID_PREFIX = "JOB_";
	/**
	 * 步骤命名前缀
	 */
	public static final String STEP_ID_PREFIX = "STEP_";
	/**
	 * 触发器前缀
	 */
	public static final String TRIGGER_ID_PREFIX = "TRIGGER_";
	/**
	 * 队列类型
	 */
	public static final String QUEUE_TYPE = "1";
	/**
	 * 任务类型
	 */
	public static final String TASK_TYPE = "2";
	/**
	 * 作业类型
	 */
	public static final String JOB_TYPE = "3";
	/**
	 * 步骤类型
	 */
	public static final String STEP_TYPE = "4";
	/**
	 * 共享锁标志. 0-是
	 */
	public static final String LOCK_SHARE_FLAG_0 = "0";
	/**
	 * 共享锁标志. 1-否
	 */
	public static final String LOCK_SHARE_FLAG_1 = "1";
	/**
	 * 字段位数
	 */
	public static int NAME_DIGITS = 10;
	/**
	 * 填充符
	 */
	public static char FILL_STR = '0';
	/**
	 * 执行STEP日志
	 */
	public static final String EXECUTOR_STEP_PREFIX = "EXECUTOR_STEP_";
	/**
	 * 执行JOB日志
	 */
	public static final String EXECUTOR_JOB_PREFIX = "EXECUTOR_JOB_";
	/**
	 * 执行TASK日志
	 */
	public static final String EXECUTOR_TASK_PREFIX = "EXECUTOR_TASK_";
	/**
	 * 执行QUEUE日志
	 */
	public static final String EXECUTOR_QUEUE_PREFIX = "EXECUTOR_QUEUE_";
	/**
	 * 日志实例前缀
	 */
	public static final String TIMING_JOB_PREFIX = "TIMING_JOB_";
	/**
	 * 日志实例前缀
	 */
	public static final String TIMING_TASK_PREFIX = "TIMING_TASK_";
	/**
	 * 日志实例前缀
	 */
	public static final String TIMING_QUEUE_PREFIX = "TIMING_QUEUE_";
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
	/**
	 * 队列并发执行. 0-是
	 */
	public static final String QUEUE_SAME_TIME_RUN_0 = "0";
	/**
	 * 队列并发执行. 1-否
	 */
	public static final String QUEUE_SAME_TIME_RUN_1 = "1";
	/**
	 * 队列执行标志. 0-被线程执行
	 */
	public static final String QUEUE_EXECUTE_FLAG_0 = "0";
    /**
     * 队列执行标志. 1-未被线程执行
     */
	public static final String QUEUE_EXECUTE_FLAG_1 = "1";
	/**
	 * JOB执行器名称
	 */
	public static final String JOB_EXECUTOR_BEAN_NAME = "JOB_EXECUTOR_BEAN_NAME";
	/**
	 * 任务状态. 0-正常
	 */
	public static final Integer TASK_STATUS_0 = 0;
	/**
	 * 任务状态. 1-停止
	 */
	public static final Integer TASK_STATUS_1 = 1;
	/**
	 * 任务状态. 2-取消
	 */
	public static final Integer TASK_STATUS_2 = 2;
	/**
	 * 作业状态. 0-正常
	 */
	public static final Integer JOB_STATUS_0 = 0;
	/**
	 * 作业状态. 1-停止
	 */
	public static final Integer JOB_STATUS_1 = 1;
	/**
	 * 作业状态. 2-取消
	 */
	public static final Integer JOB_STATUS_2 = 2;
	/**
	 * 作业状态. 8-执行完成
	 */
	public static final Integer JOB_STATUS_8 = 8;
	/**
	 * 初始化未完成
	 */
	public static final String JOB_UNINITIALIZED = "UNINITIALIZED";
	/**
	 * 并发标志 0-是, 1-否
	 */
	public static final String CCT_FLAG = "cctFlag";
	/**
	 * 并发标志 0-是
	 */
	public static final String CCT_FLAG_0 = "0";
	/**
	 * 并发标志 1-否
	 */
	public static final String CCT_FLAG_1 = "1";
	/**
	 * 过期时间
	 */
	public static final String EXPIRE_TIME = "expireTime";
	/**
	 * 重试次数
	 */
	public static final String RETRY_TIMES = "retryTimes";

	public static final String REDIS_LOCK_INPUT = "redisLockInput";
}
