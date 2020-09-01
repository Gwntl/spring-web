package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingJobLogRegister;

/**
 * 
 * @filename BatchTimingJobLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchTimingJobLogRegisterDao {
	/**
	 * 单笔插入
	 * @param batchTimingJobLogRegister 
	 */
	int insertOne(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTimingJobLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTimingJobLogRegister> list);
	/**
	 * 单笔查询
	 * @param executionId 任务执行ID
	 */
	BatchTimingJobLogRegister selectOne1(String executionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 任务执行ID
	 */
	BatchTimingJobLogRegister selectOne1R(String executionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	BatchTimingJobLogRegister selectOne1L(String executionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param executionId 任务执行ID
	 */
	int deleteOne1(String executionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	int deleteOne1L(String executionId);
	/**
	 * 单笔更新
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne1(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne1R(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne1L(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 单笔查询
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchTimingJobLogRegister selectOne2(String executionInstance, String stepId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchTimingJobLogRegister selectOne2R(String executionInstance, String stepId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchTimingJobLogRegister selectOne2L(String executionInstance, String stepId, boolean nullException);
	/**
	 * 单笔删除
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	int deleteOne2(String executionInstance, String stepId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	int deleteOne2L(String executionInstance, String stepId);
	/**
	 * 单笔更新
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne2(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne2R(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogRegister 
	 */
	int updateOne2L(BatchTimingJobLogRegister batchTimingJobLogRegister);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTimingJobLogRegister> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate2(List<BatchTimingJobLogRegister> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTimingJobLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTimingJobLogRegister> list);
}