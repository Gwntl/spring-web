package org.mine.service;

import java.util.List;

import org.mine.model.BatchRunJobDetail;
import org.mine.model.BatchRunQueue;

public interface BatchRunQueueService {
	
	List<BatchRunQueue> selectAllQueues();
	
	void batchRunInfo(BatchRunQueue queue, List<BatchRunJobDetail> details);
}
