package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchExecutorSeqLog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchExecutorSeqLogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchExecutorSeqLogDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-18 16:08:15
 * @version v1.0
*/
@Repository
public class BatchExecutorSeqLogDaoImpl extends BaseDaoSupport implements BatchExecutorSeqLogDao {
	/**
	 * 单笔插入
	 * @param batchExecutorSeqLog 
	 */
	@Override
	public int insertOne(BatchExecutorSeqLog batchExecutorSeqLog){
		return getSqlSessionTemplate().insert("BatchExecutorSeqLog.insertOne", batchExecutorSeqLog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchExecutorSeqLog> list){
		batchExcutor("BatchExecutorSeqLog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchExecutorSeqLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchExecutorSeqLog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 作业执行实例
	 */
	@Override
	public BatchExecutorSeqLog selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchExecutorSeqLog batchExecutorSeqLog = new BatchExecutorSeqLog();
		batchExecutorSeqLog = (BatchExecutorSeqLog)getSqlSessionTemplate().selectOne("BatchExecutorSeqLog.selectOne1", map);
		if (batchExecutorSeqLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rs_eqlog", CommonUtils.toString(map));
		return batchExecutorSeqLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 作业执行实例
	 */
	@Override
	public BatchExecutorSeqLog selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchExecutorSeqLog batchExecutorSeqLog = new BatchExecutorSeqLog();
		batchExecutorSeqLog = (BatchExecutorSeqLog)getSqlSessionTemplate().selectOne("BatchExecutorSeqLog.selectOne1R", map);
		if (batchExecutorSeqLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rs_eqlog", CommonUtils.toString(map));
		return batchExecutorSeqLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 作业执行实例
	 */
	@Override
	public BatchExecutorSeqLog selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchExecutorSeqLog batchExecutorSeqLog = new BatchExecutorSeqLog();
		batchExecutorSeqLog = (BatchExecutorSeqLog)getSqlSessionTemplate().selectOne("BatchExecutorSeqLog.selectOne1L", map);
		if (batchExecutorSeqLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rs_eqlog", CommonUtils.toString(map));
		return batchExecutorSeqLog;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 作业执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchExecutorSeqLog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 作业执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchExecutorSeqLog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchExecutorSeqLog 
	 */
	@Override
	public int updateOne1(BatchExecutorSeqLog batchExecutorSeqLog){
		return getSqlSessionTemplate().update("BatchExecutorSeqLog.updateOne1", batchExecutorSeqLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchExecutorSeqLog 
	 */
	@Override
	public int updateOne1R(BatchExecutorSeqLog batchExecutorSeqLog){
		return getSqlSessionTemplate().update("BatchExecutorSeqLog.updateOne1R", batchExecutorSeqLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchExecutorSeqLog 
	 */
	@Override
	public int updateOne1L(BatchExecutorSeqLog batchExecutorSeqLog){
		return getSqlSessionTemplate().update("BatchExecutorSeqLog.updateOne1L", batchExecutorSeqLog);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchExecutorSeqLog> list){
		batchExcutor("BatchExecutorSeqLog.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchExecutorSeqLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchExecutorSeqLog.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchExecutorSeqLog> list){
		batchExcutor("BatchExecutorSeqLog.deleteOne1", list, "delete", 20);
	}

}