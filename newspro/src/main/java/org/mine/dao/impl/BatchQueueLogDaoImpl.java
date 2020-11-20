package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchQueueLog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueLogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchQueueLogDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-07 19:09:41
 * @version v1.0
*/
@Repository
public class BatchQueueLogDaoImpl extends BaseDaoSupport implements BatchQueueLogDao {
	/**
	 * 单笔插入
	 * @param batchQueueLog 
	 */
	@Override
	public int insertOne(BatchQueueLog batchQueueLog){
		return getSqlSessionTemplate().insert("BatchQueueLog.insertOne", batchQueueLog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchQueueLog> list){
		batchExcutor("BatchQueueLog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchQueueLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchQueueLog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchQueueLog selectOne1(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchQueueLog batchQueueLog = new BatchQueueLog();
		batchQueueLog = (BatchQueueLog)getSqlSessionTemplate().selectOne("BatchQueueLog.selectOne1", map);
		if (batchQueueLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_elog", CommonUtils.toString(map));
		return batchQueueLog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchQueueLog selectOne1R(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchQueueLog batchQueueLog = new BatchQueueLog();
		batchQueueLog = (BatchQueueLog)getSqlSessionTemplate().selectOne("BatchQueueLog.selectOne1R", map);
		if (batchQueueLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_elog", CommonUtils.toString(map));
		return batchQueueLog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public BatchQueueLog selectOne1L(String executionInstance, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		BatchQueueLog batchQueueLog = new BatchQueueLog();
		batchQueueLog = (BatchQueueLog)getSqlSessionTemplate().selectOne("BatchQueueLog.selectOne1L", map);
		if (batchQueueLog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_elog", CommonUtils.toString(map));
		return batchQueueLog;
	}

	/**
	 * 单笔删除
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchQueueLog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executionInstance 执行实例
	 */
	@Override
	public int deleteOne1L(String executionInstance){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executionInstance", executionInstance);
		return getSqlSessionTemplate().delete("BatchQueueLog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchQueueLog 
	 */
	@Override
	public int updateOne1(BatchQueueLog batchQueueLog){
		return getSqlSessionTemplate().update("BatchQueueLog.updateOne1", batchQueueLog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueLog 
	 */
	@Override
	public int updateOne1R(BatchQueueLog batchQueueLog){
		return getSqlSessionTemplate().update("BatchQueueLog.updateOne1R", batchQueueLog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueLog 
	 */
	@Override
	public int updateOne1L(BatchQueueLog batchQueueLog){
		return getSqlSessionTemplate().update("BatchQueueLog.updateOne1L", batchQueueLog);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchQueueLog> list){
		batchExcutor("BatchQueueLog.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchQueueLog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchQueueLog.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchQueueLog> list){
		batchExcutor("BatchQueueLog.deleteOne1", list, "delete", 20);
	}

}