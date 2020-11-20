package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueLog;

/**
 * 
 * @filename BatchQueueLogDao.java
 * @author wzaUsers
 * @date 2020-09-07 19:09:41
 * @version v1.0
*/

public interface BatchQueueLogDao {
	/**
	 * 单笔插入
	 * @param batchQueueLog 
	 */
	int insertOne(BatchQueueLog batchQueueLog);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchQueueLog> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchQueueLog> list);
	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	BatchQueueLog selectOne1(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	BatchQueueLog selectOne1R(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	BatchQueueLog selectOne1L(String executionInstance, boolean nullException);
	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	int deleteOne1(String executionInstance);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	int deleteOne1L(String executionInstance);
	/**
	 * 单笔更新
	 * @param batchQueueLog 
	 */
	int updateOne1(BatchQueueLog batchQueueLog);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueLog 
	 */
	int updateOne1R(BatchQueueLog batchQueueLog);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueLog 
	 */
	int updateOne1L(BatchQueueLog batchQueueLog);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchQueueLog> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchQueueLog> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchQueueLog> list);
}