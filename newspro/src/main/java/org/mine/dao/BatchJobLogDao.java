package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobLog;

/**
 * 
 * @filename BatchJobLogDao.java
 * @author wzaUsers
 * @date 2020-08-25 15:08:12
 * @version v1.0
*/

public interface BatchJobLogDao {
	/**
	 * 单笔插入
	 * @param batchJobLog 
	 */
	int insertOne(BatchJobLog batchJobLog);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchJobLog> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchJobLog> list);
	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	BatchJobLog selectOne1(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	BatchJobLog selectOne1R(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	BatchJobLog selectOne1L(String executionInstance, boolean nullException);
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
	 * @param batchJobLog 
	 */
	int updateOne1(BatchJobLog batchJobLog);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobLog 
	 */
	int updateOne1R(BatchJobLog batchJobLog);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobLog 
	 */
	int updateOne1L(BatchJobLog batchJobLog);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchJobLog> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchJobLog> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchJobLog> list);
}