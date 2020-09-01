package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchStepExecutionLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchStepExecutionLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchStepExecutionLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
@Repository
public class BatchStepExecutionLogHistoryDaoImpl extends BaseDaoSupport implements BatchStepExecutionLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchStepExecutionLogHistory 
	 */
	@Override
	public int insertOne(BatchStepExecutionLogHistory batchStepExecutionLogHistory){
		return getSqlSessionTemplate().insert("BatchStepExecutionLogHistory.insertOne", batchStepExecutionLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchStepExecutionLogHistory> list){
		batchExcutor("BatchStepExecutionLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchStepExecutionLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchStepExecutionLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogHistory selectOne1(String historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchStepExecutionLogHistory batchStepExecutionLogHistory = new BatchStepExecutionLogHistory();
		batchStepExecutionLogHistory = (BatchStepExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchStepExecutionLogHistory.selectOne1", map);
		if (batchStepExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_loghistory", CommonUtils.toString(map));
		return batchStepExecutionLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogHistory selectOne1R(String historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchStepExecutionLogHistory batchStepExecutionLogHistory = new BatchStepExecutionLogHistory();
		batchStepExecutionLogHistory = (BatchStepExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchStepExecutionLogHistory.selectOne1R", map);
		if (batchStepExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_loghistory", CommonUtils.toString(map));
		return batchStepExecutionLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchStepExecutionLogHistory selectOne1L(String historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchStepExecutionLogHistory batchStepExecutionLogHistory = new BatchStepExecutionLogHistory();
		batchStepExecutionLogHistory = (BatchStepExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchStepExecutionLogHistory.selectOne1L", map);
		if (batchStepExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pexecuti_on_loghistory", CommonUtils.toString(map));
		return batchStepExecutionLogHistory;
	}

	/**
	 * 单笔删除
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(String historyStepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		return getSqlSessionTemplate().delete("BatchStepExecutionLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(String historyStepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		return getSqlSessionTemplate().delete("BatchStepExecutionLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchStepExecutionLogHistory 
	 */
	@Override
	public int updateOne1(BatchStepExecutionLogHistory batchStepExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchStepExecutionLogHistory.updateOne1", batchStepExecutionLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchStepExecutionLogHistory 
	 */
	@Override
	public int updateOne1R(BatchStepExecutionLogHistory batchStepExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchStepExecutionLogHistory.updateOne1R", batchStepExecutionLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchStepExecutionLogHistory 
	 */
	@Override
	public int updateOne1L(BatchStepExecutionLogHistory batchStepExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchStepExecutionLogHistory.updateOne1L", batchStepExecutionLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchStepExecutionLogHistory> list){
		batchExcutor("BatchStepExecutionLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchStepExecutionLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchStepExecutionLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchStepExecutionLogHistory> list){
		batchExcutor("BatchStepExecutionLogHistory.deleteOne1", list, "delete", 20);
	}

}