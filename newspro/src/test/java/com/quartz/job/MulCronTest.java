package com.quartz.job;

import java.text.ParseException;

import org.mine.aplt.util.CommonUtils;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class MulCronTest {

	public static void main(String[] args) {
//		test_1();
		test_0();
	}
	
	public static void test_0(){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			Inner inner = new Inner();
			inner.setName("1");
			JobDataMap dataMap = new JobDataMap();
			dataMap.put("ONE", inner);
			JobDetail detail = JobBuilder.newJob(MulCronJob.class).withIdentity("TEST_JOB_" + 1L, "TEST_GROUP").usingJobData(dataMap).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TEST_TRIGGER_" + 1L, "TEST_TRIGGER").withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ? *")).build();
			scheduler.scheduleJob(detail, trigger);
			
			JobDataMap dataMap1 = new JobDataMap();
			inner.setName("2");
			dataMap1.put("ONE", inner);
			JobDetail detail1 = JobBuilder.newJob(MulCronJob.class).withIdentity("TEST_JOB_" + 2L, "TEST_GROUP").usingJobData(dataMap1).build();
			Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("TEST_TRIGGER_" + 2L, "TEST_TRIGGER").withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ? *")).build();
			scheduler.scheduleJob(detail1, trigger1);
			
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void test_1(){
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetailImpl detailImpl = new JobDetailImpl();
			detailImpl.setName("mulCron");
			detailImpl.setGroup("DEFAULT_MUL");
			detailImpl.setJobClass(MulCronJob.class);
			detailImpl.setDurability(true);
			
			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setName("mulTrigger");
			trigger.setGroup("DEFAULT_NUL_TRIGGER");
//			trigger.setJobKey(new JobKey(detailImpl.getName(), detailImpl.getGroup()));
			trigger.setCronExpression("0/40 0/2 * * * ?");
			
			scheduler.scheduleJob(detailImpl, trigger);
			
			CronTriggerImpl trigger1 = new CronTriggerImpl();
			trigger1.setName("mulTrigger_1");
			trigger1.setGroup("DEFAULT_NUL_TRIGGER");
			trigger1.setJobKey(new JobKey(detailImpl.getName(), detailImpl.getGroup()));
			trigger1.setCronExpression("20 1/2 * * * ?");
			
			scheduler.scheduleJob(trigger1);
			
			scheduler.start();
			//没40s输出一次 12:00:00, 12:00:40, 12:01:20, 12:02:00, 12:02:40, 12:03:20
			//0/40 0/2 * * * ?, 20 1/2 * * * ?
			
			System.out.println(CommonUtils.toString(scheduler.getTriggersOfJob(new JobKey(detailImpl.getName(), detailImpl.getGroup()))));
			
		} catch (SchedulerException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	static class Inner {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
}
