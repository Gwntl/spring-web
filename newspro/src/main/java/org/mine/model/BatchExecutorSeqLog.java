package org.mine.model;

/**
 * batch_executor_seq_log--异步作业执行序列日志表
 * @filename BatchExecutorSeqLog.java
 * @author wzaUsers
 * @date 2020-08-18 16:08:15
 * @version v1.0
*/
public class BatchExecutorSeqLog {
	/**
	 * 作业执行实例
	 */
	private String executionInstance;
	/**
	 * 关联作业ID
	 */
	private String jobId;
	/**
	 * 作业名称
	 */
	private String jobName;
	/**
	 * 执行状态
	 */
	private String status;
	/**
	 * 错误信息
	 */
	private String errorMeg;
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

	public BatchExecutorSeqLog() {
		this.executionInstance = "";
		this.jobId = "";
		this.jobName = "";
		this.status = "";
		this.errorMeg = "";
		this.createDate = "";
		this.timeStamp = 0L;
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 作业执行实例
	 * @return the executionInstance
	 */
	public String getExecutionInstance() {
		return executionInstance;
	}
	/**
	 * 作业执行实例
	 * @param executionInstance the executionInstance to set
	 */
	public void setExecutionInstance(String executionInstance) {
		this.executionInstance = executionInstance;
	}
	/**
	 * 关联作业ID
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * 关联作业ID
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * 作业名称
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 作业名称
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 执行状态
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 执行状态
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 错误信息
	 * @return the errorMeg
	 */
	public String getErrorMeg() {
		return errorMeg;
	}
	/**
	 * 错误信息
	 * @param errorMeg the errorMeg to set
	 */
	public void setErrorMeg(String errorMeg) {
		this.errorMeg = errorMeg;
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
		return "BatchExecutorSeqLog[" + 
		"executionInstance=" + executionInstance + ", jobId=" + jobId + ", jobName=" + jobName + ", status=" + status + 
		", errorMeg=" + errorMeg + ", createDate=" + createDate + ", timeStamp=" + timeStamp + 
		", validStatus=" + validStatus + ", remark=" + remark + "]";
	}
}