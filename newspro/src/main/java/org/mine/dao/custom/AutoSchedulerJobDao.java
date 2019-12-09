package org.mine.dao.custom;

public interface AutoSchedulerJobDao {
	int updateTriggerStatus(String trigger_id, String vaild_status);
}
