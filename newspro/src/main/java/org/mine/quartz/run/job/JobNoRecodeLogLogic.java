package org.mine.quartz.run.job;

import org.mine.aplt.Cache.MDCCache;
import org.mine.aplt.Cache.StepReturnMapCache;
import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.customAnnotation.AspectLogAround;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchJobLogDao;
import org.mine.dao.BatchStepDefinitionDao;
import org.mine.lock.redis.DefaultRedisLock;
import org.mine.lock.redis.RedisLogicDecrConstant;
import org.mine.model.BatchJobDefinition;
import org.mine.model.BatchJobLog;
import org.mine.model.BatchStepDefinition;
import org.mine.quartz.dto.CallableResultDto;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.dto.JobOperatorDto;
import org.mine.quartz.trigger.ExecutorTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
	 * 执行中的步骤缓存[JOB实例, STEP执行信息]
	 */
	protected static Map<String, LinkedList<InnerStepNode>> stepsCache = new ConcurrentHashMap<>(1 << 10);
	/**
	 * 异步线程执行结果
	 */
	private static Map<String, List<FutureTask<CallableResultDto>>> noLogTasksResult = new ConcurrentHashMap<>();
	
	private Thread perform = null;

	private Object noLogLockObj = new Object();

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
		DefaultRedisLock lock = null;
		try{
			if (dto.getJobInitValue().get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1)) {
				lock = GitContext.getBean(DefaultRedisLock.class);
				lock.lock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_LOGIC, jobID, dto.getJobInitValue()));
			}

			List<String> executeStepIds = GitContext.queryForSingleFieldList(
					"SELECT EXECUTE_STEP_ID FROM BATCH_JOB_EXECUTE WHERE EXECUTE_JOB_ID = ? ORDER BY EXECUTE_STEP_NUM",
					new Object[]{jobID}, String.class);
			if (CommonUtils.isEmpty(executeStepIds)) {
				logger.error("The current job[{}] does not exists steps!!!!!", jobID);
			} else {
				String executionInstance = dto.getExecutionInstance();
				try {
					//Step的执行信息
					LinkedList<InnerStepNode> stepNodes = new LinkedList<>();
					for (String executeStepId : executeStepIds) {
						BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(executeStepId, true);
						MDCCache.set(stepDefinition.getStepId(), stepDefinition.getStepLogMdcValue());
						CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
						BaseServiceTaskletExecutor executor = ExecutorTrigger.getExecutor(stepDefinition.getStepActuator());
						stepNodes.add(new InnerStepNode(executor, executor.grouping(stepExecutionInfo(stepDefinition, dto))));
					}

					stepsCache.put(executionInstance, stepNodes);

					GitContext.doIndependentTransActionControl(input -> {
						BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, true);
						jobLog.setStartTime(CommonUtils.currentTime(new Date()));
						jobLog.setJobStatus(JobExecutorEnum.COMPLETING.getValue());
						jobLog.setTimeStamp(System.nanoTime());
						return GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
					}, executionInstance);
				} catch (Exception e) {
					logger.error("init no log data error. {}", MineException.getStackTrace(e));
					synchronized (stepsCache) {
						if(stepsCache.containsKey(executionInstance)) stepsCache.remove(executionInstance);
					}
					unlock(updateExecutorStatus(executionInstance, JobExecutorEnum.FAILED.getValue(),
							(e instanceof MineException ? ((MineException) e).getError_msg() : e.getMessage())));
					throw GitWebException.GIT1001("处理step时出错....");
				}
				poolExecute(dto.getExecutionInstance(), null);
			}
		} catch(Exception e){
			logger.error(GitWebException.getStackTrace(e));
			throw GitWebException.GIT1001("处理step时出错....");
		} finally {
			if (lock != null) lock.unlock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_LOGIC, jobID, null));
		}
	}

	@Override
	void poolExecute(String executionInstance, Map<String, Object> deliverValueMap) {
	    logger.debug("JobNoRecodeLogLogic.poolExecute({}) begin>>>>>>>>>>>>>>>>>>", executionInstance);
		synchronized (noLogLockObj) {
			//载入执行标志. 标志运行中.
			if (executingFlagMap.get(executionInstance) == null) {
				executingFlagMap.put(executionInstance, new AtomicInteger(JobConstant.JOB_STATUS_0));
			} else if (!isExecutingFlag(executionInstance)) {
				dataOperatorHandling(executionInstance);
				executingFlagMap.remove(executionInstance);
				return;
			}
			try {
				InnerStepNode node = null;
				synchronized (stepsCache) {
					if (stepsCache.containsKey(executionInstance) && CommonUtils.isNotEmpty(stepsCache.get(executionInstance))) {
						node = stepsCache.get(executionInstance).removeFirst();
					}
				}
				if (node != null) {
					List<FutureTask<CallableResultDto>> futureTasks = new ArrayList<>();
					for(Map<String, Object> obj : node.getList()) {
						if(CommonUtils.isNotEmpty(deliverValueMap)) obj.putAll(deliverValueMap);
						FutureTask<CallableResultDto> futureTask = new FutureTask<>(new InnerThread(node.getExecutor(), obj));
						poolExecutor.execute(futureTask);
						futureTasks.add(futureTask);
					}
					noLogTasksResult.put(executionInstance, futureTasks);
				}
			} catch (Exception e) {
				logger.error("executor thread failed. error message : {}", GitWebException.getStackTrace(e));
				//将缓存中的数据清除, 往后步骤不执行.
				synchronized (stepsCache) {
					if(stepsCache.containsKey(executionInstance)) stepsCache.remove(executionInstance);
				}
				unlock(updateExecutorStatus(executionInstance, JobExecutorEnum.FAILED.getValue(),
						(e instanceof MineException ? ((MineException) e).getError_msg() : e.getMessage())));
				throw e;
			}
		}
        logger.debug("JobNoRecodeLogLogic.poolExecute({}) end<<<<<<<<<<<<<<<<<<", executionInstance);
	}

	@Override
	@AspectLogAround
	public JobOperatorDto stopJob(String jobInstance) {
		if (CommonUtils.isEmpty(jobInstance)) {
			throw GitWebException.GIT1002("JOB实例");
		}
		JobOperatorDto dto = new JobOperatorDto();
		BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(jobInstance, false);
		AtomicInteger integerMap = null;
		if (jobLog == null) {
			dto.setInfo(JobConstant.JOB_UNINITIALIZED);
		} else if (jobLog != null) {
			BatchJobDefinition jobDef = null;
			String jobID = jobLog.getJobId();
			if ((jobDef = GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jobLog.getJobId(), true)).getJobLogFlag() != 1) {
				throw  GitWebException.GIT1001("对象构建错误.");
			}
			if (CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.NEW.getValue())
					&& CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.COMPLETING.getValue())) {
				dto.setInfo("当前状态[" + jobLog.getJobStatus() + "]不可以停止JOB.");
			} else {
				boolean isCct = false;
				try {
					Map<String, Object> map = new HashMap<>();
					CommonUtils.initMapValue(map, jobDef.getJobInitValue());
					if ((isCct = map.get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1))) {
						if (!GitContext.getBean(DefaultRedisLock.class).tryLock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_STOP, jobID, map))) {
							throw GitWebException.GIT1001("当前作业停止中或已被停止.");
						}
					}
					synchronized (noLogLockObj) {
						if ((integerMap = executingFlagMap.get(jobInstance)) == null) {
							//重取日志状态
							jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(jobInstance, true);
							if (CommonUtils.equals(jobLog.getJobStatus(), JobExecutorEnum.SUCCESS.getValue())) {
								dto.setInfo("当前JOB任务已完成.");
							} else {
								executingFlagMap.put(jobInstance, new AtomicInteger(1));
								dto.setFlag(true);
							}
						} else {
							//获取当前JOB实例中执行的STEP信息.
							List<FutureTask<CallableResultDto>> tasks = noLogTasksResult.get(jobInstance);
							//当step缓存和执行器缓存均为空时, 表示当前JOB已执行完毕.
							if (CommonUtils.isEmpty(tasks) && CommonUtils.isEmpty(stepsCache.get(jobInstance))) {
								dto.setInfo("当前JOB实例[" + jobInstance + "]下所有STEP已全部执行完毕.");
							} else {
								if (integerMap.compareAndSet(JobConstant.JOB_STATUS_0, JobConstant.JOB_STATUS_1)) {
									dto.setFlag(true);
								}
							}
						}
					}
				} finally {
					if (isCct) unlockJob(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_STOP, jobID);
				}
			}
		}
		return dto;
	}

	@Override
	@AspectLogAround
	public JobOperatorDto cancelJob(String jobInstance) {
		if (CommonUtils.isEmpty(jobInstance)) {
			throw GitWebException.GIT1002("JOB实例");
		}
		boolean res = false;
		JobOperatorDto dto = new JobOperatorDto();
		BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(jobInstance, false);
		AtomicInteger integerMap = null;
		if (jobLog == null) {
			dto.setInfo(JobConstant.JOB_UNINITIALIZED);
		} else if (jobLog != null) {
			BatchJobDefinition jobDef = null;
			String jobID = jobLog.getJobId();
			if ((jobDef = GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jobLog.getJobId(), true)).getJobLogFlag() != 1) {
				throw  GitWebException.GIT1001("对象构建错误.");
			}
			if (CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.NEW.getValue())
					&& CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.COMPLETING.getValue())) {
				dto.setInfo("当前状态[" + jobLog.getJobStatus() + "]不可以取消JOB.");
			} else {
				boolean isCct = false;
				try {
					Map<String, Object> map = new HashMap<>();
					CommonUtils.initMapValue(map, jobDef.getJobInitValue());
					if ((isCct = map.get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1))) {
						if (!GitContext.getBean(DefaultRedisLock.class).tryLock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_CANCEL, jobID, map))) {
							throw GitWebException.GIT1001("当前作业取消中或已被取消.");
						}
					}

					synchronized (noLogLockObj) {
						if ((integerMap = executingFlagMap.get(jobInstance)) == null) {
							//重取日志状态
							jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(jobInstance, true);
							if (CommonUtils.equals(jobLog.getJobStatus(), JobExecutorEnum.SUCCESS.getValue())) {
								dto.setInfo("当前JOB任务已完成.");
							} else {
								executingFlagMap.put(jobInstance, new AtomicInteger(JobConstant.JOB_STATUS_2));
								dto.setFlag(true);
							}
						} else {
							//获取当前JOB实例中执行的STEP信息.
							List<FutureTask<CallableResultDto>> tasks = noLogTasksResult.get(jobInstance);
							//当step缓存和执行器缓存均为空时, 表示当前JOB已执行完毕.
							if (CommonUtils.isEmpty(tasks) && CommonUtils.isEmpty(stepsCache.get(jobInstance))) {
								dto.setInfo("当前JOB实例[" + jobInstance + "]下所有STEP已全部执行完毕.");
							} else {
								if (integerMap.compareAndSet(JobConstant.JOB_STATUS_0, JobConstant.JOB_STATUS_2)) {
									if (CommonUtils.isNotEmpty(tasks)) {
										try {
											//STEP是顺序执行, 当存在任务在执行中时, 有且仅有一个STEP在执行.
											for (FutureTask<CallableResultDto> task : tasks) {
												if (!task.isDone()) {
													if (res = task.cancel(true)) {
														break;
													}
												}
											}
											if (!res) {
												integerMap.compareAndSet(JobConstant.JOB_STATUS_2, JobConstant.JOB_STATUS_0);
											}
											dto.setFlag(res);
										} catch (Exception e) {
											logger.error("cancel step error info : {}", MineException.getStackTrace(e));
											throw GitWebException.GIT1001("取消执行中的STEP失败.");
										}
									} else {
										dto.setFlag(true);
									}
								}
							}
						}
					}
				} finally {
					if (isCct) unlockJob(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_CANCEL, jobID);
				}
			}
		}
		return dto;
	}

	@Override
	public BatchJobLog dataOperatorHandling(String executionInstance) {
		super.dataOperatorHandling(executionInstance);
		synchronized (stepsCache) {
			if (stepsCache.containsKey(executionInstance)) stepsCache.remove(executionInstance);
		}
		//更新日志信息.
		return GitContext.doIndependentTransActionControl((input) -> {
			BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, false);
			if (jobLog != null) {
				String status = "";
				String info = "";
				if (getExecutingValue(input) == JobConstant.JOB_STATUS_1) {
					status = JobExecutorEnum.STOP.getValue();
					info = "the step is stopped at execution time.";
				} else if (getExecutingValue(input) == JobConstant.JOB_STATUS_2) {
					status = JobExecutorEnum.CANCEL.getValue();
					info = "the step is cancelled at execution time.";
				}
				jobLog.setJobStatus(status);
				jobLog.setEndTime(CommonUtils.currentTime(new Date()));
				jobLog.setTimeStamp(System.nanoTime());
				jobLog.setJobErrmsg(info);
				GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
			}
			return jobLog;
		}, executionInstance);
	}

	@Override
	public boolean restartJob(String jobInstance) {
		logger.debug("JobNoRecodeLogLogic.restartJob({}) begin>>>>>>>>>>>>>>>>>>", jobInstance);
		if (CommonUtils.isEmpty(jobInstance)) {
			throw GitWebException.GIT1002("作业实例");
		}
		BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(jobInstance, true);
		//当JOB是定义在TASK中时, 不支持重启.
		if (CommonUtils.isNotEmpty(jobLog.getAssociateTaskId()) && CommonUtils.isNotEmpty(jobLog.getAssociateTaskInstance())) {
			throw GitWebException.GIT1001("当前作业不支持单独重启.");
		}
		//当日志状态不为失败或者停止时,不支持重启.
		if (CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.STOP.getValue())
				&& CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.FAILED.getValue())) {
			throw GitWebException.GIT_JOB_STATUS_RESTART_ERROR(JobExecutorEnum.STOP.getValue() + "," + JobExecutorEnum.FAILED.getValue());
		}

		boolean isCct = false;
		DefaultRedisLock lock = null;
		String jobID = jobLog.getJobId();
		if ((isCct = dto.getJobInitValue().get(JobConstant.CCT_FLAG).equals(JobConstant.CCT_FLAG_1))) {
			if (!(lock = GitContext.getBean(DefaultRedisLock.class)).tryLock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB, jobID, dto.getJobInitValue()))) {
				throw GitWebException.GIT1001("当前作业在运行中或者已被重启.");
			}
		}

		List<String> executeStepIds = GitContext.queryForSingleFieldList(
				"SELECT EXECUTE_STEP_ID FROM BATCH_JOB_EXECUTE WHERE EXECUTE_JOB_ID = ? ORDER BY EXECUTE_STEP_NUM",
				new Object[]{jobID}, String.class);

		if (CommonUtils.isEmpty(executeStepIds)) {
			logger.error("There is no data in the job[{}] to be restarted!!!!.", jobInstance);
			if (isCct) unlockJob(RedisLogicDecrConstant.QUARTZ_TIMING_JOB, jobID);
			throw GitWebException.GIT_RESTART_JOB_NO_DATA(jobInstance);
		} else {
			try {
				if (isCct) lock.lock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_LOGIC, jobID, dto.getJobInitValue()));
				try {
					LinkedList<InnerStepNode> stepNodes = new LinkedList<>();
					Map<String, Object> map = StepReturnMapCache.getSuccessStep(jobInstance);
					for (String executeStepId : executeStepIds) {
						BatchStepDefinition stepDefinition = GitContext.getBean(BatchStepDefinitionDao.class).selectOne1R(executeStepId, true);
						if (map != null && !map.containsKey(stepDefinition.getStepActuator())) {
							MDCCache.set(stepDefinition.getStepId(), stepDefinition.getStepLogMdcValue());
							CommonUtils.initMapValue(dto.getStepInitValue(), stepDefinition.getStepInitValue());
							BaseServiceTaskletExecutor executor = ExecutorTrigger.getExecutor(stepDefinition.getStepActuator());
							stepNodes.add(new InnerStepNode(executor, executor.grouping(stepExecutionInfo(stepDefinition, dto))));
						}
					}

					if (CommonUtils.isEmpty(stepNodes)) {
						logger.error("There is no data in the job[{}] to be restarted!!!!.", jobInstance);
						throw GitWebException.GIT_RESTART_JOB_NO_DATA(jobInstance);
					}

					//将当前执行job的历史ID放入缓存中,防止quartz中job的重复执行而导致的缓存脏数据.
					stepsCache.put(jobInstance, stepNodes);

					GitContext.doIndependentTransActionControl((input) -> {
						BatchJobLog batchJobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, true);
						batchJobLog.setStartTime(CommonUtils.currentTime(new Date()));
						batchJobLog.setJobStatus(JobExecutorEnum.COMPLETING.getValue());
						batchJobLog.setTimeStamp(System.nanoTime());
						batchJobLog.setJobErrmsg("");
						return GitContext.getBean(BatchJobLogDao.class).updateOne1(batchJobLog);
					}, jobInstance);
				} catch(Exception e) {
					logger.error("restart job error message : {}", GitWebException.getStackTrace(e));
					//将缓存中的数据清除, 往后步骤不执行.
					synchronized (stepsCache) {
						if(stepsCache.containsKey(jobInstance)) stepsCache.remove(jobInstance);
					}
					unlock(updateExecutorStatus(jobInstance, JobExecutorEnum.FAILED.getValue(),
						(e instanceof MineException ? ((MineException) e).getError_msg() : e.getMessage())));

					throw GitWebException.GIT1001("重启Job时出错....");
				}
				poolExecute(jobInstance, StepReturnMapCache.get(jobInstance));
			} finally {
				if (lock != null)  lock.unlock(getJobLockInput(RedisLogicDecrConstant.QUARTZ_TIMING_JOB_LOGIC, jobID, null));
			}
		}
		logger.debug("JobNoRecodeLogLogic.restartJob({}) end>>>>>>>>>>>>>>>>>>", jobInstance);

		return true;
	}

	/**
	* 更新JOB状态
	* @param executionInstance
	* @param status
	* @param message
	* @return: void
	* @Author: wntl
	* @Date: 2020/9/25
	*/
	public BatchJobLog updateExecutorStatus(String executionInstance, final String status, final String message){
		return GitContext.doIndependentTransActionControl((input) -> {
			BatchJobLog jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input, false);
			if (jobLog != null) {
				jobLog.setJobStatus(status);
				jobLog.setJobErrmsg(message);
				jobLog.setEndTime(CommonUtils.currentTime(new Date()));
				jobLog.setTimeStamp(System.nanoTime());
				GitContext.getBean(BatchJobLogDao.class).updateOne1(jobLog);
			}
			return jobLog;
		}, executionInstance);
	}

	/**
	 * 公共阈值
	 * @param stepDefinition
	 * @return
	 */
	@Override
	public Map<String, Object> stepExecutionInfo(BatchStepDefinition stepDefinition, ExecuteTaskDto taskDto) {
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
					    synchronized (noLogTasksResult) {
                            if (noLogTasksResult.size() > 0) {
                                Iterator<String> iterator  = noLogTasksResult.keySet().iterator();
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
                                        List<FutureTask<CallableResultDto>> tasks = noLogTasksResult.get(executionInstance);
                                        Iterator<FutureTask<CallableResultDto>> taskIterator = tasks.iterator();
                                        while (taskIterator.hasNext() && isSuccess) {
                                            FutureTask<CallableResultDto> task = taskIterator.next();
                                            if (task.isDone()) {
                                                if (task.isCancelled()) {
                                                    logger.warn("The current job has been canceled. executionInstance : {}", executionInstance);
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
                                                status = JobExecutorEnum.CANCEL.getValue();
												message = "The current job have been cancelled.";
                                                isChange = true;
                                            } else {
                                                if(taskResult) {
                                                    //通知下一个Step执行
                                                    synchronized (stepsCache) {
                                                        if (stepsCache.get(executionInstance) != null && stepsCache.get(executionInstance).size() > 0) {
															//TODO 只有存在下一步时才将上一步返回的MAP存放于缓存中.
															StepReturnMapCache.put(executionInstance, deliverValueMap);
															StepReturnMapCache.putSuccessStep(executionInstance, deliverValueMap.get(JobConstant.JOB_EXECUTOR_BEAN_NAME) + "");
															poolExecute(executionInstance, deliverValueMap);
                                                        } else {
                                                            status = JobExecutorEnum.SUCCESS.getValue();
                                                            isChange = true;
															executingFlagMap.get(executionInstance).set(JobConstant.JOB_STATUS_8);
                                                        }
                                                    }
                                                } else {
                                                    status = JobExecutorEnum.FAILED.getValue();
                                                    isChange = true;
													executingFlagMap.get(executionInstance).set(JobConstant.JOB_STATUS_8);
                                                }
                                            }
											if (isChange) {
												if (CommonUtils.equals(status, JobExecutorEnum.SUCCESS.getValue()) ||
														CommonUtils.equals(status, JobExecutorEnum.CANCEL.getValue())) {
													StepReturnMapCache.remove(executionInstance);
													StepReturnMapCache.removeSuccessStep(executionInstance);
												}
												BatchJobLog jobLog = updateExecutorStatus(executionInstance, status, message);
												synchronized (stepsCache) {
													stepsCache.remove(executionInstance);
												}
												synchronized (noLogLockObj) {
													executingFlagMap.remove(executionInstance);
												}
												iterator.remove();
												unlock(jobLog);
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

	public static void unlock(BatchJobLog jobLog) {
		if (isCct(jobLog.getJobId())) {
			unlockJob(RedisLogicDecrConstant.QUARTZ_TIMING_JOB, jobLog.getJobId());
			logger.info("[{}.{}]JOB锁已释放.", jobLog.getJobId(), jobLog.getExecutionInstance());
		}
	}
}
