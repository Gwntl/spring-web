package org.mine.quartz.job;

import org.mine.aplt.support.bean.GitContext;
import org.mine.dao.BatchTaskDefinitionDao;
import org.mine.quartz.dto.ExecuteTaskDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QuartzTimingQueueExecutor
 * @date 2020/8/1317:20
 */
public class QuartzTimingQueueExecutor implements Job{
    private static final Logger logger = LoggerFactory.getLogger(QuartzTimingQueueExecutor.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("QuartzTimingQueueExecutor.execute() begin >>>>>>>>>>>>>>>>>>>>>>");
        Map<String, Object> map = context.getJobDetail().getJobDataMap();
        ExecuteTaskDto taskDto = (ExecuteTaskDto) map.get("dto");
        String taskID = taskDto.getTaskId();
        GitContext.getBean(BatchTaskDefinitionDao.class).selectOne1R(taskID, true);
        List<String> executeTaskIDs = GitContext.queryForSingleFieldList(
                "SELECT * FROM BATCH_TASK_EXECUTE WHERE EXECUTE_TASK_ID = ? AND VALID_STATUS = '0' ORDER BY EXECUTE_JOB_NUM"
                , new Object[]{taskID}, String.class);

        logger.debug("QuartzTimingQueueExecutor.execute() end >>>>>>>>>>>>>>>>>>>>>>");
    }
}
