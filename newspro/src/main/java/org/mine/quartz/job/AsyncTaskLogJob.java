package org.mine.quartz.job;

import org.mine.quartz.job.run.JobTaskLogic;
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
 * @filename AsyncTaskJob.java
 * @author wzaUsers
 * @date 2019年11月26日下午8:13:01
 * @version v1.0
 */
public class AsyncTaskLogJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(AsyncTaskLogJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("AsyncTaskJob begin >>>>>>>>>>");
		new JobTaskLogic("").logic(context.getJobDetail().getJobDataMap());
		logger.debug("AsyncTaskJob end >>>>>>>>>>");
	}
}
