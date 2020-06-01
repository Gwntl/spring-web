package org.mine.dao.custom;

public interface BatchDefineCostomDao {
	
	Integer getMaxNumByGroupDefin();
	
	Long getBatchSequence(String seqName);
	
	int updateStepLogExecuStatus(Long stepExecutionId, String stepJobStatus, String stepJobErrmsg);
}
