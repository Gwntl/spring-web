package org.mine.dao;

import java.util.List;
import org.mine.model.BatchExecutorSeqlog;

/**
 * 
 * @filename BatchExecutorSeqlogDao.java
 * @author wzaUsers
 * @date 2020-06-24 09:06:45
 * @version v1.0
*/

public interface BatchExecutorSeqlogDao {
	/**
	 * 单笔插入
	 * @param BatchExecutorSeqlog 
	 */
	int insertOne(BatchExecutorSeqlog batchExecutorSeqlog);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	void batchInsert(List<BatchExecutorSeqlog> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchExecutorSeqlog 
	 */
	void batchInsertXML(List<BatchExecutorSeqlog> list);
	/**
	 * 单笔查询
	 * @param seqId 序号ID
	 */
	BatchExecutorSeqlog selectOne1(Long seqId, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param seqId 序号ID
	 */
	BatchExecutorSeqlog selectOne1R(Long seqId, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param seqId 序号ID
	 */
	BatchExecutorSeqlog selectOne1L(Long seqId, boolean nullException);
	/**
	 * 单笔删除
	 * @param seqId 序号ID
	 */
	int deleteOne1(Long seqId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param seqId 序号ID
	 */
	int deleteOne1L(Long seqId);
	/**
	 * 单笔更新
	 * @param BatchExecutorSeqlog 
	 */
	int updateOne1(BatchExecutorSeqlog batchExecutorSeqlog);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchExecutorSeqlog 
	 */
	int updateOne1R(BatchExecutorSeqlog batchExecutorSeqlog);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchExecutorSeqlog 
	 */
	int updateOne1L(BatchExecutorSeqlog batchExecutorSeqlog);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchExecutorSeqlog 
	 */
	void batchUpdateXML1(List<BatchExecutorSeqlog> list);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	void batchUpdate(List<BatchExecutorSeqlog> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	void batchDelete(List<BatchExecutorSeqlog> list);
}