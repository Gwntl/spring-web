package org.mine.dao;

import java.util.List;
import org.mine.model.BatchGroupDefinition;

/**
 * 
 * @filename BatchGroupDefinitionDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/

public interface BatchGroupDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchGroupDefinition 
	 */
	int insertOne(BatchGroupDefinition batchGroupDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	void batchInsert(List<BatchGroupDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchGroupDefinition 
	 */
	void batchInsertXML(List<BatchGroupDefinition> list);
	/**
	 * 单笔查询
	 * @param groupId 任务执行组ID
	 */
	BatchGroupDefinition selectOne1(Long groupId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param groupId 任务执行组ID
	 */
	BatchGroupDefinition selectOne1R(Long groupId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupId 任务执行组ID
	 */
	BatchGroupDefinition selectOne1L(Long groupId, boolean nullException);
	/**
	 * 单笔删除
	 * @param groupId 任务执行组ID
	 */
	int deleteOne1(Long groupId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupId 任务执行组ID
	 */
	int deleteOne1L(Long groupId);
	/**
	 * 单笔更新
	 * @param BatchGroupDefinition 
	 */
	int updateOne1(BatchGroupDefinition batchGroupDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchGroupDefinition 
	 */
	int updateOne1R(BatchGroupDefinition batchGroupDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchGroupDefinition 
	 */
	int updateOne1L(BatchGroupDefinition batchGroupDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchGroupDefinition 
	 */
	void batchUpdateXML1(List<BatchGroupDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	void batchUpdate(List<BatchGroupDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	void batchDelete(List<BatchGroupDefinition> list);
	/**
	 * 查询多笔数据
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	List<BatchGroupDefinition> selectAll1(Long groupAssociateQueueId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	List<BatchGroupDefinition> selectAll1R(Long groupAssociateQueueId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	List<BatchGroupDefinition> selectAll1L(Long groupAssociateQueueId);
}