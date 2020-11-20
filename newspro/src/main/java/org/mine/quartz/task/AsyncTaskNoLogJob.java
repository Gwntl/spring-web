package org.mine.quartz.task;

import org.mine.aplt.support.bean.GitContext;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.quartz.JobExecutorFactory;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.job.JobNoRecodeLogLogic;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 不需要记录日志的JOB执行器.
 * @ClassName: AsyncTaskNoLogJob
 * @author: wntl
 * @date: 2020年6月9日 下午4:09:07
 */
public class AsyncTaskNoLogJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(AsyncTaskNoLogJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("AsyncTaskNoLogJob begin>>>>>>>>>>>>>>>>>>>>");
		ExecuteTaskDto taskDto = (ExecuteTaskDto) context.getJobDetail().getJobDataMap().get("dto");
		logger.debug("input info : {}", taskDto.toString());
		if (taskDto.getJobLogFlag() != null && taskDto.getJobLogFlag() != 0) {
			logger.error("The current  jobneed logging.");
		} else {
			taskDto.setExecutionInstance(GitContext.getBean(BatchConfCustomDao.class).getBatchSequence("sequence_id"));
			JobExecutorFactory.call(new JobNoRecodeLogLogic(taskDto), taskDto.getJobLogFlag());
		}
		logger.debug("AsyncTaskNoLogJob begin>>>>>>>>>>>>>>>>>>>>");
	}
}
