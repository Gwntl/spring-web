package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueExecute;

/**
 * 
 * @filename BatchQueueExecuteDao.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:21
 * @version v1.0
*/

public interface BatchQueueExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchQueueExecute 
	 */
	int insertOne(BatchQueueExecute batchQueueExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchQueueExecute 
	 */
	void batchInsert(List<BatchQueueExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchQueueExecute 
	 */
	void batchInsertXML(List<BatchQueueExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1(Long executeQueueId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1R(Long executeQueueId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1L(Long executeQueueId);
}