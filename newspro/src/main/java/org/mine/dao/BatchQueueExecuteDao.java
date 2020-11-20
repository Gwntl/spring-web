package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueExecute;

/**
 * 
 * @filename BatchQueueExecuteDao.java
 * @author wzaUsers
 * @date 2020-09-14 11:09:02
 * @version v1.0
*/

public interface BatchQueueExecuteDao {
	/**
	 * 单笔插入
	 * @param batchQueueExecute 
	 */
	int insertOne(BatchQueueExecute batchQueueExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchQueueExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchQueueExecute> list);
	/**
	 * 单笔查询
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	BatchQueueExecute selectOne1(String executeQueueId, String executeTaskId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	BatchQueueExecute selectOne1R(String executeQueueId, String executeTaskId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	BatchQueueExecute selectOne1L(String executeQueueId, String executeTaskId, boolean nullException);
	/**
	 * 单笔删除
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	int deleteOne1(String executeQueueId, String executeTaskId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	int deleteOne1L(String executeQueueId, String executeTaskId);
	/**
	 * 单笔更新
	 * @param batchQueueExecute 
	 */
	int updateOne1(BatchQueueExecute batchQueueExecute);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueExecute 
	 */
	int updateOne1R(BatchQueueExecute batchQueueExecute);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueExecute 
	 */
	int updateOne1L(BatchQueueExecute batchQueueExecute);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchQueueExecute> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchQueueExecute> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchQueueExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1(String executeQueueId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1R(String executeQueueId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 */
	List<BatchQueueExecute> selectAll1L(String executeQueueId);
}