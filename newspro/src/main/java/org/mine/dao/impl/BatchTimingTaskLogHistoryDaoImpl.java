package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTimingTaskLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingTaskLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTimingTaskLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
@Repository
public class BatchTimingTaskLogHistoryDaoImpl extends BaseDaoSupport implements BatchTimingTaskLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTimingTaskLogHistory 
	 */
	@Override
	public int insertOne(BatchTimingTaskLogHistory batchTimingTaskLogHistory){
		return getSqlSessionTemplate().insert("BatchTimingTaskLogHistory.insertOne", batchTimingTaskLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTimingTaskLogHistory> list){
		batchExcutor("BatchTimingTaskLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingTaskLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingTaskLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogHistory selectOne1(String historyTimingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTimingExecutionId", historyTimingExecutionId);
		BatchTimingTaskLogHistory batchTimingTaskLogHistory = new BatchTimingTaskLogHistory();
		batchTimingTaskLogHistory = (BatchTimingTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTimingTaskLogHistory.selectOne1", map);
		if (batchTimingTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_loghistory", CommonUtils.toString(map));
		return batchTimingTaskLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogHistory selectOne1R(String historyTimingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTimingExecutionId", historyTimingExecutionId);
		BatchTimingTaskLogHistory batchTimingTaskLogHistory = new BatchTimingTaskLogHistory();
		batchTimingTaskLogHistory = (BatchTimingTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTimingTaskLogHistory.selectOne1R", map);
		if (batchTimingTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_loghistory", CommonUtils.toString(map));
		return batchTimingTaskLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	@Override
	public BatchTimingTaskLogHistory selectOne1L(String historyTimingExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTimingExecutionId", historyTimingExecutionId);
		BatchTimingTaskLogHistory batchTimingTaskLogHistory = new BatchTimingTaskLogHistory();
		batchTimingTaskLogHistory = (BatchTimingTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTimingTaskLogHistory.selectOne1L", map);
		if (batchTimingTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gta_sk_loghistory", CommonUtils.toString(map));
		return batchTimingTaskLogHistory;
	}

	/**
	 * 单笔删除
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	@Override
	public int deleteOne1(String historyTimingExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTimingExecutionId", historyTimingExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingTaskLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTimingExecutionId 定时任务执行ID
	 */
	@Override
	public int deleteOne1L(String historyTimingExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTimingExecutionId", historyTimingExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingTaskLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingTaskLogHistory 
	 */
	@Override
	public int updateOne1(BatchTimingTaskLogHistory batchTimingTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTimingTaskLogHistory.updateOne1", batchTimingTaskLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingTaskLogHistory 
	 */
	@Override
	public int updateOne1R(BatchTimingTaskLogHistory batchTimingTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTimingTaskLogHistory.updateOne1R", batchTimingTaskLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingTaskLogHistory 
	 */
	@Override
	public int updateOne1L(BatchTimingTaskLogHistory batchTimingTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTimingTaskLogHistory.updateOne1L", batchTimingTaskLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTimingTaskLogHistory> list){
		batchExcutor("BatchTimingTaskLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTimingTaskLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTimingTaskLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTimingTaskLogHistory> list){
		batchExcutor("BatchTimingTaskLogHistory.deleteOne1", list, "delete", 20);
	}

}