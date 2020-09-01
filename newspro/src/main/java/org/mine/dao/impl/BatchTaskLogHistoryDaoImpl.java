package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 19:08:09
 * @version v1.0
*/
@Repository
public class BatchTaskLogHistoryDaoImpl extends BaseDaoSupport implements BatchTaskLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchTaskLogHistory 
	 */
	@Override
	public int insertOne(BatchTaskLogHistory batchTaskLogHistory){
		return getSqlSessionTemplate().insert("BatchTaskLogHistory.insertOne", batchTaskLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTaskLogHistory> list){
		batchExcutor("BatchTaskLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLogHistory selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLogHistory batchTaskLogHistory = new BatchTaskLogHistory();
		batchTaskLogHistory = (BatchTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTaskLogHistory.selectOne1", map);
		if (batchTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kl_oghistory", CommonUtils.toString(map));
		return batchTaskLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLogHistory selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLogHistory batchTaskLogHistory = new BatchTaskLogHistory();
		batchTaskLogHistory = (BatchTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTaskLogHistory.selectOne1R", map);
		if (batchTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kl_oghistory", CommonUtils.toString(map));
		return batchTaskLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchTaskLogHistory selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchTaskLogHistory batchTaskLogHistory = new BatchTaskLogHistory();
		batchTaskLogHistory = (BatchTaskLogHistory)getSqlSessionTemplate().selectOne("BatchTaskLogHistory.selectOne1L", map);
		if (batchTaskLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kl_oghistory", CommonUtils.toString(map));
		return batchTaskLogHistory;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchTaskLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchTaskLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTaskLogHistory 
	 */
	@Override
	public int updateOne1(BatchTaskLogHistory batchTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTaskLogHistory.updateOne1", batchTaskLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskLogHistory 
	 */
	@Override
	public int updateOne1R(BatchTaskLogHistory batchTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTaskLogHistory.updateOne1R", batchTaskLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskLogHistory 
	 */
	@Override
	public int updateOne1L(BatchTaskLogHistory batchTaskLogHistory){
		return getSqlSessionTemplate().update("BatchTaskLogHistory.updateOne1L", batchTaskLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTaskLogHistory> list){
		batchExcutor("BatchTaskLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTaskLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTaskLogHistory> list){
		batchExcutor("BatchTaskLogHistory.deleteOne1", list, "delete", 20);
	}

}