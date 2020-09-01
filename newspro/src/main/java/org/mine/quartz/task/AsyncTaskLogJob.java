package org.mine.quartz.task;

import org.mine.quartz.run.JobTaskLogic;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @filename AsyncTaskJob.java
 * @author wzaUsers
 * @date 2019年11月26日下午8:13:01
 * @version v1.0
 */
public class AsyncTaskLogJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(AsyncTaskLogJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("AsyncTaskJob begin >>>>>>>>>>");
		new JobTaskLogic().logicHasLog(context.getJobDetail().getJobDataMap());
		logger.debug("AsyncTaskJob end >>>>>>>>>>");
	}
}
