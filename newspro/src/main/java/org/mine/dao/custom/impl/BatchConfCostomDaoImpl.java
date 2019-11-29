package org.mine.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.custom.BatchConfCostomDao;
import org.springframework.stereotype.Repository;

@Repository
public class BatchConfCostomDaoImpl extends BaseDaoSupport implements BatchConfCostomDao {

	@Override
	public Long getMaxQueueId() {
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxQueueId");
	}

	@Override
	public Long getTriggerConfMaxId() {
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getTriggerConfMaxId");
	}

	@Override
	public Long getJobGroupConfMaxId() {
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getJobGroupConfMaxId");
	}

	@Override
	public Long getJobDetailConfMaxId(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getJobDetailConfMaxId", map);
	}

	@Override
	public Long getBatchSequence(String seqName) {
		Map<String, Object> map = new HashMap<>();
		map.put("seqName", seqName);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getBatchSequence", map);
	}

}
