package org.mine.quartz.job;

import java.util.Map;

import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//disallowconcurrent不允许同一个JobDetail定义的作业并发执行.
@DisallowConcurrentExecution
public class NoConcurrentExcutorJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		GitContext.doIndependentTransActionControl(new BatchOperator() {

			@Override
			public Object call(Map<String, Object> map) {
				// TODO Auto-generated method stub
				return null;
			}
			
		}, context.getJobDetail().getJobDataMap());
	}
}
