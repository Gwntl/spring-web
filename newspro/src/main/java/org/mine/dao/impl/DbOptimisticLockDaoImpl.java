package org.mine.dao.impl;

import java.util.List;
import org.mine.model.DbOptimisticLock;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.DbOptimisticLockDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename DbOptimisticLockDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-18 16:09:00
 * @version v1.0
*/
@Repository
public class DbOptimisticLockDaoImpl extends BaseDaoSupport implements DbOptimisticLockDao {
	/**
	 * 单笔插入
	 * @param dbOptimisticLock 
	 */
	@Override
	public int insertOne(DbOptimisticLock dbOptimisticLock){
		return getSqlSessionTemplate().insert("DbOptimisticLock.insertOne", dbOptimisticLock);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<DbOptimisticLock> list){
		batchExcutor("DbOptimisticLock.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<DbOptimisticLock> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("DbOptimisticLock.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	@Override
	public DbOptimisticLock selectOne1(String lockName, String lockType, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lockName", lockName);
		map.put("lockType", lockType);
		DbOptimisticLock dbOptimisticLock = new DbOptimisticLock();
		dbOptimisticLock = (DbOptimisticLock)getSqlSessionTemplate().selectOne("DbOptimisticLock.selectOne1", map);
		if (dbOptimisticLock == null && nullException)throw GitWebException.GIT_NOTFOUNT("db_optimisti_clock", CommonUtils.toString(map));
		return dbOptimisticLock;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	@Override
	public DbOptimisticLock selectOne1R(String lockName, String lockType, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lockName", lockName);
		map.put("lockType", lockType);
		DbOptimisticLock dbOptimisticLock = new DbOptimisticLock();
		dbOptimisticLock = (DbOptimisticLock)getSqlSessionTemplate().selectOne("DbOptimisticLock.selectOne1R", map);
		if (dbOptimisticLock == null && nullException)throw GitWebException.GIT_NOTFOUNT("db_optimisti_clock", CommonUtils.toString(map));
		return dbOptimisticLock;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	@Override
	public DbOptimisticLock selectOne1L(String lockName, String lockType, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lockName", lockName);
		map.put("lockType", lockType);
		DbOptimisticLock dbOptimisticLock = new DbOptimisticLock();
		dbOptimisticLock = (DbOptimisticLock)getSqlSessionTemplate().selectOne("DbOptimisticLock.selectOne1L", map);
		if (dbOptimisticLock == null && nullException)throw GitWebException.GIT_NOTFOUNT("db_optimisti_clock", CommonUtils.toString(map));
		return dbOptimisticLock;
	}

	/**
	 * 单笔删除
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	@Override
	public int deleteOne1(String lockName, String lockType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lockName", lockName);
		map.put("lockType", lockType);
		return getSqlSessionTemplate().delete("DbOptimisticLock.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param lockName 名字
	 * @param lockType 类别
	 */
	@Override
	public int deleteOne1L(String lockName, String lockType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lockName", lockName);
		map.put("lockType", lockType);
		return getSqlSessionTemplate().delete("DbOptimisticLock.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param dbOptimisticLock 
	 */
	@Override
	public int updateOne1(DbOptimisticLock dbOptimisticLock){
		return getSqlSessionTemplate().update("DbOptimisticLock.updateOne1", dbOptimisticLock);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param dbOptimisticLock 
	 */
	@Override
	public int updateOne1R(DbOptimisticLock dbOptimisticLock){
		return getSqlSessionTemplate().update("DbOptimisticLock.updateOne1R", dbOptimisticLock);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param dbOptimisticLock 
	 */
	@Override
	public int updateOne1L(DbOptimisticLock dbOptimisticLock){
		return getSqlSessionTemplate().update("DbOptimisticLock.updateOne1L", dbOptimisticLock);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<DbOptimisticLock> list){
		batchExcutor("DbOptimisticLock.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<DbOptimisticLock> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("DbOptimisticLock.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<DbOptimisticLock> list){
		batchExcutor("DbOptimisticLock.deleteOne1", list, "delete", 20);
	}

}