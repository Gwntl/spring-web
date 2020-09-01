package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskExecute;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskExecuteDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-26 10:08:58
 * @version v1.0
*/
@Repository
public class BatchTaskExecuteDaoImpl extends BaseDaoSupport implements BatchTaskExecuteDao {
	/**
	 * 单笔插入
	 * @param batchTaskExecute 
	 */
	@Override
	public int insertOne(BatchTaskExecute batchTaskExecute){
		return getSqlSessionTemplate().insert("BatchTaskExecute.insertOne", batchTaskExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchTaskExecute> list){
		batchExcutor("BatchTaskExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskExecute.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public BatchTaskExecute selectOne1(String executeTaskId, String executeJobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		map.put("executeJobId", executeJobId);
		BatchTaskExecute batchTaskExecute = new BatchTaskExecute();
		batchTaskExecute = (BatchTaskExecute)getSqlSessionTemplate().selectOne("BatchTaskExecute.selectOne1", map);
		if (batchTaskExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecute", CommonUtils.toString(map));
		return batchTaskExecute;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public BatchTaskExecute selectOne1R(String executeTaskId, String executeJobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		map.put("executeJobId", executeJobId);
		BatchTaskExecute batchTaskExecute = new BatchTaskExecute();
		batchTaskExecute = (BatchTaskExecute)getSqlSessionTemplate().selectOne("BatchTaskExecute.selectOne1R", map);
		if (batchTaskExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecute", CommonUtils.toString(map));
		return batchTaskExecute;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public BatchTaskExecute selectOne1L(String executeTaskId, String executeJobId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		map.put("executeJobId", executeJobId);
		BatchTaskExecute batchTaskExecute = new BatchTaskExecute();
		batchTaskExecute = (BatchTaskExecute)getSqlSessionTemplate().selectOne("BatchTaskExecute.selectOne1L", map);
		if (batchTaskExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecute", CommonUtils.toString(map));
		return batchTaskExecute;
	}

	/**
	 * 单笔删除
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public int deleteOne1(String executeTaskId, String executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().delete("BatchTaskExecute.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public int deleteOne1L(String executeTaskId, String executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().delete("BatchTaskExecute.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchTaskExecute 
	 */
	@Override
	public int updateOne1(BatchTaskExecute batchTaskExecute){
		return getSqlSessionTemplate().update("BatchTaskExecute.updateOne1", batchTaskExecute);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchTaskExecute 
	 */
	@Override
	public int updateOne1R(BatchTaskExecute batchTaskExecute){
		return getSqlSessionTemplate().update("BatchTaskExecute.updateOne1R", batchTaskExecute);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchTaskExecute 
	 */
	@Override
	public int updateOne1L(BatchTaskExecute batchTaskExecute){
		return getSqlSessionTemplate().update("BatchTaskExecute.updateOne1L", batchTaskExecute);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchTaskExecute> list){
		batchExcutor("BatchTaskExecute.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchTaskExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskExecute.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchTaskExecute> list){
		batchExcutor("BatchTaskExecute.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1(String executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1R(String executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 */
	@Override
	public List<BatchTaskExecute> selectAll1L(String executeTaskId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeTaskId", executeTaskId);
		return getSqlSessionTemplate().selectList("BatchTaskExecute.selectAll1L", map);
	}

}