package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskExecutionLogHistory;

/**
 * 
 * @filename BatchTaskExecutionLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchTaskExecutionLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTaskExecutionLogHistory 
	 */
	int insertOne(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1(String historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1R(String historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1L(String historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyTaskExecutionId 任务执行ID
	 */
	int deleteOne1(String historyTaskExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	int deleteOne1L(String historyTaskExecutionId);
	/**
	 * 单笔更新
	 * @param batchTaskExecutionLogHistory 
	 */
	int updateOne1(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskExecutionLogHistory 
	 */
	int updateOne1R(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskExecutionLogHistory 
	 */
	int updateOne1L(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTaskExecutionLogHistory> list);
}