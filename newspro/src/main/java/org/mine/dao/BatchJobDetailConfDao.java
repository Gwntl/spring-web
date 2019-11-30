package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobDetailConf;

/**
 * 
 * @filename BatchJobDetailConfDao.java
 * @author wzaUsers
 * @date 2019-11-29 16:11:16
 * @version v1.0
*/

public interface BatchJobDetailConfDao {
	/**
	 * 单笔插入
	 * @param BatchJobDetailConf 
	 */
	int insertOne(BatchJobDetailConf batchJobDetailConf);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	void batchInsert(List<BatchJobDetailConf> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobDetailConf 
	 */
	void batchInsertXML(List<BatchJobDetailConf> list);
	/**
	 * 单笔查询
	 * @param jobdetailId JOB任务作业ID
	 */
	BatchJobDetailConf selectOne1(Long jobdetailId);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobdetailId JOB任务作业ID
	 */
	BatchJobDetailConf selectOne1R(Long jobdetailId);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailId JOB任务作业ID
	 */
	BatchJobDetailConf selectOne1L(Long jobdetailId);
	/**
	 * 单笔删除
	 * @param jobdetailId JOB任务作业ID
	 */
	int deleteOne1(Long jobdetailId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailId JOB任务作业ID
	 */
	int deleteOne1L(Long jobdetailId);
	/**
	 * 单笔更新
	 * @param BatchJobDetailConf 
	 */
	int updateOne1(BatchJobDetailConf batchJobDetailConf);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobDetailConf 
	 */
	int updateOne1R(BatchJobDetailConf batchJobDetailConf);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobDetailConf 
	 */
	int updateOne1L(BatchJobDetailConf batchJobDetailConf);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobDetailConf 
	 */
	void batchUpdateXML1(List<BatchJobDetailConf> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	void batchUpdate(List<BatchJobDetailConf> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	void batchDelete(List<BatchJobDetailConf> list);
	/**
	 * 查询多笔数据
	 * @param jobdetailGroupId JOB作业组ID
	 */
	List<BatchJobDetailConf> selectAll1(Long jobdetailGroupId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param jobdetailGroupId JOB作业组ID
	 */
	List<BatchJobDetailConf> selectAll1R(Long jobdetailGroupId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailGroupId JOB作业组ID
	 */
	List<BatchJobDetailConf> selectAll1L(Long jobdetailGroupId);
}