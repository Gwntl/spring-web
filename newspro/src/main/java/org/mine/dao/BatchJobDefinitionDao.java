package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobDefinition;

/**
 * 
 * @filename BatchJobDefinitionDao.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/

public interface BatchJobDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchJobDefinition 
	 */
	int insertOne(BatchJobDefinition batchJobDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<BatchJobDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<BatchJobDefinition> list);
	/**
	 * 单笔查询
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1(String jobId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1R(String jobId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1L(String jobId, boolean nullException);
	/**
	 * 单笔删除
	 * @param jobId 作业ID
	 */
	int deleteOne1(String jobId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	int deleteOne1L(String jobId);
	/**
	 * 单笔更新
	 * @param batchJobDefinition 
	 */
	int updateOne1(BatchJobDefinition batchJobDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobDefinition 
	 */
	int updateOne1R(BatchJobDefinition batchJobDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobDefinition 
	 */
	int updateOne1L(BatchJobDefinition batchJobDefinition);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<BatchJobDefinition> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<BatchJobDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<BatchJobDefinition> list);
	/**
	 * 查询多笔数据
	 * @param jobAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchJobDefinition> selectAll1(Integer jobAutoFlag);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param jobAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchJobDefinition> selectAll1R(Integer jobAutoFlag);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobAutoFlag 是否自动执行. 0-是, 1-否
	 */
	List<BatchJobDefinition> selectAll1L(Integer jobAutoFlag);
}