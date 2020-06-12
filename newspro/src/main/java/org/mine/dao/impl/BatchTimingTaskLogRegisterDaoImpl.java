package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTimingTaskLogRegister;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingTaskLogRegisterDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTimingTaskLogRegisterDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:39
 * @version v1.0
*/
@Repository
public class BatchTimingTaskLogRegisterDaoImpl extends BaseDaoSupport implements BatchTimingTaskLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public int insertOne(BatchTimingTaskLogRegister batchTimingTaskLogRegister){
		return getSqlSessionTemplate().insert("BatchTimingTaskLogRegister.insertOne", batchTimingTaskLogRegister);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public void batchInsert(List<BatchTimingTaskLogRegister> list){
		batchExcutor("BatchTimingTaskLogRegister.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingTaskLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingTaskLogRegister.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param timingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogRegister selectOne1(Long timingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timingExecutionId", timingExecutionId);
		BatchTimingTaskLogRegister batchTimingTaskLogRegister = new BatchTimingTaskLogRegister();
		batchTimingTaskLogRegister = (BatchTimingTaskLogRegister)getSqlSessionTemplate().selectOne("BatchTimingTaskLogRegister.selectOne1", map);
		if (batchTimingTaskLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_logregister", CommonUtils.toString(map));
		return batchTimingTaskLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param timingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogRegister selectOne1R(Long timingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timingExecutionId", timingExecutionId);
		BatchTimingTaskLogRegister batchTimingTaskLogRegister = new BatchTimingTaskLogRegister();
		batchTimingTaskLogRegister = (BatchTimingTaskLogRegister)getSqlSessionTemplate().selectOne("BatchTimingTaskLogRegister.selectOne1R", map);
		if (batchTimingTaskLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_logregister", CommonUtils.toString(map));
		return batchTimingTaskLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param timingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogRegister selectOne1L(Long timingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timingExecutionId", timingExecutionId);
		BatchTimingTaskLogRegister batchTimingTaskLogRegister = new BatchTimingTaskLogRegister();
		batchTimingTaskLogRegister = (BatchTimingTaskLogRegister)getSqlSessionTemplate().selectOne("BatchTimingTaskLogRegister.selectOne1L", map);
		if (batchTimingTaskLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_logregister", CommonUtils.toString(map));
		return batchTimingTaskLogRegister;
	}

	/**
	 * 单笔删除
	 * @param timingExecutionId 定时任务执行ID
	 */
	@Override
	public int deleteOne1(Long timingExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timingExecutionId", timingExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingTaskLogRegister.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param timingExecutionId 定时任务执行ID
	 */
	@Override
	public int deleteOne1L(Long timingExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timingExecutionId", timingExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingTaskLogRegister.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public int updateOne1(BatchTimingTaskLogRegister batchTimingTaskLogRegister){
		return getSqlSessionTemplate().update("BatchTimingTaskLogRegister.updateOne1", batchTimingTaskLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public int updateOne1R(BatchTimingTaskLogRegister batchTimingTaskLogRegister){
		return getSqlSessionTemplate().update("BatchTimingTaskLogRegister.updateOne1R", batchTimingTaskLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public int updateOne1L(BatchTimingTaskLogRegister batchTimingTaskLogRegister){
		return getSqlSessionTemplate().update("BatchTimingTaskLogRegister.updateOne1L", batchTimingTaskLogRegister);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTimingTaskLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTimingTaskLogRegister.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public void batchUpdate(List<BatchTimingTaskLogRegister> list){
		batchExcutor("BatchTimingTaskLogRegister.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTimingTaskLogRegister 
	 */
	@Override
	public void batchDelete(List<BatchTimingTaskLogRegister> list){
		batchExcutor("BatchTimingTaskLogRegister.deleteOne1", list, "delete", 20);
	}

}