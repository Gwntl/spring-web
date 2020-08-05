package org.mine.quartz.job;

import java.util.LinkedList;
import java.util.List;

import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchQueueExecuteDao;
import org.mine.model.BatchQueueExecute;
import org.mine.quartz.dto.ConcurrTaskDto;
import org.mine.quartz.job.run.JobQueueLogic;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:  异步定时队列执行器.
 * @ClassName: AsyncQueueJob
 * @author: wntl
 * @date: 2020年6月9日 下午4:11:08
 */
public class AsyncQueueJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(AsyncQueueJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("AsyncQueueJob begin >>>>>>>>>");
		//TODO 定时队列执行逻辑
		/*
		 * 1.保存队列执行日志.(QUEUE)
		 * 2.保存任务执行日志.(TASK)
		 * 3.保存作业执行日志.(JOB)
		 * 4.保存步骤执行日志.(STEP)
		*/
		GitContext.doIndependentTransActionControl(new BatchOperator<Object, JobDataMap>() {
			@Override
			public Object call(JobDataMap map) {
				
				ConcurrTaskDto dto = (ConcurrTaskDto) map.get("dto");
				List<BatchQueueExecute> queueExecutes = GitContext.getBean(BatchQueueExecuteDao.class).selectAll1R(dto.getQueueId());
				LinkedList<Long> executorTaskIds = new LinkedList<>();
				for (BatchQueueExecute execute : queueExecutes) {
					executorTaskIds.add(execute.getExecuteTaskId());
				}
				Long nanoTime = System.nanoTime();
				JobQueueLogic.map.put(nanoTime, executorTaskIds);
//				new JobQueueLogic(new JobDetailFactoryBean)
				
				return null;
			}
		}, context.getJobDetail().getJobDataMap());
		
		
		logger.debug("AsyncQueueJob end >>>>>>>>>");
		return;
	}
}
