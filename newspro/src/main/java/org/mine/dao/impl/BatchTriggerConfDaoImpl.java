package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTriggerConf;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchTriggerConfDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTriggerConfDaoImpl.java
 * @author wzaUsers
 * @date 2019-11-14 19:11:57
 * @version v1.0
*/
@Repository
public class BatchTriggerConfDaoImpl extends BaseDaoSupport implements BatchTriggerConfDao {
	/**
	 * 单笔插入
	 * @param BatchTriggerConf 
	 */
	@Override
	public int insertOne(BatchTriggerConf batchTriggerConf){
		return getSqlSessionTemplate().insert("BatchTriggerConf.insertOne", batchTriggerConf);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTriggerConf 
	 */
	@Override
	public void batchInsert(List<BatchTriggerConf> list){
		batchExcutor("BatchTriggerConf.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTriggerConf 
	 */
	@Override
	public void batchInsertXML(List<BatchTriggerConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public void call(Map<String, Object> map) {
				getSqlSessionTemplate().insert("BatchTriggerConf.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerConf selectOne1(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().selectOne("BatchTriggerConf.selectOne1", map);
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerConf selectOne1R(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().selectOne("BatchTriggerConf.selectOne1R", map);
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	@Override
	public BatchTriggerConf selectOne1L(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().selectOne("BatchTriggerConf.selectOne1L", map);
	}

	/**
	 * 单笔删除
	 * @param triggerId 触发器ID
	 */
	@Override
	public int deleteOne1(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().delete("BatchTriggerConf.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerId 触发器ID
	 */
	@Override
	public int deleteOne1L(Long triggerId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerId", triggerId);
		return getSqlSessionTemplate().delete("BatchTriggerConf.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTriggerConf 
	 */
	@Override
	public int updateOne1(BatchTriggerConf batchTriggerConf){
		return getSqlSessionTemplate().update("BatchTriggerConf.updateOne1", batchTriggerConf);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTriggerConf 
	 */
	@Override
	public int updateOne1R(BatchTriggerConf batchTriggerConf){
		return getSqlSessionTemplate().update("BatchTriggerConf.updateOne1R", batchTriggerConf);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTriggerConf 
	 */
	@Override
	public int updateOne1L(BatchTriggerConf batchTriggerConf){
		return getSqlSessionTemplate().update("BatchTriggerConf.updateOne1L", batchTriggerConf);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTriggerConf 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTriggerConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public void call(Map<String, Object> map) {
				getSqlSessionTemplate().update("BatchTriggerConf.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTriggerConf 
	 */
	@Override
	public void batchUpdate(List<BatchTriggerConf> list){
		batchExcutor("BatchTriggerConf.batchUpdate", list, "update", 20);
	}

	/**
	 * 查询多笔数据
	 * @param triggerJobGroupId JOB作业组ID
	 */
	@Override
	public List<BatchTriggerConf> selectAll1(Long triggerJobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerJobGroupId", triggerJobGroupId);
		return getSqlSessionTemplate().selectList("BatchTriggerConf.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param triggerJobGroupId JOB作业组ID
	 */
	@Override
	public List<BatchTriggerConf> selectAll1R(Long triggerJobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerJobGroupId", triggerJobGroupId);
		return getSqlSessionTemplate().selectList("BatchTriggerConf.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param triggerJobGroupId JOB作业组ID
	 */
	@Override
	public List<BatchTriggerConf> selectAll1L(Long triggerJobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("triggerJobGroupId", triggerJobGroupId);
		return getSqlSessionTemplate().selectList("BatchTriggerConf.selectAll1L", map);
	}

}