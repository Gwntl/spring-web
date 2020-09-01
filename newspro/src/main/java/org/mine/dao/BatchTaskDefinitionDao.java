package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskDefinition;

/**
 * 
 * @filename BatchTaskDefinitionDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchTaskDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchTaskDefinition 
	 */
	int insertOne(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTaskDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTaskDefinition> list);
	/**
	 * 单笔查询
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1(String taskId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1R(String taskId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	BatchTaskDefinition selectOne1L(String taskId, boolean nullException);
	/**
	 * 单笔删除
	 * @param taskId 任务ID
	 */
	int deleteOne1(String taskId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	int deleteOne1L(String taskId);
	/**
	 * 单笔更新
	 * @param batchTaskDefinition 
	 */
	int updateOne1(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskDefinition 
	 */
	int updateOne1R(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskDefinition 
	 */
	int updateOne1L(BatchTaskDefinition batchTaskDefinition);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTaskDefinition> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTaskDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTaskDefinition> list);
	/**
	 * 查询多笔数据
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll1(Integer taskAutoFlag);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll1R(Integer taskAutoFlag);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchTaskDefinition> selectAll1L(Integer taskAutoFlag);
}