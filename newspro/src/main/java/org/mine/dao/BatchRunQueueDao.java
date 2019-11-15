package org.mine.dao;

import java.util.List;

import org.mine.model.BatchRunQueue;

public interface BatchRunQueueDao {

	List<BatchRunQueue> selectAllQueues();
	
	int insertOne(BatchRunQueue queue);
	
	int updateOne(BatchRunQueue queue);
}
