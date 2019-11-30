package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobDetailConf;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchJobDetailConfDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobDetailConfDaoImpl.java
 * @author wzaUsers
 * @date 2019-11-29 16:11:16
 * @version v1.0
*/
@Repository
public class BatchJobDetailConfDaoImpl extends BaseDaoSupport implements BatchJobDetailConfDao {
	/**
	 * 单笔插入
	 * @param BatchJobDetailConf 
	 */
	@Override
	public int insertOne(BatchJobDetailConf batchJobDetailConf){
		return getSqlSessionTemplate().insert("BatchJobDetailConf.insertOne", batchJobDetailConf);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public void batchInsert(List<BatchJobDetailConf> list){
		batchExcutor("BatchJobDetailConf.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public void batchInsertXML(List<BatchJobDetailConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				getSqlSessionTemplate().insert("BatchJobDetailConf.batchInsertXML", map);
			return null;
			};
		});
	}

	/**
	 * 单笔查询
	 * @param jobdetailId JOB任务作业ID
	 */
	@Override
	public BatchJobDetailConf selectOne1(Long jobdetailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailId", jobdetailId);
		return getSqlSessionTemplate().selectOne("BatchJobDetailConf.selectOne1", map);
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobdetailId JOB任务作业ID
	 */
	@Override
	public BatchJobDetailConf selectOne1R(Long jobdetailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailId", jobdetailId);
		return getSqlSessionTemplate().selectOne("BatchJobDetailConf.selectOne1R", map);
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailId JOB任务作业ID
	 */
	@Override
	public BatchJobDetailConf selectOne1L(Long jobdetailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailId", jobdetailId);
		return getSqlSessionTemplate().selectOne("BatchJobDetailConf.selectOne1L", map);
	}

	/**
	 * 单笔删除
	 * @param jobdetailId JOB任务作业ID
	 */
	@Override
	public int deleteOne1(Long jobdetailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailId", jobdetailId);
		return getSqlSessionTemplate().delete("BatchJobDetailConf.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailId JOB任务作业ID
	 */
	@Override
	public int deleteOne1L(Long jobdetailId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailId", jobdetailId);
		return getSqlSessionTemplate().delete("BatchJobDetailConf.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchJobDetailConf 
	 */
	@Override
	public int updateOne1(BatchJobDetailConf batchJobDetailConf){
		return getSqlSessionTemplate().update("BatchJobDetailConf.updateOne1", batchJobDetailConf);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public int updateOne1R(BatchJobDetailConf batchJobDetailConf){
		return getSqlSessionTemplate().update("BatchJobDetailConf.updateOne1R", batchJobDetailConf);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public int updateOne1L(BatchJobDetailConf batchJobDetailConf){
		return getSqlSessionTemplate().update("BatchJobDetailConf.updateOne1L", batchJobDetailConf);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public void batchUpdateXML1(List<BatchJobDetailConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				getSqlSessionTemplate().update("BatchJobDetailConf.batchUpdateXML1", map);
			return null;
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public void batchUpdate(List<BatchJobDetailConf> list){
		batchExcutor("BatchJobDetailConf.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchJobDetailConf 
	 */
	@Override
	public void batchDelete(List<BatchJobDetailConf> list){
		batchExcutor("BatchJobDetailConf.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param jobdetailGroupId JOB作业组ID
	 */
	@Override
	public List<BatchJobDetailConf> selectAll1(Long jobdetailGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailGroupId", jobdetailGroupId);
		return getSqlSessionTemplate().selectList("BatchJobDetailConf.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param jobdetailGroupId JOB作业组ID
	 */
	@Override
	public List<BatchJobDetailConf> selectAll1R(Long jobdetailGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailGroupId", jobdetailGroupId);
		return getSqlSessionTemplate().selectList("BatchJobDetailConf.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobdetailGroupId JOB作业组ID
	 */
	@Override
	public List<BatchJobDetailConf> selectAll1L(Long jobdetailGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobdetailGroupId", jobdetailGroupId);
		return getSqlSessionTemplate().selectList("BatchJobDetailConf.selectAll1L", map);
	}

}