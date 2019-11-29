package org.mine.service.impl;

import java.util.List;
import java.util.Map;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.dao.BatchRunJobDetailDao;
import org.mine.dao.BatchRunQueueDao;
import org.mine.model.BatchRunJobDetail;
import org.mine.model.BatchRunQueue;
import org.mine.service.BatchRunQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchRunQueueServiceImpl implements BatchRunQueueService {

	@Autowired
	private BatchRunQueueDao queueDao;
	
	@Autowired
	private BatchRunJobDetailDao detailDao;
	
	@Override
	public List<BatchRunQueue> selectAllQueues() {
		return queueDao.selectAllQueues();
	}
	
	@Override
	public void batchRunInfo(BatchRunQueue queue, List<BatchRunJobDetail> details){
		queueDao.insertOne(queue);
		detailDao.batchInsert(details);
	}
}
