package org.mine.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:  异步定时队列执行器.
 * @ClassName: AsyncQueueJob
 * @author: wntl
 * @date: 2020年6月9日 下午4:11:08
 */
public class AsyncQueueJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(AsyncQueueJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("AsyncQueueJob begin >>>>>>>>>");
		//TODO 定时队列执行逻辑
		
		logger.debug("AsyncQueueJob end >>>>>>>>>");
		return;
	}
}
