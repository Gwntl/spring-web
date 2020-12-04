package org.mine.lock.redis;

/**
 * Redis锁 业务逻辑描述常量类. 格式: a.b(均为小写字母/数字.)
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisLogicDecrConstant
 * @date 2020/11/2319:17
 */
public class RedisLogicDecrConstant {
    public static final String GLOBAL_REDIS_LOCK_LOGIC_DESC = "global.redis";
    public static final String GLOBAL_REDIS_LOCK_KEY = "lock";
    public static final String LOGIC_TEST = "logic.test";
    public static final String SYSTEM_DESC = "system.desc";
    public static final String BATCH_TIMING_LOCK = "batch.timing.lock";
    public static final String BATCH_CIR_LOCK = "batch.cir.lock";
    /**
     * Quartz定时队列
     */
    public static final String QUARTZ_TIMING_QUEUE = "quartz.timing.queue";
    /**
     * Quartz定时任务
     */
    public static final String QUARTZ_TIMING_TASK = "quartz.timing.task";
    /**
     * Quartz定时作业
     */
    public static final String QUARTZ_TIMING_JOB = "quartz.timing.job";
    /**
     * Quartz定时步骤
     */
    public static final String QUARTZ_TIMING_STEP = "quartz.timing.step";
    /**
     * Quartz定时队列逻辑
     */
    public static final String QUARTZ_TIMING_QUEUE_LOGIC = "quartz.timing.queue.logic";
    /**
     *Quartz定时任务逻辑
     */
    public static final String QUARTZ_TIMING_TASK_LOGIC = "quartz.timing.task.logic";
    /**
     * Quartz定时作业逻辑
     */
    public static final String QUARTZ_TIMING_JOB_LOGIC = "quartz.timing.job.logic";
    /**
     * Quartz定时步骤逻辑
     */
    public static final String QUARTZ_TIMING_STEP_LOGIC = "quartz.timing.step.logic";
    /**
     * Quartz定时作业停止
     */
    public static final String QUARTZ_TIMING_JOB_STOP = "quartz.timing.job.stop";
    /**
     * Quartz定时作业取消
     */
    public static final String QUARTZ_TIMING_JOB_CANCEL = "quartz.timing.job.cancel";
    /**
     * Quartz定时任务停止
     */
    public static final String QUARTZ_TIMING_TASK_STOP = "quartz.timing.task.stop";
    /**
     * Quartz定时任务取消
     */
    public static final String QUARTZ_TIMING_TASK_CANCEL = "quartz.timing.task.cancel";

}
