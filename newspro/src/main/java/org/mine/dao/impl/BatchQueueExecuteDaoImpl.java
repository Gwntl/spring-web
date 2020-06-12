package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchQueueExecute;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueExecuteDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchQueueExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-08 10:06:21
 * @version v1.0
*/
@Repository
public class BatchQueueExecuteDaoImpl extends BaseDaoSupport implements BatchQueueExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchQueueExecute 
	 */
	@Override
	public int insertOne(BatchQueueExecute batchQueueExecute){
		return getSqlSessionTemplate().insert("BatchQueueExecute.insertOne", batchQueueExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchQueueExecute 
	 */
	@Override
	public void batchInsert(List<BatchQueueExecute> list){
		batchExcutor("BatchQueueExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchQueueExecute 
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
	 * 查询多笔数据
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1(Long executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1R(Long executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeQueueId 执行队列ID
	 */
	@Override
	public List<BatchQueueExecute> selectAll1L(Long executeQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeQueueId", executeQueueId);
		return getSqlSessionTemplate().selectList("BatchQueueExecute.selectAll1L", map);
	}

}