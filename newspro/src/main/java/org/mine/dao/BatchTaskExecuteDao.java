package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskExecute;

/**
 * 
 * @filename BatchTaskExecuteDao.java
 * @author wzaUsers
 * @date 2020-08-26 10:08:58
 * @version v1.0
*/

public interface BatchTaskExecuteDao {
	/**
	 * 单笔插入
	 * @param batchTaskExecute 
	 */
	int insertOne(BatchTaskExecute batchTaskExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchTaskExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchTaskExecute> list);
	/**
	 * 单笔查询
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	BatchTaskExecute selectOne1(String executeTaskId, String executeJobId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	BatchTaskExecute selectOne1R(String executeTaskId, String executeJobId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	BatchTaskExecute selectOne1L(String executeTaskId, String executeJobId, boolean nullException);
	/**
	 * 单笔删除
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	int deleteOne1(String executeTaskId, String executeJobId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	int deleteOne1L(String executeTaskId, String executeJobId);
	/**
	 * 单笔更新
	 * @param batchTaskExecute 
	 */
	int updateOne1(BatchTaskExecute batchTaskExecute);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskExecute 
	 */
	int updateOne1R(BatchTaskExecute batchTaskExecute);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskExecute 
	 */
	int updateOne1L(BatchTaskExecute batchTaskExecute);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchTaskExecute> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchTaskExecute> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchTaskExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1(String executeTaskId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1R(String executeTaskId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1L(String executeTaskId);
}