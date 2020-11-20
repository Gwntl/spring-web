package org.mine.quartz.job;

import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.BaseExecutor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 当个定时JOB执行器.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QuartzTimingJobExecutor
 * @date 2020/8/1316:44
 */
public class QuartzTimingJobExecutor implements Job {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTimingJobExecutor.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("QuartzTimingJobExecutor.execute() begin >>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Object> map = context.getJobDetail().getJobDataMap();
        ExecuteTaskDto taskDto = (ExecuteTaskDto) map.get("dto");
        BaseExecutor.baseExecutor(taskDto);
        logger.debug("QuartzTimingJobExecutor.execute() end <<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
