package org.mine.quartz.schduler;

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.*;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.model.*;
import org.mine.rule.batch.BatchNameRule;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 注册器
 *
 * @author wntl
 * @version v1.0
 * @filename: AutoScheduler
 * @date 2020/8/5 20:53
 */
@Repository
@Transactional
public class AutoScheduler {
    @Autowired
    private BatchQueueDefinitionDao queueDefinitionDao;
    @Autowired
    private BatchGroupDefinitionDao groupDefinitionDao;
    @Autowired
    private BatchConfCustomDao defineCostomDao;
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
    @Autowired
    private BatchQueueExecuteDao queueExecuteDao;

    /**
     * 增加一个队列
     *
     * @param queueId    队列ID
     * @param queueName  队列名称
     * @param timingFlag 定时标志
     * @return
     */
    public int addQueue(String queueId, String queueName, int timingFlag) {
        BatchQueueDefinition definition = null;
        if (CommonUtils.isEmpty(queueId)) {
            throw GitWebException.GIT1002("队列ID");
        } else if ((definition = queueDefinitionDao.selectOne1(queueId, false)) != null) {
            //检查当前队列是否存在
            throw GitWebException.GIT1006("队列ID[" + queueId + "]");
        }
        definition = new BatchQueueDefinition();
        definition.setQueueId(BatchNameRule.queueIDNamed(queueId));
        definition.setQueueName(queueName);
        definition.setQueueExcFlag(timingFlag);
        definition.setQueueExecutionNum(defineCostomDao.getMaxNumByQueueDefin());
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        return queueDefinitionDao.insertOne(definition);
    }

    /**
     * 队列任务编排
     *
     * @param queueID
     * @param taskIDs
     * @return: int
     * @Author: wntl
     * @Date: 2020/8/10
     */

    public void orchestrationQueue(String queueID, List<String> taskIDs) {
        BatchQueueDefinition queueDefinition = null;
        if (CommonUtils.isEmpty(queueID)) {
            throw GitWebException.GIT1002("队列ID");
        } else {
            //校验队列信息是否已存在
            queueDefinition = queueDefinitionDao.selectOne1R(queueID, true);
        }
        BatchTaskDefinition taskDefinition = null;
        BatchQueueExecute queueExecute = null;
        for (String taskID : taskIDs) {
            taskDefinition = taskDefinitionDao.selectOne1R(taskID, true);
            queueExecute = new BatchQueueExecute();
            queueExecute.setExecuteQueueId(BatchNameRule.executeNamed(queueID));
            queueExecute.setExecuteQueueName(queueDefinition.getQueueName());
            queueExecute.setExecuteTaskId(taskID);
            queueExecute.setExecuteTaskName(taskDefinition.getTaskName());
            queueExecute.setExecuteNum(defineCostomDao.getMaxNumByQueueExecute(queueExecute.getExecuteQueueId()));
            queueExecute.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
            queueExecute.setValidStatus(JobContanst.VALID_STATUS_0);
            queueExecuteDao.insertOne(queueExecute);
        }
    }

    /**
     * 增加一个执行组别
     *
     * @param groupId   组别ID
     * @param groupName 组别名称
     * @param queueId   关联队列ID
     * @return
     */
 /*   public int addGroup(Long groupId, String groupName, String queueId) {
        BatchGroupDefinition definition = null;
        if (groupId == null) throw GitWebException.GIT1002("组别ID");
        if (queueId == null) {
            throw GitWebException.GIT1002("组别ID");
        } else {
            //检查当前队列是否存在
            queueDefinitionDao.selectOne1R(queueId, true);
            //检查当前队列是否存在
            if ((definition = groupDefinitionDao.selectOne1(groupId, false)) != null) {
                throw GitWebException.GIT1006("组别ID[" + groupId + "]");
            }
        }
        definition = new BatchGroupDefinition();
        definition.setGroupId(groupId);
        definition.setGroupName(groupName);
        definition.setGroupAssociateQueueId(queueId);
        definition.setGroupExecutionNum(defineCostomDao.getMaxNumByGroupDefin());
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        return groupDefinitionDao.insertOne(definition);
    }*/

