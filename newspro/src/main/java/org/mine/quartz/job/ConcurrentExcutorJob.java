package org.mine.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ConcurrentExcutorJob implements Job{

	private static final Logger logger = LoggerFactory.getLogger(ConcurrentExcutorJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		MDC.put("trade", "excutor_job");
		String object = context.getJobDetail().getJobDataMap().get("test").toString();
		logger.debug("当前时间: {}" , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		logger.debug(System.currentTimeMillis() + ", >>>>> : " + object);
	}
}
