package org.mine.quartz.job.run;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTaskLogic {
	private static final Logger logger = LoggerFactory.getLogger(JobTaskLogic.class);
	private String type = "";
	public JobTaskLogic(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void logic(Map<String, Object> jmap){
		GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Map<String, Object>>() {

			@Override
			public Integer call(Map<String, Object> dataMap) {
				ConcurrTaskDto taskDto = (ConcurrTaskDto) dataMap.get("dto");
				if (taskDto.getJobLogFlag() != 0) {
					logger.error("The current job does not logged.");
					return null;
				}
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
				JobExcutorFactory.call(new JobTaskCallable(taskDto), taskDto.getJobLogFlag());
				
				logRegister.setTimingJobStatus(JobExcutorEnum.COMPLETING.getValue());
				return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(logRegister);
			}
		}, jmap);
	}
	
}
