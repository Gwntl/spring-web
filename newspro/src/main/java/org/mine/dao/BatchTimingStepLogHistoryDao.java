package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingStepLogHistory;

/**
 * 
 * @filename BatchTimingStepLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:39
 * @version v1.0
*/

public interface BatchTimingStepLogHistoryDao {
	/**
	 * 单笔插入
	 * @param BatchTimingStepLogHistory 
	 */
	int insertOne(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingStepLogHistory 
	 */
	void batchInsert(List<BatchTimingStepLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingStepLogHistory 
	 */
	void batchInsertXML(List<BatchTimingStepLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1(Long historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1R(Long historyStepExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	BatchTimingStepLogHistory selectOne1L(Long historyStepExecutionId, boolean nullException);
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
	 * @param BatchTimingStepLogHistory 
	 */
	int updateOne1(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingStepLogHistory 
	 */
	int updateOne1R(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingStepLogHistory 
	 */
	int updateOne1L(BatchTimingStepLogHistory batchTimingStepLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingStepLogHistory 
	 */
	void batchUpdate(List<BatchTimingStepLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingStepLogHistory 
	 */
	void batchDelete(List<BatchTimingStepLogHistory> list);
}