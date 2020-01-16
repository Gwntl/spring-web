package org.mine.quartz.job;

import java.util.Date;
import java.util.Map;

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingTaskLogRegisterDao;
import org.mine.dao.custom.BatchDefineCostomDao;
import org.mine.model.BatchTimingTaskLogRegister;
import org.mine.quartz.JobExcutorFactory;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.mine.quartz.job.run.JobTaskCallable;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 该Job内执行的逻辑为Quartz自身定义的定时作业, 因此一个作业组对应一个作业.
 * @filename ConcurrentExcutorJob.java 
 * @author wzaUsers
 * @date 2019年11月26日下午8:13:01 
 * @version v1.0
 */
public class ConcurrentExcutorJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Map<String, Object>>() {

			@Override
			public Integer call(Map<String, Object> dataMap) {
				ConcurrTaskDto taskDto = (ConcurrTaskDto) dataMap.get("dto");
				
				BatchTimingTaskLogRegister logRegister = new BatchTimingTaskLogRegister();
				logRegister.setTimingExecutionId(GitContext.getBean(BatchDefineCostomDao.class).getBatchSequence("timing_sequence_task"));
				logRegister.setTimingJobId(taskDto.getJobId());
				logRegister.setTimingJobName(taskDto.getJobName());
				logRegister.setTimingAssociateTaskId(taskDto.getTaskId());
				logRegister.setTimingStartTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				logRegister.setTimingJobStatus(JobExcutorEnum.STARTED.getId());
				logRegister.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
				logRegister.setValidStatus(JobContanst.VALID_STATUS_0);
				GitContext.getBean(BatchTimingTaskLogRegisterDao.class).insertOne(logRegister);
				
				taskDto.setHistoryId(logRegister.getTimingExecutionId());
				JobExcutorFactory.getNewInstance().execute(new JobTaskCallable(taskDto));
				
				logRegister.setTimingJobStatus(JobExcutorEnum.COMPLETING.getId());
				return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(logRegister);
			}
			
		}, context.getJobDetail().getJobDataMap());
	}
}
