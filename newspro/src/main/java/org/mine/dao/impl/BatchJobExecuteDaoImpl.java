package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobExecute;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobExecuteDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:15
 * @version v1.0
*/
@Repository
public class BatchJobExecuteDaoImpl extends BaseDaoSupport implements BatchJobExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchJobExecute 
	 */
	@Override
	public int insertOne(BatchJobExecute batchJobExecute){
		return getSqlSessionTemplate().insert("BatchJobExecute.insertOne", batchJobExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobExecute 
	 */
	@Override
	public void batchInsert(List<BatchJobExecute> list){
		batchExcutor("BatchJobExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobExecute 
	 */
	@Override
	public void batchInsertXML(List<BatchJobExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchJobExecute.batchInsertXML", map);
			};
		});
	}

	/**
	 * 查询多笔数据
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1(Long executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1R(Long executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1L(Long executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1L", map);
	}

}