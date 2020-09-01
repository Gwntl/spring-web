package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobLog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobLogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobLogDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-25 15:08:12
 * @version v1.0
*/
@Repository
public class BatchJobLogDaoImpl extends BaseDaoSupport implements BatchJobLogDao {
	/**
	 * 单笔插入
	 * @param batchJobLog 
	 */
	@Override
	public int insertOne(BatchJobLog batchJobLog){
		return getSqlSessionTemplate().insert("BatchJobLog.insertOne", batchJobLog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchJobLog> list){
		batchExcutor("BatchJobLog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchJobLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchJobLog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLog selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLog batchJobLog = new BatchJobLog();
		batchJobLog = (BatchJobLog)getSqlSessionTemplate().selectOne("BatchJobLog.selectOne1", map);
		if (batchJobLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_blog", CommonUtils.toString(map));
		return batchJobLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLog selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLog batchJobLog = new BatchJobLog();
		batchJobLog = (BatchJobLog)getSqlSessionTemplate().selectOne("BatchJobLog.selectOne1R", map);
		if (batchJobLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_blog", CommonUtils.toString(map));
		return batchJobLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchJobLog selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchJobLog batchJobLog = new BatchJobLog();
		batchJobLog = (BatchJobLog)getSqlSessionTemplate().selectOne("BatchJobLog.selectOne1L", map);
		if (batchJobLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_blog", CommonUtils.toString(map));
		return batchJobLog;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchJobLog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchJobLog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchJobLog 
	 */
	@Override
	public int updateOne1(BatchJobLog batchJobLog){
		return getSqlSessionTemplate().update("BatchJobLog.updateOne1", batchJobLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobLog 
	 */
	@Override
	public int updateOne1R(BatchJobLog batchJobLog){
		return getSqlSessionTemplate().update("BatchJobLog.updateOne1R", batchJobLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobLog 
	 */
	@Override
	public int updateOne1L(BatchJobLog batchJobLog){
		return getSqlSessionTemplate().update("BatchJobLog.updateOne1L", batchJobLog);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchJobLog> list){
		batchExcutor("BatchJobLog.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchJobLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchJobLog.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchJobLog> list){
		batchExcutor("BatchJobLog.deleteOne1", list, "delete", 20);
	}

}