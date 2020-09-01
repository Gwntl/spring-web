package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTimingJobLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingJobLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTimingJobLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
@Repository
public class BatchTimingJobLogHistoryDaoImpl extends BaseDaoSupport implements BatchTimingJobLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int insertOne(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().insert("BatchTimingJobLogHistory.insertOne", batchTimingJobLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTimingJobLogHistory> list){
		batchExcutor("BatchTimingJobLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingJobLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingJobLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne1(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne1", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne1R(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne1R", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne1L(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne1L", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔删除
	 * @param executionId 任务执行ID
	 */
	@Override
	public int deleteOne1(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne1(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne1", batchTimingJobLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne1R(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne1R", batchTimingJobLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne1L(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne1L", batchTimingJobLogHistory);
	}

	/**
	 * 单笔查询
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne2(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne2", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne2R(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne2R", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogHistory selectOne2L(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogHistory batchTimingJobLogHistory = new BatchTimingJobLogHistory();
		batchTimingJobLogHistory = (BatchTimingJobLogHistory)getSqlSessionTemplate().selectOne("BatchTimingJobLogHistory.selectOne2L", map);
		if (batchTimingJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_loghistory", CommonUtils.toString(map));
		return batchTimingJobLogHistory;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public int deleteOne2(String executionInstance, String stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogHistory.deleteOne2", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public int deleteOne2L(String executionInstance, String stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogHistory.deleteOne2L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne2(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne2", batchTimingJobLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne2R(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne2R", batchTimingJobLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogHistory 
	 */
	@Override
	public int updateOne2L(BatchTimingJobLogHistory batchTimingJobLogHistory){
		return getSqlSessionTemplate().update("BatchTimingJobLogHistory.updateOne2L", batchTimingJobLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTimingJobLogHistory> list){
		batchExcutor("BatchTimingJobLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate2(List<BatchTimingJobLogHistory> list){
		batchExcutor("BatchTimingJobLogHistory.batchUpdate2", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTimingJobLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTimingJobLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTimingJobLogHistory> list){
		batchExcutor("BatchTimingJobLogHistory.deleteOne1", list, "delete", 20);
	}

}