package com.quartz.traditional;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class PrintJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		String s = context.getJobDetail().getJobDataMap().get("map") + "";
		String msg = (s.equals("2"))?("1111111111111111" + s) : ("qqqqqq" + s);
		
		String t = context.getJobDetail().getJobDataMap().get("jobDetailImpl") + "";
		
		System.out.println(">>>>>>>>>>>>>>>" + t);
		System.out.println(context.getJobDetail().getJobClass().hashCode());
		System.out.println(Thread.currentThread().getName() + ", " + msg);
	}
	
}
