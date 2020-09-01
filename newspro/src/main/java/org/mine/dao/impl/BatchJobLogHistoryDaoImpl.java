package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobLogHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobLogHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobLogHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-25 15:08:12
 * @version v1.0
*/
@Repository
public class BatchJobLogHistoryDaoImpl extends BaseDaoSupport implements BatchJobLogHistoryDao {
	/**
	 * 单笔插入
	 * @param batchJobLogHistory 
	 */
	@Override
	public int insertOne(BatchJobLogHistory batchJobLogHistory){
		return getSqlSessionTemplate().insert("BatchJobLogHistory.insertOne", batchJobLogHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchJobLogHistory> list){
		batchExcutor("BatchJobLogHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchJobLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchJobLogHistory.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLogHistory selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLogHistory batchJobLogHistory = new BatchJobLogHistory();
		batchJobLogHistory = (BatchJobLogHistory)getSqlSessionTemplate().selectOne("BatchJobLogHistory.selectOne1", map);
		if (batchJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bl_oghistory", CommonUtils.toString(map));
		return batchJobLogHistory;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLogHistory selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLogHistory batchJobLogHistory = new BatchJobLogHistory();
		batchJobLogHistory = (BatchJobLogHistory)getSqlSessionTemplate().selectOne("BatchJobLogHistory.selectOne1R", map);
		if (batchJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bl_oghistory", CommonUtils.toString(map));
		return batchJobLogHistory;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLogHistory selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLogHistory batchJobLogHistory = new BatchJobLogHistory();
		batchJobLogHistory = (BatchJobLogHistory)getSqlSessionTemplate().selectOne("BatchJobLogHistory.selectOne1L", map);
		if (batchJobLogHistory == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bl_oghistory", CommonUtils.toString(map));
		return batchJobLogHistory;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchJobLogHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchJobLogHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchJobLogHistory 
	 */
	@Override
	public int updateOne1(BatchJobLogHistory batchJobLogHistory){
		return getSqlSessionTemplate().update("BatchJobLogHistory.updateOne1", batchJobLogHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobLogHistory 
	 */
	@Override
	public int updateOne1R(BatchJobLogHistory batchJobLogHistory){
		return getSqlSessionTemplate().update("BatchJobLogHistory.updateOne1R", batchJobLogHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobLogHistory 
	 */
	@Override
	public int updateOne1L(BatchJobLogHistory batchJobLogHistory){
		return getSqlSessionTemplate().update("BatchJobLogHistory.updateOne1L", batchJobLogHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchJobLogHistory> list){
		batchExcutor("BatchJobLogHistory.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchJobLogHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchJobLogHistory.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchJobLogHistory> list){
		batchExcutor("BatchJobLogHistory.deleteOne1", list, "delete", 20);
	}

}