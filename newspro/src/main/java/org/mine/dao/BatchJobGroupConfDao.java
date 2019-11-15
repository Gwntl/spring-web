package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobGroupConf;

/**
 * 
 * @filename BatchJobGroupConfDao.java
 * @author wzaUsers
 * @date 2019-11-14 20:11:04
 * @version v1.0
*/

public interface BatchJobGroupConfDao {
	/**
	 * 单笔插入
	 * @param BatchJobGroupConf 
	 */
	int insertOne(BatchJobGroupConf batchJobGroupConf);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobGroupConf 
	 */
	void batchInsert(List<BatchJobGroupConf> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobGroupConf 
	 */
	void batchInsertXML(List<BatchJobGroupConf> list);
	/**
	 * 单笔查询
	 * @param jobGroupId JOB作业组ID
	 */
	BatchJobGroupConf selectOne1(Long jobGroupId);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobGroupId JOB作业组ID
	 */
	BatchJobGroupConf selectOne1R(Long jobGroupId);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobGroupId JOB作业组ID
	 */
	BatchJobGroupConf selectOne1L(Long jobGroupId);
	/**
	 * 单笔删除
	 * @param jobGroupId JOB作业组ID
	 */
	int deleteOne1(Long jobGroupId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobGroupId JOB作业组ID
	 */
	int deleteOne1L(Long jobGroupId);
	/**
	 * 单笔更新
	 * @param BatchJobGroupConf 
	 */
	int updateOne1(BatchJobGroupConf batchJobGroupConf);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobGroupConf 
	 */
	int updateOne1R(BatchJobGroupConf batchJobGroupConf);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobGroupConf 
	 */
	int updateOne1L(BatchJobGroupConf batchJobGroupConf);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobGroupConf 
	 */
	void batchUpdateXML1(List<BatchJobGroupConf> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobGroupConf 
	 */
	void batchUpdate(List<BatchJobGroupConf> list);
}