    /**
     * 增加任务
     *
     * @param taskId
     * @param taskName
     * @param autoFlag
     * @param triggerID
     * @param list 初始值
     * @param executor
     * @param skipFlag
     * @return
     */
    public int addTask(String taskId, String taskName, int autoFlag, String triggerID, List<String> list,
                       String executor, int skipFlag) {
        if (CommonUtils.isEmpty(taskId)) {
            throw GitWebException.GIT1002("任务ID");
        } else if (taskDefinitionDao.selectOne1(taskId, false) != null) {
            //检查当前任务是否存在
            throw GitWebException.GIT1006("任务ID[" + taskId + "]");
        }
        BatchTaskDefinition definition = new BatchTaskDefinition();
        definition.setTaskId(BatchNameRule.taskIDNamed(taskId));
        definition.setTaskName(taskName);
        definition.setTaskAutoFlag(autoFlag);
        definition.setTaskAssociateTriggerId(triggerID);
        definition.setTaskExecutor(executor);
        definition.setTaskSkipFlag(skipFlag);
        definition.setTaskInitValue(CommonUtils.listToStr(list, ";"));
        definition.setTaskConcurrencyNum(10);
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        return taskDefinitionDao.insertOne(definition);
    }

    /**
     * 增加job
     *
     * @param jobId
     * @param jobName
     * @param triggerId
     * @param list
     * @param executor
     * @param skipFlag
     * @param autoFlag
     * @param mutiFlag
     * @param delayFlag
     * @param delayTime
     * @return
     */
    public int addJob(String jobId, String jobName, String triggerId, List<String> list, String executor, int skipFlag, int autoFlag,
                      int mutiFlag, int delayFlag, String delayTime) throws ClassNotFoundException{
        BatchJobDefinition definition = null;
        if (CommonUtils.isEmpty(jobId)) {
            throw GitWebException.GIT1002("作业ID");
        } else {
            //查询当前作业是否存在
            if ((definition = jobDefinitionDao.selectOne1(jobId, false)) != null) {
                throw GitWebException.GIT1006("作业ID[" + jobId + "]");
            }

            if (autoFlag == 0) {
                if (CommonUtils.isEmpty(executor)) {
                    throw GitWebException.GIT1002("执行器");
                } else {
                    //验证执行器是否可用
                    Class.forName(executor);
                }

                //查询触发器是否存在
                triggerDefinitionDao.selectOne1R(triggerId, true);
            } else {
                executor = "";
                triggerId = "";
            }
        }

        definition = new BatchJobDefinition();
        definition.setJobId(BatchNameRule.jobIDNamed(jobId));
        definition.setJobName(jobName);
        definition.setJobAssociateTriggerId(triggerId);
        definition.setJobExecutor(executor);
        definition.setJobInitValue(CommonUtils.listToStr(list, ";"));
        definition.setJobSkipFlag(skipFlag);
        definition.setJobAutoFlag(autoFlag);
        definition.setJobRunMutiFlag(mutiFlag);
        definition.setJobTimeDelayFlag(delayFlag);
        definition.setJobTimeDelayValue(delayTime);
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        return jobDefinitionDao.insertOne(definition);
    }

    public int updateJob(String jobID, String executor, List<String> list, int autoFlag) {
        Assert.notNull(jobID, "作业ID不能为空");
        BatchJobDefinition jobDefinition = jobDefinitionDao.selectOne1R(jobID, true);
        if (CommonUtils.isNotEmpty(executor)) {
            jobDefinition.setJobExecutor(executor);
        }
        if (CommonUtils.isEmpty(list)) {
            jobDefinition.setJobInitValue(CommonUtils.isEmpty(jobDefinition.getJobInitValue()) ? CommonUtils.listToStr(list, ";")
                    : jobDefinition.getJobInitValue() + ";"+ CommonUtils.listToStr(list, ";"));
        }
        if (CommonUtils.isNotEmpty(autoFlag)) {
            jobDefinition.setJobAutoFlag(autoFlag);
        }
        return jobDefinitionDao.updateOne1R(jobDefinition);
    }

    /**
     * 增加step
     *
     * @param stepId
     * @param stepName
     * @param actuator
     * @param list
     * @return
     */
    public void addStep(String stepId, String stepName, String actuator, List<String> list) {
        BatchStepDefinition definition = null;
        if (CommonUtils.isEmpty(stepId)) {
            throw GitWebException.GIT1002("步骤ID");
        } else if ((definition = stepDefinitionDao.selectOne1(stepId, false)) != null) {
            //查询当前步骤是否存在
            throw GitWebException.GIT1006("步骤ID[" + stepId + "]");
        }

        if (CommonUtils.isEmpty(actuator)) {
            throw GitWebException.GIT1002("执行器");
        } else {
            GitContext.getBean(actuator);
        }
        definition = new BatchStepDefinition();
        definition.setStepId(BatchNameRule.stepIDNamed(stepId));
        definition.setStepName(stepName);
        //判断当前服务内是否存在该Bean.
        GitContext.getBean(actuator);
        definition.setStepActuator(actuator);
        definition.setStepLogMdcValue(CommonUtils.firstLetterUpperCase(actuator));
        definition.setStepInitValue(CommonUtils.listToStr(list, ";"));
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        stepDefinitionDao.insertOne(definition);
    }

