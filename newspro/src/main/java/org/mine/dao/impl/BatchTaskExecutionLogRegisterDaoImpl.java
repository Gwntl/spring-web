package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchTaskExecutionLogRegister;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTaskExecutionLogRegisterDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchTaskExecutionLogRegisterDaoImpl.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/
@Repository
public class BatchTaskExecutionLogRegisterDaoImpl extends BaseDaoSupport implements BatchTaskExecutionLogRegisterDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public int insertOne(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister){
		return getSqlSessionTemplate().insert("BatchTaskExecutionLogRegister.insertOne", batchTaskExecutionLogRegister);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public void batchInsert(List<BatchTaskExecutionLogRegister> list){
		batchExcutor("BatchTaskExecutionLogRegister.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public void batchInsertXML(List<BatchTaskExecutionLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchTaskExecutionLogRegister.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param taskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogRegister selectOne1(Long taskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskExecutionId", taskExecutionId);
		BatchTaskExecutionLogRegister batchTaskExecutionLogRegister = new BatchTaskExecutionLogRegister();
		batchTaskExecutionLogRegister = (BatchTaskExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogRegister.selectOne1", map);
		if (batchTaskExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_logregister", CommonUtils.toString(map));
		return batchTaskExecutionLogRegister;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param taskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogRegister selectOne1R(Long taskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskExecutionId", taskExecutionId);
		BatchTaskExecutionLogRegister batchTaskExecutionLogRegister = new BatchTaskExecutionLogRegister();
		batchTaskExecutionLogRegister = (BatchTaskExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogRegister.selectOne1R", map);
		if (batchTaskExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_logregister", CommonUtils.toString(map));
		return batchTaskExecutionLogRegister;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskExecutionId 任务执行ID
	 */
	@Override
	public BatchTaskExecutionLogRegister selectOne1L(Long taskExecutionId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskExecutionId", taskExecutionId);
		BatchTaskExecutionLogRegister batchTaskExecutionLogRegister = new BatchTaskExecutionLogRegister();
		batchTaskExecutionLogRegister = (BatchTaskExecutionLogRegister)getSqlSessionTemplate().selectOne("BatchTaskExecutionLogRegister.selectOne1L", map);
		if (batchTaskExecutionLogRegister == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_tas_kexecuti_on_logregister", CommonUtils.toString(map));
		return batchTaskExecutionLogRegister;
	}

	/**
	 * 单笔删除
	 * @param taskExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1(Long taskExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskExecutionId", taskExecutionId);
		return getSqlSessionTemplate().delete("BatchTaskExecutionLogRegister.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param taskExecutionId 任务执行ID
	 */
	@Override
	public int deleteOne1L(Long taskExecutionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskExecutionId", taskExecutionId);
		return getSqlSessionTemplate().delete("BatchTaskExecutionLogRegister.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public int updateOne1(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogRegister.updateOne1", batchTaskExecutionLogRegister);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public int updateOne1R(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogRegister.updateOne1R", batchTaskExecutionLogRegister);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public int updateOne1L(BatchTaskExecutionLogRegister batchTaskExecutionLogRegister){
		return getSqlSessionTemplate().update("BatchTaskExecutionLogRegister.updateOne1L", batchTaskExecutionLogRegister);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public void batchUpdateXML1(List<BatchTaskExecutionLogRegister> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchTaskExecutionLogRegister.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public void batchUpdate(List<BatchTaskExecutionLogRegister> list){
		batchExcutor("BatchTaskExecutionLogRegister.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchTaskExecutionLogRegister 
	 */
	@Override
	public void batchDelete(List<BatchTaskExecutionLogRegister> list){
		batchExcutor("BatchTaskExecutionLogRegister.deleteOne1", list, "delete", 20);
	}

}