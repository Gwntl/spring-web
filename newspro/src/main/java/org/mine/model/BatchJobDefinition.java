package org.mine.model;

/**
 * batch_job_definition--批量作业定义表
 * @filename BatchJobDefinition.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:43
 * @version v1.0
*/
public class BatchJobDefinition {
	/**
	 * 作业ID
	 */
	private Long jobId;
	/**
	 * 作业名称
	 */
	private String jobName;
	/**
	 * 关联定时器ID
	 */
	private String jobAssociateTriggerId;
	/**
	 * 作业执行器
	 */
	private String jobExecutor;
	/**
	 * 作业初始化值
	 */
	private String jobInitValue;
	/**
	 * 可跳过标志. 0-是, 1-否
	 */
	private Integer jobSkipFlag;
	/**
	 * 多次执行标志, 0-是, 1-否
	 */
	private Integer jobRunMutiFlag;
	/**
	 * 延时执行标志, 0-是, 1-否
	 */
	private Integer jobTimeDelayFlag;
	/**
	 * 是否一次性作业. 0-是, 1-否
	 */
	private Integer jobOneTime;
	/**
	 * 延时时间
	 */
	private String jobTimeDelayValue;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchJobDefinition() {
		this.jobId = 0L;
		this.jobName = "";
		this.jobAssociateTriggerId = "";
		this.jobExecutor = "";
		this.jobInitValue = "";
		this.jobSkipFlag = 1;
		this.jobRunMutiFlag = 0;
		this.jobTimeDelayFlag = 1;
		this.jobOneTime = 1;
		this.jobTimeDelayValue = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 作业ID
	 * @return thejobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 作业ID
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
	 * 关联定时器ID
	 * @return thejobAssociateTriggerId
	 */
	public String getJobAssociateTriggerId() {
		return jobAssociateTriggerId;
	}
	/**
	 * 关联定时器ID
	 * @param jobAssociateTriggerId the jobAssociateTriggerId to set
	 */
	public void setJobAssociateTriggerId(String jobAssociateTriggerId) {
		this.jobAssociateTriggerId = jobAssociateTriggerId;
	}
	/**
	 * 作业执行器
	 * @return thejobExecutor
	 */
	public String getJobExecutor() {
		return jobExecutor;
	}
	/**
	 * 作业执行器
	 * @param jobExecutor the jobExecutor to set
	 */
	public void setJobExecutor(String jobExecutor) {
		this.jobExecutor = jobExecutor;
	}
	/**
	 * 作业初始化值
	 * @return thejobInitValue
	 */
	public String getJobInitValue() {
		return jobInitValue;
	}
	/**
	 * 作业初始化值
	 * @param jobInitValue the jobInitValue to set
	 */
	public void setJobInitValue(String jobInitValue) {
		this.jobInitValue = jobInitValue;
	}
	/**
	 * 可跳过标志. 0-是, 1-否
	 * @return thejobSkipFlag
	 */
	public Integer getJobSkipFlag() {
		return jobSkipFlag;
	}
	/**
	 * 可跳过标志. 0-是, 1-否
	 * @param jobSkipFlag the jobSkipFlag to set
	 */
	public void setJobSkipFlag(Integer jobSkipFlag) {
		this.jobSkipFlag = jobSkipFlag;
	}
	/**
	 * 多次执行标志, 0-是, 1-否
	 * @return thejobRunMutiFlag
	 */
	public Integer getJobRunMutiFlag() {
		return jobRunMutiFlag;
	}
	/**
	 * 多次执行标志, 0-是, 1-否
	 * @param jobRunMutiFlag the jobRunMutiFlag to set
	 */
	public void setJobRunMutiFlag(Integer jobRunMutiFlag) {
		this.jobRunMutiFlag = jobRunMutiFlag;
	}
	/**
	 * 延时执行标志, 0-是, 1-否
	 * @return thejobTimeDelayFlag
	 */
	public Integer getJobTimeDelayFlag() {
		return jobTimeDelayFlag;
	}
	/**
	 * 延时执行标志, 0-是, 1-否
	 * @param jobTimeDelayFlag the jobTimeDelayFlag to set
	 */
	public void setJobTimeDelayFlag(Integer jobTimeDelayFlag) {
		this.jobTimeDelayFlag = jobTimeDelayFlag;
	}
	/**
	 * 是否一次性作业. 0-是, 1-否
	 * @return thejobOneTime
	 */
	public Integer getJobOneTime() {
		return jobOneTime;
	}
	/**
	 * 是否一次性作业. 0-是, 1-否
	 * @param jobOneTime the jobOneTime to set
	 */
	public void setJobOneTime(Integer jobOneTime) {
		this.jobOneTime = jobOneTime;
	}
	/**
	 * 延时时间
	 * @return thejobTimeDelayValue
	 */
	public String getJobTimeDelayValue() {
		return jobTimeDelayValue;
	}
	/**
	 * 延时时间
	 * @param jobTimeDelayValue the jobTimeDelayValue to set
	 */
	public void setJobTimeDelayValue(String jobTimeDelayValue) {
		this.jobTimeDelayValue = jobTimeDelayValue;
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
	 * 有效状态. 0-是, 1-否, D-已废弃
	 * @return thevalidStatus
	 */
	public String getValidStatus() {
		return validStatus;
	}
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
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
		return "BatchJobDefinition[" + 
		"jobId=" + jobId + ", jobName=" + jobName + ", jobAssociateTriggerId=" + jobAssociateTriggerId + ", jobExecutor=" + jobExecutor + 
		", jobInitValue=" + jobInitValue + ", jobSkipFlag=" + jobSkipFlag + ", jobRunMutiFlag=" + jobRunMutiFlag + 
		", jobTimeDelayFlag=" + jobTimeDelayFlag + ", jobOneTime=" + jobOneTime + ", jobTimeDelayValue=" + jobTimeDelayValue + 
		", createDate=" + createDate + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}