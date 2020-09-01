package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskLog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskLogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskLogDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 19:08:09
 * @version v1.0
*/
@Repository
public class BatchTaskLogDaoImpl extends BaseDaoSupport implements BatchTaskLogDao {
	/**
	 * 单笔插入
	 * @param batchTaskLog 
	 */
	@Override
	public int insertOne(BatchTaskLog batchTaskLog){
		return getSqlSessionTemplate().insert("BatchTaskLog.insertOne", batchTaskLog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTaskLog> list){
		batchExcutor("BatchTaskLog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskLog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLog selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLog batchTaskLog = new BatchTaskLog();
		batchTaskLog = (BatchTaskLog)getSqlSessionTemplate().selectOne("BatchTaskLog.selectOne1", map);
		if (batchTaskLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_klog", CommonUtils.toString(map));
		return batchTaskLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLog selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLog batchTaskLog = new BatchTaskLog();
		batchTaskLog = (BatchTaskLog)getSqlSessionTemplate().selectOne("BatchTaskLog.selectOne1R", map);
		if (batchTaskLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_klog", CommonUtils.toString(map));
		return batchTaskLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLog selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLog batchTaskLog = new BatchTaskLog();
		batchTaskLog = (BatchTaskLog)getSqlSessionTemplate().selectOne("BatchTaskLog.selectOne1L", map);
		if (batchTaskLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_klog", CommonUtils.toString(map));
		return batchTaskLog;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchTaskLog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchTaskLog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTaskLog 
	 */
	@Override
	public int updateOne1(BatchTaskLog batchTaskLog){
		return getSqlSessionTemplate().update("BatchTaskLog.updateOne1", batchTaskLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskLog 
	 */
	@Override
	public int updateOne1R(BatchTaskLog batchTaskLog){
		return getSqlSessionTemplate().update("BatchTaskLog.updateOne1R", batchTaskLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskLog 
	 */
	@Override
	public int updateOne1L(BatchTaskLog batchTaskLog){
		return getSqlSessionTemplate().update("BatchTaskLog.updateOne1L", batchTaskLog);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTaskLog> list){
		batchExcutor("BatchTaskLog.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTaskLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskLog.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTaskLog> list){
		batchExcutor("BatchTaskLog.deleteOne1", list, "delete", 20);
	}

}