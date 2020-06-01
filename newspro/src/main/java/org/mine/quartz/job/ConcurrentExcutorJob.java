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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 设计目标:
 * Queue : Group = 1 : N; Group : Task = 1 : N; Task : Job = 1 : N; Job : Step = 1 : N;</br>
 * Queue之间为并行, Group之间为串行, Task之间为并行, Job之间为并行, Step之间为串行.</br>
 * 
 * 异步中的作业均存放在一个异步队列中, 异步队列中只存在一个Group. 存在多个Task, 每个Task对应一类作业处理.</br>
 * Task中存在多个Job, 每个Job间的处理互不干扰, 每个Job中存在多个Step, 每个Job中的多个Step串行.</br>
 * 
 * 定义表: batch_queue_definition, batch_group_definition, batch_trigger_definition, batch_task_definition, batch_job_definition, batch_step_definition.</br>
 * 执行定义表为:batch_task_execute,batch_job_execute. 其他表均为配置表, 可根据要求在各种配置中灵活组合.</br>
 * 
 * @filename ConcurrentExcutorJob.java
 * @author wzaUsers
 * @date 2019年11月26日下午8:13:01
 * @version v1.0
 */
public class ConcurrentExcutorJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ConcurrentExcutorJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("ConcurrentExcutorJob begin >>>>>>>>>> {}, \n{} ", System.currentTimeMillis(), CommonUtils.toString(context.getJobDetail().getJobDataMap()));
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
				logRegister.setTimingJobStatus(JobExcutorEnum.NEW.getValue());
				logRegister.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
				logRegister.setValidStatus(JobContanst.VALID_STATUS_0);
				GitContext.getBean(BatchTimingTaskLogRegisterDao.class).insertOne(logRegister);
				
				taskDto.setHistoryId(logRegister.getTimingExecutionId());
				JobExcutorFactory.getNewInstance().execute(new JobTaskCallable(taskDto));
				
				logRegister.setTimingJobStatus(JobExcutorEnum.COMPLETING.getValue());
				return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(logRegister);
			}
			
		}, context.getJobDetail().getJobDataMap());
		logger.debug("ConcurrentExcutorJob end >>>>>>>>>> ");
	}
}
