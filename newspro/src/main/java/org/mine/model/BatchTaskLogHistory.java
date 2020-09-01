package org.mine.model;

/**
 * batch_task_log_history--异步作业执行序列日志表
 * @filename BatchTaskLogHistory.java
 * @author wzaUsers
 * @date 2020-08-20 19:08:09
 * @version v1.0
*/
public class BatchTaskLogHistory {
	/**
	 * 执行实例
	 */
	private String executionInstance;
	/**
	 * 关联作业ID
	 */
	private String taskId;
	/**
	 * 作业名称
	 */
	private String taskName;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 执行状态
	 */
	private String taskStatus;
	/**
	 * 错误信息
	 */
	private String taskErrmsg;
	/**
	 * 关联任务ID
	 */
	private String associateQueueId;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 时间戳
	 */
	private Long timeStamp;
	/**
	 * 有效状态. 0-是, 1-否
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchTaskLogHistory() {
		this.executionInstance = "";
		this.taskId = "";
		this.taskName = "";
		this.startTime = null;
		this.endTime = null;
		this.taskStatus = "";
		this.taskErrmsg = "";
		this.associateQueueId = "";
		this.createDate = "";
		this.timeStamp = 0L;
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 执行实例
	 * @return the executionInstance
	 */
	public String getExecutionInstance() {
		return executionInstance;
	}
	/**
	 * 执行实例
	 * @param executionInstance the executionInstance to set
	 */
	public void setExecutionInstance(String executionInstance) {
		this.executionInstance = executionInstance;
	}
	/**
	 * 关联作业ID
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 关联作业ID
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 作业名称
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * 作业名称
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 开始时间
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 开始时间
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 结束时间
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 结束时间
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 执行状态
	 * @return the taskStatus
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	/**
	 * 执行状态
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	/**
	 * 错误信息
	 * @return the taskErrmsg
	 */
	public String getTaskErrmsg() {
		return taskErrmsg;
	}
	/**
	 * 错误信息
	 * @param taskErrmsg the taskErrmsg to set
	 */
	public void setTaskErrmsg(String taskErrmsg) {
		this.taskErrmsg = taskErrmsg;
	}
	/**
	 * 关联任务ID
	 * @return the associateQueueId
	 */
	public String getAssociateQueueId() {
		return associateQueueId;
	}
	/**
	 * 关联任务ID
	 * @param associateQueueId the associateQueueId to set
	 */
	public void setAssociateQueueId(String associateQueueId) {
		this.associateQueueId = associateQueueId;
	}
	/**
	 * 创建时间
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * 创建时间
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 时间戳
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 时间戳
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 有效状态. 0-是, 1-否
	 * @return the validStatus
	 */
	public String getValidStatus() {
		return validStatus;
	}
	/**
	 * 有效状态. 0-是, 1-否
	 * @param validStatus the validStatus to set
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	/**
	 * 备注
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchTaskLogHistory[" + 
		"executionInstance=" + executionInstance + ", taskId=" + taskId + ", taskName=" + taskName + ", startTime=" + startTime + 
		", endTime=" + endTime + ", taskStatus=" + taskStatus + ", taskErrmsg=" + taskErrmsg + 
		", associateQueueId=" + associateQueueId + ", createDate=" + createDate + ", timeStamp=" + timeStamp + 
		", validStatus=" + validStatus + ", remark=" + remark + "]";
	}
}