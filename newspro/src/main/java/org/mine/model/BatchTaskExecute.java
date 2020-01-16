package org.mine.model;

/**
 * batch_task_execute--批量任务运行表
 * @filename BatchTaskExecute.java
 * @author wzaUsers
 * @date 2020-01-09 11:01:11
 * @version v1.0
*/
public class BatchTaskExecute {
	/**
	 * 执行任务ID
	 */
	private Long executeTaskId;
	/**
	 * 执行任务名称
	 */
	private String executeTaskName;
	/**
	 * 执行作业ID
	 */
	private Long executeJobId;
	/**
	 * 作业执行序号
	 */
	private Integer executeJobNum;
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
		this.executeTaskId = 0L;
		this.executeTaskName = "";
		this.executeJobId = 0L;
		this.executeJobNum = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 执行任务ID
	 * @return theexecuteTaskId
	 */
	public Long getExecuteTaskId() {
		return executeTaskId;
	}
	/**
	 * 执行任务ID
	 * @param executeTaskId the executeTaskId to set
	 */
	public void setExecuteTaskId(Long executeTaskId) {
		this.executeTaskId = executeTaskId;
	}
	/**
	 * 执行任务名称
	 * @return theexecuteTaskName
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
	 * @return theexecuteJobId
	 */
	public Long getExecuteJobId() {
		return executeJobId;
	}
	/**
	 * 执行作业ID
	 * @param executeJobId the executeJobId to set
	 */
	public void setExecuteJobId(Long executeJobId) {
		this.executeJobId = executeJobId;
	}
	/**
	 * 作业执行序号
	 * @return theexecuteJobNum
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
		return "BatchTaskExecute[" + 
		"executeTaskId=" + executeTaskId + ", executeTaskName=" + executeTaskName + ", executeJobId=" + executeJobId + ", executeJobNum=" + executeJobNum + 
		", createDate=" + createDate + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}