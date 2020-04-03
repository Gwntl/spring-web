package org.mine.quartz.job.run;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
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
import org.mine.quartz.QuartzStepExecutor;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.mine.quartz.dto.JobMonitorDto;
import org.mine.quartz.trigger.ExcutorTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobTaskCallable implements Runnable, InitializingBean{
	@Autowired
	BatchStepDefinitionDao stepDefinitionDao;
	@Autowired
	BatchDefineCostomDao costomDao;
	
	private static final Logger logger = LoggerFactory.getLogger(JobTaskCallable.class);
//	private static final int AVAIL_CPU = Runtime.getRuntime().availableProcessors();
	private static final int THREADMINLENGTH = 50;
	private static final int THREADMAXLENGTH = 100;
	/**
	 * 当前线程内执行任务DTO
	 */
	private ConcurrTaskDto dto;
	/**
	 * 当前执行线程内步骤内容队列
	 */
	private LinkedList<InnerNode> linkedList = new LinkedList<>();
	/**
	 * 当前执行线程内步骤ID队列
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
	 * 作业ID和历史ID关联缓存
	 */
	private static Map<Long, LinkedList<Long>> linkIdCache = new ConcurrentHashMap<>(); 
	/**
	 * 执行中的步骤缓存
	 */
	private static Map<Long, LinkedList<InnerNode>> stepsCache = new ConcurrentHashMap<>();
	/**
	 * 异步线程执行结果
	 */
	private static Map<Long, List<FutureTask<CallableResultDto>>> tasksResult = new ConcurrentHashMap<>();
	/**
	 * step执行线程池
	 */
	private static final ThreadPoolExecutor pool;
	static {
		pool = new ThreadPoolExecutor(THREADMINLENGTH, THREADMAXLENGTH, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(THREADMINLENGTH));
	}
	
	public JobTaskCallable(ConcurrTaskDto dto) {
		this.dto = dto;
	}
	@Override
	public void run() {
		BatchTimingStepLogRegister stepLogRegister = null;
		try{
			List<BatchJobExecute> executes = GitContext.getBean(BatchJobExecuteDao.class).selectAll1R(dto.getJobId());
			if(CommonUtils.isEmpty(executes)){
				logger.error("The current job[{}] does not exists steps!!!!!", dto.getJobId());
			} else {
				try{
					for(BatchJobExecute execute : executes){
						BatchStepDefinition stepDefinition = stepDefinitionDao.selectOne1R(execute.getExecuteStepId(), true);
						GitContext.init(stepDefinition.getStepLogMdcValue());
						CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
						Long stepExecutionId = costomDao.getBatchSequence("timing_sequence_step");
						stepLogRegister.setStepExecutionId(stepExecutionId);
						stepLogRegister = GitContext.doIndependentTransActionControl(
								new BatchOperator<BatchTimingStepLogRegister, BatchTimingStepLogRegister>() {

							@Override
							public BatchTimingStepLogRegister call(BatchTimingStepLogRegister input) {
								input.setStepJobId(stepDefinition.getStepId());
								input.setStepJobName(stepDefinition.getStepName());
								input.setStepAssociateJobId(dto.getJobId());
								input.setStepStartTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
								input.setStepJobStatus(JobExcutorEnum.STARTED.getId());
								input.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
								input.setValidStatus(JobContanst.VALID_STATUS_0);
								GitContext.getBean(BatchTimingStepLogRegisterDao.class).insertOne(input);
								return input;
							}},stepLogRegister);
						
						QuartzStepExecutor stepExecutor = ExcutorTrigger.quartzStepCache.get(stepDefinition.getStepActuator());
						linkedList.add(new InnerNode(stepExecutor, stepExecutor.grouping(stepExcutionInfo(stepDefinition))));
						linkedIDList.add(stepExecutionId);
						
						GitContext.doIndependentTransActionControl(new BatchOperator<Integer, BatchTimingStepLogRegister>() {

							@Override
							public Integer call(BatchTimingStepLogRegister input) {
								input.setStepJobStatus(JobExcutorEnum.COMPLETING.getId());
								return GitContext.getBean(BatchTimingStepLogRegisterDao.class).updateOne1R(input);
							}
						}, stepLogRegister);
					}
				} catch(Exception e){
					throw GitWebException.GIT1001("处理step时出错....");
				}
				Long jobHistoryId = dto.getHistoryId();
				//将当前执行job的历史ID放入缓存中,防止quartz中job的重复执行而导致的缓存脏数据.
				stepsCache.put(jobHistoryId, linkedList);
				linkIdCache.put(jobHistoryId, linkedIDList);
				JobMonitorDto jobMonitor = new JobMonitorDto();
				jobMonitor.setOneTime(dto.getJobOneTime());
				jobMonitor.setJobId(dto.getJobId());
				jobMonitor.setHistoryId(jobHistoryId);
				commonCache.put(jobHistoryId, jobMonitor);
				//先执行第一步
				poolExecute(jobHistoryId);
			}
		} catch(Throwable a){
			throw GitWebException.GIT1001("处理step时出错....");
		}
	}
	
	/**
	 * 公共阈值
	 * @param stepDefinition
	 * @return
	 */
	public Map<String, Object> stepExcutionInfo(BatchStepDefinition stepDefinition){
		int stepSize = dto.getStepInitValue().size();
		int jobSize = dto.getJobInitValue().size();
		int taskSize = dto.getTaskInitValue().size();
		Map<String, Object> info = new HashMap<>(CommonUtils.initialSize(stepSize + jobSize + taskSize));
		if(stepSize > 0){
			info.putAll(dto.getStepInitValue());
		}
		if(jobSize > 0){
			info.putAll(dto.getJobInitValue());
		}
		if(taskSize > 0){
			info.putAll(dto.getTaskInitValue());
		}
		info.put("jobId", dto.getJobId());
		return info;
	}

	/**
	 * 执行下一个step
	 * @param node
	 * @param jobId
	 */
	private void poolExecute(Long jobHistoryId){
		InnerNode node = stepsCache.get(jobHistoryId).removeFirst();
		if(node != null){
			List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
			for(Object obj : node.getList()){
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
									BatchTimingStepLogRegister logRegister = GitContext.getBean(BatchTimingStepLogRegisterDao.class)
											.selectOne1R(stepHistoryIds.getFirst(), false);
									if(logRegister != null){
										Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
										while(taskIterator.hasNext()){
											FutureTask<CallableResultDto> task = taskIterator.next();
											if(task.isDone()){
												if(task.isCancelled()){
													message = JobExcutorEnum.CANCEL.getId();
													isCancle = true;
													break;
												} else {
													try{
														CallableResultDto result = task.get(1, TimeUnit.SECONDS);
														taskResult = result.isResult();
														message = result.getMseeage();
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
												status = JobExcutorEnum.CANCEL.getId();
												LinkedList<InnerNode> cancelSteps = stepsCache.remove(jobHistoryId);
												linkIdCache.remove(jobHistoryId);
												isChange = true;
												message = "it is been canceled";
												logger.warn("Cancelled steps : {}", CommonUtils.toString(cancelSteps));
											} else {
												if(taskResult){
													status = JobExcutorEnum.SUCCESS.getId();
													if(stepsCache.size() > 0){
														LinkedList<InnerNode> nodes = stepsCache.get(jobHistoryId);
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
													status = JobExcutorEnum.FAILED.getId();
													stepsCache.remove(jobHistoryId);
													linkIdCache.remove(jobHistoryId);
													isChange = true;
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
												poolExecute(jobHistoryId);
											}
											if(isChange){
												iterator.remove();
												notifyJob(status, message, jobHistoryId);
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
									.selectOne1R(jobMonitor.getHistoryId(),false);
							if(register != null){
								register.setTimingJobStatus(status);
								if (JobExcutorEnum.SUCCESS.getId().equals(status)
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
										return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(t);
									}
								}, register);
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
	
	class InnerThread implements Callable<CallableResultDto>{
		QuartzStepExecutor executor;
		Object obj;
		InnerThread(QuartzStepExecutor executor, Object obj) {
			this.executor = executor;
			this.obj = obj;
		}
		@Override
		public CallableResultDto call() throws Exception {
			CallableResultDto dto = new CallableResultDto();
			try{
				executor.call(obj);
				dto.setResult(true);
			} catch(Exception e){
				dto.setResult(false);
				dto.setMseeage(e.getMessage());
			}
			return dto;
		}
	}
	
	class InnerNode{
		QuartzStepExecutor executor;
		List<Object> list;
		InnerNode(QuartzStepExecutor executor, List<Object> list) {
			this.executor = executor;
			this.list = list;
		}
		/**
		 * @return the executor
		 */
		public QuartzStepExecutor getExecutor() {
			return executor;
		}
		/**
		 * @param executor the executor to set
		 */
		public void setExecutor(QuartzStepExecutor executor) {
			this.executor = executor;
		}
		/**
		 * @return the list
		 */
		public List<Object> getList() {
			return list;
		}
		/**
		 * @param list the list to set
		 */
		public void setList(List<Object> list) {
			this.list = list;
		}
		@Override
		public String toString() {
			return "InnerNode [executor=" + executor.getClass() + ", list=" + CommonUtils.toString(list) + "]";
		}
		
	}
	
	public static String toString(List<InnerNode> nodes){
		StringBuffer buffer = new StringBuffer();
		Iterator<InnerNode> iterator = nodes.iterator();
		while(iterator.hasNext()){
			buffer.append(iterator.next().toString());
			if(iterator.hasNext()){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
}