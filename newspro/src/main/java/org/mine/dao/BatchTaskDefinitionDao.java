package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskDefinition;

/**
 * 
 * @filename BatchTaskDefinitionDao.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:39
 * @version v1.0
*/

public interface BatchTaskDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchTaskDefinition 
	 */
	int insertOne(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	void batchInsert(List<BatchTaskDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskDefinition 
	 */
	void batchInsertXML(List<BatchTaskDefinition> list);
	/**
	 * 单笔查询
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1(Long taskId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1R(Long taskId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1L(Long taskId, boolean nullException);
	/**
	 * 单笔删除
	 * @param taskId 任务ID
	 */
	int deleteOne1(Long taskId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	int deleteOne1L(Long taskId);
	/**
	 * 单笔更新
	 * @param BatchTaskDefinition 
	 */
	int updateOne1(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskDefinition 
	 */
	int updateOne1R(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskDefinition 
	 */
	int updateOne1L(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskDefinition 
	 */
	void batchUpdateXML1(List<BatchTaskDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	void batchUpdate(List<BatchTaskDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	void batchDelete(List<BatchTaskDefinition> list);
	/**
	 * 查询多笔数据
	 * @param taskAssociateQueueId 关联队列ID
	 */
	List<BatchTaskDefinition> selectAll1(Long taskAssociateQueueId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param taskAssociateQueueId 关联队列ID
	 */
	List<BatchTaskDefinition> selectAll1R(Long taskAssociateQueueId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskAssociateQueueId 关联队列ID
	 */
	List<BatchTaskDefinition> selectAll1L(Long taskAssociateQueueId);
	/**
	 * 查询多笔数据
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll2(Integer taskAutoFlag);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll2R(Integer taskAutoFlag);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll2L(Integer taskAutoFlag);
}