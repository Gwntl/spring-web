package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchGroupDefinition;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchGroupDefinitionDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchGroupDefinitionDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:24
 * @version v1.0
*/
@Repository
public class BatchGroupDefinitionDaoImpl extends BaseDaoSupport implements BatchGroupDefinitionDao {
	/**
	 * 单笔插入
	 * @param BatchGroupDefinition 
	 */
	@Override
	public int insertOne(BatchGroupDefinition batchGroupDefinition){
		return getSqlSessionTemplate().insert("BatchGroupDefinition.insertOne", batchGroupDefinition);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public void batchInsert(List<BatchGroupDefinition> list){
		batchExcutor("BatchGroupDefinition.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public void batchInsertXML(List<BatchGroupDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchGroupDefinition.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param groupId 任务执行组ID
	 */
	@Override
	public BatchGroupDefinition selectOne1(Long groupId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		BatchGroupDefinition batchGroupDefinition = new BatchGroupDefinition();
		batchGroupDefinition = (BatchGroupDefinition)getSqlSessionTemplate().selectOne("BatchGroupDefinition.selectOne1", map);
		if (batchGroupDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_grou_pdefinition", CommonUtils.toString(map));
		return batchGroupDefinition;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param groupId 任务执行组ID
	 */
	@Override
	public BatchGroupDefinition selectOne1R(Long groupId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		BatchGroupDefinition batchGroupDefinition = new BatchGroupDefinition();
		batchGroupDefinition = (BatchGroupDefinition)getSqlSessionTemplate().selectOne("BatchGroupDefinition.selectOne1R", map);
		if (batchGroupDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_grou_pdefinition", CommonUtils.toString(map));
		return batchGroupDefinition;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupId 任务执行组ID
	 */
	@Override
	public BatchGroupDefinition selectOne1L(Long groupId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		BatchGroupDefinition batchGroupDefinition = new BatchGroupDefinition();
		batchGroupDefinition = (BatchGroupDefinition)getSqlSessionTemplate().selectOne("BatchGroupDefinition.selectOne1L", map);
		if (batchGroupDefinition == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_grou_pdefinition", CommonUtils.toString(map));
		return batchGroupDefinition;
	}

	/**
	 * 单笔删除
	 * @param groupId 任务执行组ID
	 */
	@Override
	public int deleteOne1(Long groupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return getSqlSessionTemplate().delete("BatchGroupDefinition.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupId 任务执行组ID
	 */
	@Override
	public int deleteOne1L(Long groupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		return getSqlSessionTemplate().delete("BatchGroupDefinition.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchGroupDefinition 
	 */
	@Override
	public int updateOne1(BatchGroupDefinition batchGroupDefinition){
		return getSqlSessionTemplate().update("BatchGroupDefinition.updateOne1", batchGroupDefinition);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public int updateOne1R(BatchGroupDefinition batchGroupDefinition){
		return getSqlSessionTemplate().update("BatchGroupDefinition.updateOne1R", batchGroupDefinition);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public int updateOne1L(BatchGroupDefinition batchGroupDefinition){
		return getSqlSessionTemplate().update("BatchGroupDefinition.updateOne1L", batchGroupDefinition);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public void batchUpdateXML1(List<BatchGroupDefinition> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchGroupDefinition.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public void batchUpdate(List<BatchGroupDefinition> list){
		batchExcutor("BatchGroupDefinition.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchGroupDefinition 
	 */
	@Override
	public void batchDelete(List<BatchGroupDefinition> list){
		batchExcutor("BatchGroupDefinition.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	@Override
	public List<BatchGroupDefinition> selectAll1(Long groupAssociateQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupAssociateQueueId", groupAssociateQueueId);
		return getSqlSessionTemplate().selectList("BatchGroupDefinition.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	@Override
	public List<BatchGroupDefinition> selectAll1R(Long groupAssociateQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupAssociateQueueId", groupAssociateQueueId);
		return getSqlSessionTemplate().selectList("BatchGroupDefinition.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param groupAssociateQueueId 任务组关联队列ID
	 */
	@Override
	public List<BatchGroupDefinition> selectAll1L(Long groupAssociateQueueId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupAssociateQueueId", groupAssociateQueueId);
		return getSqlSessionTemplate().selectList("BatchGroupDefinition.selectAll1L", map);
	}

}