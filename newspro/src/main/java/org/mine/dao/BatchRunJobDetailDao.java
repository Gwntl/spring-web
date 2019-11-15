package org.mine.dao;

import java.util.List;

import org.mine.model.BatchRunJobDetail;

public interface BatchRunJobDetailDao {

	public List<BatchRunJobDetail> selectAllInfo();
	
	public int insertOne(BatchRunJobDetail detail);
	
	public int updateOne(BatchRunJobDetail detail);
	
	public void batchInsert(List<BatchRunJobDetail> runJobDetails);
	
	public void batchUpdate(List<BatchRunJobDetail> runJobDetails);
}
