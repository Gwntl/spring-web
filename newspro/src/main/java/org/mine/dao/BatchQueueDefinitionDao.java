package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueDefinition;

/**
 * 
 * @filename BatchQueueDefinitionDao.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:43
 * @version v1.0
*/

public interface BatchQueueDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchQueueDefinition 
	 */
	int insertOne(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchQueueDefinition 
	 */
	void batchInsert(List<BatchQueueDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchQueueDefinition 
	 */
	void batchInsertXML(List<BatchQueueDefinition> list);
	/**
	 * 单笔查询
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1(Long queueId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1R(Long queueId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	BatchQueueDefinition selectOne1L(Long queueId, boolean nullException);
	/**
	 * 单笔删除
	 * @param queueId 队列ID
	 */
	int deleteOne1(Long queueId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	int deleteOne1L(Long queueId);
	/**
	 * 单笔更新
	 * @param BatchQueueDefinition 
	 */
	int updateOne1(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchQueueDefinition 
	 */
	int updateOne1R(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchQueueDefinition 
	 */
	int updateOne1L(BatchQueueDefinition batchQueueDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchQueueDefinition 
	 */
	void batchUpdateXML1(List<BatchQueueDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchQueueDefinition 
	 */
	void batchUpdate(List<BatchQueueDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchQueueDefinition 
	 */
	void batchDelete(List<BatchQueueDefinition> list);
	/**
	 * 查询多笔数据
	 * @param queueTimingtaskFlag 是否为定时任务. 0-是,1-否
	 */
	List<BatchQueueDefinition> selectAll1(Integer queueTimingtaskFlag);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param queueTimingtaskFlag 是否为定时任务. 0-是,1-否
	 */
	List<BatchQueueDefinition> selectAll1R(Integer queueTimingtaskFlag);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueTimingtaskFlag 是否为定时任务. 0-是,1-否
	 */
	List<BatchQueueDefinition> selectAll1L(Integer queueTimingtaskFlag);
}