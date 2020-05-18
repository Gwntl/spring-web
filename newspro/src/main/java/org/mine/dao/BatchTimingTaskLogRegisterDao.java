package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTimingTaskLogRegister;

/**
 * 
 * @filename BatchTimingTaskLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/

public interface BatchTimingTaskLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTimingTaskLogRegister 
	 */
	int insertOne(BatchTimingTaskLogRegister batchTimingTaskLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	void batchInsert(List<BatchTimingTaskLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogRegister 
	 */
	void batchInsertXML(List<BatchTimingTaskLogRegister> list);
	/**
	 * 单笔查询
	 * @param timingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogRegister selectOne1(Long timingExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param timingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogRegister selectOne1R(Long timingExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param timingExecutionId 定时任务执行ID
	 */
	BatchTimingTaskLogRegister selectOne1L(Long timingExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param timingExecutionId 定时任务执行ID
	 */
	int deleteOne1(Long timingExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param timingExecutionId 定时任务执行ID
	 */
	int deleteOne1L(Long timingExecutionId);
	/**
	 * 单笔更新
	 * @param BatchTimingTaskLogRegister 
	 */
	int updateOne1(BatchTimingTaskLogRegister batchTimingTaskLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingTaskLogRegister 
	 */
	int updateOne1R(BatchTimingTaskLogRegister batchTimingTaskLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingTaskLogRegister 
	 */
	int updateOne1L(BatchTimingTaskLogRegister batchTimingTaskLogRegister);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogRegister 
	 */
	void batchUpdateXML1(List<BatchTimingTaskLogRegister> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	void batchUpdate(List<BatchTimingTaskLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	void batchDelete(List<BatchTimingTaskLogRegister> list);
}