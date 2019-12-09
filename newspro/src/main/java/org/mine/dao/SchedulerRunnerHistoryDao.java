package org.mine.dao;

import java.util.List;
import org.mine.model.SchedulerRunnerHistory;

/**
 * 
 * @filename SchedulerRunnerHistoryDao.java
 * @author wzaUsers
 * @date 2019-12-09 19:12:57
 * @version v1.0
*/

public interface SchedulerRunnerHistoryDao {
	/**
	 * 单笔插入
	 * @param SchedulerRunnerHistory 
	 */
	int insertOne(SchedulerRunnerHistory schedulerRunnerHistory);
	/**
	 * 批量插入(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	void batchInsert(List<SchedulerRunnerHistory> list);
	/**
	 * 批量插入(使用XML中的foreach语句)
	 * @param SchedulerRunnerHistory 
	 */
	void batchInsertXML(List<SchedulerRunnerHistory> list);
	/**
	 * 单笔查询
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	SchedulerRunnerHistory selectOne1(Long runnerId, Long jobId);
	/**
	 * 单笔查询(正常状态 valid_status = 0)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	SchedulerRunnerHistory selectOne1R(Long runnerId, Long jobId);
	/**
	 * 单笔查询(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	SchedulerRunnerHistory selectOne1L(Long runnerId, Long jobId);
	/**
	 * 单笔删除
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	int deleteOne1(Long runnerId, Long jobId);
	/**
	 * 单笔删除(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param runnerId 运行序号
	 * @param jobId JOB任务ID
	 */
	int deleteOne1L(Long runnerId, Long jobId);
	/**
	 * 单笔更新
	 * @param SchedulerRunnerHistory 
	 */
	int updateOne1(SchedulerRunnerHistory schedulerRunnerHistory);
	/**
	 * 单笔更新(正常状态 valid_status = 0)
	 * @param SchedulerRunnerHistory 
	 */
	int updateOne1R(SchedulerRunnerHistory schedulerRunnerHistory);
	/**
	 * 单笔更新(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param SchedulerRunnerHistory 
	 */
	int updateOne1L(SchedulerRunnerHistory schedulerRunnerHistory);
	/**
	 * 批量更新(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	void batchUpdate(List<SchedulerRunnerHistory> list);
	/**
	 * 批量删除(直接调用Mybatis代码)
	 * @param SchedulerRunnerHistory 
	 */
	void batchDelete(List<SchedulerRunnerHistory> list);
	/**
	 * 查询多笔数据
	 * @param jobId JOB任务ID
	 * @param jobStatus JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param jobMaintenanceDate 运行日期
	 */
	List<SchedulerRunnerHistory> selectAll1(Long jobId, String jobStatus, String jobMaintenanceDate);
	/**
	 * 查询多笔数据(正常状态 valid_status = 0)
	 * @param jobId JOB任务ID
	 * @param jobStatus JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param jobMaintenanceDate 运行日期
	 */
	List<SchedulerRunnerHistory> selectAll1R(Long jobId, String jobStatus, String jobMaintenanceDate);
	/**
	 * 查询多笔数据(加锁  for update: 当使用索引时锁行, 其他锁表)
	 * @param jobId JOB任务ID
	 * @param jobStatus JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param jobMaintenanceDate 运行日期
	 */
	List<SchedulerRunnerHistory> selectAll1L(Long jobId, String jobStatus, String jobMaintenanceDate);
}