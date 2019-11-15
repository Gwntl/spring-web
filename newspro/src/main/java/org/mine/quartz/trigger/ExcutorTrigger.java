package org.mine.quartz.trigger;

import java.util.List;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.BatchRunQueue;
import org.mine.quartz.ExcutorBase;
import org.mine.quartz.job.ConcurrentExcutorJob;
import org.mine.service.BatchRunQueueService;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ExcutorTrigger extends CronTriggerImpl implements InitializingBean{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1379631809538539447L;
	
	private static final Logger logger = LoggerFactory.getLogger(ExcutorTrigger.class);
	
	@Autowired
	private BatchRunQueueService queueService;
	

	/* 
	 * 此处将quartz需要的定时作业配置在数据库中, 每次加载时,从数据库中加载定时作业.
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Scheduler scheduler = ExcutorBase.getSchedulerFactoryBean().getScheduler();
//		List<BatchRunQueue> queues = queueService.selectAllQueues();
		List<BatchRunQueue> queues = null;
		logger.debug(">>>>>>> : {}", CommonUtils.isNotEmpty(queues) ? CommonUtils.toString(queues) : "");
		BatchRunQueue queue = null;
		if(CommonUtils.isNotEmpty(queues)){
			for(int i = 0, size = queues.size(); i < size; i ++){
				queue = queues.get(i);
				System.out.println(">>>>>>>>> : " + queue.toString());
				JobDetailImpl detail = new JobDetailImpl();
				detail.setName(ApltContanst.DEFAULT_JOB_NAME + queue.getQueueId());
				detail.setKey(new JobKey(detail.getName(), ApltContanst.DEFAULT_JOB_GROUP));
				detail.setJobClass(ConcurrentExcutorJob.class);
				JobDataMap detailDataMap = new JobDataMap();
//				detail.setJobDataMap((JobDataMap) CommonUtils.initMapValue(detailDataMap, queue.getQueueInitValue()));
				//确保job执行完后不从jobstore中移除
				detail.setDurability(true);
				
				ExcutorTrigger trigger = new ExcutorTrigger();
				trigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + queue.getQueueId());
				trigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
				trigger.setCronExpression(queue.getQueueCrontrigger());
				if(CommonUtils.isNotEmpty(queue.getQueueStartTime())){
					trigger.setStartTime(CommonUtils.stringToDate(queue.getQueueStartTime()));
				}
				if(CommonUtils.isNotEmpty(queue.getQueueEndTime())){
					trigger.setEndTime(CommonUtils.stringToDate(queue.getQueueEndTime()));
				}
				/*
				 * Spring根据配置文件配置trigger时,可以将job配置在trigger的datamap中.
				 * 当注册时若发现未配置Job信息, Spring会从Map中自动获取jobDetail的值,注册进Schedule中.
				 * */
//				getJobDataMap().put("jobDetail", detail);
				scheduler.scheduleJob(detail, trigger);
			}
		}
	}
}
