package org.mine.model;

/**
 * batch_executor_seqlog--异步作业执行序列日志表
 * @filename BatchExecutorSeqlog.java
 * @author wzaUsers
 * @date 2020-06-24 09:06:45
 * @version v1.0
*/
public class BatchExecutorSeqlog {
	/**
	 * 序号ID
	 */
	private Long seqId;
	/**
	 * 关联作业ID
	 */
	private Long jobId;
	/**
	 * 作业名称
	 */
	private String jobName;
	/**
	 * 执行状态
	 */
	private String status;
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

	public BatchExecutorSeqlog() {
		this.seqId = 0L;
		this.jobId = 0L;
		this.jobName = "";
		this.status = "";
		this.createDate = "";
		this.timeStamp = 0L;
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 序号ID
	 * @return theseqId
	 */
	public Long getSeqId() {
		return seqId;
	}
	/**
	 * 序号ID
	 * @param seqId the seqId to set
	 */
	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}
	/**
	 * 关联作业ID
	 * @return thejobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 关联作业ID
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 作业名称
	 * @return thejobName
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
	 * @return thestatus
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
	 * 创建时间
	 * @return thecreateDate
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
	 * @return thetimeStamp
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
	 * @return thevalidStatus
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
	 * @return theremark
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
		return "BatchExecutorSeqlog[" + 
		"seqId=" + seqId + ", jobId=" + jobId + ", jobName=" + jobName + ", status=" + status + 
		", createDate=" + createDate + ", timeStamp=" + timeStamp + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}