package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTriggerConf;

/**
 * 
 * @filename BatchTriggerConfDao.java
 * @author wzaUsers
 * @date 2019-11-14 14:11:06
 * @version v1.0
*/

public interface BatchTriggerConfDao {
	/**
	 * 单笔插入
	 * @param BatchTriggerConf 
	 */
	int insertOne(BatchTriggerConf batchTriggerConf);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTriggerConf 
	 */
	void batchInsert(List<BatchTriggerConf> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTriggerConf 
	 */
	void batchInsertXML(List<BatchTriggerConf> list);
	/**
	 * 单笔查询
	 * @param triggerId 触发器ID
	 */
	BatchTriggerConf selectOne1(Long triggerId);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerConf selectOne1R(Long triggerId);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	BatchTriggerConf selectOne1L(Long triggerId);
	/**
	 * 单笔删除
	 * @param triggerId 触发器ID
	 */
	int deleteOne1(Long triggerId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	int deleteOne1L(Long triggerId);
	/**
	 * 单笔更新
	 * @param BatchTriggerConf 
	 */
	int updateOne1(BatchTriggerConf batchTriggerConf);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTriggerConf 
	 */
	int updateOne1R(BatchTriggerConf batchTriggerConf);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTriggerConf 
	 */
	int updateOne1L(BatchTriggerConf batchTriggerConf);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTriggerConf 
	 */
	void batchUpdateXML1(List<BatchTriggerConf> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTriggerConf 
	 */
	void batchUpdate(List<BatchTriggerConf> list);
	/**
	 * 查询多笔数据
	 * @param triggerJobGroupId JOB作业组ID
	 */
	List<BatchTriggerConf> selectAll1(Long triggerJobGroupId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param triggerJobGroupId JOB作业组ID
	 */
	List<BatchTriggerConf> selectAll1R(Long triggerJobGroupId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerJobGroupId JOB作业组ID
	 */
	List<BatchTriggerConf> selectAll1L(Long triggerJobGroupId);
}