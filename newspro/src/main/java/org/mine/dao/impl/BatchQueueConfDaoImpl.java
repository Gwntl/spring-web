package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchQueueConf;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchQueueConfDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchQueueConfDaoImpl.java
 * @author wzaUsers
 * @date 2019-11-14 20:11:21
 * @version v1.0
*/
@Repository
public class BatchQueueConfDaoImpl extends BaseDaoSupport implements BatchQueueConfDao {
	/**
	 * 单笔插入
	 * @param BatchQueueConf 
	 */
	@Override
	public int insertOne(BatchQueueConf batchQueueConf){
		return getSqlSessionTemplate().insert("BatchQueueConf.insertOne", batchQueueConf);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchQueueConf 
	 */
	@Override
	public void batchInsert(List<BatchQueueConf> list){
		batchExcutor("BatchQueueConf.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchQueueConf 
	 */
	@Override
	public void batchInsertXML(List<BatchQueueConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public void call(Map<String, Object> map) {
				getSqlSessionTemplate().insert("BatchQueueConf.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueConf selectOne1(Long queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().selectOne("BatchQueueConf.selectOne1", map);
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueConf selectOne1R(Long queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().selectOne("BatchQueueConf.selectOne1R", map);
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	@Override
	public BatchQueueConf selectOne1L(Long queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().selectOne("BatchQueueConf.selectOne1L", map);
	}

	/**
	 * 单笔删除
	 * @param queueId 队列ID
	 */
	@Override
	public int deleteOne1(Long queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().delete("BatchQueueConf.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param queueId 队列ID
	 */
	@Override
	public int deleteOne1L(Long queueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queueId", queueId);
		return getSqlSessionTemplate().delete("BatchQueueConf.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchQueueConf 
	 */
	@Override
	public int updateOne1(BatchQueueConf batchQueueConf){
		return getSqlSessionTemplate().update("BatchQueueConf.updateOne1", batchQueueConf);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchQueueConf 
	 */
	@Override
	public int updateOne1R(BatchQueueConf batchQueueConf){
		return getSqlSessionTemplate().update("BatchQueueConf.updateOne1R", batchQueueConf);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchQueueConf 
	 */
	@Override
	public int updateOne1L(BatchQueueConf batchQueueConf){
		return getSqlSessionTemplate().update("BatchQueueConf.updateOne1L", batchQueueConf);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchQueueConf 
	 */
	@Override
	public void batchUpdateXML1(List<BatchQueueConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public void call(Map<String, Object> map) {
				getSqlSessionTemplate().update("BatchQueueConf.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchQueueConf 
	 */
	@Override
	public void batchUpdate(List<BatchQueueConf> list){
		batchExcutor("BatchQueueConf.batchUpdate", list, "update", 20);
	}

}