package org.mine.dao.impl;

import java.util.List;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.BatchRunJobDetailDao;
import org.mine.model.BatchRunJobDetail;
import org.springframework.stereotype.Repository;

@Repository
public class BatchRunJobDetailDaoImpl extends BaseDaoSupport implements BatchRunJobDetailDao{

	@Override
	public List<BatchRunJobDetail> selectAllInfo() {
		return null;
	}

	@Override
	public int insertOne(BatchRunJobDetail detail) {
		return getSqlSessionTemplate().insert("batchRunJobDetail.insertOne", detail);
	}

	@Override
	public int updateOne(BatchRunJobDetail detail) {
		return 0;
	}

	@Override
	public void batchInsert(List<BatchRunJobDetail> runJobDetails) {
//		BatchInsertByXML(runJobDetails, "runJobDetails", 10, new BatchOperator() {
//			
//			@Override
//			public void call(Map<String, Object> map) {
//				getSqlSessionTemplate().insert("batchRunJobDetail.batchInsert", map);
//			}
//		});
		batchExcutor("batchRunJobDetail.insertOne", runJobDetails, "insert", 10);
	}

	@Override
	public void batchUpdate(List<BatchRunJobDetail> runJobDetails) {
		batchExcutor("batchRunJobDetail.batchUpdate", runJobDetails, "update", 10);
	}
}
