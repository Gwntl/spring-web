package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobDefinition;

/**
 * 
 * @filename BatchJobDefinitionDao.java
 * @author wzaUsers
 * @date 2020-06-23 16:06:55
 * @version v1.0
*/

public interface BatchJobDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchJobDefinition 
	 */
	int insertOne(BatchJobDefinition batchJobDefinition);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	void batchInsert(List<BatchJobDefinition> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobDefinition 
	 */
	void batchInsertXML(List<BatchJobDefinition> list);
	/**
	 * 单笔查询
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1(Long jobId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1R(Long jobId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	BatchJobDefinition selectOne1L(Long jobId, boolean nullException);
	/**
	 * 单笔删除
	 * @param jobId 作业ID
	 */
	int deleteOne1(Long jobId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	int deleteOne1L(Long jobId);
	/**
	 * 单笔更新
	 * @param BatchJobDefinition 
	 */
	int updateOne1(BatchJobDefinition batchJobDefinition);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobDefinition 
	 */
	int updateOne1R(BatchJobDefinition batchJobDefinition);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobDefinition 
	 */
	int updateOne1L(BatchJobDefinition batchJobDefinition);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobDefinition 
	 */
	void batchUpdateXML1(List<BatchJobDefinition> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	void batchUpdate(List<BatchJobDefinition> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	void batchDelete(List<BatchJobDefinition> list);
}