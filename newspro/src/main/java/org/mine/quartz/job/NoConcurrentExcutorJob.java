package org.mine.quartz.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//disallowconcurrent不允许同一个JobDetail定义的作业并发执行.
@DisallowConcurrentExecution
public class NoConcurrentExcutorJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 50, 10000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30));
	}
}
