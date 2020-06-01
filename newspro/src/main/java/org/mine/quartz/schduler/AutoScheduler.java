package org.mine.quartz.schduler;

import java.util.Date;
import java.util.List;

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchGroupDefinitionDao;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchJobExecuteDao;
import org.mine.dao.BatchQueueDefinitionDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.dao.BatchTaskExecuteDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.dao.custom.BatchDefineCostomDao;
import org.mine.model.BatchGroupDefinition;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchJobExecute;
import org.mine.model.BatchQueueDefinition;
import org.mine.model.BatchStepDefinition;
import org.mine.model.BatchTaskDefinition;
import org.mine.model.BatchTaskExecute;
import org.mine.model.BatchTriggerDefinition;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AutoScheduler{
	@Autowired
	private BatchQueueDefinitionDao queueDefinitionDao;
	@Autowired
	private BatchGroupDefinitionDao groupDefinitionDao;
	@Autowired
	private BatchDefineCostomDao defineCostomDao;
	@Autowired
	private BatchTaskDefinitionDao taskDefinitionDao;
	@Autowired
	private BatchJobDefinitionDao jobDefinitionDao;
	@Autowired
	private BatchStepDefinitionDao stepDefinitionDao;
	@Autowired
	private BatchTriggerDefinitionDao triggerDefinitionDao;
	@Autowired
	private BatchTaskExecuteDao taskExecuteDao;
	@Autowired
	private BatchJobExecuteDao jobExecuteDao;
	
	/**
	 * 增加一个队列
	 * @param queueId 队列ID
	 * @param queueName 队列名称
	 * @param timingFlag 定时标志
	 * @return
	 */
	public int addQueue(Long queueId, String queueName, int timingFlag){
		BatchQueueDefinition definition = new BatchQueueDefinition();
		definition.setQueueId(queueId);
		definition.setQueueName(queueName);
		definition.setQueueTimingtaskFlag(timingFlag);
		definition.setQueueExecutionNum(0);
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		return queueDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 增加一个执行组别
	 * @param groupId 组别ID
	 * @param groupName 组别名称
	 * @param queueId 关联队列ID
	 * @return
	 */
	public int addGroup(Long groupId, String groupName, Long queueId){
		if (groupId == null) throw GitWebException.GIT1002("组别ID");
		if (queueId == null) {
			throw GitWebException.GIT1002("队列ID");
		} else {
			//检查当前队列是否存在
			queueDefinitionDao.selectOne1R(queueId, true);
		}
		BatchGroupDefinition definition = new BatchGroupDefinition();
		definition.setGroupId(groupId);
		definition.setGroupName(groupName);
		definition.setGroupAssociateQueueId(queueId);
		definition.setGroupExecutionNum((int)defineCostomDao.getMaxNumByGroupDefin());
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		return groupDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 增加任务
	 * @param taskId
	 * @param taskName
	 * @param groupId
	 * @param list 初始值
	 * @return
	 */
	public int addTask(Long taskId, String taskName, Long groupId, List<String> list){
		if(taskId == null){
			throw GitWebException.GIT1002("任务ID");
		}
		if(groupId == null){
			throw GitWebException.GIT1002("组别ID");
		} else {
			//检查当前组别是否存在
			groupDefinitionDao.selectOne1R(groupId, true);
		}
		BatchTaskDefinition definition = new BatchTaskDefinition();
		definition.setTaskId(taskId);
		definition.setTaskName(taskName);
		definition.setTaskAssociateGroupId(groupId);
		definition.setTaskSkipFlag(0);
		definition.setTaskInitValue(CommonUtils.listToStr(list,";"));
		definition.setTaskExecutionNum(0);
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		return taskDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 增加job
	 * @param jobId
	 * @param jobName
	 * @param triggerId
	 * @param list
	 * @param skipFlag
	 * @param mutiFlag
	 * @param delayFlag
	 * @param delayTime
	 * @return
	 */
	public int addJob(Long jobId, String jobName, String triggerId, List<String> list, String executor, int skipFlag,
			int mutiFlag, int delayFlag, String delayTime){
		if (jobId == null) throw GitWebException.GIT1002("作业ID");
		if (CommonUtils.isEmpty(executor)) throw GitWebException.GIT1002("执行器");
		BatchJobDefinition definition = new BatchJobDefinition();
		definition.setJobId(jobId);
		definition.setJobName(jobName);
		definition.setJobAssociateTriggerId(triggerId);
		definition.setJobExecutor(executor);
		definition.setJobInitValue(CommonUtils.listToStr(list, ";"));
		definition.setJobSkipFlag(skipFlag);
		definition.setJobRunMutiFlag(mutiFlag);
		definition.setJobTimeDelayFlag(delayFlag);
		definition.setJobTimeDelayValue(delayTime);
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		return jobDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 增加step
	 * @param stepId
	 * @param stepName
	 * @param actuator
	 * @param list
	 * @return
	 */
	public int addStep(Long stepId, String stepName, String actuator, List<String> list){
		if(stepId == null){
			throw GitWebException.GIT1002("步骤ID");
		}
		BatchStepDefinition definition = new BatchStepDefinition();
		definition.setStepId(stepId);
		definition.setStepName(stepName);
		definition.setStepActuator(actuator);
		definition.setStepLogMdcValue(actuator);
		definition.setStepInitValue(CommonUtils.listToStr(list, ";"));
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		return stepDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 新增触发器. (秒 分 时 日 月 周 年)
	 * @param triggerId
	 * @param triggerName
	 * @param startTime
	 * @param endTime
	 * @param cronTrigger
	 * @return
	 */
	public int addTrigger(Long triggerId, String triggerName, String startTime, String endTime, String cronTrigger, String remark){
		if(triggerId == null){
			throw GitWebException.GIT1002("触发器ID");
		}
		
		if (!CronExpression.isValidExpression(cronTrigger)) {
			throw GitWebException.GIT_CRONEXPRESSION(cronTrigger);
		}
		
		BatchTriggerDefinition definition = new BatchTriggerDefinition();
		definition.setTriggerId(triggerId);
		definition.setTriggerName(triggerName);
		definition.setTriggerStartTime(startTime);
		definition.setTriggerEndTime(endTime);
		definition.setTriggerCrontrigger(cronTrigger);
		definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
		definition.setValidStatus(JobContanst.VALID_STATUS_0);
		definition.setTriggerRemark(remark);
		return triggerDefinitionDao.insertOne(definition);
	}
	
	/**
	 * 更新触发器, 查询关联的job, 重新注册触发器.
	 * @param triggerId
	 * @param triggerName
	 * @param startTime
	 * @param endTime
	 * @param cronTrigger
	 * @param remark
	 * @return
	 */
	@Transactional
	public int updateTrigger(BatchTriggerDefinition triggerDefinition){
		if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerCrontrigger()) && !CronExpression.isValidExpression(triggerDefinition.getTriggerCrontrigger())) {
			throw GitWebException.GIT_CRONEXPRESSION(triggerDefinition.getTriggerCrontrigger());
		}
		BatchTriggerDefinition definition = triggerDefinitionDao.selectOne1R(triggerDefinition.getTriggerId(), true);
		if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerName())) triggerDefinition.setTriggerName(triggerDefinition.getTriggerName());
		definition.setTriggerStartTime(triggerDefinition.getTriggerStartTime());
		definition.setTriggerEndTime(triggerDefinition.getTriggerEndTime());
		if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerCrontrigger())) definition.setTriggerCrontrigger(triggerDefinition.getTriggerCrontrigger());
		if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerRemark())) definition.setTriggerRemark(triggerDefinition.getTriggerRemark());
		System.out.println(">>>>>>>>>" + definition.toString());
		return triggerDefinitionDao.updateOne1R(definition);
	}
	
	/**
	 * 编排任务
	 * @param taskId
	 * @param jobIds
	 */
	public void orchestrationTask(Long taskId, List<Long> jobIds){
		BatchTaskDefinition definition = taskDefinitionDao.selectOne1R(taskId, true);
		String taskName = definition.getTaskName();
		BatchTaskExecute taskExecute = null;
		for(Long jobId : jobIds){
			jobDefinitionDao.selectOne1R(jobId, true);
			taskExecute = new BatchTaskExecute();
			taskExecute.setExecuteTaskId(taskId);
			taskExecute.setExecuteTaskName(taskName);
			taskExecute.setExecuteJobId(jobId);
			taskExecute.setExecuteJobOneTime(JobContanst.ONE_TIME_0);
			taskExecute.setExecuteJobNum(1);
			taskExecute.setValidStatus(JobContanst.VALID_STATUS_0);
			taskExecuteDao.insertOne(taskExecute);
		}
	}
	
	/**
	 * 编排作业
	 * @param jobId
	 * @param stepIds
	 */
	public void orchestrationJob(Long jobId, List<Long> stepIds){
		BatchJobDefinition definition = jobDefinitionDao.selectOne1R(jobId, true);
		String jobName = definition.getJobName();
		BatchJobExecute jobExecute = null;
		for(Long stepId : stepIds){
			stepDefinitionDao.selectOne1R(stepId, true);
			jobExecute = new BatchJobExecute();
			jobExecute.setExecuteJobId(jobId);
			jobExecute.setExecuteJobName(jobName);
			jobExecute.setExecuteStepId(stepId);
			jobExecute.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
			jobExecute.setValidStatus(JobContanst.VALID_STATUS_0);
			jobExecuteDao.insertOne(jobExecute);
		}
	}
	
//	/**
//	 * 在已存在的作业中增加执行的step
//	 * @param jobId
//	 * @param steps
//	 */
//	public void appendStepsInJob(Long jobId, List<Long> steps){
//		
//	}
}
