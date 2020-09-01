package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingTaskLogHistory;

/**
 * 
 * @filename BatchTimingTaskLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchTimingTaskLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTimingTaskLogHistory 
	 */
	int insertOne(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTimingTaskLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1(String historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1R(String historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1L(String historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	int deleteOne1(String historyTimingExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	int deleteOne1L(String historyTimingExecutionId);
	/**
	 * 单笔更新
	 * @param batchTimingTaskLogHistory 
	 */
	int updateOne1(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingTaskLogHistory 
	 */
	int updateOne1R(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingTaskLogHistory 
	 */
	int updateOne1L(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTimingTaskLogHistory> list);
}