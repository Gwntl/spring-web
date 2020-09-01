package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingStepLogHistory;

/**
 * 
 * @filename BatchTimingStepLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-08-14 16:08:07
 * @version v1.0
*/

public interface BatchTimingStepLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTimingStepLogHistory 
	 */
	int insertOne(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTimingStepLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTimingStepLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1(String historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1R(String historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1L(String historyStepExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyStepExecutionId 任务执行ID
	 */
	int deleteOne1(String historyStepExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	int deleteOne1L(String historyStepExecutionId);
	/**
	 * 单笔更新
	 * @param batchTimingStepLogHistory 
	 */
	int updateOne1(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingStepLogHistory 
	 */
	int updateOne1R(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingStepLogHistory 
	 */
	int updateOne1L(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTimingStepLogHistory> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTimingStepLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTimingStepLogHistory> list);
}