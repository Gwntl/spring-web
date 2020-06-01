package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchStepDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchStepDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchStepDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:24
 * @version v1.0
*/
@Repository
public class BatchStepDefinitionDaoImpl extends BaseDaoSupport implements BatchStepDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchStepDefinition 
	 */
	@Override
	public int insertOne(BatchStepDefinition batchStepDefinition){
		return getSqlSessionTemplate().insert("BatchStepDefinition.insertOne", batchStepDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	@Override
	public void batchInsert(List<BatchStepDefinition> list){
		batchExcutor("BatchStepDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchStepDefinition 
	 */
	@Override
	public void batchInsertXML(List<BatchStepDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchStepDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param stepId 作业步骤ID
	 */
	@Override
	public BatchStepDefinition selectOne1(Long stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepId", stepId);
		BatchStepDefinition batchStepDefinition = new BatchStepDefinition();
		batchStepDefinition = (BatchStepDefinition)getSqlSessionTemplate().selectOne("BatchStepDefinition.selectOne1", map);
		if (batchStepDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pdefinition", CommonUtils.toString(map));
		return batchStepDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param stepId 作业步骤ID
	 */
	@Override
	public BatchStepDefinition selectOne1R(Long stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepId", stepId);
		BatchStepDefinition batchStepDefinition = new BatchStepDefinition();
		batchStepDefinition = (BatchStepDefinition)getSqlSessionTemplate().selectOne("BatchStepDefinition.selectOne1R", map);
		if (batchStepDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pdefinition", CommonUtils.toString(map));
		return batchStepDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	@Override
	public BatchStepDefinition selectOne1L(Long stepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepId", stepId);
		BatchStepDefinition batchStepDefinition = new BatchStepDefinition();
		batchStepDefinition = (BatchStepDefinition)getSqlSessionTemplate().selectOne("BatchStepDefinition.selectOne1L", map);
		if (batchStepDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_ste_pdefinition", CommonUtils.toString(map));
		return batchStepDefinition;
	}

	/**
	 * 单笔删除
	 * @param stepId 作业步骤ID
	 */
	@Override
	public int deleteOne1(Long stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchStepDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param stepId 作业步骤ID
	 */
	@Override
	public int deleteOne1L(Long stepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stepId", stepId);
		return getSqlSessionTemplate().delete("BatchStepDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchStepDefinition 
	 */
	@Override
	public int updateOne1(BatchStepDefinition batchStepDefinition){
		return getSqlSessionTemplate().update("BatchStepDefinition.updateOne1", batchStepDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchStepDefinition 
	 */
	@Override
	public int updateOne1R(BatchStepDefinition batchStepDefinition){
		return getSqlSessionTemplate().update("BatchStepDefinition.updateOne1R", batchStepDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchStepDefinition 
	 */
	@Override
	public int updateOne1L(BatchStepDefinition batchStepDefinition){
		return getSqlSessionTemplate().update("BatchStepDefinition.updateOne1L", batchStepDefinition);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchStepDefinition 
	 */
	@Override
	public void batchUpdateXML1(List<BatchStepDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchStepDefinition.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	@Override
	public void batchUpdate(List<BatchStepDefinition> list){
		batchExcutor("BatchStepDefinition.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchStepDefinition 
	 */
	@Override
	public void batchDelete(List<BatchStepDefinition> list){
		batchExcutor("BatchStepDefinition.deleteOne1", list, "delete", 20);
	}

}