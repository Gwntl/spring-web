package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTimingStepLogRegister;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingStepLogRegisterDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTimingStepLogRegisterDaoImpl.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/
@Repository
public class BatchTimingStepLogRegisterDaoImpl extends BaseDaoSupport implements BatchTimingStepLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public int insertOne(BatchTimingStepLogRegister batchTimingStepLogRegister){
		return getSqlSessionTemplate().insert("BatchTimingStepLogRegister.insertOne", batchTimingStepLogRegister);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public void batchInsert(List<BatchTimingStepLogRegister> list){
		batchExcutor("BatchTimingStepLogRegister.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingStepLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingStepLogRegister.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogRegister selectOne1(Long stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchTimingStepLogRegister batchTimingStepLogRegister = new BatchTimingStepLogRegister();
		batchTimingStepLogRegister = (BatchTimingStepLogRegister)getSqlSessionTemplate().selectOne("BatchTimingStepLogRegister.selectOne1", map);
		if (batchTimingStepLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_logregister", CommonUtils.toString(map));
		return batchTimingStepLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogRegister selectOne1R(Long stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchTimingStepLogRegister batchTimingStepLogRegister = new BatchTimingStepLogRegister();
		batchTimingStepLogRegister = (BatchTimingStepLogRegister)getSqlSessionTemplate().selectOne("BatchTimingStepLogRegister.selectOne1R", map);
		if (batchTimingStepLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_logregister", CommonUtils.toString(map));
		return batchTimingStepLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogRegister selectOne1L(Long stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchTimingStepLogRegister batchTimingStepLogRegister = new BatchTimingStepLogRegister();
		batchTimingStepLogRegister = (BatchTimingStepLogRegister)getSqlSessionTemplate().selectOne("BatchTimingStepLogRegister.selectOne1L", map);
		if (batchTimingStepLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_logregister", CommonUtils.toString(map));
		return batchTimingStepLogRegister;
	}

	/**
	 * 单笔删除
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(Long stepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingStepLogRegister.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(Long stepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingStepLogRegister.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public int updateOne1(BatchTimingStepLogRegister batchTimingStepLogRegister){
		return getSqlSessionTemplate().update("BatchTimingStepLogRegister.updateOne1", batchTimingStepLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public int updateOne1R(BatchTimingStepLogRegister batchTimingStepLogRegister){
		return getSqlSessionTemplate().update("BatchTimingStepLogRegister.updateOne1R", batchTimingStepLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public int updateOne1L(BatchTimingStepLogRegister batchTimingStepLogRegister){
		return getSqlSessionTemplate().update("BatchTimingStepLogRegister.updateOne1L", batchTimingStepLogRegister);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public void batchUpdate(List<BatchTimingStepLogRegister> list){
		batchExcutor("BatchTimingStepLogRegister.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingStepLogRegister 
	 */
	@Override
	public void batchDelete(List<BatchTimingStepLogRegister> list){
		batchExcutor("BatchTimingStepLogRegister.deleteOne1", list, "delete", 20);
	}

}