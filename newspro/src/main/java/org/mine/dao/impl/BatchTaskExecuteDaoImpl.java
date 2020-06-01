package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskExecute;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskExecuteDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:59
 * @version v1.0
*/
@Repository
public class BatchTaskExecuteDaoImpl extends BaseDaoSupport implements BatchTaskExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecute 
	 */
	@Override
	public int insertOne(BatchTaskExecute batchTaskExecute){
		return getSqlSessionTemplate().insert("BatchTaskExecute.insertOne", batchTaskExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecute 
	 */
	@Override
	public void batchInsert(List<BatchTaskExecute> list){
		batchExcutor("BatchTaskExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecute 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskExecute.batchInsertXML", map);
			};
		});
	}

	/**
	 * 查询多笔数据
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1(Long executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1R(Long executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1L(Long executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1L", map);
	}

}