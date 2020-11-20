package org.mine.dao.impl;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTimingStepLogHistoryDao;
import org.mine.model.BatchTimingStepLogHistory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @filename BatchTimingStepLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-14 19:09:48
 * @version v1.0
*/
@Repository
public class BatchTimingStepLogHistoryDaoImpl extends BaseDaoSupport implements BatchTimingStepLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTimingStepLogHistory 
	 */
	@Override
	public int insertOne(BatchTimingStepLogHistory batchTimingStepLogHistory){
		return getSqlSessionTemplate().insert("BatchTimingStepLogHistory.insertOne", batchTimingStepLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTimingStepLogHistory> list){
		batchExcutor("BatchTimingStepLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTimingStepLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTimingStepLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogHistory selectOne1(Long historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchTimingStepLogHistory batchTimingStepLogHistory = new BatchTimingStepLogHistory();
		batchTimingStepLogHistory = (BatchTimingStepLogHistory)getSqlSessionTemplate().selectOne("BatchTimingStepLogHistory.selectOne1", map);
		if (batchTimingStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_loghistory", CommonUtils.toString(map));
		return batchTimingStepLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogHistory selectOne1R(Long historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchTimingStepLogHistory batchTimingStepLogHistory = new BatchTimingStepLogHistory();
		batchTimingStepLogHistory = (BatchTimingStepLogHistory)getSqlSessionTemplate().selectOne("BatchTimingStepLogHistory.selectOne1R", map);
		if (batchTimingStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_loghistory", CommonUtils.toString(map));
		return batchTimingStepLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public BatchTimingStepLogHistory selectOne1L(Long historyStepExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		BatchTimingStepLogHistory batchTimingStepLogHistory = new BatchTimingStepLogHistory();
		batchTimingStepLogHistory = (BatchTimingStepLogHistory)getSqlSessionTemplate().selectOne("BatchTimingStepLogHistory.selectOne1L", map);
		if (batchTimingStepLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_timin_gst_ep_loghistory", CommonUtils.toString(map));
		return batchTimingStepLogHistory;
	}

	/**
	 * 单笔删除
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(Long historyStepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingStepLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyStepExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(Long historyStepExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyStepExecutionId", historyStepExecutionId);
		return getSqlSessionTemplate().delete("BatchTimingStepLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTimingStepLogHistory 
	 */
	@Override
	public int updateOne1(BatchTimingStepLogHistory batchTimingStepLogHistory){
		return getSqlSessionTemplate().update("BatchTimingStepLogHistory.updateOne1", batchTimingStepLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTimingStepLogHistory 
	 */
	@Override
	public int updateOne1R(BatchTimingStepLogHistory batchTimingStepLogHistory){
		return getSqlSessionTemplate().update("BatchTimingStepLogHistory.updateOne1R", batchTimingStepLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTimingStepLogHistory 
	 */
	@Override
	public int updateOne1L(BatchTimingStepLogHistory batchTimingStepLogHistory){
		return getSqlSessionTemplate().update("BatchTimingStepLogHistory.updateOne1L", batchTimingStepLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTimingStepLogHistory> list){
		batchExcutor("BatchTimingStepLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTimingStepLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTimingStepLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTimingStepLogHistory> list){
		batchExcutor("BatchTimingStepLogHistory.deleteOne1", list, "delete", 20);
	}

}