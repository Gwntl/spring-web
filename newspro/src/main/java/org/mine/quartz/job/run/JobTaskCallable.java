package org.mine.quartz.job.run;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mine.aplt.Cache.AsyncCache;
import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobExecuteDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.dao.BatchTimingStepLogRegisterDao;
import org.mine.dao.BatchTimingTaskLogRegisterDao;
import org.mine.dao.custom.AutoSchedulerJobDao;
import org.mine.dao.custom.BatchDefineCostomDao;
import org.mine.model.BatchJobExecute;
import org.mine.model.BatchStepDefinition;
import org.mine.model.BatchTimingStepLogRegister;
import org.mine.model.BatchTimingTaskLogRegister;
import org.mine.quartz.JobExcutorFactory;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.mine.quartz.dto.GroupInputDto;
import org.mine.quartz.dto.JobMonitorDto;
import org.mine.quartz.trigger.ExcutorTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class JobTaskCallable implements Runnable, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(JobTaskCallable.class);
	private static final int AVAIL_CPU = Runtime.getRuntime().availableProcessors();
	private static final int THREADMINLENGTH = AVAIL_CPU << 2;
	private static final int THREADMAXLENGTH = AVAIL_CPU << 3;
	/**
	 * 当前线程内执行任务DTO
	 */
	private ConcurrTaskDto dto;
	/**
	 * 当前执行线程内步骤内容队列--记录当前Step的执行信息
	 */
	private LinkedList<InnerStepNode> linkedList = new LinkedList<>();
	/**
	 * 当前执行线程内步骤ID队列--记录当前Step在日志表中的ID
	 */
	private LinkedList<Long> linkedIDList = new LinkedList<>();
	/**
	 * 监控线程
	 */
	private Thread monitor_thread = null;
	/**
	 * JOB执行监视器队列
	 */
	private static BlockingQueue<JobMonitorDto> monitorQueue = new ArrayBlockingQueue<>(THREADMAXLENGTH);
	/**
	 * 执行线程
	 */
	private Thread threadExcu = null;
	/**
	 * 执行公共值
	 */
	private static Map<Long, JobMonitorDto> commonCache = new ConcurrentHashMap<>();
	/**
	 * 作业ID和历史ID关联缓存-<任务ID, StepID>
	 */
	private static Map<Long, LinkedList<Long>> linkIdCache = new ConcurrentHashMap<>(); 
	/**
	 * 执行中的步骤缓存-<任务ID, Step执行信息>
	 */
	private static Map<Long, LinkedList<InnerStepNode>> stepsCache = new ConcurrentHashMap<>();
	/**
	 * 异步线程执行结果
	 */
	private static Map<Long, List<FutureTask<CallableResultDto>>> tasksResult = new ConcurrentHashMap<>();
	/**
	 * step执行线程池
	 */
	private static final ThreadPoolExecutor pool;
	static {
		pool = new ThreadPoolExecutor(THREADMINLENGTH, THREADMAXLENGTH, 1, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>((THREADMINLENGTH + THREADMAXLENGTH) / 2), new JobExcutorFactory.CustomThreadFactory("task-call-"));
	}
	
	public JobTaskCallable() {}
	
	public JobTaskCallable(ConcurrTaskDto dto) {
		this.dto = dto;
	}
	/**
	 * <p>Description: </p>
	 * <p>Title: run</p>
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		BatchTimingStepLogRegister stepLogRegister = null;
		try{
			//保存当前执行的JOB_ID
			Long jobID = dto.getJobId();
			List<BatchJobExecute> executes = GitContext.getBean(BatchJobExecuteDao.class).selectAll1R(jobID);
			if(CommonUtils.isEmpty(executes)){
				logger.error("The current job[{}] does not exists steps!!!!!", jobID);
			} else {
				try{
					for(BatchJobExecute execute : executes){
						BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(execute.getExecuteStepId(), true);
						MDCCache.set(stepDefinition.getStepId(), stepDefinition.getStepLogMdcValue());
						CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
						Long stepExecutionId = GitContext.getBean(BatchDefineCostomDao.class).getBatchSequence("timing_sequence_step");
						stepLogRegister = new BatchTimingStepLogRegister();
						stepLogRegister.setStepExecutionId(stepExecutionId);
						stepLogRegister = GitContext.doIndependentTransActionControl(
								new BatchOperator<BatchTimingStepLogRegister, BatchTimingStepLogRegister>() {

							@Override
							public BatchTimingStepLogRegister call(BatchTimingStepLogRegister input) {
								input.setStepJobId(stepDefinition.getStepId());
								input.setStepJobName(stepDefinition.getStepName());
								input.setStepAssociateJobId(jobID);
								input.setStepJobStatus(JobExcutorEnum.NEW.getValue());
								input.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
								input.setValidStatus(JobContanst.VALID_STATUS_0);
								GitContext.getBean(BatchTimingStepLogRegisterDao.class).insertOne(input);
								return input;
							}}, stepLogRegister);
						
						BaseServiceTasketExcutor excutor = ExcutorTrigger.getExecutor(stepDefinition.getStepActuator());
						linkedList.add(new InnerStepNode(excutor, excutor.grouping(stepExcutionInfo(stepDefinition))));
						linkedIDList.add(stepExecutionId);
					}
				} catch(Exception e){
					logger.error(GitWebException.getStackTrace(e));
					throw GitWebException.GIT1001("处理step时出错....");
				}
				Long jobHistoryId = dto.getHistoryId();
				//将当前执行job的历史ID放入缓存中,防止quartz中job的重复执行而导致的缓存脏数据.
				stepsCache.put(jobHistoryId, linkedList);
				linkIdCache.put(jobHistoryId, linkedIDList);
				JobMonitorDto jobMonitor = new JobMonitorDto();
				jobMonitor.setOneTime(dto.getJobOneTime());
				jobMonitor.setJobId(jobID);
				jobMonitor.setHistoryId(jobHistoryId);
				commonCache.put(jobHistoryId, jobMonitor);
				//先执行第一步
				poolExecute(jobHistoryId, null);
			}
		} catch(Throwable a){
			logger.error(GitWebException.getStackTrace(a));
			throw GitWebException.GIT1001("处理step时出错....");
		}
	}
	
	/**
	 * 公共阈值
	 * @param stepDefinition
	 * @return
	 */
	public GroupInputDto stepExcutionInfo(BatchStepDefinition stepDefinition){
		GroupInputDto inputDto = new GroupInputDto();
		inputDto.setStepInitValue(dto.getStepInitValue());
		inputDto.setJobInitValue(dto.getJobInitValue());
		inputDto.setTaskInitValue(dto.getTaskInitValue());
		inputDto.setStepId(stepDefinition.getStepId());
		inputDto.setJobId(dto.getJobId());
		inputDto.setStepActuator(stepDefinition.getStepActuator());
		return inputDto;
	}

	/**
	 * 执行下一个step
	 * @param node
	 * @param jobId
	 */
	private void poolExecute(Long jobHistoryId, Map<String, Object> deliverValueMap){
		InnerStepNode node = stepsCache.get(jobHistoryId).removeFirst();
		if(node != null){
			//更新下一步的起始时间及执行状态.
			GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Long>() {

				@Override
				public Integer call(Long input) {
					BatchTimingStepLogRegisterDao dao = GitContext.getBean(BatchTimingStepLogRegisterDao.class);
					BatchTimingStepLogRegister logRegister = dao.selectOne1R(linkIdCache.get(input).getFirst(), false);
					if (logRegister != null) {
						logRegister.setStepStartTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
						logRegister.setStepJobStatus(JobExcutorEnum.COMPLETING.getValue());
						dao.updateOne1R(logRegister);
					}
					return null;
				}
			}, jobHistoryId);
			
			List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
			for(Map<String, Object> obj : node.getList()){
				if(CommonUtils.isNotEmpty(deliverValueMap)) obj.putAll(deliverValueMap);
				FutureTask<CallableResultDto> futureTask = new FutureTask<>(
						new JobTaskCallable.InnerThread(node.getExecutor(), obj));
				pool.execute(futureTask);
				futureTasks.add(futureTask);
			}
			tasksResult.put(jobHistoryId, futureTasks);
		} else {
			if(linkIdCache.containsKey(jobHistoryId)) linkIdCache.remove(jobHistoryId);
		}
	}
	
	/**
	 * 通知job更改状态
	 * @param status
	 * @param errmsg
	 * @param jobHistoryId
	 */
	public void notifyJob(String status, String errmsg, Long jobHistoryId){
		JobMonitorDto dto = commonCache.get(jobHistoryId);
		if(dto != null){
			dto.setStatus(status);
			dto.setErrmsg(errmsg);
			try {
				//当队列满的时候,需等待队列可插入
				monitorQueue.put(dto);
				commonCache.remove(jobHistoryId);
			} catch (InterruptedException e) {
				logger.error("jobProcessorAfter interrupted ........");
			}
		} else {
			logger.error(
					"The absence of a job[{}] in the commonCache will result in the current job status not being updated",
					jobHistoryId);
		}
	}
	
	/**
	 * 取消运行中的job, 需执行器中响应中断. 当执行器中使用sleep方法时, 在捕获异常之后若需要执行其他逻辑时, 需要再执行一次 interrupt()方法. 由于sleep会清除中断状态
	 * 需考虑分段事务提交
	 * @param jobHistoryId
	 * @return
	 */
	public boolean cancel(Long jobHistoryId){
		boolean res = false;
		List<FutureTask<CallableResultDto>> tasks = tasksResult.get(jobHistoryId);
		if(tasks != null && tasks.size() > 0){
			for(FutureTask<CallableResultDto> task : tasks){
				if (!task.isDone()) {
					if(res = task.cancel(true)){
						break;
					}
				}
			}
		}
		return res;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(threadExcu == null){
			threadExcu = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						try{
							if(tasksResult.size() > 0){
								Iterator<Long> iterator = tasksResult.keySet().iterator();
								while(iterator.hasNext()){
									Long jobHistoryId = iterator.next();
									if (!stepsCache.containsKey(jobHistoryId) || !linkIdCache.containsKey(jobHistoryId)
											|| !commonCache.containsKey(jobHistoryId)) {
										stepsCache.remove(jobHistoryId);
										linkIdCache.remove(jobHistoryId);
										commonCache.remove(jobHistoryId);
										iterator.remove();
										break;
									}
									//当前job内的所有step是否全部执行完毕
									boolean allSuccess = true;
									//当前job是否被取消
									boolean isCancle = false;
									//step执行是否成功
									boolean taskResult = true;
									//是否更改JOB历史状态
									boolean isChange = false;
									//执行下一个step
									boolean nextStep = false;
									String message = "";
									List<FutureTask<CallableResultDto>> tasks = tasksResult.get(jobHistoryId);
									LinkedList<Long> stepHistoryIds = linkIdCache.get(jobHistoryId);
									//当前是为了防止任务执行过快而此时任务初始化过程还未结束,导致更新历史信息时未取到数据不更新历史状态.
									Long stepId = stepHistoryIds.getFirst();
									BatchTimingStepLogRegister logRegister = AsyncCache.get(stepId);
									if(logRegister != null){
										Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
										Map<String, Object> deliverValueMap = null;
										//当存在任务并且当前任务为完成状态时.
										while(taskIterator.hasNext() && allSuccess){
											FutureTask<CallableResultDto> task = taskIterator.next();
											if(task.isDone()){
												if(task.isCancelled()){
													isCancle = true;
													break;
												} else {
													try{
														CallableResultDto result = task.get(1, TimeUnit.SECONDS);
														taskResult = result.isResult();
														message = result.getMseeage();
														deliverValueMap = result.getMap();
													} catch(InterruptedException | ExecutionException | TimeoutException e){
														message = e.getMessage();
														taskResult = false;
													}
												}
											} else {
												allSuccess = false;
											}
										}
										String status = "";
										if(allSuccess){
											if(isCancle){
												status = JobExcutorEnum.CANCEL.getValue();
												stepsCache.remove(jobHistoryId);
												LinkedList<Long> steps = linkIdCache.remove(jobHistoryId);
												isChange = true;
												message = "The current step have been cancelled.";
												updateTimingStepLogStatus(steps, status, "The previous step have been cancelled.");
												logger.warn("Cancelled steps : {}", CommonUtils.toString(steps));
											} else {
												if(taskResult){
													status = JobExcutorEnum.SUCCESS.getValue();
													if(stepsCache.size() > 0){
														LinkedList<InnerStepNode> nodes = stepsCache.get(jobHistoryId);
														//当存在下一步骤时,执行下一个步骤内容
														if(nodes.size() > 0){
															//检查缓存数据正确性
															if((stepHistoryIds.size() - nodes.size()) == 1){
																nextStep = true;
															} else {
																logger.error("The current execution data is inconsistent, please check!!! stepHistoryIds: {}, nodes: {}",
																		CommonUtils.toString(stepHistoryIds), JobTaskCallable.toString(nodes));
															}
														} else {
															//表示当前job内的所有step执行完毕
															isChange = true;
														}
													}
												} else {
													//当job内某个step失败时,将job对应historyID缓存清除
													status = JobExcutorEnum.FAILED.getValue();
													stepsCache.remove(jobHistoryId);
													LinkedList<Long> steps = linkIdCache.remove(jobHistoryId);
													isChange = true;
													updateTimingStepLogStatus(steps, status, "The previous step have been failed.");
												}
											}
											logRegister.setStepJobStatus(status);
											logRegister.setStepJobErrmsg(message);
											GitContext.doIndependentTransActionControl(new BatchOperator<Integer, BatchTimingStepLogRegister>() {
												@Override
												public Integer call(BatchTimingStepLogRegister t) {
													t.setStepEndTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
													return GitContext.getBean(BatchTimingStepLogRegisterDao.class).updateOne1R(t);
												}
											}, logRegister);
											if(nextStep){
												iterator.remove();
												//当step成功时,将对应的步骤ID清除
												try{
													stepHistoryIds.removeFirst();
												} catch(NoSuchElementException e){
													logger.error("Failed to clear elements in the list, error message : {}", 
															GitWebException.getStackTrace(e));
												}
												poolExecute(jobHistoryId, deliverValueMap);
											}
											if(isChange){
												iterator.remove();
												notifyJob(status, message, jobHistoryId);
												AsyncCache.remove(stepId);
											}
										}
									}
								}
							} else {
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e) {
									logger.error("[EXECUTION] The sleep thread is been interrupted.");
								}
							}
						} catch(Throwable e){
							logger.error(MineException.getStackTrace(e));
							logger.error("[EXECUTION] The current task value setting exception!!!!!");
						}
					}
				}
			}, "EXECUTION_THREAD");
			threadExcu.start();
		}
		
		if(monitor_thread == null){
			monitor_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							JobMonitorDto jobMonitor = monitorQueue.take();
							String status = jobMonitor.getStatus();
							BatchTimingTaskLogRegister register = GitContext.getBean(BatchTimingTaskLogRegisterDao.class)
									.selectOne1R(jobMonitor.getHistoryId(), false);
							if(register != null){
								register.setTimingJobStatus(status);
								if (JobExcutorEnum.SUCCESS.getValue().equals(status)
										&& jobMonitor.getOneTime() == JobContanst.ONE_TIME_0) {
									GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Long>() {
										@Override
										public Integer call(Long t) {
											return GitContext.getBean(AutoSchedulerJobDao.class).updateTaskExectorStatus(t, JobContanst.VALID_STATUS_D);
										}
									}, jobMonitor.getJobId());
								}
								register.setTimingJobErrmsg(jobMonitor.getErrmsg());
								GitContext.doIndependentTransActionControl(new BatchOperator<Integer, BatchTimingTaskLogRegister >() {
									@Override
									public Integer call(BatchTimingTaskLogRegister  t) {
										t.setTimingEndTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
										return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(t);
									}
								}, register);
								//更新完毕,增加task.
								JobQueueLogic.queueMonitor.add(null);
							}
						} catch (InterruptedException e) {
							logger.error("Failed to get the return value from the thread of execution(MONITOR_THREAD)!!!!!!");
						}
					}
				}
			}, "MONITOR_THREAD");
			monitor_thread.start();
		}
	}
	
	/**
	 * 内部执行器
	 * @ClassName: InnerThread
	 * @author: wntl
	 * @date: 2020年4月29日 下午8:36:27
	 */
	class InnerThread implements Callable<CallableResultDto>{
		/**
		 * 执行器
		 * @Fields executor
		 */
		BaseServiceTasketExcutor executor;
		/**
		 * 执行参数
		 * @Fields obj
		 */
		Map<String, Object> obj;
		
		InnerThread(BaseServiceTasketExcutor executor, Map<String, Object> obj) {
			this.executor = executor;
			this.obj = obj;
		}
		@Override
		public CallableResultDto call() throws Exception {
			CallableResultDto dto = new CallableResultDto();
			try{
				GitContext.init(MDCCache.get((long)obj.get("stepId")));
				dto.setMap(executor.handler(obj));
				dto.setResult(true);
			} catch(Throwable e){
				logger.error("error message : {}", MineException.getStackTrace(e));
				dto.setResult(false);
				dto.setMseeage(e.getMessage());
			}
			return dto;
		}
	}
	
	/**
	 * 内部执行节点
	 * @author: wntl
	 * @date: 2020年4月29日 下午8:06:05
	 */
	class InnerStepNode{
		/**
		 * 执行器
		 * @Fields executor
		 */
		BaseServiceTasketExcutor executor;
		/**
		 * 分组信息
		 * @Fields list
		 */
		List<Map<String, Object>> list;
		
		InnerStepNode(BaseServiceTasketExcutor executor, List<Map<String, Object>> list) {
			this.executor = executor;
			this.list = list;
		}
		/**
		 * 执行器
		 * @return the executor
		 */
		public BaseServiceTasketExcutor getExecutor() {
			return executor;
		}
		/**
		 * 执行器
		 * @param executor the executor to set
		 */
		public void setExecutor(BaseServiceTasketExcutor executor) {
			this.executor = executor;
		}
		/**
		 * 分组信息
		 * @return the list
		 */
		public List<Map<String, Object>> getList() {
			return list;
		}
		/**
		 * 分组信息
		 * @param list the list to set
		 */ 
		public void setList(List<Map<String, Object>> list) {
			this.list = list;
		}
		@Override
		public String toString() {
			return "InnerNode [executor=" + executor.getClass() + ", list=" + CommonUtils.toString(list) + "]";
		}
		
	}
	
	/**
	 * 当作业处理异常时,更新后续步骤状态.
	 * @param steps
	 * @param errmsg
	 */
	void updateTimingStepLogStatus(List<Long> steps, String status, String errmsg){
		if(steps.size() > 1){
			GitContext.doIndependentTransActionControl(new BatchOperator<Integer, List<Long>>() {

				@Override
				public Integer call(List<Long> input) {
					for(int i = 1, size = steps.size(); i < size; i++){
						GitContext.getBean(BatchDefineCostomDao.class).updateStepLogExecuStatus(steps.get(i), 
								status, errmsg);
					}
					return null;
				}
			}, steps);
		}
	}
	
	public static String toString(List<InnerStepNode> nodes){
		StringBuffer buffer = new StringBuffer();
		Iterator<InnerStepNode> iterator = nodes.iterator();
		while(iterator.hasNext()){
			buffer.append(iterator.next().toString());
			if(iterator.hasNext()){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
}