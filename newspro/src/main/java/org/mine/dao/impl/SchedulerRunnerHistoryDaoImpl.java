package org.mine.dao.impl;

import java.util.List;
import org.mine.model.SchedulerRunnerHistory;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.SchedulerRunnerHistoryDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename SchedulerRunnerHistoryDaoImpl.java
 * @author wzaUsers
 * @date 2019-11-28 20:11:23
 * @version v1.0
*/
@Repository
public class SchedulerRunnerHistoryDaoImpl extends BaseDaoSupport implements SchedulerRunnerHistoryDao {
	/**
	 * 单笔插入
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public int insertOne(SchedulerRunnerHistory schedulerRunnerHistory){
		return getSqlSessionTemplate().insert("SchedulerRunnerHistory.insertOne", schedulerRunnerHistory);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public void batchInsert(List<SchedulerRunnerHistory> list){
		batchExcutor("SchedulerRunnerHistory.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public void batchInsertXML(List<SchedulerRunnerHistory> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				getSqlSessionTemplate().insert("SchedulerRunnerHistory.batchInsertXML", map);
				return null;
			};
		});
	}

	/**
	 * 单笔查询
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	@Override
	public SchedulerRunnerHistory selectOne1(Long runnerId, Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("runnerId", runnerId);
		map.put("jobId", jobId);
		return getSqlSessionTemplate().selectOne("SchedulerRunnerHistory.selectOne1", map);
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	@Override
	public SchedulerRunnerHistory selectOne1R(Long runnerId, Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("runnerId", runnerId);
		map.put("jobId", jobId);
		return getSqlSessionTemplate().selectOne("SchedulerRunnerHistory.selectOne1R", map);
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	@Override
	public SchedulerRunnerHistory selectOne1L(Long runnerId, Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("runnerId", runnerId);
		map.put("jobId", jobId);
		return getSqlSessionTemplate().selectOne("SchedulerRunnerHistory.selectOne1L", map);
	}

	/**
	 * 单笔删除
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	@Override
	public int deleteOne1(Long runnerId, Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("runnerId", runnerId);
		map.put("jobId", jobId);
		return getSqlSessionTemplate().delete("SchedulerRunnerHistory.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	@Override
	public int deleteOne1L(Long runnerId, Long jobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("runnerId", runnerId);
		map.put("jobId", jobId);
		return getSqlSessionTemplate().delete("SchedulerRunnerHistory.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public int updateOne1(SchedulerRunnerHistory schedulerRunnerHistory){
		return getSqlSessionTemplate().update("SchedulerRunnerHistory.updateOne1", schedulerRunnerHistory);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public int updateOne1R(SchedulerRunnerHistory schedulerRunnerHistory){
		return getSqlSessionTemplate().update("SchedulerRunnerHistory.updateOne1R", schedulerRunnerHistory);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public int updateOne1L(SchedulerRunnerHistory schedulerRunnerHistory){
		return getSqlSessionTemplate().update("SchedulerRunnerHistory.updateOne1L", schedulerRunnerHistory);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public void batchUpdate(List<SchedulerRunnerHistory> list){
		batchExcutor("SchedulerRunnerHistory.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	@Override
	public void batchDelete(List<SchedulerRunnerHistory> list){
		batchExcutor("SchedulerRunnerHistory.deleteOne1", list, "delete", 20);
	}

}