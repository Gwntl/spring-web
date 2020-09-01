package org.mine.quartz.run.job;

import org.mine.aplt.Cache.AsyncCache;
import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobLogDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.dao.BatchStepLogDao;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.model.BatchJobLog;
import org.mine.model.BatchStepDefinition;
import org.mine.model.BatchStepLog;
import org.mine.model.BatchTimingStepLogRegister;
import org.mine.quartz.JobExcutorFactory;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.dto.MonitorDto;
import org.mine.quartz.run.JobTaskCallable;
import org.mine.quartz.run.task.TaskRecodeLogLogic;
import org.mine.quartz.trigger.ExcutorTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* 定时JOB记录日志执行逻辑
* @filename: JobRecodeLogLogic
* @author wntl
* @date 2020/8/13 17:28
* @version v1.0
*/
@Component
public class JobRecodeLogLogic extends JobCall {
    private static final Logger logger = LoggerFactory.getLogger(JobTaskCallable.class);
    private static final int AVAIL_CPU = Runtime.getRuntime().availableProcessors();
    private static final int THREADMINLENGTH = AVAIL_CPU << 2;
    private static final int THREADMAXLENGTH = AVAIL_CPU << 3;
    /**
     * 当前线程内执行任务DTO
     */
    private ExecuteTaskDto dto;
    /**
     * 当前执行线程内步骤内容队列--记录当前Step的执行信息
     */
    private LinkedList<InnerStepNode> linkedList = new LinkedList<>();
    /**
     * 当前执行线程内步骤ID队列--记录当前Step在日志表中的ID, 唯一标志
     */
    private LinkedList<String> linkedIDList = new LinkedList<>();
    /**
     * 执行线程
     */
    private Thread threadExecutor = null;
    /**
     * 通知线程
     */
    private Thread threadNotify = null;
    /**
     * 通知队列
     */
    private static ConcurrentLinkedQueue<MonitorDto> linkedQueue = new ConcurrentLinkedQueue<>();
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    /**
     * 作业ID和历史ID关联缓存-<任务ID, Step执行唯一ID>
     */
    private static Map<String, LinkedList<String>> linkIdCache = new ConcurrentHashMap<>(1 << 10);
    /**
     * 执行中的步骤缓存-<任务ID, Step执行信息>
     */
    private static Map<String, LinkedList<InnerStepNode>> stepsCache = new ConcurrentHashMap<>(1 << 10);
    /**
     * 异步线程执行结果
     */
    private static Map<String, List<FutureTask<CallableResultDto>>> tasksResult = new ConcurrentHashMap<>(1 << 12);
    /**
     * step执行线程池
     */
    private static final ThreadPoolExecutor pool;
    static {
        pool = new ThreadPoolExecutor(THREADMINLENGTH, THREADMAXLENGTH, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>((THREADMINLENGTH + THREADMAXLENGTH) / 2), new JobExcutorFactory.CustomThreadFactory("task-call-"));
    }

    public JobRecodeLogLogic() {}

    public JobRecodeLogLogic(ExecuteTaskDto dto) {
        this.dto = dto;
    }

