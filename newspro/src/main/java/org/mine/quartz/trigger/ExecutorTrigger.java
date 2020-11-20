package org.mine.quartz.trigger;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.*;
import org.mine.model.*;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.PreProcessInfo;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExecutorTrigger extends CronTriggerImpl implements InitializingBean{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1379631809538539447L;
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutorTrigger.class);
	
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

	private int isPauseFlag;
	
	/**
	 * @return the isPauseFlag
	 */
	public int getIsPauseFlag() {
		return isPauseFlag;
	}

	/**
	 * @param isPauseFlag the isPauseFlag to set
	 */
	public void setIsPauseFlag(int isPauseFlag) {
		this.isPauseFlag = isPauseFlag;
	}

	protected static Map<String, BaseServiceTaskletExecutor> quartzStepCache = new ConcurrentHashMap<>(1 >> 5);
	
	public static BaseServiceTaskletExecutor getExecutor(String key) {
		BaseServiceTaskletExecutor executor = null;
		if (quartzStepCache.containsKey(key)) {
			executor = quartzStepCache.get(key);
		} else {
			Object obj = GitContext.getBean(key);
			if(obj != null && obj instanceof BaseServiceTaskletExecutor){
				quartzStepCache.put(key, (BaseServiceTaskletExecutor)obj);
				executor = (BaseServiceTaskletExecutor)obj;
			}
		}
		if (executor == null) {
			throw new NullPointerException("未获取到STEP执行器[" + key + "]");
		}
		return executor;
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
		quartzStepCache.putAll(context.getBeansOfType(BaseServiceTaskletExecutor.class));
		loadTimingJobData();
//		loadTimingTaskData();
//		loadTimingQueueData();
		logger.info("load timing data over....");
	}

	/**
	* 加载定时JOB信息
	* @return: void
	* @Author: wntl
	* @Date: 2020/8/13
	*/
	public void loadTimingJobData() {
		logger.info("loadTimingJobData begin>>>>>>>>>>>>>>>");
		try{
			List<BatchJobDefinition> jobDefinitions = jobDefinitionDao.selectAll1R(JobExecutorEnum.TASK_AUTO_RUN);
			if (CommonUtils.isNotEmpty(jobDefinitions)) {
				String jobID = "";
				for (BatchJobDefinition jobDefinition : jobDefinitions) {
					jobID = jobDefinition.getJobId();
					if (jobDefinition.getJobPauseFlag() == this.isPauseFlag) {
						//TODO 由于jobdatamap中保存的为对象的引用, 因此需要new对象.
						ExecuteTaskDto dto = new ExecuteTaskDto();
						//赋JOB值
						dto.setJobId(jobID);
						dto.setJobName(jobDefinition.getJobName());
						dto.setJobLogFlag(jobDefinition.getJobLogFlag());
						dto.setJobSkipFlag(jobDefinition.getJobSkipFlag());
						dto.setJobRunMutiFlag(jobDefinition.getJobRunMutiFlag());
						dto.setJobTimeDelayFlag(jobDefinition.getJobTimeDelayFlag());
						dto.setJobTimeDelayValue(jobDefinition.getJobTimeDelayValue());
						CommonUtils.initMapValue(dto.getJobInitValue(), jobDefinition.getJobInitValue());
						registerQuartz(jobDefinition.getJobId(), jobDefinition.getJobExecutor(), jobDefinition.getJobAssociateTriggerId(), dto);
					} else {
						logger.error("The current job[{}] does not exist configs when isPauseFlag is {}", jobID, this.isPauseFlag);
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
		logger.info("loadTimingJobData end>>>>>>>>>>>>>>>");
	}

	/**
	* 加载定时任务信息
	* @return: void
	* @Author: wntl
	* @Date: 2020/8/13
	*/
	public void loadTimingTaskData() {
		logger.info("loadTimingTaskData begin>>>>>>>>>>>>>>>");
		try{
			List<BatchTaskDefinition> taskDefinitions = taskDefinitionDao.selectAll1(JobExecutorEnum.TASK_AUTO_RUN);
			if (CommonUtils.isNotEmpty(taskDefinitions) && this.isPauseFlag == 1) {
				for (BatchTaskDefinition taskDefinition : taskDefinitions) {
					ExecuteTaskDto dto = new ExecuteTaskDto();
					dto.setTaskId(taskDefinition.getTaskId());
					dto.setTaskName(taskDefinition.getTaskName());
					List<BatchTaskExecute> executes = GitContext.getBean(BatchTaskExecuteDao.class).selectAll1R(dto.getTaskId());
					for (BatchTaskExecute execute : executes) {
						if (CommonUtils.isNotEmpty(execute.getExecuteJobDepends())) {
							if (execute.getExecuteJobDepends().indexOf(execute.getExecuteJobId()) != -1) {
								throw GitWebException.GIT_DEPEND_ERROR(execute.getExecuteJobId() + "依赖于自身.");
							}
							if (PreProcessInfo.isDependent(execute.getExecuteJobId(), execute.getExecuteJobDepends())) {
								throw new BeanCreationException(execute.getExecuteTaskId(), execute.getExecuteJobId(),
										"Circular depends-on relationship between '" + execute.getExecuteJobId()
												+ "' and '" + execute.getExecuteJobDepends() + "'");
							}
							PreProcessInfo.setDependJob(execute.getExecuteJobId(), execute.getExecuteJobDepends());
						}
					}

					CommonUtils.initMapValue(dto.getTaskInitValue(), taskDefinition.getTaskInitValue());
					registerQuartz(taskDefinition.getTaskId(), taskDefinition.getTaskExecutor(), taskDefinition.getTaskAssociateTriggerId(), dto);
				}
				logger.info("Loading timing task was successful!!!!!");
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("There is no timing task in the current system!!!!!");
				}
			}
		} catch (SchedulerException e) {
			logger.error("加载Quartz信息失败: {}" + MineBizException.getStackTrace(e));
			throw GitWebException.GIT_APPRUNEXC("加载Quartz信息失败");
		}
		logger.info("loadTimingTaskData end>>>>>>>>>>>>>>>");
	}
	
	/**
	 * 加载定时队列QUEUE数据
	 * @return
	 */
	public Map<JobDetailImpl, ExecutorTrigger> loadTimingQueueData() {
		logger.info("loadTimingQueueData begin>>>>>>>>>>>>>>>");
		try{
			//查询出需定时执行的任务.当前queue仅区分手动和自动.
			List<BatchQueueDefinition> queueDefinitions = queueDefinitionDao.selectAll1R(JobExecutorEnum.QUEUE_AUTO);
			if (queueDefinitions != null && queueDefinitions.size() > 0) {
				for(BatchQueueDefinition queueDefinition : queueDefinitions){
					ExecuteTaskDto dto = new ExecuteTaskDto();
					dto.setQueueId(queueDefinition.getQueueId());
					CommonUtils.initMapValue(dto.getQueueInitValue(), queueDefinition.getQueueInitValue());
					registerQuartz(queueDefinition.getQueueId(), queueDefinition.getQueueExecutor(), queueDefinition.getQueueTriggerId(), dto);
				}
				logger.info("Loading timing queue was successful!!!!!");
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("There is no timing queue in the current system!!!!!");
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
	public void registerQuartz(String id, String executor, String associTrgId, ExecuteTaskDto taskDto) throws SchedulerException {
//		JobDetail detail = JobBuilder.newJob(ExecutorBase.getExcutorJob(executor)).withIdentity(ApltContanst.DEFAULT_JOB_NAME 
//		+ id, ApltContanst.DEFAULT_JOB_GROUP).usingJobData(dataMap).build();
		JobDetailImpl detailImpl = new JobDetailImpl();
		detailImpl.setName(ApltContanst.DEFAULT_JOB_NAME + id);
		detailImpl.setKey(new JobKey(detailImpl.getName(), ApltContanst.DEFAULT_JOB_GROUP));
		if (CommonUtils.isNotEmpty(executor)) {
			detailImpl.setJobClass(ExecutorBase.getExecutorJob(executor));
		} else {
			throw GitWebException.GIT1002("执行器");
		}
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
	public void scheduleJobByTriggers(JobDetail detail, String associateTriggerID) throws SchedulerException {
		List<String> triggerIds = CommonUtils.splitStrToList(associateTriggerID, ",", false);
		if (triggerIds == null || triggerIds.size() <= 0) throw GitWebException.GIT1001(detail.getKey().toString() + ", 未设置触发器参数.");
		ExecutorTrigger trigger = definationTrigger(triggerIds.get(0));
		/*
		 * Spring当使用bean配置触发器的形式的时候,将jobdetail放至trigger的dataMap中.
		 * 当注册时若发现未配置Job信息, Spring会从Map中自动获取jobDetail的值,注册进Schedule中.
		 * */
//		getJobDataMap().put("jobDetail", detail);
//		map.put(detailImpl, trigger);
		SchedulerFactoryBean factoryBean = this.isPauseFlag == 0 ? ExecutorBase.getDynamicScheduler() : ExecutorBase.getSchedulerFactoryBean();
		Scheduler scheduler = factoryBean.getScheduler();
		scheduler.scheduleJob(detail, trigger);
		//注册该JOB中其他触发器
		if (triggerIds.size() > 1) {
			ExecutorTrigger extraTrigger = null;
			for(String triggerId : triggerIds){
				extraTrigger = definationTrigger(triggerId);
				extraTrigger.setJobKey(detail.getKey());
				scheduler.scheduleJob(extraTrigger);
			}
		}
	}
	
	/**
	 * 定义触发器
	 * @param triggerId
	 * @return ExcutorTrigger
	 */
	public ExecutorTrigger definationTrigger(String triggerId) {
		try{
			BatchTriggerDefinition triggerDefinition = triggerDefinitionDao.selectOne1R(triggerId, false);
			if (CommonUtils.isEmpty(triggerDefinition.getTriggerCrontrigger())) {
				throw GitWebException.GIT1002("batch_trigger_conf.trigger_crontrigger 触发器参数");
			}
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(ApltContanst.DEFAULT_TRIGGER_NAME + triggerDefinition.getTriggerId(), ApltContanst.DEFAULT_TRIGGER_GROUP).
//			withSchedule(CronScheduleBuilder.cronSchedule(triggerDefinition.getTriggerCrontrigger())).build();
			ExecutorTrigger trigger = new ExecutorTrigger();
			trigger.setName(ApltContanst.DEFAULT_TRIGGER_NAME + triggerDefinition.getTriggerId());
			trigger.setGroup(ApltContanst.DEFAULT_TRIGGER_GROUP);
			trigger.setCronExpression(triggerDefinition.getTriggerCrontrigger());
			if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerStartTime())) {
				trigger.setStartTime(CommonUtils.stringToDate(triggerDefinition.getTriggerStartTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			if (CommonUtils.isNotEmpty(triggerDefinition.getTriggerEndTime())) {
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