    /**
     * 新增触发器. (秒 分 时 日 月 周 年)
     *
     * @param triggerId
     * @param triggerName
     * @param startTime
     * @param endTime
     * @param cronTrigger
     * @return
     */
    public int addTrigger(String triggerId, String triggerName, String startTime, String endTime, String cronTrigger, String remark) {
        BatchTriggerDefinition definition = null;
        if (CommonUtils.isEmpty(triggerId)) {
            throw GitWebException.GIT1002("触发器ID");
        } else if ((definition = triggerDefinitionDao.selectOne1(triggerId, false)) != null) {
            //查询触发器是否已经存在
            throw GitWebException.GIT1006("触发器ID[" + triggerId + "]");
        }

        if (!CronExpression.isValidExpression(cronTrigger)) {
            throw GitWebException.GIT_CRONEXPRESSION(cronTrigger);
        }

        definition = new BatchTriggerDefinition();
        definition.setTriggerId(BatchNameRule.triggerIDNamed(triggerId));
        definition.setTriggerName(triggerName);
        definition.setTriggerStartTime(CommonUtils.isEmpty(startTime) ? null : startTime);
        definition.setTriggerEndTime(CommonUtils.isEmpty(endTime) ? null : endTime);
        definition.setTriggerCrontrigger(cronTrigger);
        definition.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
        definition.setValidStatus(JobContanst.VALID_STATUS_0);
        definition.setTriggerRemark(remark);
        return triggerDefinitionDao.insertOne(definition);
    }

    /**
     * 更新触发器, 查询关联的job, 重新注册触发器.
     *
     * @param triggerDefinition
     * @return
     */
    @Transactional
    public int updateTrigger(BatchTriggerDefinition triggerDefinition) {
        if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerCrontrigger())
                && !CronExpression.isValidExpression(triggerDefinition.getTriggerCrontrigger())) {
            throw GitWebException.GIT_CRONEXPRESSION(triggerDefinition.getTriggerCrontrigger());
        }
        BatchTriggerDefinition definition = triggerDefinitionDao.selectOne1R(triggerDefinition.getTriggerId(), true);
        if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerName())){
            triggerDefinition.setTriggerName(triggerDefinition.getTriggerName());
        }
        definition.setTriggerStartTime(CommonUtils.isEmpty(triggerDefinition.getTriggerStartTime()) ? null : triggerDefinition.getTriggerStartTime());
        definition.setTriggerEndTime(CommonUtils.isEmpty(triggerDefinition.getTriggerEndTime()) ? null : triggerDefinition.getTriggerEndTime());

        if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerCrontrigger())){
            definition.setTriggerCrontrigger(triggerDefinition.getTriggerCrontrigger());
        }

        if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerRemark())){
            definition.setTriggerRemark(triggerDefinition.getTriggerRemark());
        }
        return triggerDefinitionDao.updateOne1R(definition);
    }

    /**
     * 编排任务
     *
     * @param taskId
     * @param jobIds
     */
    public void orchestrationTask(String taskId, List<String> jobIds) {
        BatchTaskDefinition definition = taskDefinitionDao.selectOne1R(taskId, true);
        String taskName = definition.getTaskName();
        BatchTaskExecute taskExecute = null;
        for (String jobId : jobIds) {
            jobDefinitionDao.selectOne1R(jobId, true);
            taskExecute = new BatchTaskExecute();
            taskExecute.setExecuteTaskId(taskId);
            taskExecute.setExecuteTaskName(taskName);
            taskExecute.setExecuteJobId(jobId);
            taskExecute.setExecuteJobOneTime(JobContanst.ONE_TIME_1);
            taskExecute.setExecuteJobNum(defineCostomDao.getMaxNumByTaskExecute(taskExecute.getExecuteTaskId()));
            taskExecute.setValidStatus(JobContanst.VALID_STATUS_0);
            taskExecuteDao.insertOne(taskExecute);
        }
    }

    /**
     * 编排作业
     *
     * @param jobId
     * @param stepIds
     */
    public void orchestrationJob(String jobId, List<String> stepIds) {
        BatchJobDefinition definition = jobDefinitionDao.selectOne1R(jobId, true);
        String jobName = definition.getJobName();
        BatchJobExecute jobExecute = null;
        for (String stepId : stepIds) {
            stepDefinitionDao.selectOne1R(stepId, true);
            jobExecute = new BatchJobExecute();
            jobExecute.setExecuteJobId(jobId);
            jobExecute.setExecuteJobName(jobName);
            jobExecute.setExecuteStepId(stepId);
            jobExecute.setExecuteStepNum(defineCostomDao.getMaxNumByJobExecute(jobExecute.getExecuteJobId()));
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
