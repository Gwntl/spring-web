package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTriggerDefinition;

/**
 * 
 * @filename BatchTriggerDefinitionDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/

public interface BatchTriggerDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchTriggerDefinition 
	 */
	int insertOne(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	void batchInsert(List<BatchTriggerDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTriggerDefinition 
	 */
	void batchInsertXML(List<BatchTriggerDefinition> list);
	/**
	 * 单笔查询
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1(Long triggerId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1R(Long triggerId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerDefinition selectOne1L(Long triggerId, boolean nullException);
	/**
	 * 单笔删除
	 * @param triggerId 触发器ID
	 */
	int deleteOne1(Long triggerId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	int deleteOne1L(Long triggerId);
	/**
	 * 单笔更新
	 * @param BatchTriggerDefinition 
	 */
	int updateOne1(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTriggerDefinition 
	 */
	int updateOne1R(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTriggerDefinition 
	 */
	int updateOne1L(BatchTriggerDefinition batchTriggerDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTriggerDefinition 
	 */
	void batchUpdateXML1(List<BatchTriggerDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	void batchUpdate(List<BatchTriggerDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	void batchDelete(List<BatchTriggerDefinition> list);
}