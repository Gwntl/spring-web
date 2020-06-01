package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepExecutionLogRegister;

/**
 * 
 * @filename BatchStepExecutionLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:24
 * @version v1.0
*/

public interface BatchStepExecutionLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchStepExecutionLogRegister 
	 */
	int insertOne(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogRegister 
	 */
	void batchInsert(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchStepExecutionLogRegister 
	 */
	void batchInsertXML(List<BatchStepExecutionLogRegister> list);
	/**
	 * 单笔查询
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1(Long stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1R(Long stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogRegister selectOne1L(Long stepExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param stepExecutionId 任务执行ID
	 */
	int deleteOne1(Long stepExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	int deleteOne1L(Long stepExecutionId);
	/**
	 * 单笔更新
	 * @param BatchStepExecutionLogRegister 
	 */
	int updateOne1(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchStepExecutionLogRegister 
	 */
	int updateOne1R(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchStepExecutionLogRegister 
	 */
	int updateOne1L(BatchStepExecutionLogRegister batchStepExecutionLogRegister);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchStepExecutionLogRegister 
	 */
	void batchUpdateXML1(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogRegister 
	 */
	void batchUpdate(List<BatchStepExecutionLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogRegister 
	 */
	void batchDelete(List<BatchStepExecutionLogRegister> list);
}