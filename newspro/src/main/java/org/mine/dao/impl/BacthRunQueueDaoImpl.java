package org.mine.dao.impl;

import java.util.List;

import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.dao.BatchRunQueueDao;
import org.mine.model.BatchRunQueue;
import org.springframework.stereotype.Repository;

@Repository
public class BacthRunQueueDaoImpl extends BaseDaoSupport implements BatchRunQueueDao {

	@Override
	public List<BatchRunQueue> selectAllQueues() {
		return getSqlSessionTemplate().selectList("batchRunQueue.selectAllQueues");
	}

	@Override
	public int insertOne(BatchRunQueue queue) {
		return getSqlSessionTemplate().insert("batchRunQueue.insertOne", queue);
	}

	@Override
	public int updateOne(BatchRunQueue queue) {
		return getSqlSessionTemplate().update("batchRunQueue.updateOne", queue);
	}
}
