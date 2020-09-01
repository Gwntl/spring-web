package org.mine.model;

/**
 * batch_job_log_history--异步作业执行序列日志表
 * @filename BatchJobLogHistory.java
 * @author wzaUsers
 * @date 2020-08-25 15:08:12
 * @version v1.0
*/
public class BatchJobLogHistory {
	/**
	 * 执行实例
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
	private String jobStatus;
	/**
	 * 错误信息
	 */
	private String jobErrmsg;
	/**
	 * 任务并发数
	 */
	private Integer concurrencyNum;
	/**
	 * 关联任务ID
	 */
	private String associateTaskId;
	/**
	 * 关联任务实例
	 */
	private String associateTaskInstance;
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

	public BatchJobLogHistory() {
		this.executionInstance = "";
		this.jobId = "";
		this.jobName = "";
		this.startTime = null;
		this.endTime = null;
		this.jobStatus = "";
		this.jobErrmsg = "";
		this.concurrencyNum = 0;
		this.associateTaskId = "";
		this.associateTaskInstance = "";
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
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * 执行状态
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	/**
	 * 错误信息
	 * @return the jobErrmsg
	 */
	public String getJobErrmsg() {
		return jobErrmsg;
	}
	/**
	 * 错误信息
	 * @param jobErrmsg the jobErrmsg to set
	 */
	public void setJobErrmsg(String jobErrmsg) {
		this.jobErrmsg = jobErrmsg;
	}
	/**
	 * 任务并发数
	 * @return the concurrencyNum
	 */
	public Integer getConcurrencyNum() {
		return concurrencyNum;
	}
	/**
	 * 任务并发数
	 * @param concurrencyNum the concurrencyNum to set
	 */
	public void setConcurrencyNum(Integer concurrencyNum) {
		this.concurrencyNum = concurrencyNum;
	}
	/**
	 * 关联任务ID
	 * @return the associateTaskId
	 */
	public String getAssociateTaskId() {
		return associateTaskId;
	}
	/**
	 * 关联任务ID
	 * @param associateTaskId the associateTaskId to set
	 */
	public void setAssociateTaskId(String associateTaskId) {
		this.associateTaskId = associateTaskId;
	}
	/**
	 * 关联任务实例
	 * @return the associateTaskInstance
	 */
	public String getAssociateTaskInstance() {
		return associateTaskInstance;
	}
	/**
	 * 关联任务实例
	 * @param associateTaskInstance the associateTaskInstance to set
	 */
	public void setAssociateTaskInstance(String associateTaskInstance) {
		this.associateTaskInstance = associateTaskInstance;
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
		return "BatchJobLogHistory[" + 
		"executionInstance=" + executionInstance + ", jobId=" + jobId + ", jobName=" + jobName + ", startTime=" + startTime + 
		", endTime=" + endTime + ", jobStatus=" + jobStatus + ", jobErrmsg=" + jobErrmsg + 
		", concurrencyNum=" + concurrencyNum + ", associateTaskId=" + associateTaskId + ", associateTaskInstance=" + associateTaskInstance + 
		", createDate=" + createDate + ", timeStamp=" + timeStamp + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}