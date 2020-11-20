package org.mine.quartz.run;

import java.util.Date;
import java.util.Map;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingTaskLogRegisterDao;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.model.BatchTimingTaskLogRegister;
import org.mine.quartz.JobExecutorFactory;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTaskLogic {
	private static final Logger logger = LoggerFactory.getLogger(JobTaskLogic.class);

	public void logicHasLog(Map<String, Object> jmap){
		GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Map<String, Object>>() {

			@Override
			public Integer call(Map<String, Object> dataMap) {
				ExecuteTaskDto taskDto = (ExecuteTaskDto) dataMap.get("dto");
				if (taskDto.getJobLogFlag() != 0) {
					logger.error("The current job does not logged.");
					return null;
				}
				BatchTimingTaskLogRegister logRegister = new BatchTimingTaskLogRegister();
				logRegister.setTimingExecutionId(GitContext.getBean(BatchConfCustomDao.class).getBatchSequence("timing_sequence_task"));
				logRegister.setTimingTaskId(taskDto.getJobId());
				logRegister.setTimingJobName(taskDto.getJobName());
				logRegister.setTimingAssociateTaskId(taskDto.getTaskId());
				logRegister.setTimingStartTime(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				logRegister.setTimingJobStatus(JobExecutorEnum.NEW.getValue());
				logRegister.setCreateDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
				logRegister.setValidStatus(JobConstant.VALID_STATUS_0);
				GitContext.getBean(BatchTimingTaskLogRegisterDao.class).insertOne(logRegister);

				taskDto.setExecutionInstance(logRegister.getTimingExecutionId());
				JobExecutorFactory.call(new JobTaskCallable(taskDto), taskDto.getJobLogFlag());

				logRegister.setTimingJobStatus(JobExecutorEnum.COMPLETING.getValue());
				return GitContext.getBean(BatchTimingTaskLogRegisterDao.class).updateOne1R(logRegister);
			}
		}, jmap);
	}

	public void logicNoLog(Map<String, Object> dataMap){

	}

}
