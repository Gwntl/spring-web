package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskExecutionLogRegister;

/**
 * 
 * @filename BatchTaskExecutionLogRegisterDao.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/

public interface BatchTaskExecutionLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecutionLogRegister 
	 */
	int insertOne(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	void batchInsert(List<BatchTaskExecutionLogRegister> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogRegister 
	 */
	void batchInsertXML(List<BatchTaskExecutionLogRegister> list);
	/**
	 * 单笔查询
	 * @param taskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogRegister selectOne1(Long taskExecutionId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param taskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogRegister selectOne1R(Long taskExecutionId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskExecutionId 任务执行ID
	 */
	BatchTaskExecutionLogRegister selectOne1L(Long taskExecutionId, boolean nullException);
	/**
	 * 单笔删除
	 * @param taskExecutionId 任务执行ID
	 */
	int deleteOne1(Long taskExecutionId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskExecutionId 任务执行ID
	 */
	int deleteOne1L(Long taskExecutionId);
	/**
	 * 单笔更新
	 * @param BatchTaskExecutionLogRegister 
	 */
	int updateOne1(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskExecutionLogRegister 
	 */
	int updateOne1R(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskExecutionLogRegister 
	 */
	int updateOne1L(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogRegister 
	 */
	void batchUpdateXML1(List<BatchTaskExecutionLogRegister> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	void batchUpdate(List<BatchTaskExecutionLogRegister> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	void batchDelete(List<BatchTaskExecutionLogRegister> list);
}