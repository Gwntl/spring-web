package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTimingJobLogRegister;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingJobLogRegisterDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTimingJobLogRegisterDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
@Repository
public class BatchTimingJobLogRegisterDaoImpl extends BaseDaoSupport implements BatchTimingJobLogRegisterDao {
	/**
	 * 单笔插入
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int insertOne(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().insert("BatchTimingJobLogRegister.insertOne", batchTimingJobLogRegister);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTimingJobLogRegister> list){
		batchExcutor("BatchTimingJobLogRegister.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingJobLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingJobLogRegister.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne1(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne1", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne1R(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne1R", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne1L(String executionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne1L", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
	}

	/**
	 * 单笔删除
	 * @param executionId 任务执行ID
	 */
	@Override
	public int deleteOne1(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogRegister.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(String executionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionId", executionId);
		return getSqlSessionTemplate().delete("BatchTimingJobLogRegister.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne1(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne1", batchTimingJobLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne1R(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne1R", batchTimingJobLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne1L(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne1L", batchTimingJobLogRegister);
	}

	/**
	 * 单笔查询
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne2(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne2", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne2R(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne2R", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 任务执行实例
	 * @param stepId 执行步骤ID
	 */
	@Override
	public BatchTimingJobLogRegister selectOne2L(String executionInstance, String stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		map.put("stepId", stepId);
		BatchTimingJobLogRegister batchTimingJobLogRegister = new BatchTimingJobLogRegister();
		batchTimingJobLogRegister = (BatchTimingJobLogRegister)getSqlSessionTemplate().selectOne("BatchTimingJobLogRegister.selectOne2L", map);
		if (batchTimingJobLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gj_ob_logregister", CommonUtils.toString(map));
		return batchTimingJobLogRegister;
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
		return getSqlSessionTemplate().delete("BatchTimingJobLogRegister.deleteOne2", map);
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
		return getSqlSessionTemplate().delete("BatchTimingJobLogRegister.deleteOne2L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne2(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne2", batchTimingJobLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne2R(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne2R", batchTimingJobLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingJobLogRegister 
	 */
	@Override
	public int updateOne2L(BatchTimingJobLogRegister batchTimingJobLogRegister){
		return getSqlSessionTemplate().update("BatchTimingJobLogRegister.updateOne2L", batchTimingJobLogRegister);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTimingJobLogRegister> list){
		batchExcutor("BatchTimingJobLogRegister.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate2(List<BatchTimingJobLogRegister> list){
		batchExcutor("BatchTimingJobLogRegister.batchUpdate2", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTimingJobLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTimingJobLogRegister.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTimingJobLogRegister> list){
		batchExcutor("BatchTimingJobLogRegister.deleteOne1", list, "delete", 20);
	}

}