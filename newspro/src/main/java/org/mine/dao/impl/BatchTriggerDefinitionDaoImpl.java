package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTriggerDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTriggerDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/
@Repository
public class BatchTriggerDefinitionDaoImpl extends BaseDaoSupport implements BatchTriggerDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public int insertOne(BatchTriggerDefinition batchTriggerDefinition){
		return getSqlSessionTemplate().insert("BatchTriggerDefinition.insertOne", batchTriggerDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public void batchInsert(List<BatchTriggerDefinition> list){
		batchExcutor("BatchTriggerDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public void batchInsertXML(List<BatchTriggerDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTriggerDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerDefinition selectOne1(Long triggerId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		BatchTriggerDefinition batchTriggerDefinition = new BatchTriggerDefinition();
		batchTriggerDefinition = (BatchTriggerDefinition)getSqlSessionTemplate().selectOne("BatchTriggerDefinition.selectOne1", map);
		if (batchTriggerDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_trigge_rdefinition", CommonUtils.toString(map));
		return batchTriggerDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerDefinition selectOne1R(Long triggerId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		BatchTriggerDefinition batchTriggerDefinition = new BatchTriggerDefinition();
		batchTriggerDefinition = (BatchTriggerDefinition)getSqlSessionTemplate().selectOne("BatchTriggerDefinition.selectOne1R", map);
		if (batchTriggerDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_trigge_rdefinition", CommonUtils.toString(map));
		return batchTriggerDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerDefinition selectOne1L(Long triggerId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		BatchTriggerDefinition batchTriggerDefinition = new BatchTriggerDefinition();
		batchTriggerDefinition = (BatchTriggerDefinition)getSqlSessionTemplate().selectOne("BatchTriggerDefinition.selectOne1L", map);
		if (batchTriggerDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_trigge_rdefinition", CommonUtils.toString(map));
		return batchTriggerDefinition;
	}

	/**
	 * 单笔删除
	 * @param triggerId 触发器ID
	 */
	@Override
	public int deleteOne1(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().delete("BatchTriggerDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	@Override
	public int deleteOne1L(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().delete("BatchTriggerDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public int updateOne1(BatchTriggerDefinition batchTriggerDefinition){
		return getSqlSessionTemplate().update("BatchTriggerDefinition.updateOne1", batchTriggerDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public int updateOne1R(BatchTriggerDefinition batchTriggerDefinition){
		return getSqlSessionTemplate().update("BatchTriggerDefinition.updateOne1R", batchTriggerDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public int updateOne1L(BatchTriggerDefinition batchTriggerDefinition){
		return getSqlSessionTemplate().update("BatchTriggerDefinition.updateOne1L", batchTriggerDefinition);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTriggerDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTriggerDefinition.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public void batchUpdate(List<BatchTriggerDefinition> list){
		batchExcutor("BatchTriggerDefinition.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTriggerDefinition 
	 */
	@Override
	public void batchDelete(List<BatchTriggerDefinition> list){
		batchExcutor("BatchTriggerDefinition.deleteOne1", list, "delete", 20);
	}

}