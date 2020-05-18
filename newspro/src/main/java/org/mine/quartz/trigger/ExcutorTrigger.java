package org.mine.quartz.trigger;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchGroupDefinitionDao;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchQueueDefinitionDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.dao.BatchTaskExecuteDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.model.BatchGroupDefinition;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchQueueDefinition;
import org.mine.model.BatchTaskDefinition;
import org.mine.model.BatchTaskExecute;
import org.mine.model.BatchTriggerDefinition;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
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
	private BatchTriggerDefinitionDao triggerDefinitionDao;
	@Autowired
	private BatchQueueDefinitionDao definitionDao;
	@Autowired
	private BatchGroupDefinitionDao groupDefinitionDao;
	@Autowired
	private BatchTaskDefinitionDao taskDefinitionDao;
	@Autowired
	private BatchTaskExecuteDao taskExecuteDao;
	@Autowired
	private BatchJobDefinitionDao jobDefinitionDao;
	@Autowired
	private GitContext context;

	protected static Map<String, BaseServiceTasketExcutor> quartzStepCache = new ConcurrentHashMap<>(1 >> 5);
	
	public static BaseServiceTasketExcutor getExecutor(String key){
		if (quartzStepCache.containsKey(key)) {
			return quartzStepCache.get(key);
		} else {
			Object obj = GitContext.getBean(key);
			if(obj != null && obj instanceof BaseServiceTasketExcutor){
				quartzStepCache.put(key, (BaseServiceTasketExcutor)obj);
				return (BaseServiceTasketExcutor)obj;
			}
		}
		return null;
	}
	
	/* 
	 * 此处将quartz需要的定时作业配置在数据库中, 每次加载时,从数据库中加载定时作业.
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化执行器缓存
		quartzStepCache.putAll(context.getBeansOfType(BaseServiceTasketExcutor.class));
		System.out.println(">>>>> " + CommonUtils.toString(quartzStepCache));
		loadTimingData();
	}
	
	/**
	 * 加载定时任务
	 * @return
	 */
	public Map<JobDetailImpl, ExcutorTrigger> loadTimingData(){
		try{
			//查询出需定时执行的任务
			List<BatchQueueDefinition> queueDefinitions = definitionDao.selectAll1R(JobExcutorEnum.AUTO_RUN);
			ConcurrTaskDto taskDto = null;
			int size = queueDefinitions.size();
			if(size > 0){
				for(int i = 0; i < size; i++){
					taskDto = new ConcurrTaskDto();
					BatchQueueDefinition queueDefinition = queueDefinitions.get(i);
					Long queueId = queueDefinition.getQueueId();
					List<BatchGroupDefinition> groupDefinitions = groupDefinitionDao.selectAll1R(queueId);
					if(CommonUtils.isEmpty(groupDefinitions)){
						logger.error("No executable groups exists on the current queue[{}]!!!!", queueId);
					} else {
						for(int group = 0, groupSize = groupDefinitions.size(); group < groupSize; group++){
							BatchGroupDefinition groupDefinition = groupDefinitions.get(group);
							taskDto.setGroupId(groupDefinition.getGroupId());
							Long groupId = groupDefinition.getGroupId();
							List<BatchTaskDefinition> taskDefinitions = taskDefinitionDao.selectAll1R(groupId);
							if(CommonUtils.isEmpty(taskDefinitions)){
								logger.error("No executable tasks exists on the current group[{}]!!!", groupId);
							} else {
								for(BatchTaskDefinition taskDefinition : taskDefinitions){
									Long taskId = taskDefinition.getTaskId();
									taskDto.setTaskId(taskId);
									taskDto.setTaskSkipFlag(taskDefinition.getTaskSkipFlag());
									CommonUtils.initMapValue(taskDto.getTaskInitValue(), taskDefinition.getTaskInitValue());
									List<BatchTaskExecute> taskExecutes = taskExecuteDao.selectAll1R(taskId);
									if(CommonUtils.isEmpty(taskExecutes)){
										logger.error("No executable jobs exists on the current task[{}]", taskId);
									} else {
										for(int taskE = 0, taskESize = taskExecutes.size(); taskE < taskESize; taskE++){
											BatchTaskExecute taskExecute = taskExecutes.get(taskE);
											Long jobId = taskExecute.getExecuteJobId();
											BatchJobDefinition jobDefinition = jobDefinitionDao.selectOne1R(jobId, false);
											if(jobDefinition != null){
												taskDto.setJobId(jobId);
												taskDto.setJobName(jobDefinition.getJobName());
												taskDto.setJobSkipFlag(jobDefinition.getJobSkipFlag());
												taskDto.setJobRunMutiFlag(jobDefinition.getJobRunMutiFlag());
												taskDto.setJobTimeDelayFlag(jobDefinition.getJobTimeDelayFlag());
												taskDto.setJobTimeDelayValue(jobDefinition.getJobTimeDelayValue());
												taskDto.setJobOneTime(taskExecute.getExecuteJobOneTime());
												CommonUtils.initMapValue(taskDto.getJobInitValue(), jobDefinition.getJobInitValue());
												registerQuartz(jobDefinition, taskDto);
											} else {
												logger.error("The current job[{}] does not exist", jobId);
											}
										}
									}
								}
							}
						}
					}
				}
				logger.info("Loading timing job was successful!!!!!");
			} else {
				logger.warn("There is no timing job in the current system!!!!!");
			}
		} catch(SchedulerException e){
			logger.error("加载Quartz信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_APPRUNEXC("加载Quartz信息失败");
		}
		
		return null;
	}
	
	/**
	 * 注册定时任务
	 * @param jobDefinition
	 * @throws SchedulerException
	 */
	public void registerQuartz(BatchJobDefinition jobDefinition, ConcurrTaskDto taskDto)throws SchedulerException{
		JobDetailImpl detailImpl = new JobDetailImpl();
		detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + jobDefinition.getJobId());
		detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
		detailImpl.setJobClass(ExecutorBase.getExcutorJob(jobDefinition.getJobExecutor()));
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("dto", taskDto);
		detailImpl.setJobDataMap(dataMap);
		//确保job执行完后不从jobstore中移除
		detailImpl.setDurability(true);
		
		List<String> triggerIds = CommonUtils.splitStrToList(jobDefinition.getJobAssociateTriggerId(), ",", false);
		ExcutorTrigger trigger = definationTrigger(Long.valueOf(triggerIds.get(0)));
		/*
		 * Spring当使用bean配置触发器的形式的时候,将jobdetail放至trigger的dataMap中.
		 * 当注册时若发现未配置Job信息, Spring会从Map中自动获取jobDetail的值,注册进Schedule中.
		 * */
//		getJobDataMap().put("jobDetail", detail);
		ExecutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detailImpl, trigger);
