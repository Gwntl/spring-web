package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepLogHistory;

/**
 * 
 * @filename BatchStepLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-08-20 17:08:03
 * @version v1.0
*/

public interface BatchStepLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchStepLogHistory 
	 */
	int insertOne(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchStepLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchStepLogHistory> list);
	/**
	 * 单笔查询
	 * @param executionId 任务执行ID
	 */
	BatchStepLogHistory selectOne1(String executionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 任务执行ID
	 */
	BatchStepLogHistory selectOne1R(String executionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	BatchStepLogHistory selectOne1L(String executionId, boolean nullException);
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
	 * @param batchStepLogHistory 
	 */
	int updateOne1(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLogHistory 
	 */
	int updateOne1R(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLogHistory 
	 */
	int updateOne1L(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 单笔查询
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchStepLogHistory selectOne2(String executionInstance, String stepId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchStepLogHistory selectOne2R(String executionInstance, String stepId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	BatchStepLogHistory selectOne2L(String executionInstance, String stepId, boolean nullException);
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
	 * @param batchStepLogHistory 
	 */
	int updateOne2(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLogHistory 
	 */
	int updateOne2R(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLogHistory 
	 */
	int updateOne2L(BatchStepLogHistory batchStepLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchStepLogHistory> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate2(List<BatchStepLogHistory> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchStepLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchStepLogHistory> list);
}