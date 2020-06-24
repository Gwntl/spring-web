package com.quartz.traditional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.util.CommonUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class QuatrzSpringTest {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/quartz/quartz_spring_test.xml");
		
//		String[] strs = context.getBeanNamesForType(SchedulerFactoryBean.class);
//		System.out.println(CommonUtils.toString(strs));
//		
//		Map<String, SchedulerFactoryBean> map = context.getBeansOfType(SchedulerFactoryBean.class);
//		System.out.println(CommonUtils.toString(map));
		
//		SchedulerFactoryBean scheduler = (SchedulerFactoryBean) context.getBean("&quartzSch_bean");
//		SchedulerFactoryBean scheduler_1 = (SchedulerFactoryBean) context.getBean("&quartzSch_bean_1");
		
//		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("TRIGGER_DEFAULT_1", "TRIGGER_DEFAULT_GROUP")).withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ? *")
//				.withMisfireHandlingInstructionDoNothing()).build();
//		
//		JobDataMap jobDataMap = new JobDataMap();
//		jobDataMap.put("jobDetail", "jobDetail");
//		JobDetail jobDetail = JobBuilder.newJob(InnerJob.class).withIdentity(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP")).usingJobData(jobDataMap).build();
//		
//		
//		scheduler.getScheduler().scheduleJob(jobDetail, cronTrigger);
//		jobDetail.getJobDataMap().put("num", "22222");
//		CronTrigger cronTrigger1 = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("TRIGGER_DEFAULT_1", "TRIGGER_DEFAULT_GROUP"))
//				.withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ? *")).build();
//		scheduler_1.getScheduler().scheduleJob(jobDetail, cronTrigger1);
//		
//		scheduler.start();
//		scheduler_1.start();
//		TimeUnit.SECONDS.sleep(4);
//		scheduler.getScheduler().pauseJob(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP"));
//		System.out.println("暂停成功......");
//		scheduler_1.getScheduler().pauseJob(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP"));
//		System.out.println("22222暂停成功......");
//		TimeUnit.SECONDS.sleep(4);
//		
//		scheduler.getScheduler().resumeJob(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP"));
//		System.out.println("重启成功......");
//		scheduler_1.getScheduler().resumeJob(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP"));
//		System.out.println("22222重启成功......");
//		
//		TimeUnit.SECONDS.sleep(4);
//		scheduler_1.getScheduler().deleteJob(new JobKey("JOB_DEFAULT_1", "JOB_DEFAULT_GROUP"));
//		System.out.println("删除完毕");
//		TimeUnit.SECONDS.sleep(6);
//		
//		scheduler.getScheduler().shutdown();
//		scheduler_1.getScheduler().shutdown();
//		System.out.println("执行完毕......");
	}
	
	public static class InnerJob implements Job {
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			if(context.getJobDetail().getJobDataMap().get("num") != null){
				System.out.println(context.getJobDetail().getJobDataMap().get("num"));
			}
			System.out.println(context.getJobDetail().getJobDataMap().get("jobDetail"));
			System.out.println(System.currentTimeMillis() + "----------------------");
		}
	}
}
