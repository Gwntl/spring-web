package org.mine.model;

/**
 * batch_job_group_conf--JOB作业组定义表
 * @filename BatchJobGroupConf.java
 * @author wzaUsers
 * @date 2019-11-14 20:11:04
 * @version v1.0
*/
public class BatchJobGroupConf {
	/**
	 * JOB作业组ID
	 */
	private Long jobGroupId;
	/**
	 * JOB作业组名称
	 */
	private String jobGroupName;
	/**
	 * 是否记录日志
	 */
	private String jobGroupSavelog;
	/**
	 * 是否支持重跑 0-是 1-否
	 */
	private String jobGroupRerun;
	/**
	 * 任务数
	 */
	private Integer jobGroupNumber;
	/**
	 * JOB作业组维护日期
	 */
	private String jobGroupMaintenanceDate;
	/**
	 * 有效状态
	 */
	private String vaildStatus;
	/**
	 * 备注
	 */
	private String jobGroupRemark;

	public BatchJobGroupConf() {
		this.jobGroupId = 0L;
		this.jobGroupName = "";
		this.jobGroupSavelog = "";
		this.jobGroupRerun = "";
		this.jobGroupNumber = 0;
		this.jobGroupMaintenanceDate = "";
		this.vaildStatus = "0";
		this.jobGroupRemark = "";
	}

	/**
	 * JOB作业组ID
	 * @return thejobGroupId
	 */
	public Long getJobGroupId() {
		return jobGroupId;
	}
	/**
	 * JOB作业组ID
	 * @param jobGroupId the jobGroupId to set
	 */
	public void setJobGroupId(Long jobGroupId) {
		this.jobGroupId = jobGroupId;
	}
	/**
	 * JOB作业组名称
	 * @return thejobGroupName
	 */
	public String getJobGroupName() {
		return jobGroupName;
	}
	/**
	 * JOB作业组名称
	 * @param jobGroupName the jobGroupName to set
	 */
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	/**
	 * 是否记录日志
	 * @return thejobGroupSavelog
	 */
	public String getJobGroupSavelog() {
		return jobGroupSavelog;
	}
	/**
	 * 是否记录日志
	 * @param jobGroupSavelog the jobGroupSavelog to set
	 */
	public void setJobGroupSavelog(String jobGroupSavelog) {
		this.jobGroupSavelog = jobGroupSavelog;
	}
	/**
	 * 是否支持重跑 0-是 1-否
	 * @return thejobGroupRerun
	 */
	public String getJobGroupRerun() {
		return jobGroupRerun;
	}
	/**
	 * 是否支持重跑 0-是 1-否
	 * @param jobGroupRerun the jobGroupRerun to set
	 */
	public void setJobGroupRerun(String jobGroupRerun) {
		this.jobGroupRerun = jobGroupRerun;
	}
	/**
	 * 任务数
	 * @return thejobGroupNumber
	 */
	public Integer getJobGroupNumber() {
		return jobGroupNumber;
	}
	/**
	 * 任务数
	 * @param jobGroupNumber the jobGroupNumber to set
	 */
	public void setJobGroupNumber(Integer jobGroupNumber) {
		this.jobGroupNumber = jobGroupNumber;
	}
	/**
	 * JOB作业组维护日期
	 * @return thejobGroupMaintenanceDate
	 */
	public String getJobGroupMaintenanceDate() {
		return jobGroupMaintenanceDate;
	}
	/**
	 * JOB作业组维护日期
	 * @param jobGroupMaintenanceDate the jobGroupMaintenanceDate to set
	 */
	public void setJobGroupMaintenanceDate(String jobGroupMaintenanceDate) {
		this.jobGroupMaintenanceDate = jobGroupMaintenanceDate;
	}
	/**
	 * 有效状态
	 * @return thevaildStatus
	 */
	public String getVaildStatus() {
		return vaildStatus;
	}
	/**
	 * 有效状态
	 * @param vaildStatus the vaildStatus to set
	 */
	public void setVaildStatus(String vaildStatus) {
		this.vaildStatus = vaildStatus;
	}
	/**
	 * 备注
	 * @return thejobGroupRemark
	 */
	public String getJobGroupRemark() {
		return jobGroupRemark;
	}
	/**
	 * 备注
	 * @param jobGroupRemark the jobGroupRemark to set
	 */
	public void setJobGroupRemark(String jobGroupRemark) {
		this.jobGroupRemark = jobGroupRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchJobGroupConf[" + 
		"jobGroupId=" + jobGroupId + ", jobGroupName=" + jobGroupName + ", jobGroupSavelog=" + jobGroupSavelog + ", jobGroupRerun=" + jobGroupRerun + 
		", jobGroupNumber=" + jobGroupNumber + ", jobGroupMaintenanceDate=" + jobGroupMaintenanceDate + ", vaildStatus=" + vaildStatus + 
		", jobGroupRemark=" + jobGroupRemark + "]";
	}
}