//		map.put(detailImpl, trigger);
		
		//注册该JOB中其他触发器
		int triggerSize = triggerIds.size();
		if(triggerSize > 1){
			ExcutorTrigger extraTrigger = null;
			for(int register = 1; register < triggerSize; register ++){
				extraTrigger = definationTrigger(Long.valueOf(triggerIds.get(register)));
				extraTrigger.setJobKey(detailImpl.getKey());
				ExecutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(extraTrigger);
			}
		}
	}
	
	/**
	 * 定义触发器
	 * @param triggerId
	 * @return ExcutorTrigger
	 */
	public ExcutorTrigger definationTrigger(Long triggerId){
		try{
			BatchTriggerDefinition triggerDefinition = triggerDefinitionDao.selectOne1R(triggerId, false);
			if(CommonUtils.isEmpty(triggerDefinition.getTriggerCrontrigger())){
				throw GitWebException.GIT1002("batch_trigger_conf.trigger_crontrigger 触发器参数");
			}
			ExcutorTrigger trigger = new ExcutorTrigger();
			trigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerDefinition.getTriggerId());
			trigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			trigger.setCronExpression(triggerDefinition.getTriggerCrontrigger());
			if(CommonUtils.isNotEmpty(triggerDefinition.getTriggerStartTime())){
				trigger.setStartTime(CommonUtils.stringToDate(triggerDefinition.getTriggerStartTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			if(CommonUtils.isNotEmpty(triggerDefinition.getTriggerEndTime())){
				trigger.setEndTime(CommonUtils.stringToDate(triggerDefinition.getTriggerEndTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			return trigger;
		} catch(ParseException e){
			logger.error("加载trigger信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_TRIGGER();
		}
	}
}
