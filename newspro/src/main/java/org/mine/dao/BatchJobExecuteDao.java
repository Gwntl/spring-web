package org.mine.dao;

import java.util.List;
import org.mine.model.BatchJobExecute;

/**
 * 
 * @filename BatchJobExecuteDao.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:15
 * @version v1.0
*/

public interface BatchJobExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchJobExecute 
	 */
	int insertOne(BatchJobExecute batchJobExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchJobExecute 
	 */
	void batchInsert(List<BatchJobExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchJobExecute 
	 */
	void batchInsertXML(List<BatchJobExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1(Long executeJobId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1R(Long executeJobId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeJobId 执行作业ID
	 */
	List<BatchJobExecute> selectAll1L(Long executeJobId);
}