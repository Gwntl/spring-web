package org.mine.model;

/**
 * batch_queue_log--异步作业执行序列日志表
 * @filename BatchQueueLog.java
 * @author wzaUsers
 * @date 2020-09-07 19:09:41
 * @version v1.0
*/
public class BatchQueueLog {
	/**
	 * 执行实例
	 */
	private String executionInstance;
	/**
	 * 队列ID
	 */
	private String queueId;
	/**
	 * 队列名称
	 */
	private String queueName;
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
	private String queueStatus;
	/**
	 * 错误信息
	 */
	private String queueErrmsg;
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

	public BatchQueueLog() {
		this.executionInstance = "";
		this.queueId = "";
		this.queueName = "";
		this.startTime = null;
		this.endTime = null;
		this.queueStatus = "";
		this.queueErrmsg = "";
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
	 * 队列ID
	 * @return the queueId
	 */
	public String getQueueId() {
		return queueId;
	}
	/**
	 * 队列ID
	 * @param queueId the queueId to set
	 */
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	/**
	 * 队列名称
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}
	/**
	 * 队列名称
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
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
	 * @return the queueStatus
	 */
	public String getQueueStatus() {
		return queueStatus;
	}
	/**
	 * 执行状态
	 * @param queueStatus the queueStatus to set
	 */
	public void setQueueStatus(String queueStatus) {
		this.queueStatus = queueStatus;
	}
	/**
	 * 错误信息
	 * @return the queueErrmsg
	 */
	public String getQueueErrmsg() {
		return queueErrmsg;
	}
	/**
	 * 错误信息
	 * @param queueErrmsg the queueErrmsg to set
	 */
	public void setQueueErrmsg(String queueErrmsg) {
		this.queueErrmsg = queueErrmsg;
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
		return "BatchQueueLog[" + 
		"executionInstance=" + executionInstance + ", queueId=" + queueId + ", queueName=" + queueName + ", startTime=" + startTime + 
		", endTime=" + endTime + ", queueStatus=" + queueStatus + ", queueErrmsg=" + queueErrmsg + 
		", createDate=" + createDate + ", timeStamp=" + timeStamp + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}