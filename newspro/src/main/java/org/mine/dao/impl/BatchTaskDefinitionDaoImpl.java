package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:24
 * @version v1.0
*/
@Repository
public class BatchTaskDefinitionDaoImpl extends BaseDaoSupport implements BatchTaskDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchTaskDefinition 
	 */
	@Override
	public int insertOne(BatchTaskDefinition batchTaskDefinition){
		return getSqlSessionTemplate().insert("BatchTaskDefinition.insertOne", batchTaskDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public void batchInsert(List<BatchTaskDefinition> list){
		batchExcutor("BatchTaskDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param taskId 任务ID
	 */
	@Override
	public BatchTaskDefinition selectOne1(Long taskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		BatchTaskDefinition batchTaskDefinition = new BatchTaskDefinition();
		batchTaskDefinition = (BatchTaskDefinition)getSqlSessionTemplate().selectOne("BatchTaskDefinition.selectOne1", map);
		if (batchTaskDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kdefinition", CommonUtils.toString(map));
		return batchTaskDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param taskId 任务ID
	 */
	@Override
	public BatchTaskDefinition selectOne1R(Long taskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		BatchTaskDefinition batchTaskDefinition = new BatchTaskDefinition();
		batchTaskDefinition = (BatchTaskDefinition)getSqlSessionTemplate().selectOne("BatchTaskDefinition.selectOne1R", map);
		if (batchTaskDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kdefinition", CommonUtils.toString(map));
		return batchTaskDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	@Override
	public BatchTaskDefinition selectOne1L(Long taskId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		BatchTaskDefinition batchTaskDefinition = new BatchTaskDefinition();
		batchTaskDefinition = (BatchTaskDefinition)getSqlSessionTemplate().selectOne("BatchTaskDefinition.selectOne1L", map);
		if (batchTaskDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kdefinition", CommonUtils.toString(map));
		return batchTaskDefinition;
	}

	/**
	 * 单笔删除
	 * @param taskId 任务ID
	 */
	@Override
	public int deleteOne1(Long taskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return getSqlSessionTemplate().delete("BatchTaskDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskId 任务ID
	 */
	@Override
	public int deleteOne1L(Long taskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return getSqlSessionTemplate().delete("BatchTaskDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTaskDefinition 
	 */
	@Override
	public int updateOne1(BatchTaskDefinition batchTaskDefinition){
		return getSqlSessionTemplate().update("BatchTaskDefinition.updateOne1", batchTaskDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public int updateOne1R(BatchTaskDefinition batchTaskDefinition){
		return getSqlSessionTemplate().update("BatchTaskDefinition.updateOne1R", batchTaskDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public int updateOne1L(BatchTaskDefinition batchTaskDefinition){
		return getSqlSessionTemplate().update("BatchTaskDefinition.updateOne1L", batchTaskDefinition);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTaskDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskDefinition.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public void batchUpdate(List<BatchTaskDefinition> list){
		batchExcutor("BatchTaskDefinition.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskDefinition 
	 */
	@Override
	public void batchDelete(List<BatchTaskDefinition> list){
		batchExcutor("BatchTaskDefinition.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param taskAssociateGroupId 关联任务组ID
	 */
	@Override
	public List<BatchTaskDefinition> selectAll1(Long taskAssociateGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskAssociateGroupId", taskAssociateGroupId);
		return getSqlSessionTemplate().selectList("BatchTaskDefinition.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param taskAssociateGroupId 关联任务组ID
	 */
	@Override
	public List<BatchTaskDefinition> selectAll1R(Long taskAssociateGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskAssociateGroupId", taskAssociateGroupId);
		return getSqlSessionTemplate().selectList("BatchTaskDefinition.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskAssociateGroupId 关联任务组ID
	 */
	@Override
	public List<BatchTaskDefinition> selectAll1L(Long taskAssociateGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskAssociateGroupId", taskAssociateGroupId);
		return getSqlSessionTemplate().selectList("BatchTaskDefinition.selectAll1L", map);
	}

}