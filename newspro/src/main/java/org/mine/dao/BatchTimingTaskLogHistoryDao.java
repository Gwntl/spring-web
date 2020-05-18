package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingTaskLogHistory;

/**
 * 
 * @filename BatchTimingTaskLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/

public interface BatchTimingTaskLogHistoryDao {
	/**
	 * 单笔插入
	 * @param BatchTimingTaskLogHistory 
	 */
	int insertOne(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogHistory 
	 */
	void batchInsert(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogHistory 
	 */
	void batchInsertXML(List<BatchTimingTaskLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1(Long historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1R(Long historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogHistory selectOne1L(Long historyTimingExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	int deleteOne1(Long historyTimingExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	int deleteOne1L(Long historyTimingExecutionId);
	/**
	 * 单笔更新
	 * @param BatchTimingTaskLogHistory 
	 */
	int updateOne1(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingTaskLogHistory 
	 */
	int updateOne1R(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingTaskLogHistory 
	 */
	int updateOne1L(BatchTimingTaskLogHistory batchTimingTaskLogHistory);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogHistory 
	 */
	void batchUpdateXML1(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogHistory 
	 */
	void batchUpdate(List<BatchTimingTaskLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogHistory 
	 */
	void batchDelete(List<BatchTimingTaskLogHistory> list);
}