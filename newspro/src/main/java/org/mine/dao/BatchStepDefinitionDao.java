package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepDefinition;

/**
 * 
 * @filename BatchStepDefinitionDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchStepDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchStepDefinition 
	 */
	int insertOne(BatchStepDefinition batchStepDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchStepDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchStepDefinition> list);
	/**
	 * 单笔查询
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1(String stepId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1R(String stepId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1L(String stepId, boolean nullException);
	/**
	 * 单笔删除
	 * @param stepId 作业步骤ID
	 */
	int deleteOne1(String stepId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	int deleteOne1L(String stepId);
	/**
	 * 单笔更新
	 * @param batchStepDefinition 
	 */
	int updateOne1(BatchStepDefinition batchStepDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepDefinition 
	 */
	int updateOne1R(BatchStepDefinition batchStepDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepDefinition 
	 */
	int updateOne1L(BatchStepDefinition batchStepDefinition);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchStepDefinition> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchStepDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchStepDefinition> list);
}