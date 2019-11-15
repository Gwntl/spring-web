package org.mine.quartz.schduler;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.mine.quartz.ExcutorBase;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoScheduler{
	
	private static final Logger logger = LoggerFactory.getLogger(AutoScheduler.class);
	
	/**
	 * 容器启动中,设置定时任务.一旦设置之后下次启动容器会自动注册.
	 * @param queueId
	 * @param curr
	 * @param cronExpression
	 * @param startTime
	 * @param endTime
	 */
	public void addScheduleJob(String queueId, String curr, String cronExpression, String startTime, String endTime){
		try {
			
			//先插表.
			
			
			JobDetailImpl detailImpl = new JobDetailImpl();
			detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + queueId);
			detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
			detailImpl.setJobClass(ExcutorBase.getScheduleJobClass(curr));
			
			CronTriggerImpl triggerImpl = new CronTriggerImpl();
			triggerImpl.setName(ApltContanst.DEFAULT_TRIGGER_NAME + queueId);
			triggerImpl.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			triggerImpl.setCronExpression(cronExpression);
			if(CommonUtils.isNotEmpty(startTime)){
				triggerImpl.setStartTime(CommonUtils.stringToDate(startTime));
			}
			if(CommonUtils.isNotEmpty(endTime)){
				triggerImpl.setStartTime(CommonUtils.stringToDate(endTime));
			}
			
			ExcutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, triggerImpl);
			
		} catch (Exception e) {
			logger.error("设置定时任务失败!!!! 错误信息: {}", MineException.getStackTrace(e));
			throw GitWebException.GIT1001("设置定时任务失败!!!!");
		}
	}
	
	/**
	 * 重新设置定时任务
	 * @param queueId
	 * @param curr
	 * @param cronExpression
	 * @param startTime
	 * @param endTime
	 */
	public void rescheduleJob(String queueId, String curr, String cronExpression, String startTime, String endTime){
		try{
			//数据的校验放在Controller中
			//更新表
			//update
			
			TriggerKey triggerKey = new TriggerKey(ApltContanst.DEFAULT_TRIGGER_NAME + queueId, ApltContanst.DEFAULT_TRIGGER_GROUP);
			Scheduler scheduler = ExcutorBase.getSchedulerFactoryBean().getScheduler();
			if(scheduler.getTrigger(triggerKey) == null){
				throw GitWebException.GIT1001("指定定时任务不存在!!!!!");
			}
			
			CronTriggerImpl newTrigger = new CronTriggerImpl();
			newTrigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + queueId);
			newTrigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			newTrigger.setCronExpression(cronExpression);
			if(CommonUtils.isNotEmpty(startTime)){
				newTrigger.setStartTime(CommonUtils.stringToDate(startTime));
			}
			if(CommonUtils.isNotEmpty(endTime)){
				newTrigger.setStartTime(CommonUtils.stringToDate(endTime));
			}
			
			scheduler.rescheduleJob(triggerKey, newTrigger);
		}catch(Exception e){
			logger.error("重新设置定时任务失败!!!! 错误信息: {}", MineException.getStackTrace(e));
			throw GitWebException.GIT1001("重新设置定时任务失败!!!!" 
			+ ((e instanceof MineException) ? ((MineException)e).getError_msg() : ""));
		}
	}
}
