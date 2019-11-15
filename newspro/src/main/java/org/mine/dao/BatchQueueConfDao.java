package org.mine.dao;

import java.util.List;
import org.mine.model.BatchQueueConf;

/**
 * 
 * @filename BatchQueueConfDao.java
 * @author wzaUsers
 * @date 2019-11-14 20:11:21
 * @version v1.0
*/

public interface BatchQueueConfDao {
	/**
	 * 单笔插入
	 * @param BatchQueueConf 
	 */
	int insertOne(BatchQueueConf batchQueueConf);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchQueueConf 
	 */
	void batchInsert(List<BatchQueueConf> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchQueueConf 
	 */
	void batchInsertXML(List<BatchQueueConf> list);
	/**
	 * 单笔查询
	 * @param queueId 队列ID
	 */
	BatchQueueConf selectOne1(Long queueId);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param queueId 队列ID
	 */
	BatchQueueConf selectOne1R(Long queueId);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	BatchQueueConf selectOne1L(Long queueId);
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
	 * @param BatchQueueConf 
	 */
	int updateOne1(BatchQueueConf batchQueueConf);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchQueueConf 
	 */
	int updateOne1R(BatchQueueConf batchQueueConf);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchQueueConf 
	 */
	int updateOne1L(BatchQueueConf batchQueueConf);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchQueueConf 
	 */
	void batchUpdateXML1(List<BatchQueueConf> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchQueueConf 
	 */
	void batchUpdate(List<BatchQueueConf> list);
}