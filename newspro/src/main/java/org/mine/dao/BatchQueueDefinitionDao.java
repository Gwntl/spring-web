package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueDefinition;

/**
 * 
 * @filename BatchQueueDefinitionDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchQueueDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchQueueDefinition 
	 */
	int insertOne(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchQueueDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchQueueDefinition> list);
	/**
	 * 单笔查询
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1(String queueId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1R(String queueId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1L(String queueId, boolean nullException);
	/**
	 * 单笔删除
	 * @param queueId 队列ID
	 */
	int deleteOne1(String queueId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	int deleteOne1L(String queueId);
	/**
	 * 单笔更新
	 * @param batchQueueDefinition 
	 */
	int updateOne1(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueDefinition 
	 */
	int updateOne1R(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueDefinition 
	 */
	int updateOne1L(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchQueueDefinition> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchQueueDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchQueueDefinition> list);
	/**
	 * 查询多笔数据
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	List<BatchQueueDefinition> selectAll1(Integer queueExcFlag);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	List<BatchQueueDefinition> selectAll1R(Integer queueExcFlag);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	List<BatchQueueDefinition> selectAll1L(Integer queueExcFlag);
}