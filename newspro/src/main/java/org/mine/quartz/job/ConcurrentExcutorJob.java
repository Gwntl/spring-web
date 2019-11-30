package org.mine.quartz.job;

import java.util.List;
import java.util.Map;

import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchJobDetailConfDao;
import org.mine.dao.custom.BatchConfCostomDao;
import org.mine.model.BatchJobDetailConf;
import org.mine.quartz.ExecutionLogicSubject;
import org.mine.quartz.TaskRepositoryValues;
import org.mine.quartz.job.logic.JTaskExecutionUnit;
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
		GitContext.doIndependentTransActionControl(new BatchOperator() {
			@Override
			public Object call(Map<String, Object> dataMap) {
				GitContext.init(dataMap);
				//是否记录日志
				String isSaveLog = dataMap.get("savelog") + "";
				//作业组ID
				Long groupId = (Long)dataMap.get("group_id");
				
				BatchJobDetailConfDao detailConfDao = GitContext.getBean(BatchJobDetailConfDao.class);
				List<BatchJobDetailConf> detailConfs = detailConfDao.selectAll1R(groupId);
				final TaskRepositoryValues values = new TaskRepositoryValues();
				values.setIsSaveLog(isSaveLog);
				values.setRunnerId(isSaveLog.equals(JobExcutorEnum.SAVE_LOG)
						? GitContext.getBean(BatchConfCostomDao.class).getBatchSequence("batch_sequence_1") : -1L);
				values.setJobDetailId(detailConfs.get(0).getJobdetailId());
				values.setJobDetailProvider(detailConfs.get(0).getJobdetailProvider());
				
				JTaskExecutionUnit.performsConcreteLogic(new ExecutionLogicSubject() {
					@Override
					public void logicalSubject(Map<String, Object> groupInfo) {
//						groupInfo.put("javaClass", values.getJobDetailProvider());
					}
				}, values, dataMap);
				return null;
			}
		}, context.getJobDetail().getJobDataMap());
	}
}
