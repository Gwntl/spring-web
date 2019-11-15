package com.quartz.traditional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class SimpleJobListener implements JobListener{

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String jobName = ((JobListener) context.getJobDetail()).getName();   
        System.out.println(jobName + " is about to be executed");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		String jobName = ((JobListener) context.getJobDetail()).getName();   
        System.out.println(jobName + " was vetoed and not executed()"); 
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobName = ((JobListener) context.getJobDetail()).getName();   
        System.out.println(jobName + " was executed");
	}

}
