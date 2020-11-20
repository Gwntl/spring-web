package org.mine.quartz.run;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchJobLogDao;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.model.BatchJobLog;
import org.mine.quartz.JobExecutorFactory;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.mine.quartz.run.job.JobNoRecodeLogLogic;
import org.mine.quartz.run.job.JobRecodeLogLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: BaseExecutor
 * @date 2020/8/1917:04
 */
public class BaseExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BaseExecutor.class);

    /**
    * 基础执行器
    * @param taskDto
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/8/26
    */
    public static void baseExecutor(ExecuteTaskDto taskDto) {
        logger.debug("BaseExecutor.baseExecutor() begin >>>>>>>>>>>>>>>>>>>>>>");
        if (CommonUtils.isEmpty(taskDto.getExecutionInstance())) {
            taskDto.setExecutionInstance(preStartupProcessing(taskDto.getJobId()));
        }

        addJobLog(taskDto);

        if (taskDto.getJobLogFlag() == 0) {
            JobExecutorFactory.call(new JobRecodeLogLogic(taskDto), taskDto.getJobLogFlag());
        } else {
            JobExecutorFactory.call(new JobNoRecodeLogLogic(taskDto), taskDto.getJobLogFlag());
        }
        logger.debug("BaseExecutor.baseExecutor() end >>>>>>>>>>>>>>>>>>>>>>");
    }
    /**
    * 执行前处理
    * @param jobID
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/8/26
    */
    public static String preStartupProcessing(String jobID) {
        GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jobID, true);
        return completeExecutionID("sequence_instance", JobConstant.EXECUTOR_JOB_PREFIX);
    }

    public static void addJobLog(ExecuteTaskDto taskDto) {
        if (CommonUtils.isEmpty(taskDto.getExecutionInstance())) {
            throw GitWebException.GIT1002("JOB实例");
        }
        GitContext.doIndependentTransActionControl((input) -> {
            BatchJobLog jobLog = null;
            jobLog = GitContext.getBean(BatchJobLogDao.class).selectOne1R(input.getExecutionInstance(), false);
            if (jobLog == null) {
                jobLog = new BatchJobLog();
                jobLog.setExecutionInstance(input.getExecutionInstance());
                jobLog.setJobId(input.getJobId());
                jobLog.setJobName(input.getJobName());
                jobLog.setJobStatus(JobExecutorEnum.NEW.getValue());
                jobLog.setAssociateTaskId(input.getTaskId());
                jobLog.setAssociateTaskInstance(input.getTaskExecutionInstance());
                jobLog.setConcurrencyNum(input.getConcurrencyNum());
                jobLog.setCreateDate(CommonUtils.currentDate(new Date()));
                jobLog.setTimeStamp(System.nanoTime());
                jobLog.setValidStatus(JobConstant.VALID_STATUS_0);
                return GitContext.getBean(BatchJobLogDao.class).insertOne(jobLog);
            } else {
                if (CommonUtils.notEquals(jobLog.getJobStatus(), JobExecutorEnum.NEW.getValue())) {
                    jobLog.setJobStatus(JobExecutorEnum.NEW.getValue());
                }
                jobLog.setTimeStamp(System.nanoTime());
                return GitContext.getBean(BatchJobLogDao.class).updateOne1R(jobLog);
            }
        },taskDto);
    }

    public static String completeExecutionID(String seqName, String prefix){
        return CommonUtils.fillByRule(getExecutionID(seqName), JobConstant.NAME_DIGITS, JobConstant.FILL_STR, prefix);
    }

    public static String getExecutionID(String seqName){
        return GitContext.getBean(BatchConfCustomDao.class).getBatchSequence(seqName);
    }
}
