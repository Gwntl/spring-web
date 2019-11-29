package org.mine.dao.custom;

public interface BatchConfCostomDao {
	Long getMaxQueueId();
	
	Long getTriggerConfMaxId();
	
	Long getJobGroupConfMaxId();
	
	Long getJobDetailConfMaxId(Long id);
	
	Long getBatchSequence(String seqName);
}
