package org.mine.model;

public class BatchRunJobDetail {
	/**
	 * JOB作业ID
	 */
	private Long jobId;
	/**
	 * JOB作业名称
	 */
	private String jobName;
	/**
	 * JOB作业详细信息
	 */
	private String jobProvider;
	/**
	 * 是否并发 0-并发 1-不并发 ,默认0
	 */
	private String jobIsConcurrent;
	/**
	 * 备注
	 */
	private String jobRemark;
	
	public BatchRunJobDetail() {
		this.jobId = 0L;
		this.jobName = "";
		this.jobProvider = "";
		this.jobIsConcurrent = "0";
		this.jobRemark = "";
	}
	
	/**
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the jobProvider
	 */
	public String getJobProvider() {
		return jobProvider;
	}
	/**
	 * @param jobProvider the jobProvider to set
	 */
	public void setJobProvider(String jobProvider) {
		this.jobProvider = jobProvider;
	}
	/**
	 * @return the jobIsConcurrent
	 */
	public String getJobIsConcurrent() {
		return jobIsConcurrent;
	}
	/**
	 * @param jobIsConcurrent the jobIsConcurrent to set
	 */
	public void setJobIsConcurrent(String jobIsConcurrent) {
		this.jobIsConcurrent = jobIsConcurrent;
	}
	/**
	 * @return the jobRemark
	 */
	public String getJobRemark() {
		return jobRemark;
	}
	/**
	 * @param jobRemark the jobRemark to set
	 */
	public void setJobRemark(String jobRemark) {
		this.jobRemark = jobRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchRunJobDetail [jobId=" + jobId + ", jobName=" + jobName + ", jobProvider=" + jobProvider
				+ ", jobIsConcurrent=" + jobIsConcurrent + ", jobRemark=" + jobRemark + "]";
	}
}
