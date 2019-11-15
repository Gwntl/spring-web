package com.quartz.traditional;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class QuartzTest {

	public static void main(String[] args) {
		try {
//			SchedulerFactory factory = new StdSchedulerFactory();
//			Scheduler scheduler = factory.getScheduler();
//			org.slf4j.MDC.put("trade", "TEST");
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
//			System.out.println(scheduler.getSchedulerName() + ", " + scheduler.getSchedulerInstanceId());
//			
//			JobDetail jobDetail = JobBuilder.newJob(PrintJob.class)
//					.withIdentity("jobDetail", "Test")
//					.usingJobData("map", "2").build();
//			
//			Trigger trigger = TriggerBuilder.newTrigger().startNow()
//					.withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?")).build();
//			
//			scheduler.scheduleJob(jobDetail, trigger);
			
			
			scheduler.start();
//			System.out.println("--------scheduler start ! ------------");
//			
//			Thread.sleep(10000);
			
			Date date = new Date(System.currentTimeMillis() + 1000L);
			
			Date endDate = new Date(date.getTime() + 3000L);
			
			System.out.println(date + ", " + endDate);
			
			//在规定时间内执行cron函数
			CronTrigger trigger1 = (CronTrigger) TriggerBuilder.newTrigger().startNow()
					.startAt(date)
					.withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?"))
					.endAt(endDate).build();
			
			JobDetail jobDetai2 = JobBuilder.newJob(PrintJob.class)
					.withIdentity("jobDetail_1", "Test")
					.usingJobData("map", "5").build();
			
//			JobListener listener = new SimpleJobListener();
			
			scheduler.scheduleJob(jobDetai2, trigger1);
			
			Thread.sleep(8000);
			
		    scheduler.shutdown();
		    System.out.println("--------scheduler shutdown ! ------------");
		    
		    
		    
//		    Scheduler scheduler2 = StdSchedulerFactory.getDefaultScheduler();
//		    
//		    JobDetailImpl impl = new JobDetailImpl();
//		    impl.setName("test");
//		    impl.setKey(new JobKey("print", "pringGroup"));
//		    impl.setJobClass(PrintJob.class);
//		    impl.getJobDataMap().put("jobDetailImpl", "impl");
//		    impl.setDurability(true);
//		    
//		    CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
//		    cronTriggerImpl.setName("trigger");
//		    cronTriggerImpl.setGroup("triggerGroup");
//		    cronTriggerImpl.setKey(new TriggerKey(cronTriggerImpl.getName(), cronTriggerImpl.getGroup()));
//		    cronTriggerImpl.setJobKey(impl.getKey());
//		    cronTriggerImpl.setJobName(impl.getName());
//		    cronTriggerImpl.setJobGroup(impl.getGroup());
//		    cronTriggerImpl.setCronExpression("*/2 * * * * ?");
//		    JobDataMap dataMap = new JobDataMap();
//		    dataMap.put("jobDetail", impl);
//		    cronTriggerImpl.setJobDataMap(dataMap);
//		    
//		    scheduler2.scheduleJob(cronTriggerImpl);
//		    scheduler2.start();
		    
//		    Thread.sleep(10000);
//		    scheduler2.shutdown();
			
		} catch (SchedulerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

