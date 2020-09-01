package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepExecutionLogRegister;

/**
 * 
 * @filename BatchStepExecutionLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchStepExecutionLogRegisterDao {
	/**
	 * 单笔插入
	 * @param batchStepExecutionLogRegister 
	 */
	int insertOne(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchStepExecutionLogRegister> list);
	/**
	 * 单笔查询
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1(String stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1R(String stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1L(String stepExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param stepExecutionId 任务执行ID
	 */
	int deleteOne1(String stepExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	int deleteOne1L(String stepExecutionId);
	/**
	 * 单笔更新
	 * @param batchStepExecutionLogRegister 
	 */
	int updateOne1(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepExecutionLogRegister 
	 */
	int updateOne1R(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepExecutionLogRegister 
	 */
	int updateOne1L(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchStepExecutionLogRegister> list);
}