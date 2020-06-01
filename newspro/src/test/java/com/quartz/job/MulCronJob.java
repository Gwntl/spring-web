package com.quartz.job;

import java.util.Date;

import org.mine.aplt.util.CommonUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MulCronJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(CommonUtils.dateToString(new Date(), "HH:mm:ss") + ">>>---->>" + CommonUtils.toString(context.getJobDetail().getJobDataMap()));
	}
}
