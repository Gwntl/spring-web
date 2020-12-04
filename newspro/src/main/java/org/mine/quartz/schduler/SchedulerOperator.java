package org.mine.quartz.schduler;

import org.mine.aplt.Cache.JobCache;
import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.model.BatchJobDefinition;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.trigger.ExecutorTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

/**
 * @Description:
 * @ClassName: SchdulerOperator
 * @author: wntl
 * @date: 2020年6月12日 上午9:30:07
 */
@Repository
public class SchedulerOperator {
	protected static final Logger logger = LoggerFactory.getLogger(SchedulerOperator.class);
	@Autowired
	private BatchJobDefinitionDao jobDefinitionDao;
	@Autowired
	private BatchTriggerDefinitionDao triggerDefinitionDao;

	/**
	 * @param jobID
	 * 动态注册JOB服务.
	 * @param triggerID
	 */
	public boolean registerJob(String jobID, String triggerID) {
		boolean isSuccess = false;
		try {
			JobDetailImpl detailImpl = new JobDetailImpl();
			detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + jobID);
			detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
			BatchJobDefinition definition = jobDefinitionDao.selectOne1R(jobID, true);
			detailImpl.setJobClass(ExecutorBase.getExecutorJob(definition.getJobExecutor()));
			ExecuteTaskDto taskDto = new ExecuteTaskDto();
			JobDataMap dataMap = new JobDataMap();
			CommonUtils.initMapValue(taskDto.getJobInitValue(), definition.getJobInitValue());
			dataMap.put("dto", taskDto);
			detailImpl.setJobDataMap(dataMap);
			detailImpl.setDurability(true);

			CronTriggerImpl triggerImpl = new CronTriggerImpl();
			triggerImpl.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerID);
			triggerImpl.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
//		triggerImpl.setKey(new TriggerKey(triggerImpl.getName(), ApltContanst.DEFAULT_TRIGGER_GROUP));
			triggerImpl.setCronExpression(triggerDefinitionDao.selectOne1R(triggerID, true).getTriggerCrontrigger());
			triggerImpl.setJobKey(detailImpl.getKey());

			ExecutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, triggerImpl);
			isSuccess = true;
		} catch (ParseException | SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("注册定时服务失败....");
		}
		return isSuccess;
	}

	/**
	 *更新JOB触发器信息
	 * @param jobID
	 * @param triggerIds
	 */
	public boolean reloadJob(String jobID, List<String> triggerIds) {
		boolean isSuccess = false;
		try {
			StdScheduler scheduler = (StdScheduler) ExecutorBase.getSchedulerFactoryBean().getScheduler();
			JobKey jobKey = new JobKey(ApltContanst.DEFAULT_JOB_NAME + jobID, ApltContanst.DEFAULT_JOB_GROUP);
			ExecutorTrigger trigger = new ExecutorTrigger();
			@SuppressWarnings("unchecked")
			List<CronTriggerImpl> triggerImpls =  (List<CronTriggerImpl>) scheduler.getTriggersOfJob(jobKey);

			if (scheduler.deleteJob(jobKey)) {
				for(CronTriggerImpl triggerImpl : triggerImpls){
					triggerImpl.setJobKey(jobKey);
					scheduler.scheduleJob(triggerImpl);
				}

				for (String triggerId : triggerIds) {
					CronTriggerImpl cronTriggerImpl = trigger.definationTrigger(triggerId);
					cronTriggerImpl.setJobKey(jobKey);
					scheduler.scheduleJob(cronTriggerImpl);
				}
				isSuccess = true;
			}
		} catch (SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("JOB更新失败....");
		}
		return isSuccess;
	}

	/**
	 * 重新加载JOB触发器信息
	 * @param jobID
	 * @param triggerIds
	 */
	public boolean reloadTrigger(String jobID, List<String> triggerIds) {
		boolean isSuccess = false;
		try {
			StdScheduler scheduler = (StdScheduler) ExecutorBase.getSchedulerFactoryBean().getScheduler();
			JobKey jobKey = getJobKey(scheduler, jobID);
			if (jobKey != null) {
				JobDetail jobdtail = scheduler.getJobDetail(jobKey);
				if (scheduler.deleteJob(jobKey)) {
					ExecutorTrigger trigger = new ExecutorTrigger();
					for (String triggerId : triggerIds) {
						scheduler.scheduleJob(jobdtail, trigger.definationTrigger(triggerId));
					}
					isSuccess = true;
				}
			}
		} catch (SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("JOB更新失败....");
		}
		return isSuccess;
	}

	/**
	 * 移除定时JOB作业
	 * @param jobID
	 * @throws SchedulerException
	 */
	public boolean removeJob(String jobID) throws SchedulerException {
		boolean isSuccess = false;
		StdScheduler scheduler = (StdScheduler) ExecutorBase.getSchedulerFactoryBean().getScheduler();
		JobKey jobKey = getJobKey(scheduler, jobID);
		if(jobKey != null){
			isSuccess = scheduler.deleteJob(jobKey);
		}
		return isSuccess;
	}

	/**
	 * 暂停JOB作业
	 * @param jobID
	 * @param pauseFlag 可暂停标志
	 * @param force 强制暂停标志. 当JOB不支持暂停时,使用强制暂停时,重启后会将misfir的时间点全部补回来.
	 */
	public boolean pauseJob(String jobID, int pauseFlag, boolean force) {
		boolean isSuccess = false;
		try {
			StdScheduler scheduler = (StdScheduler) ExecutorBase.getSchedulerFactoryBean().getScheduler();
			JobKey jobKey = getJobKey(scheduler, jobID);
			if (jobKey != null && !JobCache.get(jobKey)) {
				scheduler.pauseJob(jobKey);
				JobCache.put(jobKey);
				isSuccess = true;
			} else {
				logger.error("The current job has been suspended or is null.");
			}
		} catch (SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("JOB暂停失败....");
		}
		return isSuccess;
	}

	/**
	 * 重启JOB作业
	 * @param jobID
	 * @param resumeFlag 可重启标志.
	 * @param force 强制重启标志.
	 */
	public boolean restartJob(String jobID, int resumeFlag, boolean force) {
		boolean isSuccess = false;
		try {
			StdScheduler scheduler = (StdScheduler) ExecutorBase.getSchedulerFactoryBean().getScheduler();
			JobKey jobKey = getJobKey(scheduler, jobID);
			if (jobKey != null && JobCache.get(jobKey)) {
				scheduler.resumeJob(jobKey);
				JobCache.remove(jobKey);
				isSuccess = true;
			} else {
				logger.error("The current job is not suspended or is null.");
			}
		} catch (SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("JOB重启失败....");
		}
		return isSuccess;
	}

	/**
	 * 获取JobKey
	 * @param scheduler
	 * @param jobID
	 * @return
	 * @throws SchedulerException
	 */
	public JobKey getJobKey(StdScheduler scheduler, String jobID) throws SchedulerException {
		JobKey jobKey = new JobKey(ApltContanst.DEFAULT_JOB_NAME + jobID, ApltContanst.DEFAULT_JOB_GROUP);
		return scheduler.checkExists(jobKey) ? jobKey : null;
	}
}
