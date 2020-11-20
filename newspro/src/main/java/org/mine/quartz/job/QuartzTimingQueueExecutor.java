package org.mine.quartz.job;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueDefinitionDao;
import org.mine.dao.BatchQueueLogDao;
import org.mine.lock.db.DBLock;
import org.mine.lock.db.DBLockInfo;
import org.mine.lock.LockWorker;
import org.mine.lock.db.QueueDBOptimisticLocking;
import org.mine.model.BatchQueueDefinition;
import org.mine.model.BatchQueueLog;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.BaseExecutor;
import org.mine.quartz.run.queue.QueueRecodeLogLogic;
import org.mine.rule.batch.BatchNameRule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QuartzTimingQueueExecutor
 * @date 2020/8/1317:20
 */
public class QuartzTimingQueueExecutor implements Job {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTimingQueueExecutor.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("QuartzTimingQueueExecutor.execute() begin >>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Object> map = context.getJobDetail().getJobDataMap();
        final ExecuteTaskDto taskDto = (ExecuteTaskDto) map.get("dto");
        final BatchQueueDefinition definition = GitContext.getBean(BatchQueueDefinitionDao.class).selectOne1R(taskDto.getQueueId(), true);
        if (CommonUtils.equals(definition.getQueueSameTimeRun(), JobConstant.QUEUE_SAME_TIME_RUN_1)) {
            DBLock lock = new QueueDBOptimisticLocking();
            lock.tryLock(definition.getQueueName(), BatchNameRule.getType(definition.getQueueName()), new LockWorker() {
                @Override
                public void worker(DBLockInfo lockInfo) {
                    applyQueueOperator(taskDto);
                }
            });
        } else if (CommonUtils.equals(definition.getQueueSameTimeRun(), JobConstant.QUEUE_SAME_TIME_RUN_0)) {
            //以数据库锁形式, 暂不支持共享锁.
            applyQueueOperator(taskDto);
        }
        logger.debug("QuartzTimingQueueExecutor.execute() end >>>>>>>>>>>>>>>>>>>>>>");
    }

    private void applyQueueOperator(ExecuteTaskDto taskDto) {
        taskDto.setQueueExecutionInstance(BaseExecutor.completeExecutionID("sequence_queue_instance", JobConstant.EXECUTOR_QUEUE_PREFIX));
        GitContext.doIndependentTransActionControl(new BatchOperator<Integer, ExecuteTaskDto>() {
            @Override
            public Integer call(ExecuteTaskDto dto) {
                BatchQueueLog queueLog = new BatchQueueLog();
                queueLog.setExecutionInstance(dto.getQueueExecutionInstance());
                queueLog.setQueueId(dto.getQueueId());
                queueLog.setQueueName(dto.getQueueName());
                queueLog.setQueueStatus(JobExecutorEnum.NEW.getValue());
                queueLog.setCreateDate(CommonUtils.currentDate(new Date()));
                queueLog.setTimeStamp(System.nanoTime());
                queueLog.setValidStatus(JobConstant.VALID_STATUS_0);
                GitContext.getBean(BatchQueueLogDao.class).insertOne(queueLog);
                return null;
            }
        }, taskDto);
        //执行
        QueueRecodeLogLogic.queueLogic(taskDto);
    }
}
