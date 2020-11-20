package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchStepLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchStepLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchStepLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-24 14:09:30
 * @version v1.0
*/
@Repository
public class BatchStepLogHistoryDaoImpl extends BaseDaoSupport implements BatchStepLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchStepLogHistory 
	 */
	@Override
	public int insertOne(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().insert("BatchStepLogHistory.insertOne", batchStepLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchStepLogHistory> list){
		batchExcutor("BatchStepLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchStepLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchStepLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLogHistory selectOne1(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne1", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLogHistory selectOne1R(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne1R", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public BatchStepLogHistory selectOne1L(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne1L", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
	}

	/**
	 * 单笔删除
	 * @param executionId 步骤执行实例
	 */
	@Override
	public int deleteOne1(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchStepLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 步骤执行实例
	 */
	@Override
	public int deleteOne1L(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchStepLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne1(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne1", batchStepLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne1R(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne1R", batchStepLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne1L(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne1L", batchStepLogHistory);
	}

	/**
	 * 单笔查询
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLogHistory selectOne2(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne2", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLogHistory selectOne2R(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne2R", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance JOB执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchStepLogHistory selectOne2L(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchStepLogHistory batchStepLogHistory = new BatchStepLogHistory();
		batchStepLogHistory = (BatchStepLogHistory)getSqlSessionTemplate().selectOne("BatchStepLogHistory.selectOne2L", map);
		if (batchStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pl_oghistory", CommonUtils.toString(map));
		return batchStepLogHistory;
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
		return getSqlSessionTemplate().delete("BatchStepLogHistory.deleteOne2", map);
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
		return getSqlSessionTemplate().delete("BatchStepLogHistory.deleteOne2L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne2(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne2", batchStepLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne2R(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne2R", batchStepLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepLogHistory 
	 */
	@Override
	public int updateOne2L(BatchStepLogHistory batchStepLogHistory){
		return getSqlSessionTemplate().update("BatchStepLogHistory.updateOne2L", batchStepLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchStepLogHistory> list){
		batchExcutor("BatchStepLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate2(List<BatchStepLogHistory> list){
		batchExcutor("BatchStepLogHistory.batchUpdate2", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchStepLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchStepLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchStepLogHistory> list){
		batchExcutor("BatchStepLogHistory.deleteOne1", list, "delete", 20);
	}

}