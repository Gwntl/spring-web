package org.mine.dao.impl;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueExecuteDao;
import org.mine.model.BatchQueueExecute;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @filename BatchQueueExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-14 11:09:02
 * @version v1.0
*/
@Repository
public class BatchQueueExecuteDaoImpl extends BaseDaoSupport implements BatchQueueExecuteDao {
	/**
	 * 单笔插入
	 * @param batchQueueExecute 
	 */
	@Override
	public int insertOne(BatchQueueExecute batchQueueExecute){
		return getSqlSessionTemplate().insert("BatchQueueExecute.insertOne", batchQueueExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchQueueExecute> list){
		batchExcutor("BatchQueueExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchQueueExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchQueueExecute.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public BatchQueueExecute selectOne1(String executeQueueId, String executeTaskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		map.put("executeTaskId", executeTaskId);
		BatchQueueExecute batchQueueExecute = new BatchQueueExecute();
		batchQueueExecute = (BatchQueueExecute)getSqlSessionTemplate().selectOne("BatchQueueExecute.selectOne1", map);
		if (batchQueueExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_eexecute", CommonUtils.toString(map));
		return batchQueueExecute;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public BatchQueueExecute selectOne1R(String executeQueueId, String executeTaskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		map.put("executeTaskId", executeTaskId);
		BatchQueueExecute batchQueueExecute = new BatchQueueExecute();
		batchQueueExecute = (BatchQueueExecute)getSqlSessionTemplate().selectOne("BatchQueueExecute.selectOne1R", map);
		if (batchQueueExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_eexecute", CommonUtils.toString(map));
		return batchQueueExecute;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public BatchQueueExecute selectOne1L(String executeQueueId, String executeTaskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		map.put("executeTaskId", executeTaskId);
		BatchQueueExecute batchQueueExecute = new BatchQueueExecute();
		batchQueueExecute = (BatchQueueExecute)getSqlSessionTemplate().selectOne("BatchQueueExecute.selectOne1L", map);
		if (batchQueueExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_eexecute", CommonUtils.toString(map));
		return batchQueueExecute;
	}

	/**
	 * 单笔删除
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public int deleteOne1(String executeQueueId, String executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().delete("BatchQueueExecute.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public int deleteOne1L(String executeQueueId, String executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().delete("BatchQueueExecute.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchQueueExecute 
	 */
	@Override
	public int updateOne1(BatchQueueExecute batchQueueExecute){
		return getSqlSessionTemplate().update("BatchQueueExecute.updateOne1", batchQueueExecute);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueExecute 
	 */
	@Override
	public int updateOne1R(BatchQueueExecute batchQueueExecute){
		return getSqlSessionTemplate().update("BatchQueueExecute.updateOne1R", batchQueueExecute);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueExecute 
	 */
	@Override
	public int updateOne1L(BatchQueueExecute batchQueueExecute){
		return getSqlSessionTemplate().update("BatchQueueExecute.updateOne1L", batchQueueExecute);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchQueueExecute> list){
		batchExcutor("BatchQueueExecute.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchQueueExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchQueueExecute.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchQueueExecute> list){
		batchExcutor("BatchQueueExecute.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1(String executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1R(String executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1L(String executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1L", map);
	}

}