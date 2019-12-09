package org.mine.quartz.schduler;

import java.util.Date;
import java.util.regex.Pattern;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDetailConfDao;
import org.mine.dao.BatchJobGroupConfDao;
import org.mine.dao.BatchQueueConfDao;
import org.mine.dao.BatchTriggerConfDao;
import org.mine.dao.custom.BatchConfCostomDao;
import org.mine.model.BatchJobDetailConf;
import org.mine.model.BatchJobGroupConf;
import org.mine.model.BatchQueueConf;
import org.mine.model.BatchTriggerConf;
import org.mine.quartz.ExcutorBase;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AutoScheduler{
	
	private static final Logger logger = LoggerFactory.getLogger(AutoScheduler.class);
	
	@Autowired
	private BatchQueueConfDao queueConfDao;
	@Autowired
	private BatchTriggerConfDao triggerConfDao;
	@Autowired
	private BatchJobGroupConfDao jobGroupConfDao;
	@Autowired
	private BatchConfCostomDao confCostomDao;
	@Autowired
	private BatchJobDetailConfDao detailConfDao;
	
	/**
	 * 添加一个批量执行队列
	 * @param queueId
	 * @param queueName
	 * @param queueAutoFlag
	 */
	public void addBatchQueue(Long queueId, String queueName, int queueAutoFlag){
		if(queueId == null){
			throw GitWebException.GIT1002("队列ID(queueId)");
		}
		BatchQueueConf conf = new BatchQueueConf();
		conf.setQueueId(queueId);
		conf.setQueueName(queueName);
		conf.setQueueAutoFlag(queueAutoFlag);
		queueConfDao.insertOne(conf);
	}
	
	/**
	 * 增加触发器
	 * @param triggerId
	 * @param triggerName
	 * @param startTime
	 * @param endTime
	 * @param cronTrigger
	 */
	public void addTrigger(Long triggerId, String triggerName, String startTime, String endTime,
			String cronTrigger){
		if(triggerId == null || CommonUtils.isEmpty(cronTrigger)){
			throw GitWebException.GIT1002("触发器ID(triggerId)或者触发器表达式");
		}
		
		if(Pattern.matches(ApltContanst.UNDERLINEPATTERNT, startTime)
				|| Pattern.matches(ApltContanst.UNDERLINEPATTERNT, endTime)){
			throw GitWebException.GIT1001(String.format("日期格式错误[%s, %s]", startTime, endTime));
		}
		
		BatchTriggerConf triggerConf = new BatchTriggerConf();
		triggerConf.setTriggerId(triggerId);
		triggerConf.setTriggerName(triggerName);
		triggerConf.setTriggerStartTime(CommonUtils.isEmpty(startTime)? null : startTime);
		triggerConf.setTriggerEndTime(CommonUtils.isEmpty(endTime)? null : endTime);
		triggerConf.setTriggerCrontrigger(cronTrigger);
		triggerConf.setTriggerMaintenanceDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		triggerConfDao.insertOne(triggerConf);
	}
	
	/**
	 * 增加JOB作业组
	 * @param groupId
	 * @param groupName
	 * @param isSaveLog
	 * @param isConcurr
	 * @param initValue
	 * @param triggerId
	 * @param reRun
	 * @param groupNum
	 * @param queueId
	 */
	public void addBatchGroup(Long groupId, String groupName, String isSaveLog, String isConcurr, 
			String initValue, Long triggerId, String reRun, int groupNum, Long queueId){
		if(groupId == null && triggerId == null){
			throw GitWebException.GIT1002("作业组ID或者触发器ID");
		}
		BatchJobGroupConf conf = new BatchJobGroupConf();
		conf.setJobGroupId(groupId);
		conf.setJobGroupName(groupName);
		conf.setJobGroupInitValue(initValue);
		conf.setJobGroupSavelog(isSaveLog);
		conf.setJobGroupNumber(groupNum);
		conf.setJobGroupJobpath(isConcurr);
		conf.setJobGroupRerun(reRun);
		conf.setJobGroupTriggerId(triggerId);
		conf.setJobGroupMaintenanceDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		jobGroupConfDao.insertOne(conf);
		
		BatchQueueConf queueConf = queueConfDao.selectOne1R(queueId);
		queueConf.setQueueJobGroupId(conf.getJobGroupId());
		queueConfDao.updateOne1R(queueConf);
	}
	
	/**
	 * 容器启动中,设置定时任务.一旦设置之后下次启动容器会自动注册.
	 * @param queueId 队列ID
	 * @param jobDetailId
	 * @param jobDetailName
	 * @param curr 是否并发
	 * @param cronExpression 触发器
	 * @param startTime
	 * @param endTime
	 */
	public void addScheduleJob(Long queueId, Long jobDetailId, String jobDetailName, Long triggerId
			, String provider){
		try {
			BatchQueueConf queueConf = queueConfDao.selectOne1R(queueId);
			Long jobGroupId = queueConf.getQueueJobGroupId();
			BatchJobDetailConf detailConf = new BatchJobDetailConf();
			detailConf.setJobdetailId(jobDetailId == null ? CommonUtils.addZeroWithMiddle(jobGroupId, 
					confCostomDao.getJobDetailConfMaxId(jobGroupId)) : jobDetailId);
			detailConf.setJobdetailName(CommonUtils.isEmpty(jobDetailName) ? detailConf.getJobdetailId() 
					+ "- DEFAULT" : jobDetailName);
			detailConf.setJobdetailGroupId(jobGroupId);
			detailConf.setJobdetailProvider(provider);
			
			BatchJobGroupConf groupConf = jobGroupConfDao.selectOne1R(jobGroupId);
			JobDetailImpl detailImpl = new JobDetailImpl();
			detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + detailConf.getJobdetailId());
			detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
			detailImpl.setJobClass(ExcutorBase.getExcutorJob(groupConf.getJobGroupJobpath()));
			JobDataMap dataMap = new JobDataMap();
			dataMap.put("savelog", groupConf.getJobGroupSavelog());
			dataMap.put("group_id", jobGroupId);
			detailImpl.setJobDataMap((JobDataMap)CommonUtils.initMapValue(dataMap, groupConf.getJobGroupInitValue()));
			detailImpl.setDurability(true);
			
			BatchTriggerConf triggerConf = triggerConfDao.selectOne1R(triggerId);
			CronTriggerImpl triggerImpl = new CronTriggerImpl();
			triggerImpl.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerId);
			triggerImpl.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			triggerImpl.setCronExpression(triggerConf.getTriggerCrontrigger());
			if(CommonUtils.isNotEmpty(triggerConf.getTriggerStartTime())){
				triggerImpl.setStartTime(CommonUtils.stringToDate(triggerConf.getTriggerStartTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(CommonUtils.isNotEmpty(triggerConf.getTriggerEndTime())){
				triggerImpl.setStartTime(CommonUtils.stringToDate(triggerConf.getTriggerEndTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			ExcutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, triggerImpl);
			//插表.
			detailConfDao.insertOne(detailConf);
		} catch (Exception e) {
			logger.error("设置定时任务失败!!!! 错误信息: {}", MineException.getStackTrace(e));
			throw GitWebException.GIT1001("设置定时任务失败!!!!");
		}
	}
	
	
	/**
	 * 重新设置定时任务
	 * @param queueId
	 * @param cronExpression
	 * @param startTime
	 * @param endTime
	 */
	public void rescheduleJob(Long triggerId, String cronExpression, String startTime, String endTime){
		try{
			//数据的校验放在Controller中
			BatchTriggerConf conf = triggerConfDao.selectOne1R(triggerId);
			conf.setTriggerStartTime(CommonUtils.isEmpty(startTime)? null : startTime);
			conf.setTriggerEndTime(CommonUtils.isEmpty(endTime)? null : endTime);
			conf.setTriggerCrontrigger(cronExpression);
			conf.setTriggerMaintenanceDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
			
			TriggerKey triggerKey = new TriggerKey(ApltContanst.DEFAULT_TRIGGER_NAME + conf.getTriggerId(), ApltContanst.DEFAULT_TRIGGER_GROUP);
			Scheduler scheduler = ExcutorBase.getSchedulerFactoryBean().getScheduler();
			if(scheduler.checkExists(triggerKey)){
				throw GitWebException.GIT1001("指定定时任务不存在!!!!!");
			}
			
			CronTriggerImpl newTrigger = new CronTriggerImpl();
			newTrigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + conf.getTriggerId());
			newTrigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			newTrigger.setCronExpression(conf.getTriggerCrontrigger());
			if(CommonUtils.isNotEmpty(conf.getTriggerStartTime())){
				newTrigger.setStartTime(CommonUtils.stringToDate(conf.getTriggerStartTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(CommonUtils.isNotEmpty(conf.getTriggerEndTime())){
				newTrigger.setStartTime(CommonUtils.stringToDate(conf.getTriggerEndTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			
			scheduler.rescheduleJob(triggerKey, newTrigger);
			//更新表
			triggerConfDao.updateOne1R(conf);
		}catch(Exception e){
			logger.error("重新设置定时任务失败!!!! 错误信息: {}", MineException.getStackTrace(e));
			throw GitWebException.GIT1001("重新设置定时任务失败!!!!" 
			+ ((e instanceof MineException) ? ((MineException)e).getError_msg() : ""));
		}
	}
	
	public void dropJob(Long jobDetailId){
		if(jobDetailId == null){
			throw GitWebException.GIT1002("作业ID");
		}
		try {
			BatchJobDetailConf detailConf = detailConfDao.selectOne1R(jobDetailId);
			Scheduler scheduler = ExcutorBase.getSchedulerFactoryBean().getScheduler();
			JobKey jobKey = new JobKey(ApltContanst.DEFAULT_JOB_NAME + jobDetailId, ApltContanst.DEFAULT_JOB_GROUP);
			if(!scheduler.checkExists(jobKey)){
				throw GitWebException.GIT1001("指定JOB任务不存在!!!!!");
			}
			
			BatchJobGroupConf conf = jobGroupConfDao.selectOne1R(detailConf.getJobdetailGroupId());
			Long triggerId = conf.getJobGroupTriggerId();
			TriggerKey triggerKey = new TriggerKey(ApltContanst.DEFAULT_TRIGGER_NAME + triggerId, ApltContanst.DEFAULT_TRIGGER_GROUP);
			if(scheduler.checkExists(triggerKey)){
				throw GitWebException.GIT1001("指定定时任务不存在!!!!!");
			}
			
			//该方法会将Job和与其相关联的Trigger从JobStore中删除
			scheduler.deleteJob(jobKey);
			
			detailConf.setVaildStatus("1");
			detailConfDao.updateOne1(detailConf);
			conf.setVaildStatus("1");
			jobGroupConfDao.updateOne1(conf);
		} catch (Exception e) {
			logger.error("删除定时任务失败!!!! 错误信息: {}", MineException.getStackTrace(e));
			throw GitWebException.GIT1001("删除定时任务失败!!!!" 
			+ ((e instanceof MineException) ? ((MineException)e).getError_msg() : ""));
		}
	}
	
}
