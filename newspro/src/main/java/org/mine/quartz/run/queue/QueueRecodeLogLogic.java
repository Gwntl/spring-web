package org.mine.quartz.run.queue;

import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueLogDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.model.*;
import org.mine.quartz.JobExecutorFactory;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.task.TaskRecodeLogLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QueueRecodeLogLogic
 * @date 2020/9/220:43
 */
@Component
public class QueueRecodeLogLogic implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 任务执行缓存
     */
    private static Map<String, LinkedList<String>> taskMap = new ConcurrentHashMap<>(64);
    private static Thread taskResultThread = null;
    /**
     * 任务执行结果队列
     */
    private static BlockingQueue<String> taskResultQueue = new ArrayBlockingQueue<>(256);

    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(20, 50, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(512),
            new JobExecutorFactory.CustomThreadFactory("QUEUE_EXECUTOR"));

    /**
    * 队列执行逻辑
    * @param taskDto
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/21
    */
    public static void queueLogic(ExecuteTaskDto taskDto) {
        String queueID = taskDto.getQueueId();
        String queueInstance = taskDto.getQueueExecutionInstance();
        List<BatchQueueExecute> executes = GitContext.queryForList(
                "select * from batch_queue_execute where execute_queue_id = ? and valid_status = '0'",
                new Object[]{queueID}, BatchQueueExecute.class);
        BatchTaskLog taskLog = null;
        for (BatchQueueExecute execute : executes) {
            GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(execute.getExecuteTaskId(), true);
            if (taskMap.get(queueInstance) == null) {
                taskMap.put(queueInstance, new LinkedList<>());
            }
            taskMap.get(queueInstance).add(execute.getExecuteTaskId());
        }
        GitContext.doIndependentTransActionControl((input) -> {
            BatchQueueLog queueLog = GitContext.getBean(BatchQueueLogDao.class).selectOne1R(input.getQueueExecutionInstance(), true);
            queueLog.setQueueStatus(JobExecutorEnum.COMPLETING.getValue());
            queueLog.setTimeStamp(System.nanoTime());
            return GitContext.getBean(BatchQueueLogDao.class).updateOne1R(queueLog);
        }, taskDto);
        //执行任务
        synchronized (taskMap) {
            executeNextTask(taskMap.get(queueInstance).removeFirst(), taskDto);
        }
    }

    public static void executeNextTask(String taskID, ExecuteTaskDto taskDto) {
        BatchTaskDefinition definition = GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(taskID, true);
        taskDto.setTaskId(definition.getTaskId());
        taskDto.setTaskName(definition.getTaskName());
        CommonUtils.initMapValue(taskDto.getTaskInitValue(), definition.getTaskInitValue());
        TaskRecodeLogLogic.taskLogic(taskDto);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (taskResultThread == null) {
            taskResultThread = new Thread(() -> {
                while (true) {
                    try {
                        String s = taskResultQueue.take();
                        POOL_EXECUTOR.submit(new QueueWorker(s));
                    } catch (InterruptedException e) {
                    }
                }
            }, "TASK_RESULT_MONITOR");
        }
    }

    public static class QueueWorker implements Runnable {
        private String s;
        public QueueWorker(String s){
            this.s = s;
        }
        @Override
        public void run() {

        }
    }
}

