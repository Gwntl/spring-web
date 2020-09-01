package org.mine.dao;

import java.util.List;
import org.mine.model.BatchExecutorSeqLog;

/**
 * 
 * @filename BatchExecutorSeqLogDao.java
 * @author wzaUsers
 * @date 2020-08-18 16:08:15
 * @version v1.0
*/

public interface BatchExecutorSeqLogDao {
	/**
	 * 单笔插入
	 * @param batchExecutorSeqLog 
	 */
	int insertOne(BatchExecutorSeqLog batchExecutorSeqLog);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchExecutorSeqLog> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchExecutorSeqLog> list);
	/**
	 * 单笔查询
	 * @param executionInstance 作业执行实例
	 */
	BatchExecutorSeqLog selectOne1(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 作业执行实例
	 */
	BatchExecutorSeqLog selectOne1R(String executionInstance, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 作业执行实例
	 */
	BatchExecutorSeqLog selectOne1L(String executionInstance, boolean nullException);
	/**
	 * 单笔删除
	 * @param executionInstance 作业执行实例
	 */
	int deleteOne1(String executionInstance);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 作业执行实例
	 */
	int deleteOne1L(String executionInstance);
	/**
	 * 单笔更新
	 * @param batchExecutorSeqLog 
	 */
	int updateOne1(BatchExecutorSeqLog batchExecutorSeqLog);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchExecutorSeqLog 
	 */
	int updateOne1R(BatchExecutorSeqLog batchExecutorSeqLog);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchExecutorSeqLog 
	 */
	int updateOne1L(BatchExecutorSeqLog batchExecutorSeqLog);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchExecutorSeqLog> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchExecutorSeqLog> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchExecutorSeqLog> list);
}