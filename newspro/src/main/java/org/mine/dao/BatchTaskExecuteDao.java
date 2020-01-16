package org.mine.dao;

import java.util.List;
import org.mine.model.BatchTaskExecute;

/**
 * 
 * @filename BatchTaskExecuteDao.java
 * @author wzaUsers
 * @date 2020-01-09 11:01:11
 * @version v1.0
*/

public interface BatchTaskExecuteDao {
	/**
	 * 单笔插入
	 * @param BatchTaskExecute 
	 */
	int insertOne(BatchTaskExecute batchTaskExecute);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param BatchTaskExecute 
	 */
	void batchInsert(List<BatchTaskExecute> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param BatchTaskExecute 
	 */
	void batchInsertXML(List<BatchTaskExecute> list);
	/**
	 * 查询多笔数据
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1(Long executeTaskId);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1R(Long executeTaskId);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param executeTaskId 执行任务ID
	 */
	List<BatchTaskExecute> selectAll1L(Long executeTaskId);
}