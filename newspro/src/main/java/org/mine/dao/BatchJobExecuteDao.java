package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobExecute;

/**
 * 
 * @filename BatchJobExecuteDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:19
 * @version v1.0
*/

public interface BatchJobExecuteDao {
	/**
	 * 单笔插入
	 * @param batchJobExecute 
	 */
	int insertOne(BatchJobExecute batchJobExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchJobExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchJobExecute> list);
	/**
	 * 单笔查询
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	BatchJobExecute selectOne1(String executeJobId, String executeStepId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	BatchJobExecute selectOne1R(String executeJobId, String executeStepId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	BatchJobExecute selectOne1L(String executeJobId, String executeStepId, boolean nullException);
	/**
	 * 单笔删除
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	int deleteOne1(String executeJobId, String executeStepId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	int deleteOne1L(String executeJobId, String executeStepId);
	/**
	 * 单笔更新
	 * @param batchJobExecute 
	 */
	int updateOne1(BatchJobExecute batchJobExecute);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobExecute 
	 */
	int updateOne1R(BatchJobExecute batchJobExecute);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobExecute 
	 */
	int updateOne1L(BatchJobExecute batchJobExecute);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchJobExecute> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchJobExecute> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchJobExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1(String executeJobId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1R(String executeJobId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1L(String executeJobId);
}