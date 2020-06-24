package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-23 16:06:55
 * @version v1.0
*/
@Repository
public class BatchJobDefinitionDaoImpl extends BaseDaoSupport implements BatchJobDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchJobDefinition 
	 */
	@Override
	public int insertOne(BatchJobDefinition batchJobDefinition){
		return getSqlSessionTemplate().insert("BatchJobDefinition.insertOne", batchJobDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	@Override
	public void batchInsert(List<BatchJobDefinition> list){
		batchExcutor("BatchJobDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobDefinition 
	 */
	@Override
	public void batchInsertXML(List<BatchJobDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchJobDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param jobId 作业ID
	 */
	@Override
	public BatchJobDefinition selectOne1(Long jobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		BatchJobDefinition batchJobDefinition = new BatchJobDefinition();
		batchJobDefinition = (BatchJobDefinition)getSqlSessionTemplate().selectOne("BatchJobDefinition.selectOne1", map);
		if (batchJobDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bdefinition", CommonUtils.toString(map));
		return batchJobDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobId 作业ID
	 */
	@Override
	public BatchJobDefinition selectOne1R(Long jobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		BatchJobDefinition batchJobDefinition = new BatchJobDefinition();
		batchJobDefinition = (BatchJobDefinition)getSqlSessionTemplate().selectOne("BatchJobDefinition.selectOne1R", map);
		if (batchJobDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bdefinition", CommonUtils.toString(map));
		return batchJobDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	@Override
	public BatchJobDefinition selectOne1L(Long jobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		BatchJobDefinition batchJobDefinition = new BatchJobDefinition();
		batchJobDefinition = (BatchJobDefinition)getSqlSessionTemplate().selectOne("BatchJobDefinition.selectOne1L", map);
		if (batchJobDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bdefinition", CommonUtils.toString(map));
		return batchJobDefinition;
	}

	/**
	 * 单笔删除
	 * @param jobId 作业ID
	 */
	@Override
	public int deleteOne1(Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		return getSqlSessionTemplate().delete("BatchJobDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId 作业ID
	 */
	@Override
	public int deleteOne1L(Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		return getSqlSessionTemplate().delete("BatchJobDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchJobDefinition 
	 */
	@Override
	public int updateOne1(BatchJobDefinition batchJobDefinition){
		return getSqlSessionTemplate().update("BatchJobDefinition.updateOne1", batchJobDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobDefinition 
	 */
	@Override
	public int updateOne1R(BatchJobDefinition batchJobDefinition){
		return getSqlSessionTemplate().update("BatchJobDefinition.updateOne1R", batchJobDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobDefinition 
	 */
	@Override
	public int updateOne1L(BatchJobDefinition batchJobDefinition){
		return getSqlSessionTemplate().update("BatchJobDefinition.updateOne1L", batchJobDefinition);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobDefinition 
	 */
	@Override
	public void batchUpdateXML1(List<BatchJobDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchJobDefinition.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	@Override
	public void batchUpdate(List<BatchJobDefinition> list){
		batchExcutor("BatchJobDefinition.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchJobDefinition 
	 */
	@Override
	public void batchDelete(List<BatchJobDefinition> list){
		batchExcutor("BatchJobDefinition.deleteOne1", list, "delete", 20);
	}

}