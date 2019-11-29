package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobGroupConf;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchJobGroupConfDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobGroupConfDaoImpl.java
 * @author wzaUsers
 * @date 2019-11-26 15:11:20
 * @version v1.0
*/
@Repository
public class BatchJobGroupConfDaoImpl extends BaseDaoSupport implements BatchJobGroupConfDao {
	/**
	 * 单笔插入
	 * @param BatchJobGroupConf 
	 */
	@Override
	public int insertOne(BatchJobGroupConf batchJobGroupConf){
		return getSqlSessionTemplate().insert("BatchJobGroupConf.insertOne", batchJobGroupConf);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public void batchInsert(List<BatchJobGroupConf> list){
		batchExcutor("BatchJobGroupConf.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public void batchInsertXML(List<BatchJobGroupConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				getSqlSessionTemplate().insert("BatchJobGroupConf.batchInsertXML", map);
				return null;
			};
		});
	}

	/**
	 * 单笔查询
	 * @param jobGroupId JOB作业组ID
	 */
	@Override
	public BatchJobGroupConf selectOne1(Long jobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobGroupId", jobGroupId);
		return getSqlSessionTemplate().selectOne("BatchJobGroupConf.selectOne1", map);
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param jobGroupId JOB作业组ID
	 */
	@Override
	public BatchJobGroupConf selectOne1R(Long jobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobGroupId", jobGroupId);
		return getSqlSessionTemplate().selectOne("BatchJobGroupConf.selectOne1R", map);
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobGroupId JOB作业组ID
	 */
	@Override
	public BatchJobGroupConf selectOne1L(Long jobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobGroupId", jobGroupId);
		return getSqlSessionTemplate().selectOne("BatchJobGroupConf.selectOne1L", map);
	}

	/**
	 * 单笔删除
	 * @param jobGroupId JOB作业组ID
	 */
	@Override
	public int deleteOne1(Long jobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobGroupId", jobGroupId);
		return getSqlSessionTemplate().delete("BatchJobGroupConf.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobGroupId JOB作业组ID
	 */
	@Override
	public int deleteOne1L(Long jobGroupId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobGroupId", jobGroupId);
		return getSqlSessionTemplate().delete("BatchJobGroupConf.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchJobGroupConf 
	 */
	@Override
	public int updateOne1(BatchJobGroupConf batchJobGroupConf){
		return getSqlSessionTemplate().update("BatchJobGroupConf.updateOne1", batchJobGroupConf);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public int updateOne1R(BatchJobGroupConf batchJobGroupConf){
		return getSqlSessionTemplate().update("BatchJobGroupConf.updateOne1R", batchJobGroupConf);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public int updateOne1L(BatchJobGroupConf batchJobGroupConf){
		return getSqlSessionTemplate().update("BatchJobGroupConf.updateOne1L", batchJobGroupConf);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public void batchUpdateXML1(List<BatchJobGroupConf> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				getSqlSessionTemplate().update("BatchJobGroupConf.batchUpdateXML1", map);
				return null;
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public void batchUpdate(List<BatchJobGroupConf> list){
		batchExcutor("BatchJobGroupConf.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchJobGroupConf 
	 */
	@Override
	public void batchDelete(List<BatchJobGroupConf> list){
		batchExcutor("BatchJobGroupConf.deleteOne1", list, "delete", 20);
	}

}