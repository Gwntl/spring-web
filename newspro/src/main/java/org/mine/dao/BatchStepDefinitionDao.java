package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepDefinition;

/**
 * 
 * @filename BatchStepDefinitionDao.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:39
 * @version v1.0
*/

public interface BatchStepDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchStepDefinition 
	 */
	int insertOne(BatchStepDefinition batchStepDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	void batchInsert(List<BatchStepDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchStepDefinition 
	 */
	void batchInsertXML(List<BatchStepDefinition> list);
	/**
	 * 单笔查询
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1(Long stepId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1R(Long stepId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	BatchStepDefinition selectOne1L(Long stepId, boolean nullException);
	/**
	 * 单笔删除
	 * @param stepId 作业步骤ID
	 */
	int deleteOne1(Long stepId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	int deleteOne1L(Long stepId);
	/**
	 * 单笔更新
	 * @param BatchStepDefinition 
	 */
	int updateOne1(BatchStepDefinition batchStepDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchStepDefinition 
	 */
	int updateOne1R(BatchStepDefinition batchStepDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchStepDefinition 
	 */
	int updateOne1L(BatchStepDefinition batchStepDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchStepDefinition 
	 */
	void batchUpdateXML1(List<BatchStepDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	void batchUpdate(List<BatchStepDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	void batchDelete(List<BatchStepDefinition> list);
}