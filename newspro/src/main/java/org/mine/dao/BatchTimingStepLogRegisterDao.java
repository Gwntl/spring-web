package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingStepLogRegister;

/**
 * 
 * @filename BatchTimingStepLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/

public interface BatchTimingStepLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTimingStepLogRegister 
	 */
	int insertOne(BatchTimingStepLogRegister batchTimingStepLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	void batchInsert(List<BatchTimingStepLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingStepLogRegister 
	 */
	void batchInsertXML(List<BatchTimingStepLogRegister> list);
	/**
	 * 单笔查询
	 * @param stepExecutionId 任务执行ID
	 */
	BatchTimingStepLogRegister selectOne1(Long stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchTimingStepLogRegister selectOne1R(Long stepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	BatchTimingStepLogRegister selectOne1L(Long stepExecutionId, boolean nullException);
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
	 * @param BatchTimingStepLogRegister 
	 */
	int updateOne1(BatchTimingStepLogRegister batchTimingStepLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingStepLogRegister 
	 */
	int updateOne1R(BatchTimingStepLogRegister batchTimingStepLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingStepLogRegister 
	 */
	int updateOne1L(BatchTimingStepLogRegister batchTimingStepLogRegister);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	void batchUpdate(List<BatchTimingStepLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	void batchDelete(List<BatchTimingStepLogRegister> list);
}