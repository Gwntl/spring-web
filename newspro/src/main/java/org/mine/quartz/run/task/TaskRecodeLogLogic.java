package org.mine.quartz.run.task;

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.dao.BatchTaskExecuteDao;
import org.mine.dao.BatchTaskLogDao;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchTaskDefinition;
import org.mine.model.BatchTaskExecute;
import org.mine.model.BatchTaskLog;
import org.mine.quartz.JobExcutorFactory;
import org.mine.quartz.PreProcessInfo;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.dto.MonitorDto;
import org.mine.quartz.dto.TaskExecuteDto;
import org.mine.quartz.run.BaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

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
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(16, 32, 4, TimeUnit.SECONDS, new ArrayBlockingQueue<>(64),
            new JobExcutorFactory.CustomThreadFactory("EXECUTE_TASK_"));
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
    private static  Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>(1 << 6);
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
        String taskID = taskDto.getTaskId();
        BatchTaskDefinition taskDefinition = GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(taskID, true);
        taskDto.setConcurrencyNum(taskDefinition.getTaskConcurrencyNum());
        if (!semaphoreMap.containsKey(taskDto.getTaskExecutionInstance())) {
            semaphoreMap.put(taskDto.getTaskExecutionInstance(), new Semaphore(taskDto.getConcurrencyNum()));
        } else {
            throw GitWebException.GIT1001("TASK实例冲突, 请检查程序是否存在问题.");
        }
        List<BatchTaskExecute> executes = GitContext.queryForList("SELECT EXECUTE_TASK_ID, EXECUTE_JOB_ID, EXECUTE_JOB_ONE_TIME, " +
              "EXECUTE_JOB_DEPENDS FROM BATCH_TASK_EXECUTE WHERE EXECUTE_TASK_ID = ? AND VALID_STATUS = '0' " +
              "ORDER BY EXECUTE_JOB_NUM", new Object[]{taskID}, BatchTaskExecute.class);
        for (BatchTaskExecute execute : executes) {
            try {
                poolExecutor.submit(new TaskWorker(assemblyObjectInfo(taskDto, execute.getExecuteJobId()), execute));
            } catch (CloneNotSupportedException e) {
                logger.error("deep clone error. {}", MineException.getStackTrace(e));
            }
        }
        GitContext.doIndependentTransActionControl((input) -> {
            BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1(input.getTaskExecutionInstance(), true);
            taskLog.setStartTime(CommonUtils.currentTime(new Date()));
            taskLog.setTaskStatus(JobExcutorEnum.COMPLETING.getValue());
            taskLog.setTimeStamp(System.nanoTime());
            return GitContext.getBean(BatchTaskLogDao.class).updateOne1R(taskLog);
        }, taskDto);
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
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("run info. taskDto : {}, execute : {}", taskDto.toString(), execute.toString());
                }
                /*
                * 依赖其他JOB的JOB,只将信息记录入
                * */
                boolean isExecute = true;
                String taskIns = taskDto.getTaskExecutionInstance();
                synchronized (taskInstanceMap) {
                    String jobExecutionInstance = "";
                    if (!taskInstanceMap.containsKey(taskIns)) {
                        taskInstanceMap.put(taskIns, new TaskExecuteDto());
                    }
                    TaskExecuteDto executeDto = taskInstanceMap.get(taskIns);

                    String jobID = execute.getExecuteJobId();
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
                                jobInfoProcessingWithinTask(innerDto, jobExecutionInstance, execute, innerDtoMap);
                                //保存当前TASK执行的DTO信息.
                                innerDto.setTaskDto((ExecuteTaskDto) taskDto.clone());
                            } catch (CloneNotSupportedException e) {
                                logger.error("1. clone error. {}", GitWebException.getStackTrace(e));
                            }
                        }

                        if (CommonUtils.isNotEmpty(taskDto.getExecutionInstance())) {
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
                        innerDtoMap.put(jobID, jobInfoProcessingWithinTask(innerDto, jobExecutionInstance, execute, innerDtoMap));
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

                    //增加JOB运行实例
                    executeDto.getJobInstances().add(jobExecutionInstance = BaseExecutor.preStartupProcessing(taskDto.getJobId()));
                    innerDto.setCurrentJobInstance(jobExecutionInstance);
                    taskDto.setExecutionInstance(jobExecutionInstance);
                    taskDto.setJobLogFlag(0);
                }
                if (semaphoreMap.containsKey(taskIns)) {
                    semaphoreMap.get(taskIns).acquire();
                    BaseExecutor.baseExecutor(taskDto);
                } else {
                    logger.error("[{}]执行信号量已被消除.", taskIns);
                }
            } catch (InterruptedException e) {
                logger.error(MineException.getStackTrace(e));
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
            BatchTaskExecute execute, Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap) {
        innerDto.setCurrentJobInstance(jobExecutionInstance);
        innerDto.setTaskID(execute.getExecuteTaskId());
        innerDto.setJobID(execute.getExecuteJobId());
        innerDto.setOneTimeFlag(execute.getExecuteJobOneTime() == JobContanst.ONE_TIME_0);
        /*innerDto.setDependsFlag(CommonUtils.isNotEmpty(jobDepends));
        if (innerDto.isDependsFlag()) {
            LinkedList<String> depends = CommonUtils.splitStrToLink(jobDepends, ";", false);
            if (depends != null) {
                innerDto.setDependsJob(depends);
                for (String depend : depends) {
                    if (!innerDtoMap.containsKey(depend)) {
                        innerDtoMap.put(depend, new TaskExecuteDto.TaskInnerDto());
                    }
                    innerDtoMap.get(depend).setBeDependsFlag(true);
                    innerDtoMap.get(depend).getBeDependsJob().add(innerDto.getJobID());
                }
            }
        }*/
        //获取依赖JOB信息
        Set<String> dependJobs = PreProcessInfo.getDependJob(innerDto.getJobID());
        if (CommonUtils.isNotEmpty(dependJobs)) {
            innerDto.setDependsFlag(true);
            innerDto.setDependsJob(new LinkedList<>(dependJobs));
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

    @Override
    public void afterPropertiesSet() throws Exception {
        if (MONITOR_TASK_THREAD == null) {
            MONITOR_TASK_THREAD = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            MonitorDto monitorDto = monitorQueue.take();
                            logger.info("notify message: {}.", monitorDto.toString());
                            String task = monitorDto.getTaskExecutionInstance();
                            String job = monitorDto.getJobExecutionInstance();
                            String jobID = monitorDto.getJobID();
                            String jobStatus = monitorDto.getJobStatus();
                            boolean isCompleted = false;

                            synchronized (taskInstanceMap) {
                                if (taskInstanceMap.size() > 0 && taskInstanceMap.containsKey(task)) {
                                    TaskExecuteDto executeDto = taskInstanceMap.get(task);
                                    if (executeDto.getJobInstances().size() > 0) {
                                        //将执行的JOB实例从缓存中消除
                                        executeDto.getJobInstances().remove(job);
                                    }
                                    Map<String, TaskExecuteDto.TaskInnerDto> innerDtoMap = executeDto.getInnerDtoMap();
                                    if (innerDtoMap.size() > 0 && innerDtoMap.containsKey(jobID)) {
                                        TaskExecuteDto.TaskInnerDto innerDto = innerDtoMap.get(jobID);
                                        if (CommonUtils.equals(jobStatus, JobExcutorEnum.SUCCESS.getValue())) {
                                            /*
                                             * 当JOB任务执行成功时。按照TASK内定义的JOB性质做特殊处理.
                                             * 1.当JOB在TASK属于一次型作业时,将配置表中的记录去除.
                                             * */
                                            if (innerDto.isOneTimeFlag()) {
                                                //更新执行表中的状态.
                                                GitContext.doIndependentTransActionControl((input) -> {
                                                    BatchTaskExecute taskExecute = GitContext.getBean(BatchTaskExecuteDao.class)
                                                            .selectOne1R(input.getTaskID(), input.getJobID(), false);
                                                    taskExecute.setValidStatus(JobContanst.VALID_STATUS_1);
                                                    return GitContext.getBean(BatchTaskExecuteDao.class).updateOne1(taskExecute);
                                                }, innerDto);
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
                                                        }
                                                    } else {
                                                        logger.error("{}消除依赖时出错.{}", jobID, beDepend);
                                                    }
                                                }
                                                innerDto.setBeDependsFlag(false);
                                                innerDto.setBeDependsJob(null);
                                            }
                                        } else {
                                            /**
                                             * 当JOB执行失败时.判断当前作业是否被其他作业依赖,当存在其他作业依赖,将依赖该作业的实例从缓存中去除,不继续执行.
                                             * */
                                            if (innerDto.isBeDependsFlag()) {
                                                //获取依赖当前JOB的所有JOB.将执行缓存中的信息消除.
                                                LinkedList<String> beDepends = innerDto.getBeDependsJob();
                                                for (String beDepend : beDepends) {
                                                    innerDtoMap.remove(beDepend);
                                                }
                                            }
                                            executeDto.setTaskSuccessFlag(false);
                                        }
                                        innerDtoMap.remove(jobID);
                                    }

                                    if (executeDto.getJobInstances().size() == 0 && executeDto.getInnerDtoMap().size() == 0) {
                                        logger.info("任务[{}]内作业逻辑上执行完毕, 更新任务状态.", task);
                                        if (!executeDto.isTaskSuccessFlag()) {
                                            monitorDto.setJobStatus(JobExcutorEnum.FAILED.getValue());
                                        }
                                        //表示任务已经完成
                                        GitContext.doIndependentTransActionControl((input) -> {
                                            BatchTaskLog taskLog = GitContext.getBean(BatchTaskLogDao.class).selectOne1(input.getTaskExecutionInstance(), true);
                                            taskLog.setEndTime(CommonUtils.currentTime(new Date()));
                                            taskLog.setTimeStamp(System.nanoTime());
                                            taskLog.setTaskStatus(input.getJobStatus());
                                            return GitContext.getBean(BatchTaskLogDao.class).updateOne1(taskLog);
                                        }, monitorDto);
                                        taskInstanceMap.remove(task);
                                        isCompleted = true;
                                    }
                                }
                            }

                            if (semaphoreMap.size() > 0 && semaphoreMap.containsKey(task)) {
                                if(monitorDto.getConcurrencyNum() < semaphoreMap.get(task).availablePermits()){
                                    logger.error("实例锁使用异常. 实例为: [}, 指定并发数为: {}, 使用并发数: {}.", task
                                    , monitorDto.getConcurrencyNum(), semaphoreMap.get(task).availablePermits());
                                    throw GitWebException.GIT_APP_ERROR();
                                }
                                if (isCompleted) {
                                    semaphoreMap.remove(task);
                                    logger.info("任务[{}]执行完毕, 消除执行信号量.", task);
                                } else {
                                    semaphoreMap.get(task).release();
                                }
                            } else {
                                logger.error("[{}]执行信号量异常.", task);
                            }
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }, "MONITOR_TASK_THREAD");
            MONITOR_TASK_THREAD.start();
        }
    }
}