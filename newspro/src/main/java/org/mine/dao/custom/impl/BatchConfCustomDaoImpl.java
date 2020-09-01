package org.mine.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.custom.BatchConfCustomDao;
import org.springframework.stereotype.Repository;

@Repository
public class BatchConfCustomDaoImpl extends BaseDaoSupport implements BatchConfCustomDao {

	@Override
	public Integer getMaxNumByQueueDefin() {
		HashMap<String, Object> map = new HashMap<>();
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByQueueDefin", map);
	}

	@Override
	public Integer getMaxNumByGroupDefin() {
		Map<String, Object> map = new HashMap<>();
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByGroupDefin", map);
	}

	@Override
	public Integer getMaxNumByTaskExecute(String taskID) {
		Map<String, Object> map = new HashMap<>();
		map.put("taskID", taskID);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByTaskExecute", map);
	}

	@Override
	public Integer getMaxNumByJobExecute(String jobID) {
		Map<String, Object> map = new HashMap<>();
		map.put("jobID", jobID);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByJobExecute", map);
	}

	@Override
	public Integer getMaxNumByQueueExecute(String queueID) {
		Map<String, Object> map = new HashMap<>();
		map.put("queueID", queueID);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getMaxNumByQueueExecute", map);
	}

	@Override
	public String getBatchSequence(String seqName) {
		Map<String, Object> map = new HashMap<>();
		map.put("seqName", seqName);
		return getSqlSessionTemplate().selectOne("BatchConfCustom.getBatchSequence", map) + "";
	}

	@Override
	public int updateStepLogInUnknownFailed(String executionInstance, String stepStatus, String stepErrorMsg) {
		Map<String, Object> map = new HashMap<>();
		map.put("executionInstance", executionInstance);
		map.put("stepStatus", stepStatus);
		map.put("stepErrorMsg", stepErrorMsg);
		map.put("timeStamp", System.nanoTime());
		return getSqlSessionTemplate().update("BatchConfCustom.updateStepLogInUnknownFailed", map);
	}
}
