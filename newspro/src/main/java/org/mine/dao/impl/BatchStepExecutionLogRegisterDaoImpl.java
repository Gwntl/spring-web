package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchStepExecutionLogRegister;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchStepExecutionLogRegisterDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchStepExecutionLogRegisterDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
@Repository
public class BatchStepExecutionLogRegisterDaoImpl extends BaseDaoSupport implements BatchStepExecutionLogRegisterDao {
	/**
	 * 单笔插入
	 * @param batchStepExecutionLogRegister 
	 */
	@Override
	public int insertOne(BatchStepExecutionLogRegister batchStepExecutionLogRegister){
		return getSqlSessionTemplate().insert("BatchStepExecutionLogRegister.insertOne", batchStepExecutionLogRegister);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchStepExecutionLogRegister> list){
		batchExcutor("BatchStepExecutionLogRegister.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchStepExecutionLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchStepExecutionLogRegister.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogRegister selectOne1(String stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchStepExecutionLogRegister batchStepExecutionLogRegister = new BatchStepExecutionLogRegister();
		batchStepExecutionLogRegister = (BatchStepExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchStepExecutionLogRegister.selectOne1", map);
		if (batchStepExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_logregister", CommonUtils.toString(map));
		return batchStepExecutionLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogRegister selectOne1R(String stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchStepExecutionLogRegister batchStepExecutionLogRegister = new BatchStepExecutionLogRegister();
		batchStepExecutionLogRegister = (BatchStepExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchStepExecutionLogRegister.selectOne1R", map);
		if (batchStepExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_logregister", CommonUtils.toString(map));
		return batchStepExecutionLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogRegister selectOne1L(String stepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		BatchStepExecutionLogRegister batchStepExecutionLogRegister = new BatchStepExecutionLogRegister();
		batchStepExecutionLogRegister = (BatchStepExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchStepExecutionLogRegister.selectOne1L", map);
		if (batchStepExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_logregister", CommonUtils.toString(map));
		return batchStepExecutionLogRegister;
	}

	/**
	 * 单笔删除
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(String stepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		return getSqlSessionTemplate().delete("BatchStepExecutionLogRegister.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(String stepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepExecutionId", stepExecutionId);
		return getSqlSessionTemplate().delete("BatchStepExecutionLogRegister.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepExecutionLogRegister 
	 */
	@Override
	public int updateOne1(BatchStepExecutionLogRegister batchStepExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchStepExecutionLogRegister.updateOne1", batchStepExecutionLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepExecutionLogRegister 
	 */
	@Override
	public int updateOne1R(BatchStepExecutionLogRegister batchStepExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchStepExecutionLogRegister.updateOne1R", batchStepExecutionLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepExecutionLogRegister 
	 */
	@Override
	public int updateOne1L(BatchStepExecutionLogRegister batchStepExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchStepExecutionLogRegister.updateOne1L", batchStepExecutionLogRegister);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchStepExecutionLogRegister> list){
		batchExcutor("BatchStepExecutionLogRegister.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchStepExecutionLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchStepExecutionLogRegister.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchStepExecutionLogRegister> list){
		batchExcutor("BatchStepExecutionLogRegister.deleteOne1", list, "delete", 20);
	}

}