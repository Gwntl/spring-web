package org.mine.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.exception.MineBizException;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.BeanUtil;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.SchedulerRunnerHistoryDao;
import org.mine.dao.custom.BatchConfCostomDao;
import org.mine.model.SchedulerRunnerHistory;
import org.mine.quartz.ExecutionLogicSubject;
import org.mine.quartz.TaskRepositoryValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

/**
 * Job作业执行单元
 * @filename JobExcutionUnit.java 
 * @author wzaUsers
 * @date 2019年11月28日下午2:14:33 
 * @version v1.0
 */
@Repository
public class JTaskExecutionUnit implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(JTaskExecutionUnit.class);
	
	public static Map<Long, List<FutureTask<CallableResult>>> excutorTaskMap = new ConcurrentHashMap<>();
	
	/**
	 * Job执行缓存
	 * jobRunnerMonitorMap
	 */
	public static Map<Long, Long> jobRunnerMonitorMap = new ConcurrentHashMap<>();
	
//	public static AtomicInteger count = new AtomicInteger(0);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						synchronized (excutorTaskMap) {
							if(excutorTaskMap.size() > 0){
								Iterator<Long> iterator = excutorTaskMap.keySet().iterator();
								while(iterator.hasNext()){
									Long runnid = iterator.next();
									if(!jobRunnerMonitorMap.containsKey(runnid)){
										iterator.remove();
										break;
									}
									Long jobDetailId = jobRunnerMonitorMap.get(runnid);
									Iterator<FutureTask<CallableResult>> iteratorTask = excutorTaskMap.get(runnid).iterator();
									//判断是否全部运行完成
									boolean isAllRun = true;
									//判断任务是否执行成功
									boolean isSuccess = false;
									//获取任务返回信息
									String message = "";
									while(iteratorTask.hasNext()){
										FutureTask<CallableResult> task = iteratorTask.next();
										//当获取到返回值时
										if(task.isDone()){
											try {
												CallableResult result = task.get(1, TimeUnit.SECONDS);
												if(result.isResult()){
													isSuccess = true;
												}
												message = result.getMseeage();
											} catch (InterruptedException | ExecutionException | TimeoutException e) {
												logger.error("Failed to get the return value!!!!!");
											}
										} else {
											isAllRun = false;
											break;
										}
									}
									if(isAllRun){
										SchedulerRunnerHistory history = GitContext.getBean(SchedulerRunnerHistoryDao.class).selectOne1(runnid, jobDetailId);
										
										if(CommonUtils.isEmpty(history)){
											TimeUnit.SECONDS.sleep(1);
										}
										
										if(isSuccess){
											history.setJobStatus(JobExcutorEnum.SEECUSS.getId());
										} else {
											history.setJobStatus(JobExcutorEnum.FAILED.getId());
											history.setJobErrmsg(message);
										}
										history.setEndTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
										GitContext.getBean(SchedulerRunnerHistoryDao.class).updateOne1R(history);
										iterator.remove();
									}
								}
							} else {
								try {
									TimeUnit.SECONDS.sleep(2);
								} catch (InterruptedException e) {
								}
							}
						}
					} catch(Exception e){
						logger.error(MineException.getStackTrace(e));
						logger.error("Job value setting exception!!!!!");
					}
				}
			}
		}, "MONITOR-RESULT");
		thread.start();
	}
	
	/**
	 * Job作业执行逻辑
	 * @param subject
	 * @param values
	 * @param map
	 */
	public static void performsConcreteLogic(ExecutionLogicSubject subject, TaskRepositoryValues values, Map<String, Object> map){
		SchedulerRunnerHistory history = null;
		String isSaveLog = values.getIsSaveLog();
		Long runnerId = values.getRunnerId();
		Long jobDetailId = values.getJobDetailId();
		String provider = values.getJobDetailProvider();
		String jobStatus = JobExcutorEnum.STARTED.getId();
		try{
			if(isSaveLog.equals(JobExcutorEnum.SAVE_LOG)){
				BatchConfCostomDao costomDao = GitContext.getBean(BatchConfCostomDao.class);
				runnerId = costomDao.getBatchSequence("batch_sequence_1");
				history = new SchedulerRunnerHistory();
				history.setRunnerId(runnerId);
				history.setJobId(jobDetailId);
				history.setStartTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				history.setJobStatus(jobStatus);
				GitContext.getBean(SchedulerRunnerHistoryDao.class).insertOne(history);
			}
			
//			count.getAndIncrement();
			
			//是否存在分组(若子类未实现分组)
			BaseServiceTasketExcutor excutor = BeanUtil.getObj(provider);
			List<Map<String, Object>> groups = excutor.grouping(map);
			if(groups == null || groups.size() <= 0){
				jobStatus = JobExcutorEnum.UNKOWN.getId();
				logger.error("the job does not exist, please deal with it timely !!!!!!");
			} else {
				List<FutureTask<CallableResult>> callables = new ArrayList<>();
				//获取交易执行类,执行逻辑主题
				for(Map<String, Object> groupInfo : groups){
					subject.logicalSubject(groupInfo);
					groupInfo.put("javaClass", provider);
					FutureTask<CallableResult> task = new FutureTask<>(new JobRunnable(excutor, groupInfo));
					JobExcutorFactory.getFactory().call(task);
					callables.add(task);
				}
				if(isSaveLog.equals(JobExcutorEnum.SAVE_LOG)){
					excutorTaskMap.put(runnerId, callables);
					jobRunnerMonitorMap.put(runnerId, jobDetailId);
				}
				jobStatus = JobExcutorEnum.COMPLETING.getId();
			}
			
			//记录日志后,更新日志状态
			if(isSaveLog.equals(JobExcutorEnum.SAVE_LOG)){
				if(history != null && runnerId != null){
					history = GitContext.getBean(SchedulerRunnerHistoryDao.class).selectOne1(runnerId, jobDetailId);
					history.setJobStatus(jobStatus);
					GitContext.getBean(SchedulerRunnerHistoryDao.class).updateOne1R(history);
				}
			}
			
		} catch(Throwable e){
			System.out.println(System.currentTimeMillis());
			logger.error("异步job调用异常: {}", MineBizException.getStackTrace(e));
			throw e;
		}
	}
}
