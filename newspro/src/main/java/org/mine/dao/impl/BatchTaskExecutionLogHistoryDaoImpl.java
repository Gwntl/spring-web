package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskExecutionLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskExecutionLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskExecutionLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/
@Repository
public class BatchTaskExecutionLogHistoryDaoImpl extends BaseDaoSupport implements BatchTaskExecutionLogHistoryDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public int insertOne(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory){
		return getSqlSessionTemplate().insert("BatchTaskExecutionLogHistory.insertOne", batchTaskExecutionLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public void batchInsert(List<BatchTaskExecutionLogHistory> list){
		batchExcutor("BatchTaskExecutionLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskExecutionLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskExecutionLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param historyTaskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogHistory selectOne1(Long historyTaskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTaskExecutionId", historyTaskExecutionId);
		BatchTaskExecutionLogHistory batchTaskExecutionLogHistory = new BatchTaskExecutionLogHistory();
		batchTaskExecutionLogHistory = (BatchTaskExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogHistory.selectOne1", map);
		if (batchTaskExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_loghistory", CommonUtils.toString(map));
		return batchTaskExecutionLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogHistory selectOne1R(Long historyTaskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTaskExecutionId", historyTaskExecutionId);
		BatchTaskExecutionLogHistory batchTaskExecutionLogHistory = new BatchTaskExecutionLogHistory();
		batchTaskExecutionLogHistory = (BatchTaskExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogHistory.selectOne1R", map);
		if (batchTaskExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_loghistory", CommonUtils.toString(map));
		return batchTaskExecutionLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogHistory selectOne1L(Long historyTaskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTaskExecutionId", historyTaskExecutionId);
		BatchTaskExecutionLogHistory batchTaskExecutionLogHistory = new BatchTaskExecutionLogHistory();
		batchTaskExecutionLogHistory = (BatchTaskExecutionLogHistory)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogHistory.selectOne1L", map);
		if (batchTaskExecutionLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_loghistory", CommonUtils.toString(map));
		return batchTaskExecutionLogHistory;
	}

	/**
	 * 单笔删除
	 * @param historyTaskExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(Long historyTaskExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTaskExecutionId", historyTaskExecutionId);
		return getSqlSessionTemplate().delete("BatchTaskExecutionLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param historyTaskExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(Long historyTaskExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("historyTaskExecutionId", historyTaskExecutionId);
		return getSqlSessionTemplate().delete("BatchTaskExecutionLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public int updateOne1(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogHistory.updateOne1", batchTaskExecutionLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public int updateOne1R(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogHistory.updateOne1R", batchTaskExecutionLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public int updateOne1L(BatchTaskExecutionLogHistory batchTaskExecutionLogHistory){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogHistory.updateOne1L", batchTaskExecutionLogHistory);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTaskExecutionLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskExecutionLogHistory.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public void batchUpdate(List<BatchTaskExecutionLogHistory> list){
		batchExcutor("BatchTaskExecutionLogHistory.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogHistory 
	 */
	@Override
	public void batchDelete(List<BatchTaskExecutionLogHistory> list){
		batchExcutor("BatchTaskExecutionLogHistory.deleteOne1", list, "delete", 20);
	}

}