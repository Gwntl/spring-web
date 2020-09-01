package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchJobExecute;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchJobExecuteDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchJobExecuteDaoImpl.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:19
 * @version v1.0
*/
@Repository
public class BatchJobExecuteDaoImpl extends BaseDaoSupport implements BatchJobExecuteDao {
	/**
	 * 单笔插入
	 * @param batchJobExecute 
	 */
	@Override
	public int insertOne(BatchJobExecute batchJobExecute){
		return getSqlSessionTemplate().insert("BatchJobExecute.insertOne", batchJobExecute);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchInsert(List<BatchJobExecute> list){
		batchExcutor("BatchJobExecute.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchInsertXML(List<BatchJobExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchJobExecute.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	@Override
	public BatchJobExecute selectOne1(String executeJobId, String executeStepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		map.put("executeStepId", executeStepId);
		BatchJobExecute batchJobExecute = new BatchJobExecute();
		batchJobExecute = (BatchJobExecute)getSqlSessionTemplate().selectOne("BatchJobExecute.selectOne1", map);
		if (batchJobExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bexecute", CommonUtils.toString(map));
		return batchJobExecute;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	@Override
	public BatchJobExecute selectOne1R(String executeJobId, String executeStepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		map.put("executeStepId", executeStepId);
		BatchJobExecute batchJobExecute = new BatchJobExecute();
		batchJobExecute = (BatchJobExecute)getSqlSessionTemplate().selectOne("BatchJobExecute.selectOne1R", map);
		if (batchJobExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bexecute", CommonUtils.toString(map));
		return batchJobExecute;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	@Override
	public BatchJobExecute selectOne1L(String executeJobId, String executeStepId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		map.put("executeStepId", executeStepId);
		BatchJobExecute batchJobExecute = new BatchJobExecute();
		batchJobExecute = (BatchJobExecute)getSqlSessionTemplate().selectOne("BatchJobExecute.selectOne1L", map);
		if (batchJobExecute == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_jo_bexecute", CommonUtils.toString(map));
		return batchJobExecute;
	}

	/**
	 * 单笔删除
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	@Override
	public int deleteOne1(String executeJobId, String executeStepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		map.put("executeStepId", executeStepId);
		return getSqlSessionTemplate().delete("BatchJobExecute.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 * @param executeStepId 关联步骤ID
	 */
	@Override
	public int deleteOne1L(String executeJobId, String executeStepId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		map.put("executeStepId", executeStepId);
		return getSqlSessionTemplate().delete("BatchJobExecute.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param batchJobExecute 
	 */
	@Override
	public int updateOne1(BatchJobExecute batchJobExecute){
		return getSqlSessionTemplate().update("BatchJobExecute.updateOne1", batchJobExecute);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param batchJobExecute 
	 */
	@Override
	public int updateOne1R(BatchJobExecute batchJobExecute){
		return getSqlSessionTemplate().update("BatchJobExecute.updateOne1R", batchJobExecute);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param batchJobExecute 
	 */
	@Override
	public int updateOne1L(BatchJobExecute batchJobExecute){
		return getSqlSessionTemplate().update("BatchJobExecute.updateOne1L", batchJobExecute);
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchUpdate1(List<BatchJobExecute> list){
		batchExcutor("BatchJobExecute.batchUpdate1", list, "update", 20);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param list 
	 */
	@Override
	public void batchUpdateXML(List<BatchJobExecute> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchJobExecute.batchUpdateXML", map);
			};
		});
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param list 
	 */
	@Override
	public void batchDelete(List<BatchJobExecute> list){
		batchExcutor("BatchJobExecute.deleteOne1", list, "delete", 20);
	}

	/**
	 * 查询多笔数据
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1(String executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1", map);
	}

	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1R(String executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1R", map);
	}

	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 */
	@Override
	public List<BatchJobExecute> selectAll1L(String executeJobId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("executeJobId", executeJobId);
		return getSqlSessionTemplate().selectList("BatchJobExecute.selectAll1L", map);
	}

}