    /**
    * 执行逻辑
    * @return: void
    * @Author: wntl
    * @Date: 2020/8/13
    */
    @Override
    public void run() {
        BatchTimingStepLogRegister stepLogRegister = null;
        try {
            //保存当前执行的JOB_ID
            final String jobID = dto.getJobId();
            List<String> executeStepIds = GitContext.queryForSingleFieldList(
                    "SELECT EXECUTE_STEP_ID FROM BATCH_JOB_EXECUTE WHERE EXECUTE_JOB_ID = ? ORDER BY EXECUTE_STEP_NUM",
                    new Object[]{jobID}, String.class);
            if (CommonUtils.isEmpty(executeStepIds)) {
                logger.error("The current job[{}] does not exists steps!!!!!", jobID);
            } else {
                String executionInstance = "";
                try {
                    for (String executeStepId : executeStepIds) {
                        BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(executeStepId, true);
                        String stepID = stepDefinition.getStepId();
                        MDCCache.set(stepID, stepDefinition.getStepLogMdcValue());
                        CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
                        final String executionID = GitContext.getBean(BatchConfCustomDao.class).getBatchSequence("sequence_step_execution");
                        GitContext.doIndependentTransActionControl(new BatchOperator<Object, BatchStepDefinition>() {
                            @Override
                            public Object call(BatchStepDefinition input) {
                                BatchStepLog stepLog = new BatchStepLog();
                                stepLog.setExecutionId(executionID);
                                stepLog.setExecutionInstance(dto.getExecutionInstance());
                                stepLog.setStepId(input.getStepId());
                                stepLog.setStepName(input.getStepName());
                                stepLog.setAssociateJobId(jobID);
                                stepLog.setStepStatus(JobExcutorEnum.NEW.getValue());
                                stepLog.setCreateDate(CommonUtils.currentDate(new Date()));
                                stepLog.setValidStatus(JobContanst.VALID_STATUS_0);
                                stepLog.setTimeStamp(System.nanoTime());
                                return GitContext.getBean(BatchStepLogDao.class).insertOne(stepLog);
                            }
                        }, stepDefinition);

                        BaseServiceTaskletExecutor executor = ExcutorTrigger.getExecutor(stepDefinition.getStepActuator());
                        linkedList.add(new InnerStepNode(executor, executor.grouping(stepExecutionInfo(stepDefinition, dto))));
                        linkedIDList.add(executionID);
                    }

                    executionInstance = dto.getExecutionInstance();
                    //将当前执行job的历史ID放入缓存中,防止quartz中job的重复执行而导致的缓存脏数据.
                    stepsCache.put(executionInstance, linkedList);
                    linkIdCache.put(executionInstance, linkedIDList);

                    GitContext.doIndependentTransActionControl((input) -> {
                        BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, true);
                        jobLog.setStartTime(CommonUtils.currentTime(new Date()));
                        jobLog.setJobStatus(JobExcutorEnum.COMPLETING.getValue());
                        jobLog.setTimeStamp(System.nanoTime());
                        return GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
                    }, executionInstance);
                } catch(Exception e) {
                    logger.error(GitWebException.getStackTrace(e));

                    BatchJobLog job_log = GitContext.doIndependentTransActionControl((input) -> {
                        GitContext.getBean(BatchConfCustomDao.class).updateStepLogInUnknownFailed(input,
                                JobExcutorEnum.FAILED.getValue(), "an error occurred while deal step.");

                        BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, false);
                        if (jobLog != null) {
                            jobLog.setJobStatus(JobExcutorEnum.FAILED.getValue());
                            jobLog.setEndTime(CommonUtils.currentTime(new Date()));
                            jobLog.setTimeStamp(System.nanoTime());
                            jobLog.setJobErrmsg("An unknown error occurred.");
                            GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
                        }
                        return jobLog;
                    }, executionInstance);
                    synchronized (stepsCache) {
                        if(stepsCache.containsKey(executionInstance)) stepsCache.remove(executionInstance);
                    }
                    synchronized (linkIdCache) {
                        if(linkIdCache.containsKey(executionInstance)) linkIdCache.remove(executionInstance);
                    }
                    //TODO 通知TASK任务已经完成,当前JOB结束运行. executionInstance
                    notifyTask(job_log);

                    throw GitWebException.GIT1001("处理step时出错....");
                }

                //先执行第一步
                poolExecute(executionInstance, null);
            }
        } catch(Throwable a){
            logger.error(GitWebException.getStackTrace(a));
            throw GitWebException.GIT1001("in " + this.getClass().getName() + ", an error occurred while executing step.");
        }
    }

    /**
     * 公共阈值
     * @param stepDefinition
     * @return
     */
    @Override
    public Map<String, Object> stepExecutionInfo(BatchStepDefinition stepDefinition, ExecuteTaskDto taskDto) {
        Map<String, Object> map = super.stepExecutionInfo(stepDefinition, taskDto);
        map.put("operator", "Logging");
        return map;
    }

    /**
     * 执行下一个step
     * @param executionInstance
     * @param deliverValueMap
     */
    @Override
    void poolExecute(String executionInstance, Map<String, Object> deliverValueMap) {
        logger.debug("JobRecodeLogLogic.poolExecute({}) begin>>>>>>>>>>>>>>>>>>", executionInstance);
        try {
            InnerStepNode node = null;
            synchronized (stepsCache) {
                node = stepsCache.get(executionInstance).removeFirst();
            }
            if(node != null){
                //更新下一步的起始时间及执行状态.
                GitContext.doIndependentTransActionControl(new BatchOperator<Integer, String>() {

                    @Override
                    public Integer call(String input) {
                        BatchStepLogDao dao = GitContext.getBean(BatchStepLogDao.class);
                        BatchStepLog stepLog = dao.selectOne1R(linkIdCache.get(input).getFirst(), false);
                        if (stepLog != null) {
                            stepLog.setStartTime(CommonUtils.currentTime(new Date()));
                            stepLog.setStepStatus(JobExcutorEnum.COMPLETING.getValue());
                            stepLog.setTimeStamp(System.nanoTime());
                            dao.updateOne1(stepLog);
                        }
                        return null;
                    }
                }, executionInstance);

                List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
                for(Map<String, Object> obj : node.getList()){
                    if(CommonUtils.isNotEmpty(deliverValueMap)) obj.putAll(deliverValueMap);
                    FutureTask<CallableResultDto> futureTask = new FutureTask<>(
                            new InnerThread(node.getExecutor(), obj));
                    pool.execute(futureTask);
                    futureTasks.add(futureTask);
                }
                tasksResult.put(executionInstance, futureTasks);
            } else {
                synchronized (linkIdCache) {
                    if(linkIdCache.containsKey(executionInstance)) linkIdCache.remove(executionInstance);
                }
            }
        } catch (Exception e) {
            //将缓存中的数据清除, 往后步骤不执行.
            synchronized (stepsCache) {
                stepsCache.put(executionInstance, new LinkedList<>());
            }
            BatchJobLog job_log = GitContext.doIndependentTransActionControl((input) -> {
                BatchStepLogDao dao = GitContext.getBean(BatchStepLogDao.class);
                BatchStepLog stepLog = dao.selectOne1R(linkIdCache.get(input).getFirst(), false);
                if (stepLog != null && CommonUtils.equals(stepLog.getStepStatus(), JobExcutorEnum.COMPLETING.getValue())) {
                    stepLog.setStepStatus(JobExcutorEnum.FAILED.getValue());
                    stepLog.setStepErrmsg(e instanceof MineException ? ((MineException)e).getError_msg() : e.getMessage());
                    stepLog.setTimeStamp(System.nanoTime());
                    dao.updateOne1(stepLog);
                }

                GitContext.getBean(BatchConfCustomDao.class).updateStepLogInUnknownFailed(input,
                        JobExcutorEnum.FAILED.getValue(), "an error occurred while executing step[" + (stepLog != null ? stepLog.getStepId() : "Unknown") + "]");
                BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, false);
                if (jobLog != null) {
                    jobLog.setJobStatus(JobExcutorEnum.FAILED.getValue());
                    jobLog.setEndTime(CommonUtils.currentTime(new Date()));
                    jobLog.setTimeStamp(System.nanoTime());
                    jobLog.setJobErrmsg("An unknown error occurred.");
                    GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
                }
                return jobLog;
            }, executionInstance);
            //TODO 通知TASK任务已经完成,当前JOB结束运行. executionInstance
            notifyTask(job_log);
            throw e;
        }
        logger.debug("JobRecodeLogLogic.poolExecute({}) end<<<<<<<<<<<<<<<<<", executionInstance);
    }

    /**
     * 取消运行中的job, 需执行器中响应中断. 当执行器中使用sleep方法时, 在捕获异常之后若需要执行其他逻辑时, 需要再执行一次 interrupt()方法. 由于sleep会清除中断状态
     * 需考虑分段事务提交
     * @param executionID
     * @return
     */
    public boolean cancel(String executionID) {
        boolean res = false;
        List<FutureTask<CallableResultDto>> tasks = tasksResult.get(executionID);
        if (tasks != null && tasks.size() > 0) {
            for (FutureTask<CallableResultDto> task : tasks) {
                if (!task.isDone()) {
                    if (res = task.cancel(true)) {
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
    * 通知TASK线程任务执行完毕.
    * @param job_log
    * @return: void
    * @Author: wntl
    * @Date: 2020/8/25
    */
    public static void notifyTask(BatchJobLog job_log) {
        if (CommonUtils.isNotEmpty(job_log.getAssociateTaskInstance())) {
            MonitorDto monitorDto = new MonitorDto();
            monitorDto.setJobID(job_log.getJobId());
            monitorDto.setJobExecutionInstance(job_log.getExecutionInstance());
            monitorDto.setTaskExecutionInstance(job_log.getAssociateTaskInstance());
            monitorDto.setConcurrencyNum(job_log.getConcurrencyNum());
            monitorDto.setJobStatus(job_log.getJobStatus());
            if (!TaskRecodeLogLogic.notify(monitorDto)) {
                logger.warn("接收队列数已满, 使用异步方式推送信息.");
                //异步发送
                try {
                    lock.lock();
                    linkedQueue.offer(monitorDto);
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(threadExecutor == null){
            threadExecutor = new Thread(new Runnable() {

                @Override
                public void run() {
                    while(true){
                        synchronized (tasksResult) {
                            if(tasksResult.size() > 0){
                                Iterator<String> iterator = tasksResult.keySet().iterator();
                                while(iterator.hasNext()){
                                    String executionInstance = iterator.next();
                                    try {
                                        if (!stepsCache.containsKey(executionInstance) || !linkIdCache.containsKey(executionInstance)) {
                                            synchronized (stepsCache) {
                                                stepsCache.remove(executionInstance);
                                            }
                                            synchronized (linkIdCache) {
                                                linkIdCache.remove(executionInstance);
                                            }
                                            iterator.remove();
                                            break;
                                        }
                                        //当前job内的所有step是否全部执行完毕
                                        boolean allSuccess = true;
                                        //当前job是否被取消
                                        boolean isCancel = false;
                                        //step执行是否成功
                                        boolean taskResult = true;
                                        //是否更改JOB历史状态
                                        boolean isChange = false;
                                        //执行下一个step
                                        boolean nextStep = false;
                                        String message = "";
                                        List<FutureTask<CallableResultDto>> tasks = tasksResult.get(executionInstance);
                                        LinkedList<String> executionIDs = linkIdCache.get(executionInstance);
                                        //当前是为了防止任务执行过快而此时任务初始化过程还未结束,导致更新历史信息时未取到数据不更新历史状态.
                                        String executionID = executionIDs.getFirst();
                                        BatchStepLog stepLog = AsyncCache.get(executionID);
                                        if(stepLog != null){
                                            Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
                                            Map<String, Object> deliverValueMap = new HashMap<>();
                                            //当存在任务并且当前任务为完成状态时.
                                            while (taskIterator.hasNext() && allSuccess) {
                                                FutureTask<CallableResultDto> task = taskIterator.next();
                                                if (task.isDone()) {
                                                    if (task.isCancelled()) {
                                                        isCancel = true;
                                                        break;
                                                    } else {
                                                        try {
                                                            CallableResultDto result = task.get(1, TimeUnit.SECONDS);
                                                            logger.info("get callable result : {}", result.toString());
                                                            taskResult = result.isResult();
                                                            message = result.getMseeage();
                                                            CommonUtils.putAll(deliverValueMap, result.getMap());
                                                            //当子任务执行完毕, 则从链表中去除.
                                                            taskIterator.remove();
                                                        } catch(InterruptedException | ExecutionException | TimeoutException e) {
                                                            logger.error("recode log thread get result error");
                                                            message = e.getMessage();
                                                            taskResult = false;
                                                        }
                                                    }
                                                } else {
                                                    allSuccess = false;
                                                }
                                            }
                                            String status = "";
                                            if (allSuccess) {
                                                if (isCancel) {
                                                    status = JobExcutorEnum.CANCEL.getValue();
                                                    synchronized (stepsCache) {
                                                        stepsCache.remove(executionInstance);
                                                    }
                                                    synchronized (linkIdCache) {
                                                        LinkedList<String> steps = linkIdCache.remove(executionInstance);
                                                        logger.warn("Cancelled steps : {}", CommonUtils.toString(steps));
                                                    }
                                                    isChange = true;
                                                    message = "The current step have been cancelled.";
                                                    updateTimingStepLogStatus(executionInstance, status, "The previous step have been cancelled.");
                                                } else {
                                                    if(taskResult){
                                                        status = JobExcutorEnum.SUCCESS.getValue();
                                                        synchronized (stepsCache) {
                                                            if(stepsCache.size() > 0){
                                                                LinkedList<InnerStepNode> nodes = stepsCache.get(executionInstance);
                                                                //当存在下一步骤时,执行下一个步骤内容
                                                                if(nodes.size() > 0){
                                                                    //检查缓存数据正确性
                                                                    if((executionIDs.size() - nodes.size()) == 1){
                                                                        nextStep = true;
                                                                    } else {
                                                                        logger.error("The current execution data is inconsistent, please check!!! stepHistoryIds: {}, nodes: {}",
                                                                                CommonUtils.toString(executionIDs), JobRecodeLogLogic.toString(nodes));
                                                                    }
                                                                } else {
                                                                    //表示当前job内的所有step执行完毕
                                                                    isChange = true;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        //当job内某个step失败时,将job对应historyID缓存清除
                                                        status = JobExcutorEnum.FAILED.getValue();
                                                        synchronized (stepsCache) {
                                                            stepsCache.remove(executionInstance);
                                                        }
                                                        synchronized (linkIdCache) {
                                                            LinkedList<String> steps = linkIdCache.remove(executionInstance);
                                                            logger.warn("Failed steps : {}", CommonUtils.toString(steps));
                                                        }
                                                        isChange = true;
                                                        updateTimingStepLogStatus(executionInstance, status, "The previous step have been failed.");
                                                    }
                                                }
                                                stepLog.setStepStatus(status);
                                                stepLog.setStepErrmsg(message);
                                                GitContext.doIndependentTransActionControl(new BatchOperator<Integer, BatchStepLog>() {
                                                    @Override
                                                    public Integer call(BatchStepLog log) {
                                                        log.setEndTime(CommonUtils.currentTime(new Date()));
                                                        log.setTimeStamp(System.nanoTime());
                                                        return GitContext.getBean(BatchStepLogDao.class).updateOne1(log);
                                                    }
                                                }, stepLog);
                                                if(nextStep){
                                                    try{
                                                        iterator.remove();
                                                        //当step成功时,将对应的步骤ID清除
                                                        synchronized (linkIdCache) {
                                                            executionIDs.removeFirst();
                                                        }
                                                        poolExecute(executionInstance, deliverValueMap);
                                                    } catch(NoSuchElementException e){
                                                        logger.error("Failed to clear elements in the list, error message : {}",
                                                                GitWebException.getStackTrace(e));
                                                    }
                                                }
                                                if(isChange){
                                                    //更新JOB执行状态
                                                    BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(executionInstance, false);
                                                    if(jobLog != null){
                                                        jobLog.setJobStatus(status);
                                                        jobLog.setJobErrmsg(message);
                                                        GitContext.doIndependentTransActionControl((log) -> {
                                                            log.setEndTime(CommonUtils.currentTime(new Date()));
                                                            log.setTimeStamp(System.nanoTime());
                                                            return GitContext.getBean(BatchJobLogDao.class).updateOne1(log);
                                                        }, jobLog);
                                                    }
//                                                notifyJob(status, message, executionInstance);
                                                    iterator.remove();
                                                    AsyncCache.remove(executionID);
                                                    //TODO 通知TASK任务已经完成,当前JOB结束运行. executionInstance
                                                    notifyTask(jobLog);
                                                }
                                            }
                                        }
                                    } catch(Throwable e){
                                        logger.error(MineException.getStackTrace(e));
                                        logger.error("[EXECUTION] The current instance {} has been occurred exception!!!!!", executionInstance);
                                    }
                                }
                            } else {
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    logger.error("[EXECUTION] The sleep thread is been interrupted.");
                                }
                            }
                        }
                    }
                }
            }, "EXECUTION_THREAD");
            threadExecutor.start();
        }


        if (threadNotify == null) {
            threadNotify = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            lock.lock();
                            if(linkedQueue.size() > 0){
                                TaskRecodeLogLogic.notifyWaiting(linkedQueue.poll());
                            } else {
                                condition.await();
                            }
                        } catch (InterruptedException e) {

                        } finally {
                            lock.unlock();
                        }
//                        try {
//                            if(linkedQueue.size() > 0){
//                                synchronized (linkedQueue) {
//                                    TaskRecodeLogLogic.notifyWaiting(linkedQueue.poll());
//                                }
//                            } else {
//                                TimeUnit.SECONDS.sleep(2);
//                            }
//                        } catch (InterruptedException e) {
//
//                        }
                    }
                }
            }, "NOTIFY_THREAD");
            threadNotify.start();
        }
    }

    /**
     * 当作业处理异常时,更新后续步骤状态.
     * @param executionInstance
     * @param error_message
     */
    void updateTimingStepLogStatus(final String executionInstance, final String status, final String error_message){
        GitContext.doIndependentTransActionControl((input) -> {
            GitContext.getBean(BatchConfCustomDao.class).updateStepLogInUnknownFailed(input,
                    status, error_message + "an error occurred while executing instance[" + input + "]");
            return null;
        }, executionInstance);
    }

    public static String toString(List<InnerStepNode> nodes){
        StringBuffer buffer = new StringBuffer();
        Iterator<InnerStepNode> iterator = nodes.iterator();
        while(iterator.hasNext()){
            buffer.append(iterator.next().toString());
            if(iterator.hasNext()){
                buffer.append(",");
            }
        }
        return buffer.toString();
    }
}
