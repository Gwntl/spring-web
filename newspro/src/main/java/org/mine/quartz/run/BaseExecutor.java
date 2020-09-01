package org.mine.quartz.run;

import org.mine.aplt.constant.JobContanst;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.mine.dao.BatchJobLogDao;
import org.mine.dao.custom.BatchConfCustomDao;
import org.mine.model.BatchJobLog;
import org.mine.quartz.JobExcutorFactory;
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
    public static void baseExecutor(ExecuteTaskDto taskDto){
        logger.debug("BaseExecutor.baseExecutor() begin >>>>>>>>>>>>>>>>>>>>>>");
        if (CommonUtils.isEmpty(taskDto.getExecutionInstance())) {
            taskDto.setExecutionInstance(preStartupProcessing(taskDto.getJobId()));
        }
        GitContext.doIndependentTransActionControl((input) -> {
            BatchJobLog jobLog = new BatchJobLog();
            jobLog.setExecutionInstance(input.getExecutionInstance());
            jobLog.setJobId(input.getJobId());
            jobLog.setJobName(input.getJobName());
            jobLog.setJobStatus(JobExcutorEnum.NEW.getValue());
            jobLog.setAssociateTaskId(input.getTaskId());
            jobLog.setAssociateTaskInstance(input.getTaskExecutionInstance());
            jobLog.setConcurrencyNum(input.getConcurrencyNum());
            jobLog.setCreateDate(CommonUtils.currentDate(new Date()));
            jobLog.setTimeStamp(System.nanoTime());
            jobLog.setValidStatus(JobContanst.VALID_STATUS_0);
            return GitContext.getBean(BatchJobLogDao.class).insertOne(jobLog);
        },taskDto);

        if (taskDto.getJobLogFlag() == 0) {
            JobExcutorFactory.call(new JobRecodeLogLogic(taskDto), taskDto.getJobLogFlag());
        } else {
            JobExcutorFactory.call(new JobNoRecodeLogLogic(taskDto), taskDto.getJobLogFlag());
        }
        logger.debug("BaseExecutor.baseExecutor() end >>>>>>>>>>>>>>>>>>>>>>");
    }
    /**
    * 执行前处理
    * @param jonID
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/8/26
    */
    public static String preStartupProcessing(String jonID) {
        GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jonID, true);
        return completeExecutionID("sequence_instance", JobContanst.EXECUTOR_PREFIX);
    }

    public static String completeExecutionID(String seqName, String prefix){
        return CommonUtils.fillByRule(getExecutionID(seqName), JobContanst.NAME_DIGITS, JobContanst.FILL_STR, prefix);
    }

    public static String getExecutionID(String seqName){
        return GitContext.getBean(BatchConfCustomDao.class).getBatchSequence(seqName);
    }
}
