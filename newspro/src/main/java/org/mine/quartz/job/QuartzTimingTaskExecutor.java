package org.mine.quartz.job;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskLogDao;
import org.mine.model.BatchTaskLog;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.BaseExecutor;
import org.mine.quartz.run.task.TaskRecodeLogLogic;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 定时任务执行器.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QuartzTimingTaskExecutor
 * @date 2020/8/1317:20
 */
public class QuartzTimingTaskExecutor implements Job{
    private static final Logger logger = LoggerFactory.getLogger(QuartzTimingTaskExecutor.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("QuartzTimingTaskExecutor.execute() begin >>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Object> map = context.getJobDetail().getJobDataMap();
        ExecuteTaskDto taskDto = (ExecuteTaskDto) map.get("dto");
        taskDto.setTaskExecutionInstance(BaseExecutor.completeExecutionID("sequence_task_instance", JobConstant.EXECUTOR_TASK_PREFIX));
        GitContext.doIndependentTransActionControl((input) -> {
            BatchTaskLog taskLog = new BatchTaskLog();
            taskLog.setExecutionInstance(input.getTaskExecutionInstance());
            taskLog.setTaskId(input.getTaskId());
            taskLog.setTaskName(input.getTaskName());
            taskLog.setAssociateQueueId(input.getQueueId());
            taskLog.setStartTime(CommonUtils.currentTime(new Date()));
            taskLog.setCreateDate(CommonUtils.currentDate(new Date()));
            taskLog.setTaskStatus(JobExecutorEnum.NEW.getValue());
            taskLog.setTimeStamp(System.nanoTime());
            taskLog.setValidStatus(JobConstant.VALID_STATUS_0);
            return GitContext.getBean(BatchTaskLogDao.class).insertOne(taskLog);
        }, taskDto);
        TaskRecodeLogLogic.taskLogic(taskDto);

        logger.debug("QuartzTimingTaskExecutor.execute() end >>>>>>>>>>>>>>>>>>>>>>");
    }
}
