package org.mine.quartz.run.job;

import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobLogDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.model.BatchJobLog;
import org.mine.model.BatchStepDefinition;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.trigger.ExcutorTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Description: 直接调用作业
 * @ClassName: JobNoRecodeLogLogic
 * @author: wntl
 * @date: 2020年6月23日 下午5:19:13
 */
@Component
public class JobNoRecodeLogLogic extends JobCall {

	private ExecuteTaskDto dto;
	
	/**
	 * 异步线程执行结果
	 */
	private static Map<String, List<FutureTask<CallableResultDto>>> tasksResult = new ConcurrentHashMap<>();
	
	private Thread perform = null;
	/**
	 * 执行中的步骤缓存-<任务ID, Step执行信息>
	 */
	private static Map<String, LinkedList<InnerStepNode>> stepsCache = new ConcurrentHashMap<>();
	
	private static final ThreadPoolExecutor poolExecutor;
	static {
		poolExecutor = new ThreadPoolExecutor(ApltContanst.CPU_COUNT << 1, ApltContanst.CPU_COUNT << 1,
				5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(ApltContanst.CPU_COUNT << 8));
	}

	public JobNoRecodeLogLogic() {}
	public JobNoRecodeLogLogic(ExecuteTaskDto dto) {
		this.dto = dto;
	}
	
	@Override
	public void run() {
		//保存当前执行的JOB_ID
		String jobID = dto.getJobId();
		List<String> executeStepIds = GitContext.queryForSingleFieldList(
				"SELECT EXECUTE_STEP_ID FROM BATCH_JOB_EXECUTE WHERE EXECUTE_JOB_ID = ? ORDER BY EXECUTE_STEP_NUM",
				new Object[]{jobID}, String.class);
		if(CommonUtils.isEmpty(executeStepIds)){
			logger.error("The current job[{}] does not exists steps!!!!!", jobID);
		} else {
			try{
				LinkedList<InnerStepNode> stepNodes = new LinkedList<>();
				for (String executeStepId : executeStepIds) {
					BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(executeStepId, true);
					MDCCache.set(stepDefinition.getStepId(), stepDefinition.getStepLogMdcValue());
					CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
					BaseServiceTaskletExecutor executor = ExcutorTrigger.getExecutor(stepDefinition.getStepActuator());
					stepNodes.add(new InnerStepNode(executor, executor.grouping(stepExecutionInfo(stepDefinition, dto))));
				}
                stepsCache.put(dto.getExecutionInstance(), stepNodes);
                poolExecute(dto.getExecutionInstance(), null);

				GitContext.doIndependentTransActionControl(dto -> {
					BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(dto.getExecutionInstance(), true);
					jobLog.setStartTime(CommonUtils.currentTime(new Date()));
					jobLog.setJobStatus(JobExcutorEnum.COMPLETING.getValue());
					jobLog.setTimeStamp(System.nanoTime());
					return GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
				}, dto);

			} catch(Exception e){
				logger.error(GitWebException.getStackTrace(e));
				throw GitWebException.GIT1001("处理step时出错....");
			}
		}
	}
	
	/**
	 * 执行
	 * @param executionInstance
	 * @param deliverValueMap
	 * @see JobCall#poolExecute(java.lang.String, java.util.Map)
	 */
	@Override
	void poolExecute(String executionInstance, Map<String, Object> deliverValueMap) {
	    logger.debug("JobNoRecodeLogLogic.poolExecute({}) begin>>>>>>>>>>>>>>>>>>", executionInstance);
		try {
            InnerStepNode node = null;
            synchronized (stepsCache) {
                node = stepsCache.get(executionInstance).removeFirst();
            }
            if(node != null){
                List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
                for(Map<String, Object> obj : node.getList()){
                    if(CommonUtils.isNotEmpty(deliverValueMap)) obj.putAll(deliverValueMap);
                    FutureTask<CallableResultDto> futureTask = new FutureTask<>(new InnerThread(node.getExecutor(), obj));
                    poolExecutor.execute(futureTask);
                    futureTasks.add(futureTask);
                }
                tasksResult.put(executionInstance, futureTasks);
            }
		} catch (Exception e) {
			logger.error("executor thread failed. error message : {}", GitWebException.getStackTrace(e));
			//将缓存中的数据清除, 往后步骤不执行.
            synchronized (stepsCache) {
                stepsCache.put(executionInstance, new LinkedList<>());
            }
			updateExecutorStatus(executionInstance, JobExcutorEnum.FAILED.getValue(),
					(e instanceof MineException ? ((MineException) e).getError_msg() : e.getMessage()));
			throw e;
		}
        logger.debug("JobNoRecodeLogLogic.poolExecute({}) end<<<<<<<<<<<<<<<<<<", executionInstance);
	}
	
