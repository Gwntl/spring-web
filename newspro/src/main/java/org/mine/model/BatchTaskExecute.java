package org.mine.model;

/**
 * batch_task_execute--批量任务运行表
 * @filename BatchTaskExecute.java
 * @author wzaUsers
 * @date 2020-09-02 10:09:24
 * @version v1.0
*/
public class BatchTaskExecute {
	/**
	 * 执行任务ID
	 */
	private String executeTaskId;
	/**
	 * 执行任务名称
	 */
	private String executeTaskName;
	/**
	 * 执行作业ID
	 */
	private String executeJobId;
	/**
	 * 作业执行序号
	 */
	private Integer executeJobNum;
	/**
	 * 作业执行次数
	 */
	private Integer executeJobTimes;
	/**
	 * 依赖作业
	 */
	private String executeJobDepends;
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

	public BatchTaskExecute() {
		this.executeTaskId = "";
		this.executeTaskName = "";
		this.executeJobId = "";
		this.executeJobNum = 0;
		this.executeJobTimes = 1;
		this.executeJobDepends = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 执行任务ID
	 * @return the executeTaskId
	 */
	public String getExecuteTaskId() {
		return executeTaskId;
	}
	/**
	 * 执行任务ID
	 * @param executeTaskId the executeTaskId to set
	 */
	public void setExecuteTaskId(String executeTaskId) {
		this.executeTaskId = executeTaskId;
	}
	/**
	 * 执行任务名称
	 * @return the executeTaskName
	 */
	public String getExecuteTaskName() {
		return executeTaskName;
	}
	/**
	 * 执行任务名称
	 * @param executeTaskName the executeTaskName to set
	 */
	public void setExecuteTaskName(String executeTaskName) {
		this.executeTaskName = executeTaskName;
	}
	/**
	 * 执行作业ID
	 * @return the executeJobId
	 */
	public String getExecuteJobId() {
		return executeJobId;
	}
	/**
	 * 执行作业ID
	 * @param executeJobId the executeJobId to set
	 */
	public void setExecuteJobId(String executeJobId) {
		this.executeJobId = executeJobId;
	}
	/**
	 * 作业执行序号
	 * @return the executeJobNum
	 */
	public Integer getExecuteJobNum() {
		return executeJobNum;
	}
	/**
	 * 作业执行序号
	 * @param executeJobNum the executeJobNum to set
	 */
	public void setExecuteJobNum(Integer executeJobNum) {
		this.executeJobNum = executeJobNum;
	}
	/**
	 * 作业执行次数
	 * @return the executeJobTimes
	 */
	public Integer getExecuteJobTimes() {
		return executeJobTimes;
	}
	/**
	 * 作业执行次数
	 * @param executeJobTimes the executeJobTimes to set
	 */
	public void setExecuteJobTimes(Integer executeJobTimes) {
		this.executeJobTimes = executeJobTimes;
	}
	/**
	 * 依赖作业
	 * @return the executeJobDepends
	 */
	public String getExecuteJobDepends() {
		return executeJobDepends;
	}
	/**
	 * 依赖作业
	 * @param executeJobDepends the executeJobDepends to set
	 */
	public void setExecuteJobDepends(String executeJobDepends) {
		this.executeJobDepends = executeJobDepends;
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
	 * 有效状态. 0-是, 1-否, D-已废弃
	 * @return the validStatus
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
		return "BatchTaskExecute[" + 
		"executeTaskId=" + executeTaskId + ", executeTaskName=" + executeTaskName + ", executeJobId=" + executeJobId + ", executeJobNum=" + executeJobNum + 
		", executeJobTimes=" + executeJobTimes + ", executeJobDepends=" + executeJobDepends + ", createDate=" + createDate + 
		", validStatus=" + validStatus + ", remark=" + remark + "]";
	}
}