package org.mine.dao;

import org.mine.model.DbOptimisticLock;

import java.util.List;

/**
 * 
 * @filename DbOptimisticLockDao.java
 * @author wzaUsers
 * @date 2020-09-18 16:09:00
 * @version v1.0
*/

public interface DbOptimisticLockDao {
	/**
	 * 单笔插入
	 * @param dbOptimisticLock 
	 */
	int insertOne(DbOptimisticLock dbOptimisticLock);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchInsert(List<DbOptimisticLock> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchInsertXML(List<DbOptimisticLock> list);
	/**
	 * 单笔查询
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	DbOptimisticLock selectOne1(String lockName, String lockType, boolean nullException);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	DbOptimisticLock selectOne1R(String lockName, String lockType, boolean nullException);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	DbOptimisticLock selectOne1L(String lockName, String lockType, boolean nullException);
	/**
	 * 单笔删除
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	int deleteOne1(String lockName, String lockType);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	int deleteOne1L(String lockName, String lockType);
	/**
	 * 单笔更新
	 * @param dbOptimisticLock 
	 */
	int updateOne1(DbOptimisticLock dbOptimisticLock);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param dbOptimisticLock 
	 */
	int updateOne1R(DbOptimisticLock dbOptimisticLock);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param dbOptimisticLock 
	 */
	int updateOne1L(DbOptimisticLock dbOptimisticLock);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchUpdate1(List<DbOptimisticLock> list);
	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	void batchUpdateXML(List<DbOptimisticLock> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	void batchDelete(List<DbOptimisticLock> list);
}