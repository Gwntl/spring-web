package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchStepLog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchStepLogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchStepLogDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-24 14:09:29
 * @version v1.0
*/
@Repository
public class BatchStepLogDaoImpl extends BaseDaoSupport implements BatchStepLogDao {
	/**
	 * 单笔插入
	 * @param batchStepLog 
	 */
	@Override
	public int insertOne(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().insert("BatchStepLog.insertOne", batchStepLog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchStepLog> list){
		batchExcutor("BatchStepLog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchStepLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchStepLog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLog selectOne1(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne1", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLog selectOne1R(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne1R", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLog selectOne1L(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne1L", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔删除
	 * @param executionId 步骤执行实例
	 */
	@Override
	public int deleteOne1(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchStepLog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public int deleteOne1L(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchStepLog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne1(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne1", batchStepLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne1R(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne1R", batchStepLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne1L(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne1L", batchStepLog);
	}

	/**
	 * 单笔查询
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLog selectOne2(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne2", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLog selectOne2R(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne2R", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLog selectOne2L(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLog batchStepLog = new BatchStepLog();
		batchStepLog = (BatchStepLog)getSqlSessionTemplate().selectOne("BatchStepLog.selectOne2L", map);
		if (batchStepLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_plog", CommonUtils.toString(map));
		return batchStepLog;
	}

	/**
	 * 单笔删除
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public int deleteOne2(String executionInstance, String stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchStepLog.deleteOne2", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public int deleteOne2L(String executionInstance, String stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchStepLog.deleteOne2L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne2(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne2", batchStepLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne2R(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne2R", batchStepLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLog 
	 */
	@Override
	public int updateOne2L(BatchStepLog batchStepLog){
		return getSqlSessionTemplate().update("BatchStepLog.updateOne2L", batchStepLog);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchStepLog> list){
		batchExcutor("BatchStepLog.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate2(List<BatchStepLog> list){
		batchExcutor("BatchStepLog.batchUpdate2", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchStepLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchStepLog.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchStepLog> list){
		batchExcutor("BatchStepLog.deleteOne1", list, "delete", 20);
	}

}