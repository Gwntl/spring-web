package org.mine.quartz.run.task;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.dao.BatchTaskExecuteDao;
import org.mine.dao.BatchTaskLogDao;
import org.mine.lock.redis.DefaultRedisLock;
import org.mine.lock.redis.RedisLock;
import org.mine.lock.redis.RedisLockInput;
import org.mine.lock.redis.RedisLogicDecrConstant;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchTaskDefinition;
import org.mine.model.BatchTaskExecute;
import org.mine.model.BatchTaskLog;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.JobExecutorFactory;
import org.mine.quartz.PreProcessInfo;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.dto.MonitorDto;
import org.mine.quartz.dto.RestartTaskDto;
import org.mine.quartz.dto.TaskExecuteDto;
import org.mine.quartz.run.BaseExecutor;
import org.mine.quartz.run.job.JobRecodeLogLogic;
import org.mine.quartz.util.QuartzUtil;
import org.mine.rule.redis.RedisRuler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单个定时任务执行逻辑. 每个TASK中存在多个JOB.单个JOB之间是并发执行,可以通过依赖关系将各个不相干的JOB串联起来.
 * 先获取TASK中定义的JOB, 然后根据每个JOB的不同特性执行其逻辑.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: TaskRecodeLogLogic
 * @date 2020/8/1815:59
 */
@Component
public class TaskRecodeLogLogic implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(TaskRecodeLogLogic.class);
    /**
     * Task执行线程池.
     */
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(8, 32, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(128),
            new JobExecutorFactory.CustomThreadFactory("EXECUTE_TASK_"));
    /**
     * TASK执行实例缓存
     */
    private static Map<String, TaskExecuteDto> taskInstanceMap = new ConcurrentHashMap<>(1 << 10);
    /**
     * TASK监控队列
     */
    private static BlockingQueue<MonitorDto> monitorQueue = new LinkedBlockingQueue<>();
    /**
     * 信号量缓存
     */
    private static Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>(1 << 6);
    /**
     * 执行次数缓存. 使用本地缓存时当服务重启数据便会消失, 因此需要第三方来保存数据.(后期维护更改)
     */
    private static Map<String, AtomicInteger> timesMap = new ConcurrentHashMap<>(1 << 6);
    /**
     * TASK执行监控线程
     */
    private Thread MONITOR_TASK_THREAD = null;

    /**
    * 任务执行逻辑
    * @param taskDto
    * @return: void
    * @Author: wntl
    * @Date: 2020/8/27
    */
    public static void taskLogic(ExecuteTaskDto taskDto) {
        logger.debug("TaskRecodeLogLogic.taskLogic begin>>>>>>>>>>>>>>>>>>>>>");
        String taskID = taskDto.getTaskId();
        BatchTaskDefinition taskDefinition = GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(taskID, true);
        taskDto.setConcurrencyNum(taskDefinition.getTaskConcurrencyNum());
        String taskInstance = taskDto.getTaskExecutionInstance();
        if (CommonUtils.isEmpty(taskInstance)) {
            throw GitWebException.GIT_BATCH_INSTANCE("TASK", taskID);
        }

        if (taskDto.getConcurrencyNum() > 0) {
            if (!semaphoreMap.containsKey(taskInstance)) {
                semaphoreMap.put(taskInstance, new Semaphore(taskDto.getConcurrencyNum()));
            } else {
                throw GitWebException.GIT_BATCH_CONFLICT_INSTANCE("TASK", taskID);
            }
        } else {
            throw GitWebException.GIT_SPECIFIED_CONCURRENT_NUMBER(taskID);
        }

        List<BatchTaskExecute> executes = GitContext.queryForList("SELECT EXECUTE_TASK_ID, EXECUTE_JOB_ID, EXECUTE_JOB_TIMES, " +
              "EXECUTE_JOB_DEPENDS FROM BATCH_TASK_EXECUTE WHERE EXECUTE_TASK_ID = ? AND VALID_STATUS = '0' " +
              "ORDER BY EXECUTE_JOB_NUM", new Object[]{taskID}, BatchTaskExecute.class);
        if (CommonUtils.isEmpty(executes)) {
            logger.warn("The current task[{}] does not exists jobs!!!!.", taskID);
            updateTaskLogStatus(taskInstance, JobExecutorEnum.SUCCESS.getValue(), "The current task does not exists jobs!!!!");
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("TASK{}内作业信息: {}", taskID, CommonUtils.toString(executes));
            }
            Map<String, ExecuteTaskDto> taskDtoMap = new HashMap<>();
            for (BatchTaskExecute execute : executes) {
                try {
                    if (execute.getExecuteJobTimes() > 0) {
                        timesMap.put(taskInstance + execute.getExecuteJobId(), new AtomicInteger(execute.getExecuteJobTimes()));
                    }
                    taskDtoMap.put(execute.getExecuteJobId(), assemblyObjectInfo(taskDto, execute.getExecuteJobId()));
//                    poolExecutor.submit(new TaskWorker(assemblyObjectInfo(taskDto, execute.getExecuteJobId()), execute));
                } catch (CloneNotSupportedException e) {
                    logger.error("deep clone error. {}", MineException.getStackTrace(e));
                    updateTaskLogStatus(taskInstance, JobExecutorEnum.FAILED.getValue(), "deep clone error");
                    String timesKey = "";
                    for (BatchTaskExecute executeFailed : executes) {
                        if (timesMap.containsKey(timesKey = (taskInstance + executeFailed.getExecuteJobId()))) {
                            timesMap.remove(timesKey);
                        }
                    }
                    semaphoreMap.remove(taskInstance);
                    throw GitWebException.GIT_OBJECT_CLONE_ERROR();
                }
            }

            for (BatchTaskExecute execute : executes) {
                ExecuteTaskDto executeTaskDto = taskDtoMap.get(execute.getExecuteJobId());
                String taskIns = taskDto.getTaskExecutionInstance();
                taskInstanceMap.put(taskIns, new TaskExecuteDto());
                poolExecutor.submit(new TaskWorker(executeTaskDto, execute));
            }

            GitContext.doIndependentTransActionControl((input) -> {
                BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1(input, true);
                taskLog.setTaskStatus(JobExecutorEnum.COMPLETING.getValue());
                taskLog.setTimeStamp(System.nanoTime());
                return GitContext.getBean(BatchTaskLogDao.class).updateOne1R(taskLog);
            }, taskInstance);
        }
        logger.debug("TaskRecodeLogLogic.taskLogic end>>>>>>>>>>>>>>>>>>>>>");
    }
    /**
    * 组装对象信息
    * @param taskDto
    * @param jobID
    * @return: org.mine.quartz.dto.ExecuteTaskDto
    * @Author: wntl
    * @Date: 2020/8/27
    */
    static ExecuteTaskDto assemblyObjectInfo(ExecuteTaskDto taskDto, String jobID) throws CloneNotSupportedException {
        BatchJobDefinition definition = GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jobID, true);
        //此处使用深度拷贝获得新对象, 用来防止在DTO传输过程中导致数据信息被修改.
        //由于此处使用的是引用传递, DTO内数据类型复杂,不止基本数据类型,因此使用Cloneable接口的clone()方法无法满足要求
        //因此将DTO实现序列化接口, 然后使用流进行深度拷贝.
