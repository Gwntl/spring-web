package org.mine.dao.custom;

public interface BatchConfCustomDao {

	Integer getMaxNumByQueueDefin();

	Integer getMaxNumByGroupDefin();

	Integer getMaxNumByTaskExecute(String taskID);

	Integer getMaxNumByJobExecute(String jobID);

	Integer getMaxNumByQueueExecute(String queueID);
	
	String getBatchSequence(String seqName);

	/**
	* 更新当前作业中未执行步骤状态
	* @param executionInstance
	* @param stepStatus
	* @param stepErrorMsg
	* @return: int
	* @Author: wntl
	* @Date: 2020/8/20
	*/
	int updateStepLogInUnknownFailed(String executionInstance, String stepStatus, String stepErrorMsg);
}
