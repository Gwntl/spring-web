package org.mine.quartz.job;

import org.mine.quartz.JobExcutorFactory;
import org.mine.quartz.dto.ConcurrTaskDto;
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
		ConcurrTaskDto taskDto = (ConcurrTaskDto) context.getJobDetail().getJobDataMap().get("dto");
		if (taskDto.getJobLogFlag() != null && taskDto.getJobLogFlag() != 0) {
			logger.error("The current job need logging.");
		} else {
			JobExcutorFactory.call(new Runnable() {
				@Override
				public void run() {
					taskDto.getJobId();
				}
			}, taskDto.getJobLogFlag());
		}
	}
}
