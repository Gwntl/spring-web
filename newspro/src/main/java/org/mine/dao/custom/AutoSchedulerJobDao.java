package org.mine.dao.custom;

public interface AutoSchedulerJobDao {
	int updateTriggerStatus(String triggerId, String vaildStatus);
	
	int updateTaskExectorStatus(String executeTaskId, String vaildStatus);
}
