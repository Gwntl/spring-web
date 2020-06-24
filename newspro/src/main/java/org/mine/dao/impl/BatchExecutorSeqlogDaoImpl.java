package org.mine.dao.impl;

import java.util.List;
import org.mine.model.BatchExecutorSeqlog;
import java.util.Map;
import java.util.HashMap;
import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.dao.BaseDaoSupport;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchExecutorSeqlogDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @filename BatchExecutorSeqlogDaoImpl.java
 * @author wzaUsers
 * @date 2020-06-24 09:06:45
 * @version v1.0
*/
@Repository
public class BatchExecutorSeqlogDaoImpl extends BaseDaoSupport implements BatchExecutorSeqlogDao {
	/**
	 * 单笔插入
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public int insertOne(BatchExecutorSeqlog batchExecutorSeqlog){
		return getSqlSessionTemplate().insert("BatchExecutorSeqlog.insertOne", batchExecutorSeqlog);
	}

	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public void batchInsert(List<BatchExecutorSeqlog> list){
		batchExcutor("BatchExecutorSeqlog.batchInsert", list, "insert", 20);
	}

	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public void batchInsertXML(List<BatchExecutorSeqlog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().insert("BatchExecutorSeqlog.batchInsertXML", map);
			};
		});
	}

	/**
	 * 单笔查询
	 * @param seqId 序号ID
	 */
	@Override
	public BatchExecutorSeqlog selectOne1(Long seqId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		BatchExecutorSeqlog batchExecutorSeqlog = new BatchExecutorSeqlog();
		batchExecutorSeqlog = (BatchExecutorSeqlog)getSqlSessionTemplate().selectOne("BatchExecutorSeqlog.selectOne1", map);
		if (batchExecutorSeqlog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rseqlog", CommonUtils.toString(map));
		return batchExecutorSeqlog;
	}

	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param seqId 序号ID
	 */
	@Override
	public BatchExecutorSeqlog selectOne1R(Long seqId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		BatchExecutorSeqlog batchExecutorSeqlog = new BatchExecutorSeqlog();
		batchExecutorSeqlog = (BatchExecutorSeqlog)getSqlSessionTemplate().selectOne("BatchExecutorSeqlog.selectOne1R", map);
		if (batchExecutorSeqlog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rseqlog", CommonUtils.toString(map));
		return batchExecutorSeqlog;
	}

	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param seqId 序号ID
	 */
	@Override
	public BatchExecutorSeqlog selectOne1L(Long seqId, boolean nullException){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		BatchExecutorSeqlog batchExecutorSeqlog = new BatchExecutorSeqlog();
		batchExecutorSeqlog = (BatchExecutorSeqlog)getSqlSessionTemplate().selectOne("BatchExecutorSeqlog.selectOne1L", map);
		if (batchExecutorSeqlog == null && nullException)throw GitWebException.GIT_NOTFOUNT("batch_executo_rseqlog", CommonUtils.toString(map));
		return batchExecutorSeqlog;
	}

	/**
	 * 单笔删除
	 * @param seqId 序号ID
	 */
	@Override
	public int deleteOne1(Long seqId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		return getSqlSessionTemplate().delete("BatchExecutorSeqlog.deleteOne1", map);
	}

	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param seqId 序号ID
	 */
	@Override
	public int deleteOne1L(Long seqId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		return getSqlSessionTemplate().delete("BatchExecutorSeqlog.deleteOne1L", map);
	}

	/**
	 * 单笔更新
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public int updateOne1(BatchExecutorSeqlog batchExecutorSeqlog){
		return getSqlSessionTemplate().update("BatchExecutorSeqlog.updateOne1", batchExecutorSeqlog);
	}

	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public int updateOne1R(BatchExecutorSeqlog batchExecutorSeqlog){
		return getSqlSessionTemplate().update("BatchExecutorSeqlog.updateOne1R", batchExecutorSeqlog);
	}

	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public int updateOne1L(BatchExecutorSeqlog batchExecutorSeqlog){
		return getSqlSessionTemplate().update("BatchExecutorSeqlog.updateOne1L", batchExecutorSeqlog);
	}

	/**
	 * 批量更新(使用XML中的foreach语句)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public void batchUpdateXML1(List<BatchExecutorSeqlog> list){
		BatchInsertByXML(list, "list", 20, new BatchOperator<Integer, Map<String, Object>>() {
			@Override
			public Integer call(Map<String, Object> map) {
				return getSqlSessionTemplate().update("BatchExecutorSeqlog.batchUpdateXML1", map);
			};
		});
	}

	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public void batchUpdate(List<BatchExecutorSeqlog> list){
		batchExcutor("BatchExecutorSeqlog.batchUpdate", list, "update", 20);
	}

	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param BatchExecutorSeqlog 
	 */
	@Override
	public void batchDelete(List<BatchExecutorSeqlog> list){
		batchExcutor("BatchExecutorSeqlog.deleteOne1", list, "delete", 20);
	}

}