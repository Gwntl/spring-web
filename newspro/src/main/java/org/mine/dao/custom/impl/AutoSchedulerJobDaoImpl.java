package org.mine.dao.custom.impl;

import java.util.HashMap;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.custom.AutoSchedulerJobDao;
import org.springframework.stereotype.Repository;

@Repository
public class AutoSchedulerJobDaoImpl extends BaseDaoSupport implements AutoSchedulerJobDao {

	@Override
	public int updateTriggerStatus(String triggerId, String vaildStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateTaskExectorStatus(String executeTaskId, String vaildStatus) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("executeTaskId", executeTaskId);
		map.put("vaildStatus", vaildStatus);
		return getSqlSessionTemplate().update("AutoSchedulerJob.updateStepExectorStatus", map);
	}

}
