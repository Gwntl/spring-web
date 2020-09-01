package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskLog;

/**
 * 
 * @filename BatchTaskLogDao.java
 * @author wzaUsers
 * @date 2020-08-20 19:08:09
 * @version v1.0
*/

public interface BatchTaskLogDao {
	/**
	 * 单笔插入
	 * @param batchTaskLog 
	 */
	int insertOne(BatchTaskLog batchTaskLog);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTaskLog> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTaskLog> list);
	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	BatchTaskLog selectOne1(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	BatchTaskLog selectOne1R(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	BatchTaskLog selectOne1L(String executionInstance, boolean nullException);
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
	 * @param batchTaskLog 
	 */
	int updateOne1(BatchTaskLog batchTaskLog);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskLog 
	 */
	int updateOne1R(BatchTaskLog batchTaskLog);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskLog 
	 */
	int updateOne1L(BatchTaskLog batchTaskLog);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTaskLog> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTaskLog> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTaskLog> list);
}