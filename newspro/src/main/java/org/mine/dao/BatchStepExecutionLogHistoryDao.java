package org.mine.dao;

import java.util.List;
import org.mine.model.BatchStepExecutionLogHistory;

/**
 * 
 * @filename BatchStepExecutionLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/

public interface BatchStepExecutionLogHistoryDao {
	/**
	 * 单笔插入
	 * @param BatchStepExecutionLogHistory 
	 */
	int insertOne(BatchStepExecutionLogHistory batchStepExecutionLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogHistory 
	 */
	void batchInsert(List<BatchStepExecutionLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchStepExecutionLogHistory 
	 */
	void batchInsertXML(List<BatchStepExecutionLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogHistory selectOne1(Long historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogHistory selectOne1R(Long historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchStepExecutionLogHistory selectOne1L(Long historyStepExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyStepExecutionId 任务执行ID
	 */
	int deleteOne1(Long historyStepExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	int deleteOne1L(Long historyStepExecutionId);
	/**
	 * 单笔更新
	 * @param BatchStepExecutionLogHistory 
	 */
	int updateOne1(BatchStepExecutionLogHistory batchStepExecutionLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchStepExecutionLogHistory 
	 */
	int updateOne1R(BatchStepExecutionLogHistory batchStepExecutionLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchStepExecutionLogHistory 
	 */
	int updateOne1L(BatchStepExecutionLogHistory batchStepExecutionLogHistory);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchStepExecutionLogHistory 
	 */
	void batchUpdateXML1(List<BatchStepExecutionLogHistory> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogHistory 
	 */
	void batchUpdate(List<BatchStepExecutionLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchStepExecutionLogHistory 
	 */
	void batchDelete(List<BatchStepExecutionLogHistory> list);
}