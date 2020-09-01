package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskLogHistory;

/**
 * 
 * @filename BatchTaskLogHistoryDao.java
 * @author wzaUsers
 * @date 2020-08-20 19:08:09
 * @version v1.0
*/

public interface BatchTaskLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTaskLogHistory 
	 */
	int insertOne(BatchTaskLogHistory batchTaskLogHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTaskLogHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTaskLogHistory> list);
	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	BatchTaskLogHistory selectOne1(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	BatchTaskLogHistory selectOne1R(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	BatchTaskLogHistory selectOne1L(String executionInstance, boolean nullException);
	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	int deleteOne1(String executionInstance);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	int deleteOne1L(String executionInstance);
	/**
	 * 单笔更新
	 * @param batchTaskLogHistory 
	 */
	int updateOne1(BatchTaskLogHistory batchTaskLogHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskLogHistory 
	 */
	int updateOne1R(BatchTaskLogHistory batchTaskLogHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskLogHistory 
	 */
	int updateOne1L(BatchTaskLogHistory batchTaskLogHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTaskLogHistory> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTaskLogHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTaskLogHistory> list);
}