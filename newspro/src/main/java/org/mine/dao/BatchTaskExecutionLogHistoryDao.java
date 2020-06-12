package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskExecutionLogHistory;

/**
 * 
 * @filename BatchTaskExecutionLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:39
 * @version v1.0
*/

public interface BatchTaskExecutionLogHistoryDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecutionLogHistory 
	 */
	int insertOne(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	void batchInsert(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogHistory 
	 */
	void batchInsertXML(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 单笔查询
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1(Long historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1R(Long historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogHistory selectOne1L(Long historyTaskExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param historyTaskExecutionId 任务执行ID
	 */
	int deleteOne1(Long historyTaskExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	int deleteOne1L(Long historyTaskExecutionId);
	/**
	 * 单笔更新
	 * @param BatchTaskExecutionLogHistory 
	 */
	int updateOne1(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskExecutionLogHistory 
	 */
	int updateOne1R(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskExecutionLogHistory 
	 */
	int updateOne1L(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogHistory 
	 */
	void batchUpdateXML1(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	void batchUpdate(List<BatchTaskExecutionLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	void batchDelete(List<BatchTaskExecutionLogHistory> list);
}