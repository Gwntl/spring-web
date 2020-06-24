package org.mine.quartz.job.run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchExecutorSeqlogDao;
import org.mine.dao.BatchJobExecuteDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.model.BatchExecutorSeqlog;
import org.mine.model.BatchJobExecute;
import org.mine.model.BatchStepDefinition;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.mine.quartz.dto.GroupInputDto;
import org.mine.quartz.trigger.ExcutorTrigger;
import org.springframework.stereotype.Component;

/**
 * @Description: 直接调用作业
 * @ClassName: JobDirectCall
 * @author: wntl
 * @date: 2020年6月23日 下午5:19:13
 */
@Component
public class JobDirectCall extends JobCall {
	
	private ConcurrTaskDto dto;
	
	/**
	 * 异步线程执行结果
	 */
	private static Map<Long, List<FutureTask<CallableResultDto>>> tasksResult = new ConcurrentHashMap<>();
	
	private Thread perform = null;
	/**
	 * 执行中的步骤缓存-<任务ID, Step执行信息>
	 */
	private static Map<Long, LinkedList<InnerStepNode>> stepsCache = new ConcurrentHashMap<>();
	
	private static final ThreadPoolExecutor poolExecutor;
	static {
		poolExecutor = new ThreadPoolExecutor(ApltContanst.CPU_COUNT << 1, ApltContanst.CPU_COUNT << 1,
				5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(ApltContanst.CPU_COUNT << 8));
	}
	/**
	 * @return the dto
	 */
	public ConcurrTaskDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(ConcurrTaskDto dto) {
		this.dto = dto;
	}

	public JobDirectCall(ConcurrTaskDto dto) {
		this.dto = dto;
	}
	
	@Override
	public void run() {
		//保存当前执行的JOB_ID
		Long jobID = dto.getJobId();
		List<BatchJobExecute> executes = GitContext.getBean(BatchJobExecuteDao.class).selectAll1R(jobID);
		if(CommonUtils.isEmpty(executes)){
			logger.error("The current job[{}] does not exists steps!!!!!", jobID);
		} else {
			try{
				LinkedList<InnerStepNode> stepNodes = new LinkedList<>();
				for(BatchJobExecute execute : executes){
					BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(execute.getExecuteStepId(), true);
					MDCCache.set(stepDefinition.getStepId(), stepDefinition.getStepLogMdcValue());
					CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
					BaseServiceTasketExcutor executor = ExcutorTrigger.getExecutor(stepDefinition.getStepActuator());
					stepNodes.add(new InnerStepNode(executor, executor.grouping(stepExcutionInfo(stepDefinition))));
				}
				stepsCache.put(dto.getHistoryId(), stepNodes);
				updateExecutorStatus(dto.getHistoryId(), JobExcutorEnum.NEW.getValue());
				poolExecute(dto.getHistoryId(), null);
			} catch(Exception e){
				logger.error(GitWebException.getStackTrace(e));
				throw GitWebException.GIT1001("处理step时出错....");
			}
		}
	}
	
	/**
	 * @param historyId
	 * @param deliverValueMap
	 * @see org.mine.quartz.job.run.JobCall#poolExecute(java.lang.Long, java.util.Map)
	 */
	@Override
	void poolExecute(Long historyId, Map<String, Object> deliverValueMap) {
		InnerStepNode node = stepsCache.get(historyId).removeFirst();
		if (node != null) {
			List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
			for(Map<String, Object> obj : node.getList()){
				if(CommonUtils.isNotEmpty(deliverValueMap)) obj.putAll(deliverValueMap);
				FutureTask<CallableResultDto> futureTask = new FutureTask<>(new InnerThread(node.getExecutor(), obj));
				poolExecutor.execute(futureTask);
				futureTasks.add(futureTask);
			}
			tasksResult.put(historyId, futureTasks);
		}
	}
	
	public void updateExecutorStatus(Long historyId, final String status){
		GitContext.doIndependentTransActionControl(new BatchOperator<Object, Long>() {

			@Override
			public Object call(Long input) {
				BatchExecutorSeqlog seqlog = GitContext.getBean(BatchExecutorSeqlogDao.class).selectOne1R(historyId, false);
				if (seqlog != null) {
					seqlog.setStatus(status);
					seqlog.setTimeStamp(null);
					GitContext.getBean(BatchExecutorSeqlogDao.class).updateOne1R(seqlog);
				}
				return null;
			}
		}, historyId);
	}

	/**
	 * 公共阈值
	 * @param stepDefinition
	 * @return
	 */
	@Override
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
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (perform == null) {
			perform = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						if (tasksResult.size() > 0) {
							Iterator<Long> iterator  = tasksResult.keySet().iterator();
							while (iterator.hasNext()) {
								Long historyId = iterator.next();
								if(!stepsCache.containsKey(historyId)){
									iterator.remove();
									continue;
								}
								boolean isSuccess = true;
								boolean isCancel = false;
								boolean taskResult = false;
								List<FutureTask<CallableResultDto>> tasks = tasksResult.get(historyId);
								Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
								while (taskIterator.hasNext()) {
									FutureTask<CallableResultDto> task = taskIterator.next();
									if (task.isDone()) {
										if (task.isCancelled()) {
											logger.warn("The current step has been canceled. historyId : {}", historyId);
											isCancel = true;
											break;
										} else {
											try {
												CallableResultDto resultDto = task.get(1, TimeUnit.SECONDS);
												if (!(taskResult = resultDto.isResult())) {
													logger.error("The current step execution failed.  error message : {}", resultDto.getMseeage());
												}
											} catch (InterruptedException | ExecutionException | TimeoutException e) {
												taskResult = false;
											}
										}
									} else {
										isSuccess = false;
									}
								}
								
								String status = "";
								if (isSuccess) {
									if (isCancel) {
										status = JobExcutorEnum.CANCEL.getValue();
									} else {
										if(taskResult) {
											//通知下一个Step执行
											if (stepsCache.get(historyId).getFirst() != null && stepsCache.get(historyId).getFirst().getList() != null 
													&& stepsCache.get(historyId).getFirst().getList().size() > 0) {
												poolExecute(historyId, null);
											} else {
												status = JobExcutorEnum.SUCCESS.getValue();
											}
										} else {
											status = JobExcutorEnum.FAILED.getValue();
										}
									}
									updateExecutorStatus(historyId, status);
									iterator.remove();
								}
							}
						}
					}
				}
			}, "PERFORM_THREAD");
			perform.start();
		}
	}
}
