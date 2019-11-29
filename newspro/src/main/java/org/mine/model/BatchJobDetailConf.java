package org.mine.model;

/**
 * batch_job_detail_conf--JOb任务作业定义
 * @filename BatchJobDetailConf.java
 * @author wzaUsers
 * @date 2019-11-26 15:11:20
 * @version v1.0
*/
public class BatchJobDetailConf {
	/**
	 * JOB任务作业ID
	 */
	private Long jobdetailId;
	/**
	 * JOB作业名称
	 */
	private String jobdetailName;
	/**
	 * JOB作业执行类信息
	 */
	private String jobdetailProvider;
	/**
	 * 初始化值
	 */
	private String jobdetailInitValue;
	/**
	 * JOB作业组ID
	 */
	private Long jobdetailGroupId;
	/**
	 * 执行器
	 */
	private String jobdetailActuator;
	/**
	 * 触发器ID
	 */
	private Long jobdetailTriggerId;
	/**
	 * 有效状态
	 */
	private String vaildStatus;
	/**
	 * 备注
	 */
	private String jobdetailRemark;

	public BatchJobDetailConf() {
		this.jobdetailId = 0L;
		this.jobdetailName = "";
		this.jobdetailProvider = "";
		this.jobdetailInitValue = "";
		this.jobdetailGroupId = 0L;
		this.jobdetailActuator = "";
		this.jobdetailTriggerId = 0L;
		this.vaildStatus = "0";
		this.jobdetailRemark = "";
	}

	/**
	 * JOB任务作业ID
	 * @return thejobdetailId
	 */
	public Long getJobdetailId() {
		return jobdetailId;
	}
	/**
	 * JOB任务作业ID
	 * @param jobdetailId the jobdetailId to set
	 */
	public void setJobdetailId(Long jobdetailId) {
		this.jobdetailId = jobdetailId;
	}
	/**
	 * JOB作业名称
	 * @return thejobdetailName
	 */
	public String getJobdetailName() {
		return jobdetailName;
	}
	/**
	 * JOB作业名称
	 * @param jobdetailName the jobdetailName to set
	 */
	public void setJobdetailName(String jobdetailName) {
		this.jobdetailName = jobdetailName;
	}
	/**
	 * JOB作业执行类信息
	 * @return thejobdetailProvider
	 */
	public String getJobdetailProvider() {
		return jobdetailProvider;
	}
	/**
	 * JOB作业执行类信息
	 * @param jobdetailProvider the jobdetailProvider to set
	 */
	public void setJobdetailProvider(String jobdetailProvider) {
		this.jobdetailProvider = jobdetailProvider;
	}
	/**
	 * 初始化值
	 * @return thejobdetailInitValue
	 */
	public String getJobdetailInitValue() {
		return jobdetailInitValue;
	}
	/**
	 * 初始化值
	 * @param jobdetailInitValue the jobdetailInitValue to set
	 */
	public void setJobdetailInitValue(String jobdetailInitValue) {
		this.jobdetailInitValue = jobdetailInitValue;
	}
	/**
	 * JOB作业组ID
	 * @return thejobdetailGroupId
	 */
	public Long getJobdetailGroupId() {
		return jobdetailGroupId;
	}
	/**
	 * JOB作业组ID
	 * @param jobdetailGroupId the jobdetailGroupId to set
	 */
	public void setJobdetailGroupId(Long jobdetailGroupId) {
		this.jobdetailGroupId = jobdetailGroupId;
	}
	/**
	 * 执行器
	 * @return thejobdetailActuator
	 */
	public String getJobdetailActuator() {
		return jobdetailActuator;
	}
	/**
	 * 执行器
	 * @param jobdetailActuator the jobdetailActuator to set
	 */
	public void setJobdetailActuator(String jobdetailActuator) {
		this.jobdetailActuator = jobdetailActuator;
	}
	/**
	 * 触发器ID
	 * @return thejobdetailTriggerId
	 */
	public Long getJobdetailTriggerId() {
		return jobdetailTriggerId;
	}
	/**
	 * 触发器ID
	 * @param jobdetailTriggerId the jobdetailTriggerId to set
	 */
	public void setJobdetailTriggerId(Long jobdetailTriggerId) {
		this.jobdetailTriggerId = jobdetailTriggerId;
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
	 * @return thejobdetailRemark
	 */
	public String getJobdetailRemark() {
		return jobdetailRemark;
	}
	/**
	 * 备注
	 * @param jobdetailRemark the jobdetailRemark to set
	 */
	public void setJobdetailRemark(String jobdetailRemark) {
		this.jobdetailRemark = jobdetailRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchJobDetailConf[" + 
		"jobdetailId=" + jobdetailId + ", jobdetailName=" + jobdetailName + ", jobdetailProvider=" + jobdetailProvider + ", jobdetailInitValue=" + jobdetailInitValue + 
		", jobdetailGroupId=" + jobdetailGroupId + ", jobdetailActuator=" + jobdetailActuator + ", jobdetailTriggerId=" + jobdetailTriggerId + 
		", vaildStatus=" + vaildStatus + ", jobdetailRemark=" + jobdetailRemark + "]";
	}
}