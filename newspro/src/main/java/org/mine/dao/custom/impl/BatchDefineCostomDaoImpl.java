package org.mine.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.custom.BatchDefineCostomDao;
import org.springframework.stereotype.Repository;

@Repository
public class BatchDefineCostomDaoImpl extends BaseDaoSupport implements BatchDefineCostomDao {

	@Override
	public Integer getMaxNumByGroupDefin() {
		Map<String, Object> map = new HashMap<>();
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByGroupDefin", map);
	}

	@Override
	public Long getBatchSequence(String seqName) {
		Map<String, Object> map = new HashMap<>();
		map.put("seqName", seqName);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getBatchSequence", map);
	}
}
