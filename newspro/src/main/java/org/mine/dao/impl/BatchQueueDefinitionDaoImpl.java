package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchQueueDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchQueueDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-09-17 20:09:12
 * @version v1.0
*/
@Repository
public class BatchQueueDefinitionDaoImpl extends BaseDaoSupport implements BatchQueueDefinitionDao {
	/**
	 * 单笔插入
	 * @param batchQueueDefinition 
	 */
	@Override
	public int insertOne(BatchQueueDefinition batchQueueDefinition){
		return getSqlSessionTemplate().insert("BatchQueueDefinition.insertOne", batchQueueDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchQueueDefinition> list){
		batchExcutor("BatchQueueDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchQueueDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchQueueDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueDefinition selectOne1(String queueId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		BatchQueueDefinition batchQueueDefinition = new BatchQueueDefinition();
		batchQueueDefinition = (BatchQueueDefinition)getSqlSessionTemplate().selectOne("BatchQueueDefinition.selectOne1", map);
		if (batchQueueDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_edefinition", CommonUtils.toString(map));
		return batchQueueDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueDefinition selectOne1R(String queueId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		BatchQueueDefinition batchQueueDefinition = new BatchQueueDefinition();
		batchQueueDefinition = (BatchQueueDefinition)getSqlSessionTemplate().selectOne("BatchQueueDefinition.selectOne1R", map);
		if (batchQueueDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_edefinition", CommonUtils.toString(map));
		return batchQueueDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueDefinition selectOne1L(String queueId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		BatchQueueDefinition batchQueueDefinition = new BatchQueueDefinition();
		batchQueueDefinition = (BatchQueueDefinition)getSqlSessionTemplate().selectOne("BatchQueueDefinition.selectOne1L", map);
		if (batchQueueDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_queu_edefinition", CommonUtils.toString(map));
		return batchQueueDefinition;
	}

	/**
	 * 单笔删除
	 * @param queueId 队列ID
	 */
	@Override
	public int deleteOne1(String queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().delete("BatchQueueDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	@Override
	public int deleteOne1L(String queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().delete("BatchQueueDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchQueueDefinition 
	 */
	@Override
	public int updateOne1(BatchQueueDefinition batchQueueDefinition){
		return getSqlSessionTemplate().update("BatchQueueDefinition.updateOne1", batchQueueDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchQueueDefinition 
	 */
	@Override
	public int updateOne1R(BatchQueueDefinition batchQueueDefinition){
		return getSqlSessionTemplate().update("BatchQueueDefinition.updateOne1R", batchQueueDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchQueueDefinition 
	 */
	@Override
	public int updateOne1L(BatchQueueDefinition batchQueueDefinition){
		return getSqlSessionTemplate().update("BatchQueueDefinition.updateOne1L", batchQueueDefinition);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchQueueDefinition> list){
		batchExcutor("BatchQueueDefinition.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchQueueDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchQueueDefinition.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchQueueDefinition> list){
		batchExcutor("BatchQueueDefinition.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	@Override
	public List<BatchQueueDefinition> selectAll1(Integer queueExcFlag){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueExcFlag", queueExcFlag);
		return getSqlSessionTemplate().selectList("BatchQueueDefinition.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	@Override
	public List<BatchQueueDefinition> selectAll1R(Integer queueExcFlag){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueExcFlag", queueExcFlag);
		return getSqlSessionTemplate().selectList("BatchQueueDefinition.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueExcFlag 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	@Override
	public List<BatchQueueDefinition> selectAll1L(Integer queueExcFlag){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueExcFlag", queueExcFlag);
		return getSqlSessionTemplate().selectList("BatchQueueDefinition.selectAll1L", map);
	}

}