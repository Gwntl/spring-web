package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTriggerDefinition;

/**
 * 
 * @filename BatchTriggerDefinitionDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchTriggerDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchTriggerDefinition 
	 */
	int insertOne(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTriggerDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTriggerDefinition> list);
	/**
	 * 单笔查询
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1(String triggerId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1R(String triggerId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1L(String triggerId, boolean nullException);
	/**
	 * 单笔删除
	 * @param triggerId 触发器ID
	 */
	int deleteOne1(String triggerId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	int deleteOne1L(String triggerId);
	/**
	 * 单笔更新
	 * @param batchTriggerDefinition 
	 */
	int updateOne1(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTriggerDefinition 
	 */
	int updateOne1R(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTriggerDefinition 
	 */
	int updateOne1L(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTriggerDefinition> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTriggerDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTriggerDefinition> list);
}