	public void updateExecutorStatus(String executionInstance, final String status, final String message){
		GitContext.doIndependentTransActionControl(new BatchOperator<Object, String>() {

			@Override
			public Object call(String input) {
				BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, false);
				if (jobLog != null) {
					jobLog.setJobStatus(status);
					jobLog.setJobErrmsg(message);
					jobLog.setEndTime(CommonUtils.currentTime(new Date()));
					jobLog.setTimeStamp(System.nanoTime());
					GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
				}
				return null;
			}
		}, executionInstance);
	}

	/**
	 * 公共阈值
	 * @param stepDefinition
	 * @return
	 */
	@Override
	public Map<String, Object> stepExecutionInfo(BatchStepDefinition stepDefinition, ExecuteTaskDto taskDto){
		Map<String, Object> map = super.stepExecutionInfo(stepDefinition, taskDto);
		map.put("operator", "NoLogging");
		return map;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (perform == null) {
			perform = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
					    synchronized (tasksResult) {
                            if (tasksResult.size() > 0) {
                                Iterator<String> iterator  = tasksResult.keySet().iterator();
                                while (iterator.hasNext()) {
                                    String executionInstance = iterator.next();
                                    try {
                                        if (!stepsCache.containsKey(executionInstance)) {
                                            iterator.remove();
                                            continue;
                                        }
                                        boolean isSuccess = true;
                                        boolean isCancel = false;
                                        boolean taskResult = false;
                                        String message = "";
                                        Map<String, Object> deliverValueMap = new HashMap<>();
                                        List<FutureTask<CallableResultDto>> tasks = tasksResult.get(executionInstance);
                                        Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
                                        while (taskIterator.hasNext() && isSuccess) {
                                            FutureTask<CallableResultDto> task = taskIterator.next();
                                            if (task.isDone()) {
                                                if (task.isCancelled()) {
                                                    logger.warn("The current step has been canceled. executionInstance : {}", executionInstance);
                                                    isCancel = true;
                                                    break;
                                                } else {
                                                    try {
                                                        CallableResultDto resultDto = task.get(1, TimeUnit.SECONDS);
                                                        taskResult = resultDto.isResult();
                                                        message = resultDto.getMseeage();
                                                        CommonUtils.putAll(deliverValueMap, resultDto.getMap());
														//当子任务执行完毕, 则从链表中去除.
														taskIterator.remove();
                                                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                                                        taskResult = false;
                                                    }
                                                }
                                            } else {
                                                isSuccess = false;
                                            }
                                        }

                                        String status = "";
                                        boolean isChange = false;
                                        if (isSuccess) {
                                            if (isCancel) {
                                                status = JobExcutorEnum.CANCEL.getValue();
                                                isChange = true;
                                            } else {
                                                if(taskResult) {
                                                    //通知下一个Step执行
                                                    synchronized (stepsCache) {
                                                        if (stepsCache.get(executionInstance) != null && stepsCache.get(executionInstance).size() > 0) {
                                                            poolExecute(executionInstance, deliverValueMap);
                                                        } else {
                                                            status = JobExcutorEnum.SUCCESS.getValue();
                                                            isChange = true;
                                                        }
                                                    }
                                                } else {
                                                    status = JobExcutorEnum.FAILED.getValue();
                                                    isChange = true;
                                                }
                                            }
                                            if(isChange){
                                                updateExecutorStatus(executionInstance, status, message);
                                                synchronized (stepsCache) {
                                                    stepsCache.remove(executionInstance);
                                                }
                                                iterator.remove();
                                            }
                                        }
                                    } catch (Exception e) {
                                        logger.error("scan thread(PERFORM_THREAD) error. info : {}", MineException.getStackTrace(e));
                                    }
                                }
                            } else {
                                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {}
                            }
                        }
					}
				}
			}, "PERFORM_THREAD");
			perform.start();
		}
	}
}