//        ExecuteTaskDto executeTaskDto = BeanUtil.deepClone(taskDto);
        //此处使用手动调用clone方法的形式 来进行深度拷贝.
        ExecuteTaskDto executeTaskDto = (ExecuteTaskDto)taskDto.clone();
        executeTaskDto.setJobId(jobID);
        executeTaskDto.setJobName(definition.getJobName());
        CommonUtils.initMapValue(executeTaskDto.getJobInitValue(), definition.getJobInitValue());
        return executeTaskDto;
    }

    /**
    * 内部任务工作器
    * @filename: TaskRecodeLogLogic
    * @author wntl
    * @date 2020/8/27 10:53
    * @version v1.0
    */
    static class TaskWorker implements Runnable {
        ExecuteTaskDto taskDto;
        BatchTaskExecute execute;
        public TaskWorker(ExecuteTaskDto taskDto, BatchTaskExecute execute) {
            this.taskDto = taskDto;
            this.execute = execute;
        }
        @Override
        public void run() {
            RedisLock lock = null;
            RedisLockInput input = null;
            String jobID = execute.getExecuteJobId();
            try {
                if (taskDto.getTaskInitValue().get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1)) {
                    lock = GitContext.getBean(DefaultRedisLock.class);
                    input = new RedisLockInput();
                    String logicDesc = RedisLogicDecrConstant.QUARTZ_TIMING_TASK_LOGIC;
                    input.setLogicDesc(logicDesc).setKey(jobID).setValue(RedisRuler.doCreateValue(logicDesc, jobID))
                            .setExpire(Long.valueOf((String)taskDto.getTaskInitValue().get(JobConstant.EXPIRE_TIME)))
                            .setRetryTimes(Long.valueOf((String)taskDto.getTaskInitValue().get(JobConstant.RETRY_TIMES)));
                    lock.lock(input);
                }
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("run info. taskDto : {}, execute : {}", taskDto.toString(), execute.toString());
                    }

                    /*
                     * 依赖其他JOB的JOB,只将信息记录入
                     * */
                    boolean isExecute = true;
                    String taskIns = taskDto.getTaskExecutionInstance();
                    String jobExecutionInstance = "";
                    TaskExecuteDto executeDto = null;
                    synchronized (taskInstanceMap) {
                        executeDto = taskInstanceMap.get(taskIns);
                        //当任务已停止, 则直接返回不继续向下执行.
                        if (executeDto == null || executeDto.isTaskStatusAbnormal()) {
                            logger.warn("当前任务已不存在或被停止或被取消, 不继续向下执行.");
                            if (executeDto.isTaskStatusAbnormal()) {
                                logger.info("任务状态: {}", executeDto.getTaskStatusFlag());
                                taskInstanceMap.remove(taskIns);
                            }
                            return;
                        }

                        //初始化依赖关系
                        Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap = executeDto.getInnerDtoMap();
                        TaskExecuteDto.TaskInnerDto innerDto = null;
                        if (innerDtoMap.containsKey(jobID)) {
                            //当缓存中已存在待执行作业时,表明该作业依赖于其他作业,当再次执行时表明依赖已完全消除.
                            innerDto = innerDtoMap.get(jobID);
                            if (innerDto.isBeDependsFlag() && CommonUtils.isEmpty(innerDto.getTaskID())
                                    && CommonUtils.isEmpty(innerDto.getJobID())) {
                                try {
                                    //表明当前JOB被其他JOB依赖, 在执行之前已被初始化. 需要重新填充值
                                    jobInfoProcessingWithinTask(innerDto, jobExecutionInstance, execute, innerDtoMap,
                                            taskDto.getRestartTaskFlag(), taskDto.getSuccessJobMap());
                                    //保存当前TASK执行的DTO信息.
                                    innerDto.setTaskDto((ExecuteTaskDto) taskDto.clone());
                                } catch (CloneNotSupportedException e) {
                                    logger.error("1. clone error. {}", GitWebException.getStackTrace(e));
                                }
                            }

                            if (CommonUtils.isNotEmpty(taskDto.getExecutionInstance())
                                    && CommonUtils.notEquals(taskDto.getExecutionInstance(), innerDto.getCurrentJobInstance())) {
                                logger.error("当前实例运行重复, 请查找原因.{}", innerDto.toString());
                                isExecute = false;
                            }
                            //当依赖未消除时直接报错
                            if (innerDto.isDependsFlag()) {
                                if (!innerDto.isBeDependsFlag()) {
                                    logger.error("当前作业依赖未消除,请检查原因. 执行信息: {}.", innerDto.toString());
                                }
                                logger.warn("当前任务实例{}中作业{}依赖于其他作业{}, 暂不执行, 待依赖任务执行完毕激活.", taskDto.getTaskExecutionInstance(),
                                        jobID, CommonUtils.toString(innerDto.getDependsJob()));
                                isExecute = false;
                            }
                        } else {
                            innerDto = new TaskExecuteDto.TaskInnerDto();
                            innerDtoMap.put(jobID, jobInfoProcessingWithinTask(innerDto, jobExecutionInstance, execute, innerDtoMap,
                                    taskDto.getRestartTaskFlag(), taskDto.getSuccessJobMap()));
                            //增加JOB运行实例. 先生成JOB执行日志.
                            if (CommonUtils.isEmpty(jobExecutionInstance = taskDto.getExecutionInstance())) {
                                executeDto.getJobInstances().add(jobExecutionInstance = BaseExecutor.preStartupProcessing(jobID));
                            }
                            executeDto.getInnerDtoMap().get(jobID).setCurrentJobInstance(jobExecutionInstance);
                            taskDto.setExecutionInstance(jobExecutionInstance);
                            BaseExecutor.addJobLog(taskDto);
                            //判断是否依赖于其他JOB执行.依赖于其他JOB时直接返回.
                            if (innerDto.isDependsFlag()) {
                                try {
                                    //保存当前TASK执行的DTO信息.
                                    innerDto.setTaskDto((ExecuteTaskDto) taskDto.clone());
                                    logger.warn("当前任务实例{}中作业{}依赖于其他作业{}, 暂不执行, 待依赖任务执行完毕激活.", taskDto.getTaskExecutionInstance(),
                                            jobID, CommonUtils.toString(innerDto.getDependsJob()));
                                } catch (CloneNotSupportedException e) {
                                    logger.error("2. clone error. {}", GitWebException.getStackTrace(e));
                                }
                                isExecute = false;
                            }
                        }
                        if (!isExecute) {
                            return;
                        }
                    }
                    //当获取锁时才执行真正的逻辑.
                    if (semaphoreMap.containsKey(taskIns)) {
                        //由于semaphore在申请锁时会一致阻塞当前线程不会将当前持有锁释放.
                        semaphoreMap.get(taskIns).acquire();
                        synchronized (taskInstanceMap) {
                            executeDto = taskInstanceMap.get(taskIns);
                            if (executeDto == null) {
                                logger.error("任务实例[{}]缓存异常.", taskIns);
                            }
                            String pendingJob = taskIns + jobID;
                            //将待执行的作业放入内存中
                            executeDto.getPendingJobs().add(pendingJob);
                            if (executeDto.isTaskStatusAbnormal()) {
                                logger.warn("当前任务[{}]已被停止/取消, 待执行的作业[{}]不继续向下执行.状态: {}.", taskIns, jobID, executeDto.getTaskStatusFlag());
                                executeDto.getPendingJobs().remove(pendingJob);
                                //TODO 移除待执行作业实例.
                                executeDto.getJobInstances().remove(jobExecutionInstance);
                                semaphoreMap.get(taskIns).release();
                                if (CommonUtils.isEmpty(executeDto.getInnerDtoMap()) && CommonUtils.isEmpty(executeDto.getJobInstances())
                                        && CommonUtils.isEmpty(executeDto.getPendingJobs())) {
                                    taskInstanceMap.remove(taskIns);
                                    //更新TASK状态
                                    String status = executeDto.getTaskStatusFlag() == 1 ? JobExecutorEnum.STOP.getValue() :
                                            ((executeDto.getTaskStatusFlag() == 2) ? JobExecutorEnum.CANCEL.getValue() : "");
                                    updateTaskLogToStopOrCancel(taskIns, status);
                                    updateJobLogToStopOrCancel(taskIns, status);
                                }
                                return;
                            }
                            //增加JOB运行实例. 执行时才生成.
//                        executeDto.getJobInstances().add(jobExecutionInstance = BaseExecutor.preStartupProcessing(jobID));
//                        executeDto.getInnerDtoMap().get(jobID).setCurrentJobInstance(jobExecutionInstance);
//                        taskDto.setExecutionInstance(jobExecutionInstance);
                            taskDto.setJobLogFlag(0);
                            //将执行的作业从待执行队列中移除.
                            executeDto.getPendingJobs().remove(pendingJob);
                            //标志当前JOB在执行中.
                            executeDto.getInnerDtoMap().get(jobID).setExecutingFlag(true);
                        }
                        BaseExecutor.baseExecutor(taskDto);
                    } else {
                        logger.error("[{}]执行信号量已被消除.", taskIns);
                    }
                } catch (InterruptedException e) {
                    logger.error(MineException.getStackTrace(e));
                }
            } finally {
                if (lock != null) lock.unlock(input);
            }
        }
    }

    /**
    * 设置及保存运行时任务信息
    * @param innerDto
    * @param jobExecutionInstance
    * @param execute
    * @param innerDtoMap
    * @return: org.mine.quartz.dto.TaskExecuteDto.TaskInnerDto
    * @Author: wntl
    * @Date: 2020/8/27
    */
    public static TaskExecuteDto.TaskInnerDto jobInfoProcessingWithinTask(TaskExecuteDto.TaskInnerDto innerDto, String jobExecutionInstance,
            BatchTaskExecute execute, Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap, boolean isRestart, Map<String, Object> successMap) {
        innerDto.setCurrentJobInstance(jobExecutionInstance);
        innerDto.setTaskID(execute.getExecuteTaskId());
        innerDto.setJobID(execute.getExecuteJobId());
        innerDto.setExecuteTimes(execute.getExecuteJobTimes());
        //获取依赖JOB信息
        Set<String> dependJobs = PreProcessInfo.getDependJob(innerDto.getJobID());
        if (CommonUtils.isNotEmpty(dependJobs)) {
            innerDto.setDependsJob(new LinkedList<>(checkDependsJob(dependJobs, successMap)));
            if (CommonUtils.isNotEmpty(innerDto.getDependsJob())) {
                innerDto.setDependsFlag(true);
            }
            Set<String> beDependJobs = null;
            for (String dependJob : dependJobs) {
                if (innerDtoMap.containsKey(dependJob) && innerDtoMap.get(dependJob).isBeDependsFlag()) {
                    continue;
                }
                if (CommonUtils.isNotEmpty(beDependJobs = PreProcessInfo.getBeDependJob(dependJob))) {
                    if (!innerDtoMap.containsKey(dependJob)) {
                        innerDtoMap.put(dependJob, new TaskExecuteDto.TaskInnerDto());
                    }
                    if (CommonUtils.isEmpty(innerDtoMap.get(dependJob).getBeDependsJob())) {
                        innerDtoMap.get(dependJob).setBeDependsJob(new LinkedList<>(beDependJobs));
                    }
                    innerDtoMap.get(dependJob).setBeDependsFlag(true);
                }
            }
        }
        return innerDto;
    }
    /**
    * 检查依赖作业
    * @param dependJobs
    * @param successJobMap
    * @return: java.util.Set<java.lang.String>
    * @Author: wntl
    * @Date: 2020/9/24
    */
    static Set<String> checkDependsJob(Set<String> dependJobs, Map<String, Object> successJobMap) {
        if (CommonUtils.isEmpty(successJobMap)) {
            return dependJobs;
        }
//        dependJobs.removeIf(jobID -> successJobMap.get(jobID) != null);
        Iterator<String> iterator = dependJobs.iterator();
        while (iterator.hasNext()) {
            String jobID = iterator.next();
            if (successJobMap.get(jobID) != null) {
                iterator.remove();
            }
        }
        return dependJobs;
    }

    /**
    * 通知任务队列,不阻塞失败直接返回false
    * @param monitorDto
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/8/27
    */
    public static boolean notify(MonitorDto monitorDto){
        return monitorQueue.offer(monitorDto);
    }
    /**
    * 阻塞通知任务队列.适用于异步通知.
    * @param monitorDto
    * @return: void
    * @Author: wntl
    * @Date: 2020/8/27
    */
    public static void notifyWaiting(final MonitorDto monitorDto) throws InterruptedException {
        monitorQueue.put(monitorDto);
    }

    /**
     * 停止任务.
     * 1、判断是否有执行中的任务, 若存在的话，将依赖于其任务的信息移除.
     * 2、移除还未执行的作业.
     * @param taskInstance 任务实例
     */
    public static void stopTask(String taskInstance) {
        logger.debug("TaskRecodeLogLogic.stopTask begin>>>>>>>>>>>>>>>>>>>>>");
        if (CommonUtils.isEmpty(taskInstance)) {
            throw GitWebException.GIT1002("任务实例");
        }
        RedisLock lock = null;
        String taskID = getTaskIDByIns(taskInstance);
        RedisLockInput input = null;
        try {
            Map<String, Object> map = new HashMap<>();
            CommonUtils.initMapValue(map, getTaskInitValue(taskID));
            if (map.get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1)) {
                lock = GitContext.getBean(DefaultRedisLock.class);
                input = new RedisLockInput();
                String logicDesc = RedisLogicDecrConstant.QUARTZ_TIMING_TASK_STOP;
                input.setLogicDesc(logicDesc).setKey(taskID).setValue(RedisRuler.doCreateValue(logicDesc, taskID))
                        .setExpire(Long.valueOf((String)map.get(JobConstant.EXPIRE_TIME)))
                        .setRetryTimes(Long.valueOf((String)map.get(JobConstant.RETRY_TIMES)));
                lock.lock(input);
            }

            synchronized (taskInstanceMap) {
                TaskExecuteDto executeDto = taskInstanceMap.get(taskInstance);
                if (executeDto != null) {
                    if (executeDto.getJobInstances().size() == 0 && executeDto.getPendingJobs().size() == 0
                            && executeDto.getInnerDtoMap().size() == 0) {
                        throw GitWebException.GIT1001("任务已执行完毕.");
                    }
                    //设置停止标志. 标志当前任务已被停止.
                    boolean stopFlag = executeDto.setTaskStatusFlag(JobConstant.TASK_STATUS_0, JobConstant.TASK_STATUS_1);
                    if (!stopFlag) {
                        throw GitWebException.GIT1001("当前任务状态[" + executeDto.getTaskStatusFlag() + "]不正常, 不能停止.");
                    }
                    Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap = executeDto.getInnerDtoMap();
                    if (CommonUtils.isNotEmpty(innerDtoMap)) {
                        Iterator<String> iterator = innerDtoMap.keySet().iterator();
                        LinkedList<String> dependsJob = null;
                        LinkedList<String> beDependsJob = null;
                        while (iterator.hasNext()) {
                            String jobID = iterator.next();
                            TaskExecuteDto.TaskInnerDto innerDto = innerDtoMap.get(jobID);
                            if (innerDto.isExecutingFlag()) {
                                //1.移除依赖的任务
                                if (innerDto.isDependsFlag()) {
                                    if ((dependsJob = innerDto.getDependsJob()) != null) {
                                        for (String dependJob : dependsJob) {
                                            innerDtoMap.get(dependJob).getBeDependsJob().remove(jobID);
                                        }
                                    }
                                }
                            } else {
                                //移除未执行作业的实例
                                executeDto.getJobInstances().remove(innerDto.getCurrentJobInstance());
                                iterator.remove();
                                logger.info("{}已被停止.", jobID);
                            }
                        }
                    }
                    //当不存在执行中的任务时, 直接将任务状态置为STOP.
                    if (CommonUtils.isEmpty(innerDtoMap) && CommonUtils.isEmpty(executeDto.getJobInstances())
                            && CommonUtils.isEmpty(executeDto.getPendingJobs())) {
                        taskInstanceMap.remove(taskInstance);
                        //更新TASK状态
                        updateTaskLogToStopOrCancel(taskInstance, JobExecutorEnum.STOP.getValue());
                        updateJobLogToStopOrCancel(taskInstance, JobExecutorEnum.STOP.getValue());
                    }
                } else {
                    throw GitWebException.GIT1001("任务已执行完毕或初始化未完成/失败.");
                }
            }
            //记录停止事件
        } finally {
            if (lock != null) lock.unlock(input);
        }
        logger.debug("TaskRecodeLogLogic.stopTask end>>>>>>>>>>>>>>>>>>>>>");
    }
    /**
    * 重启任务
    * @param taskInstance 任务实例
    * @param taskID 任务ID
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/23
    */
    public static void restartTask(String taskInstance, String taskID) {
        if (CommonUtils.isEmpty(taskInstance)) {
            throw GitWebException.GIT_BATCH_INSTANCE("TASK", taskID);
        }
        if (CommonUtils.isEmpty(taskID)) {
            throw GitWebException.GIT1002("任务ID");
        }

        RedisLock lock = null;
        RedisLockInput in = null;
        try {
            Map<String, Object> map = new HashMap<>();
            CommonUtils.initMapValue(map, getTaskInitValue(taskID));
            if (map.get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1)) {
                lock = GitContext.getBean(DefaultRedisLock.class);
                in = new RedisLockInput();
                String logicDesc = RedisLogicDecrConstant.QUARTZ_TIMING_TASK;
                in.setLogicDesc(logicDesc).setKey(taskID).setValue(RedisRuler.doCreateValue(logicDesc, taskID))
                        .setExpire(Long.valueOf((String)map.get(JobConstant.EXPIRE_TIME)))
                        .setRetryTimes(Long.valueOf((String)map.get(JobConstant.RETRY_TIMES)));
                lock.lock(in);
            }

            BatchTaskLog checkTaskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1R(taskInstance, true);
            if (CommonUtils.notEquals(checkTaskLog.getTaskStatus(), JobExecutorEnum.STOP.getValue())
                    && CommonUtils.notEquals(checkTaskLog.getTaskStatus(), JobExecutorEnum.FAILED.getValue())) {
                throw GitWebException.GIT_TASK_STATUS_RESTART_ERROR(JobExecutorEnum.STOP.getValue() + "," + JobExecutorEnum.FAILED.getValue());
            }

            ExecuteTaskDto taskDto = new ExecuteTaskDto();
            BatchTaskDefinition taskDefinition = GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(taskID, true);
            taskDto.setConcurrencyNum(taskDefinition.getTaskConcurrencyNum());
            taskDto.setTaskId(taskID);
            taskDto.setTaskName(taskDefinition.getTaskName());
            taskDto.setTaskExecutionInstance(taskInstance);
            CommonUtils.initMapValue(taskDto.getTaskInitValue(), taskDefinition.getTaskInitValue());

            if (taskDto.getConcurrencyNum() > 0) {
                if (!semaphoreMap.containsKey(taskInstance)) {
                    semaphoreMap.put(taskInstance, new Semaphore(taskDto.getConcurrencyNum()));
                } else {
                    throw GitWebException.GIT_BATCH_CONFLICT_INSTANCE("TASK", taskID);
                }
            } else {
                throw GitWebException.GIT_SPECIFIED_CONCURRENT_NUMBER(taskID);
            }

            String taskStatus = checkTaskLog.getTaskStatus();
            List<RestartTaskDto> dtos = GitContext.queryForList("SELECT a.EXECUTE_TASK_ID, a.EXECUTE_JOB_ID, a.EXECUTE_JOB_TIMES, " +
                            "a.EXECUTE_JOB_DEPENDS, b.EXECUTION_INSTANCE FROM BATCH_TASK_EXECUTE a, BATCH_JOB_LOG b " +
                            "WHERE EXECUTE_TASK_ID = ? AND a.VALID_STATUS = '0' AND a.EXECUTE_JOB_ID = b.JOB_ID AND b.ASSOCIATE_TASK_INSTANCE = ? " +
                            "AND b.JOB_STATUS = ? AND b.VALID_STATUS = '0' ORDER BY a.EXECUTE_JOB_NUM",
                    new Object[]{taskID, taskInstance, taskStatus}, RestartTaskDto.class);
            if (CommonUtils.isEmpty(dtos)) {
                logger.error("There is no data in the task[{}] to be restarted!!!!.", taskID);
                throw GitWebException.GIT_RESTART_TASK_NO_DATA(taskID);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("TASK{}内作业信息: {}", taskID, CommonUtils.toString(dtos));
                }
                Map<String, ExecuteTaskDto> taskDtoMap = new HashMap<>();
                for (RestartTaskDto dto : dtos) {
                    try {
                        if (dto.getExecuteJobTimes() > 0) {
                            timesMap.put(taskInstance + dto.getExecuteJobId(), new AtomicInteger(dto.getExecuteJobTimes()));
                        }
                        taskDtoMap.put(dto.getExecuteJobId(), assemblyObjectInfo(taskDto, dto.getExecuteJobId()));
                    } catch (CloneNotSupportedException e) {
                        logger.error("deep clone error. {}", MineException.getStackTrace(e));
                        updateTaskLogStatus(taskInstance, JobExecutorEnum.FAILED.getValue(), "deep clone error");
                        String timesKey = "";
                        for (RestartTaskDto executeFailed : dtos) {
                            if (timesMap.containsKey(timesKey = (taskInstance + executeFailed.getExecuteJobId()))) {
                                timesMap.remove(timesKey);
                            }
                        }
                        semaphoreMap.remove(taskInstance);
                        throw GitWebException.GIT_OBJECT_CLONE_ERROR();
                    }
                }

                Map<String, Object> successJobMap = GitContext.queryForMap(
                        "SELECT JOB_ID, EXECUTION_INSTANCE FROM BATCH_JOB_LOG WHERE ASSOCIATE_TASK_INSTANCE = ? AND JOB_STATUS = ? AND VALID_STATUS = '0'",
                        new Object[]{taskInstance, JobExecutorEnum.SUCCESS.getValue()}, "JOB_ID", "EXECUTION_INSTANCE");
                BatchTaskExecute execute = null;
                for (RestartTaskDto dto : dtos) {
                    ExecuteTaskDto executeTaskDto = taskDtoMap.get(dto.getExecuteJobId());
                    executeTaskDto.setExecutionInstance(dto.getExecutionInstance());
                    executeTaskDto.setSuccessJobMap(successJobMap);
                    executeTaskDto.setRestartTaskFlag(true);
                    String taskIns = taskDto.getTaskExecutionInstance();
                    taskInstanceMap.put(taskIns, new TaskExecuteDto());
                    execute = new BatchTaskExecute();
                    execute.setExecuteJobId(dto.getExecuteJobId());
                    execute.setExecuteJobDepends(dto.getExecuteJobDepends());
                    execute.setExecuteJobTimes(dto.getExecuteJobTimes());
                    execute.setExecuteTaskId(dto.getExecuteTaskId());
                    poolExecutor.submit(new TaskWorker(executeTaskDto, execute));
                }

                GitContext.doIndependentTransActionControl((input) -> {
                    BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1(input, true);
                    taskLog.setTaskStatus(JobExecutorEnum.COMPLETING.getValue());
                    taskLog.setTimeStamp(System.nanoTime());
                    return GitContext.getBean(BatchTaskLogDao.class).updateOne1R(taskLog);
                }, taskInstance);
            }
        } catch (Throwable e) {
            if (lock != null) lock.unlock(in);
            throw e;
        }
    }

    /**
    * 取消任务执行
    * @param taskInstance 任务实例
    * @param isForceCancel 是否强制取消
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/23
    */
    public static void cancelTask(String taskInstance, boolean isForceCancel) {
        if (CommonUtils.isEmpty(taskInstance)) {
            throw GitWebException.GIT1002("任务实例");
        }
        RedisLock lock = null;
        String taskID = getTaskIDByIns(taskInstance);
        RedisLockInput input = null;
        try {
            Map<String, Object> map = new HashMap<>();
            CommonUtils.initMapValue(map, getTaskInitValue(taskID));
            if (map.get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1)) {
                lock = GitContext.getBean(DefaultRedisLock.class);
                input = new RedisLockInput();
                String logicDesc = RedisLogicDecrConstant.QUARTZ_TIMING_TASK_CANCEL;
                input.setLogicDesc(logicDesc).setKey(taskID).setValue(RedisRuler.doCreateValue(logicDesc, taskID))
                        .setExpire(Long.valueOf((String) map.get(JobConstant.EXPIRE_TIME)))
                        .setRetryTimes(Long.valueOf((String) map.get(JobConstant.RETRY_TIMES)));
                lock.lock(input);
            }
            synchronized (taskInstanceMap) {
                TaskExecuteDto executeDto = taskInstanceMap.get(taskInstance);
                if (executeDto != null) {
                    if (executeDto.getJobInstances().size() == 0 && executeDto.getPendingJobs().size() == 0
                            && executeDto.getInnerDtoMap().size() == 0) {
                        throw GitWebException.GIT1001("任务已执行完毕.");
                    }
                    boolean cancelFlag = executeDto.setTaskStatusFlag(JobConstant.TASK_STATUS_0, JobConstant.TASK_STATUS_2);
                    if (!cancelFlag) {
                        throw GitWebException.GIT1001("当前任务状态[" + executeDto.getTaskStatusFlag() + "]不正常, 不能取消.");
                    }
                    Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap = executeDto.getInnerDtoMap();
                    if (CommonUtils.isNotEmpty(innerDtoMap)) {
                        Iterator<String> iterator = innerDtoMap.keySet().iterator();
                        LinkedList<String> dependsJob = null;
                        LinkedList<String> beDependsJob = null;
                        JobRecodeLogLogic jobRecodeLogLogic = null;
                        while (iterator.hasNext()) {
                            String jobID = iterator.next();
                            TaskExecuteDto.TaskInnerDto innerDto = innerDtoMap.get(jobID);
                            if (innerDto.isExecutingFlag()) {
                                //1.移除依赖的任务
                                if (innerDto.isDependsFlag()) {
                                    if ((dependsJob = innerDto.getDependsJob()) != null) {
                                        for (String dependJob : dependsJob) {
                                            innerDtoMap.get(dependJob).getBeDependsJob().remove(jobID);
                                        }
                                    }
                                }
                                if (isForceCancel) {
                                    //取消运行中的JOB
                                    if (jobRecodeLogLogic == null) {
                                        jobRecodeLogLogic = new JobRecodeLogLogic();
                                    }
                                    while (true) {
                                        try {
                                            //当执行取消操作时,程序运行中正好处于调度JOB前的数据处理逻辑中, 则等待调度开始再执行取消操作.
                                            if (jobRecodeLogLogic.cancelJob(innerDto.getCurrentJobInstance()).getFlag()) {
                                                break;
                                            }
                                            TimeUnit.MILLISECONDS.sleep(50);
                                        } catch (InterruptedException e) {

                                        } catch (MineException me) {
                                            logger.error("cancel task error. message : {}", MineException.getStackTrace(me));
                                        }
                                    }
                                }
                            } else {
                                //移除未执行作业的实例
                                executeDto.getJobInstances().remove(innerDto.getCurrentJobInstance());
                                iterator.remove();
                                logger.info("{}已被取消.", jobID);
                            }
                        }
                    }
                    //当不存在执行中的任务时, 直接将任务状态置为CANCEL.
                    if (CommonUtils.isEmpty(innerDtoMap) && CommonUtils.isEmpty(executeDto.getJobInstances())
                            && CommonUtils.isEmpty(executeDto.getPendingJobs())) {
                        taskInstanceMap.remove(taskInstance);
                        //更新TASK状态
                        updateTaskLogToStopOrCancel(taskInstance, JobExecutorEnum.CANCEL.getValue());
                        updateJobLogToStopOrCancel(taskInstance, JobExecutorEnum.CANCEL.getValue());
                    }
                } else {
                    throw GitWebException.GIT1001("任务已执行完毕或初始化未完成/失败.");
                }
            }
        } finally {
            if (lock != null) lock.unlock(input);
        }
    }

    /**
    * 更新任务日志
    * @param taskInstance 任务实例
    * @param status
    * @param remark
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/22
    */
    static void updateTaskLogStatus(String taskInstance, String status, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("taskInstance", taskInstance);
        map.put("status", status);
        map.put("remark", remark);
        GitContext.doIndependentTransActionControl((input) -> {
            BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1(input.get("taskInstance") + "", true);
            taskLog.setTaskStatus(input.get("status") + "");
            taskLog.setEndTime(CommonUtils.currentTime(new Date()));
            taskLog.setRemark(input.get("remark") + "");
            taskLog.setTimeStamp(System.nanoTime());
            return GitContext.getBean(BatchTaskLogDao.class).updateOne1R(taskLog);
        }, map);
    }
    /**
    * 将任务日志更改为停止
    * @param taskInstance 任务实例
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/22
    */
    static void updateTaskLogToStopOrCancel(String taskInstance, final String status) {
        if (logger.isDebugEnabled()) {
            logger.debug("将任务[{}]日志状态置为停止.", taskInstance);
        }
        GitContext.doIndependentTransActionControl((input) -> {
            BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1R(input, true);
            if (CommonUtils.notEquals(taskLog.getTaskStatus(), status)) {
                taskLog.setTaskStatus(status);
                taskLog.setEndTime(CommonUtils.currentTime(new Date()));
                taskLog.setTimeStamp(System.nanoTime());
                taskLog.setRemark("the current task is stopped.");
                GitContext.getBean(BatchTaskLogDao.class).updateOne1R(taskLog);
            }
            return null;
        }, taskInstance);
    }

    /**
    * 作业日志状态置为停止.
    * @param taskInstance 任务实例
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/23
    */
    static void updateJobLogToStopOrCancel(String taskInstance, final String status) {
        if (logger.isDebugEnabled()) {
            logger.debug("将作业[{}]日志状态置为停止.", taskInstance);
        }
        Object[] args = new Object[]{status, taskInstance, JobExecutorEnum.NEW.getValue()};
        GitContext.doIndependentTransActionControl((input) -> GitContext.update(
                "update batch_job_log set job_status = ? where associate_task_instance = ? and job_status = ? and valid_status = '0'",
                input), args);
    }

    /**
    * 作业日志状态置为失败.
    * @param jobInstance
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/24
    */
    static void updateJobLogToFailed(String jobInstance) {
        if (logger.isDebugEnabled()) {
            logger.debug("将作业[{}]日志状态置为失败.", jobInstance);
        }
        GitContext.doIndependentTransActionControl((input) -> GitContext.update(
                "update batch_job_log set job_status = ? where execution_instance = ? and job_status = ? and valid_status = '0'",
                new Object[]{JobExecutorEnum.FAILED.getValue(), input, JobExecutorEnum.NEW.getValue()}), jobInstance);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (MONITOR_TASK_THREAD == null) {
            MONITOR_TASK_THREAD = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String taskID = "";
                            //TODO 获取结果后的处理之后可以放置异步运行
                            MonitorDto monitorDto = monitorQueue.take();
                            if (monitorDto.hasEmptyTask()) {
                                logger.error("获取结果值存在错误.");
                            }
                            logger.info("notify message: {}.", monitorDto.toString());
                            //执行结果处理异步逻辑
                            ExecutorBase.getResultExecutor().submit(new TaskPostRunWorker(monitorDto));
                        } catch (InterruptedException e) {
                        } catch (Exception e) {
                            logger.error("任务执行逻辑出错.{}", GitWebException.getStackTrace(e));
                        }
                    }
                }
            }, "MONITOR_TASK_THREAD");
            MONITOR_TASK_THREAD.start();
        }
    }

    static class TaskPostRunWorker implements Runnable {
        private MonitorDto monitorDto;
        public TaskPostRunWorker(MonitorDto monitorDto) {
            this.monitorDto = monitorDto;
        }

        @Override
        public void run() {
            postRunProcessing(monitorDto);
        }
    }

    /**
    * 任务执行后结果处理
    * @param monitorDto
    * @return: void
    * @Author: wntl
    * @Date: 2020/12/1
    */
    public static void postRunProcessing(MonitorDto monitorDto) {
        String task = monitorDto.getTaskExecutionInstance();
        String job = monitorDto.getJobExecutionInstance();
        String jobID = monitorDto.getJobID();
        String jobStatus = monitorDto.getJobStatus();
        boolean isCompleted = false;

        synchronized (taskInstanceMap) {
            if (taskInstanceMap.size() > 0 && taskInstanceMap.containsKey(task)) {
                TaskExecuteDto executeDto = taskInstanceMap.get(task);
                //将执行完毕的JOB实例从缓存中消除.
                if (executeDto.getJobInstances().size() > 0) {
                    executeDto.getJobInstances().remove(job);
                }
                //处理任务中作业执行内容缓存.
                Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap = executeDto.getInnerDtoMap();
                if (innerDtoMap.size() > 0 && innerDtoMap.containsKey(jobID)) {
                    TaskExecuteDto.TaskInnerDto innerDto = innerDtoMap.get(jobID);
                    if (CommonUtils.equals(jobStatus, JobExecutorEnum.SUCCESS.getValue())) {
                        /*
                         * 当JOB任务执行成功时。按照TASK内定义的JOB性质做特殊处理.
                         * 1.判断JOB在TASK中的执行次数.
                         * */
                        if (innerDto.getExecuteTimes() > 0) {
                            String timesKey = "";
                            if (timesMap.get(timesKey = (task + jobID)).get() == 1) {
                                GitContext.doIndependentTransActionControl((input) -> {
                                    BatchTaskExecute taskExecute = GitContext.getBean(BatchTaskExecuteDao.class)
                                            .selectOne1R(input.getTaskID(), input.getJobID(), false);
                                    taskExecute.setValidStatus(JobConstant.VALID_STATUS_D);
                                    return GitContext.getBean(BatchTaskExecuteDao.class).updateOne1(taskExecute);
                                }, innerDto);
                                timesMap.remove(timesKey);
                            } else {
                                timesMap.get(timesKey).decrementAndGet();
                            }
                        }
                        //2.判断当前JOB是否被依赖.当被依赖时信息需要消除对应的依赖.
                        if (innerDto.isBeDependsFlag()) {
                            //获取依赖当前JOB的所有JOB
                            LinkedList<String> beDepends = innerDto.getBeDependsJob();
                            for (String beDepend : beDepends) {
                                if (innerDtoMap.containsKey(beDepend)) {
                                    TaskExecuteDto.TaskInnerDto dependDto = innerDtoMap.get(beDepend);
                                    if (dependDto.isDependsFlag()) {
                                        if (logger.isDebugEnabled()) {
                                            logger.debug("{}消除依赖{}", beDepend, jobID);
                                        }
                                        dependDto.getDependsJob().remove(jobID);
                                        //当依赖JOB全部执行完毕, 则执行当前JOB.
                                        if (dependDto.getDependsJob().size() <= 0) {
                                            try {
                                                logger.info("执行依赖完成的作业[{}].", beDepend);
                                                BatchTaskExecute taskExecute = GitContext.getBean(BatchTaskExecuteDao.class).selectOne1R(dependDto.getTaskID(), beDepend, true);
                                                poolExecutor.submit(new TaskWorker(assemblyObjectInfo(dependDto.getTaskDto(), dependDto.getJobID()), taskExecute));
                                                dependDto.setDependsFlag(false);
                                                dependDto.setDependsJob(null);
                                            } catch (CloneNotSupportedException e) {
                                                logger.error("deep clone error. {}", MineException.getStackTrace(e));
                                            }
                                        }
                                    } else {
                                        logger.error("{}未依赖于其他作业, 当前作业不执行跳过, 请检查作业执行配置是否正确.", beDepend);
                                    }
                                } else {
                                    if (executeDto.isTaskStatusAbnormal()) {
                                        logger.warn("当前任务已被停止或取消, 依赖项已被移除. 状态: {}.", executeDto.getTaskStatusFlag());
                                    } else {
                                        logger.error("{}消除依赖时出错.{}", jobID, beDepend);
                                    }
                                }
                            }
                            innerDto.setBeDependsFlag(false);
                            innerDto.setBeDependsJob(null);
                        }
                    } else {
                        /*
                        * 当JOB执行失败时.判断当前作业是否被其他作业依赖,当存在其他作业依赖,将依赖该作业的实例从缓存中去除,不继续执行.
                        * */
                        if (innerDto.isBeDependsFlag()) {
                            //获取依赖当前JOB的所有JOB.将执行缓存中的信息消除.
                            LinkedList<String> beDepends = innerDto.getBeDependsJob();
                            for (String beDepend : beDepends) {
                                TaskExecuteDto.TaskInnerDto taskInnerDto = innerDtoMap.remove(beDepend);
                                //TODO 将状态置为失败
                                updateJobLogToFailed(taskInnerDto.getCurrentJobInstance());
                                //TODO 移除待执行作业实例.
                                executeDto.getJobInstances().remove(taskInnerDto.getCurrentJobInstance());
                            }
                        }
                        executeDto.setTaskSuccessFlag(false);
                    }
                    innerDtoMap.remove(jobID);
                }

                //当任务被停止/取消时
                if (executeDto.isTaskStatusAbnormal() && CommonUtils.isEmpty(executeDto.getJobInstances())) {
                    String status = executeDto.getTaskStatusFlag() == 1 ? JobExecutorEnum.STOP.getValue() :
                            ((executeDto.getTaskStatusFlag() == 2) ? JobExecutorEnum.CANCEL.getValue() : "");
                    updateTaskLogToStopOrCancel(task, status);
                    updateJobLogToStopOrCancel(task, status);
                    taskInstanceMap.remove(task);
                    isCompleted = true;
                } else if (CommonUtils.isEmpty(executeDto.getJobInstances()) && CommonUtils.isEmpty(executeDto.getInnerDtoMap())
                        && CommonUtils.isEmpty(executeDto.getPendingJobs())) {
                    logger.info("任务[{}]内作业逻辑上执行完毕, 更新任务状态.", task);
                    if (!executeDto.isTaskSuccessFlag()) {
                        monitorDto.setJobStatus(JobExecutorEnum.FAILED.getValue());
                    }
                    updateTaskLogStatus(monitorDto.getTaskExecutionInstance(), monitorDto.getJobStatus(),"");
                    taskInstanceMap.remove(task);
                    //表示任务已经完成
                    isCompleted = true;
                }
            }
        }

        if (semaphoreMap.size() > 0 && semaphoreMap.containsKey(task)) {
            if (monitorDto.getConcurrencyNum() < semaphoreMap.get(task).availablePermits()) {
                logger.warn("实例锁使用异常. 实例为: [}, 指定并发数为: {}, 使用并发数: {}.", task
                        , monitorDto.getConcurrencyNum(), semaphoreMap.get(task).availablePermits());
            }
            if (isCompleted) {
                Semaphore semaphore = semaphoreMap.remove(task);
                semaphore.release();
                semaphore = null;
                logger.info("任务[{}]执行完毕, 消除执行信号量.", task);
                //任务完成时释放TASK锁
                unLock(task);
                logger.info("TASK锁已释放.");
            } else {
                semaphoreMap.get(task).release();
            }
        } else {
            logger.error("[{}]执行信号量异常.", task);
        }
    }

    /**
    * 当存在锁时，是否TASK锁。
    * @param taskIns
    * @return: void
    * @Author: wntl
    * @Date: 2020/12/1
    */
    public static void unLock(String taskIns) {
        String taskID = getTaskIDByIns(taskIns);
        if (QuartzUtil.isCct(getTaskInitValue(taskID))) {
            GitContext.getBean(DefaultRedisLock.class).unlock(new RedisLockInput().setLogicDesc(RedisLogicDecrConstant.QUARTZ_TIMING_TASK).setKey(taskID));
            logger.info("[{}.{}]TASK锁已释放.", taskID, taskIns);
        } else {
            logger.warn("{}不存在锁.", taskIns);
        }
    }

    public static String getTaskIDByIns(String taskIns) {
        return GitContext.queryForString("SELECT TASK_ID FROM BATCH_TASK_LOG WHERE EXECUTION_INSTANCE = ?", new Object[]{ taskIns });
    }

    public static String getTaskInitValue(String taskID) {
        return GitContext.queryForString("SELECT TASK_INIT_VALUE FROM BATCH_TASK_DEFINITION WHERE TASK_ID = ?", new Object[]{ taskID });
    }
}