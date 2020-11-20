package com.quartz.traditional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.exception.MineException;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDeleteTest {

	private static final String JOB_NAME = "JOB_";
	private static final String JOB_GROUP = "JOB_GROUP";
	private static final String TRIGGER_NAME = "TRIGGER_";
	private static final String TRIGGER_GROUP = "TRIGGER_GROUP";
	
	public static void main(String[] args) {
		try {
			//设置加载指定的配置文件
			System.setProperty(StdSchedulerFactory.PROPERTIES_FILE, "src/test/java/com/batch/traditional/batch.properties");
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			System.clearProperty(StdSchedulerFactory.PROPERTIES_FILE);
//			StdSchedulerFactoryDecoration decoration = new StdSchedulerFactoryDecoration("TEST");
//			Scheduler scheduler2 = decoration.getScheduler();
			Map<Long, String> map = new HashMap<>();
			map.put(1L, "*/1 * * * * ? *");
			JobDataMap dataMap = new JobDataMap();
			dataMap.put("jobDetail", "jobDetail");
			JobDetail jobDetail = JobBuilder.newJob(AsyncJob.class).withIdentity(new JobKey(JOB_NAME + 1L, JOB_GROUP)).usingJobData(dataMap).build();
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(TRIGGER_NAME + 1L, TRIGGER_GROUP))
					.withSchedule(CronScheduleBuilder.cronSchedule(map.get(1L)).withMisfireHandlingInstructionDoNothing()).build();
			
			int misfireInstruction = cronTrigger.getMisfireInstruction();
	        System.out.println("当前misfire策略：" + misfireInstruction);
			
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
			
			
//			jobDetail.getJobDataMap().put("num", "22222");
//			scheduler2.scheduleJob(jobDetail, cronTrigger);
//			scheduler2.start();
			
			TimeUnit.SECONDS.sleep(4);
			
			JobKey jobKey = new JobKey(JOB_NAME + 1L, JOB_GROUP);
			try {
//				scheduler.pauseJob(jobKey);
//				System.out.println("暂停成功......");
				
//				scheduler2.pauseJob(jobKey);
//				System.out.println("22222暂停成功......");
				
				TimeUnit.SECONDS.sleep(8);
				
				scheduler.resumeJob(jobKey);
				System.out.println("重启成功......");
				
//				scheduler2.resumeJob(jobKey);
//				System.out.println("22222重启成功......");
				
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			TimeUnit.SECONDS.sleep(4);
//			scheduler.deleteJob(jobKey);
//			TimeUnit.SECONDS.sleep(4);
			if(!scheduler.isShutdown()) scheduler.shutdown();
//			if(!scheduler2.isShutdown()) scheduler2.shutdown();
			
		} catch (SchedulerException | InterruptedException e) {
			System.out.println(MineException.getStackTrace(e));
		}
	}
	
	public static class AsyncJob implements Job {

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
//			if(context.getJobDetail().getJobDataMap().get("num") != null){
//				System.out.println(context.getJobDetail().getJobDataMap().get("num"));
//			}
			System.out.println(context.getJobDetail().getJobDataMap().get("jobDetail"));
			System.out.println(System.currentTimeMillis() + "----------------------");
		}
	}
}
