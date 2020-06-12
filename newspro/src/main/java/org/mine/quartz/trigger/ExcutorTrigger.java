package org.mine.quartz.trigger;

import java.text.ParseException;
import java.util.HashMap;
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
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchQueueDefinitionDao;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.dao.BatchTaskExecuteDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchQueueDefinition;
import org.mine.model.BatchTaskDefinition;
import org.mine.model.BatchTaskExecute;
import org.mine.model.BatchTriggerDefinition;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
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
	private BatchQueueDefinitionDao queueDefinitionDao;
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
		loadTimingTaskData();
		loadTimingQueueData();
		logger.info("load timing data over....");
	}
	
	public void loadTimingTaskData(){
		logger.info("loadTimingTaskData begin>>>>>>>>>>>>>>>");
		try{
			List<BatchTaskDefinition> taskDefinitions = taskDefinitionDao.selectAll2(JobExcutorEnum.TASK_AUTO_RUN);
			int size = taskDefinitions.size();
			if (size > 0) {
				Map<String, Object> taskInitValue = null;
				for (BatchTaskDefinition taskDefinition : taskDefinitions) {
					Long taskID = taskDefinition.getTaskId();
					CommonUtils.initMapValue(taskInitValue = new HashMap<>(), taskDefinition.getTaskInitValue());
					List<BatchTaskExecute> taskExecutes = taskExecuteDao.selectAll1R(taskID);
					if (CommonUtils.isEmpty(taskExecutes)) {
						logger.error("No executable jobs exists on the current task[{}]", taskID);
					} else {
						for (BatchTaskExecute taskExecute : taskExecutes) {
							Long jobID = taskExecute.getExecuteJobId();
							BatchJobDefinition jobDefinition = jobDefinitionDao.selectOne1R(jobID, false);
							if (jobDefinition != null) {
								//由于jobdatamap中保存的为对象的引用, 因此需要new对象.
								ConcurrTaskDto dto = new ConcurrTaskDto();
								//赋TASK值
								dto.setTaskId(taskID);
								dto.setTaskSkipFlag(taskDefinition.getTaskSkipFlag());
								dto.setTaskInitValue(taskInitValue);
								//赋JOB值
								dto.setJobId(jobID);
								dto.setJobName(jobDefinition.getJobName());
								dto.setJobLogFlag(jobDefinition.getJobLogFlag());
								dto.setJobSkipFlag(jobDefinition.getJobSkipFlag());
								dto.setJobRunMutiFlag(jobDefinition.getJobRunMutiFlag());
								dto.setJobTimeDelayFlag(jobDefinition.getJobTimeDelayFlag());
								dto.setJobTimeDelayValue(jobDefinition.getJobTimeDelayValue());
								dto.setJobOneTime(taskExecute.getExecuteJobOneTime());
								CommonUtils.initMapValue(dto.getJobInitValue(), jobDefinition.getJobInitValue());
								registerQuartz(jobDefinition.getJobId(), jobDefinition.getJobExecutor(), jobDefinition.getJobAssociateTriggerId(), dto);
							} else {
								logger.error("The current job[{}] does not exist", jobID);
							}
						}
					}
				}
				logger.info("Loading timing job was successful!!!!!");
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("There is no timing job in the current system!!!!!");
				}
			}
		} catch (SchedulerException e) {
			logger.error("加载Quartz信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_APPRUNEXC("加载Quartz信息失败");
		}
		logger.info("loadTimingTaskData end>>>>>>>>>>>>>>>");
	}
	
	/**
	 * 加载定时任务数据
	 * @return
	 */
	public Map<JobDetailImpl, ExcutorTrigger> loadTimingQueueData(){
		logger.info("loadTimingQueueData begin>>>>>>>>>>>>>>>");
		try{
			//查询出需定时执行的任务.当前queue仅区分手动和自动.
			List<BatchQueueDefinition> queueDefinitions = queueDefinitionDao.selectAll1R(JobExcutorEnum.QUEUE_AUTO);
			if(queueDefinitions != null && queueDefinitions.size() > 0){
				for(BatchQueueDefinition queueDefinition : queueDefinitions){
					ConcurrTaskDto dto = new ConcurrTaskDto();
					CommonUtils.initMapValue(dto.getQueueInitValue(), queueDefinition.getQueueInitValue());
					registerQuartz(queueDefinition.getQueueId(), queueDefinition.getQueueExecutor(), queueDefinition.getQueueTriggerId(), dto);
				}
				logger.info("Loading timing queue was successful!!!!!");
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("There is no timing job in the current system!!!!!");
				}
			}
		} catch(SchedulerException e){
			logger.error("加载Quartz信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_APPRUNEXC("加载Quartz信息失败");
		}
		logger.info("loadTimingQueueData end>>>>>>>>>>>>>>>");
		return null;
	}
	
	/**
	 * 注册定时作业
	 * @param id
	 * @param executor
	 * @param associTrgId
	 * @param taskDto
	 * @throws SchedulerException
	 */
	public void registerQuartz(Long id, String executor, String associTrgId, ConcurrTaskDto taskDto)throws SchedulerException{
//		JobDetail detail = JobBuilder.newJob(ExecutorBase.getExcutorJob(executor)).withIdentity(ApltContanst.DEFAULT_JOB_NAME 
//		+ id, ApltContanst.DEFAULT_JOB_GROUP).usingJobData(dataMap).build();
		JobDetailImpl detailImpl = new JobDetailImpl();
		detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + id);
		detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
		detailImpl.setJobClass(ExecutorBase.getExcutorJob(executor));
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("dto", taskDto);
		detailImpl.setJobDataMap(dataMap);
		// 确保job执行完后不从jobstore中移除
		detailImpl.setDurability(true);

		scheduleJobByTriggers(detailImpl, associTrgId);
	}
	
	/**
	 * 注册服务
	 * @param detail job实例
	 * @param associateTriggerID 触发器ID
	 * @throws SchedulerException
	 */
	public void scheduleJobByTriggers(JobDetail detail, String associateTriggerID) throws SchedulerException{
		List<String> triggerIds = CommonUtils.splitStrToList(associateTriggerID, ",", false);
		if (triggerIds.size() <= 0) throw GitWebException.GIT1001(detail.getKey().toString() + ", 未设置触发器参数.");
		ExcutorTrigger trigger = definationTrigger(Long.valueOf(triggerIds.get(0)));
		/*
		 * Spring当使用bean配置触发器的形式的时候,将jobdetail放至trigger的dataMap中.
		 * 当注册时若发现未配置Job信息, Spring会从Map中自动获取jobDetail的值,注册进Schedule中.
		 * */
//		getJobDataMap().put("jobDetail", detail);
//		map.put(detailImpl, trigger);
		ExecutorBase.getSchedulerFactoryBean().getScheduler().scheduleJob(detail, trigger);
		//注册该JOB中其他触发器
		if(triggerIds.size() > 1){
			ExcutorTrigger extraTrigger = null;
			for(String triggerId : triggerIds){
				extraTrigger = definationTrigger(Long.valueOf(triggerId));
				extraTrigger.setJobKey(detail.getKey());
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
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(ApltContanst.DEFAULT_TRIGGER_NAME + triggerDefinition.getTriggerId(), ApltContanst.DEFAULT_TRIGGER_GROUP).
//			withSchedule(CronScheduleBuilder.cronSchedule(triggerDefinition.getTriggerCrontrigger())).build();
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
			//当为可暂停的JOB时
//			trigger.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
			
			return trigger;
		} catch(ParseException e){
			logger.error("加载trigger信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_TRIGGER();
		}
	}
}
