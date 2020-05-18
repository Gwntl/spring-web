package org.mine.quartz.schduler;

import java.text.ParseException;
import java.util.List;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.model.BatchJobDefinition;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchdulerOperator {
	protected static final Logger logger = LoggerFactory.getLogger(SchdulerOperator.class);
	@Autowired
	private BatchJobDefinitionDao jobDefinitionDao;
	@Autowired
	private BatchTriggerDefinitionDao triggerDefinitionDao;
	
	public void registerJob(Long jobId, Long triggerId){
		try {
			JobDetailImpl detailImpl = new JobDetailImpl();
			detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + jobId);
			detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
			BatchJobDefinition definition = jobDefinitionDao.selectOne1R(jobId, true);
			detailImpl.setJobClass(ExecutorBase.getExcutorJob(definition.getJobExecutor()));
			ConcurrTaskDto taskDto = new ConcurrTaskDto();
			JobDataMap dataMap = new JobDataMap();
			CommonUtils.initMapValue(taskDto.getJobInitValue(), definition.getJobInitValue());
			dataMap.put("dto", taskDto);
			detailImpl.setJobDataMap(dataMap);
			detailImpl.setDurability(true);
			
			CronTriggerImpl triggerImpl = new CronTriggerImpl();
			triggerImpl.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerId);
			triggerImpl.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
//		triggerImpl.setKey(new TriggerKey(triggerImpl.getName(), ApltContanst.DEFAULT_TRIGGER_GROUP));
			triggerImpl.setCronExpression(triggerDefinitionDao.selectOne1R(triggerId, true).getTriggerCrontrigger());
			triggerImpl.setJobKey(detailImpl.getKey());
			
			ExecutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, triggerImpl);
			
		} catch (ParseException | SchedulerException e) {
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("注册定时服务失败....");
		}
	}
	
	public void reloadJob(Long job, List<Long> triggerIds){
		
	}
	
	public void reloadTrigger(Long job, Long triggerId){
		
	}
	
	public void removeJob(Long job){
		
	}
	
	public void pauseJob(Long job){
		
	}
	
	public void restartJob(Long job){
		
	}
}
