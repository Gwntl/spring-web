package org.mine.model;

/**
 * batch_queue_execute--批量队列运行表
 * @filename BatchQueueExecute.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:19
 * @version v1.0
*/
public class BatchQueueExecute {
	/**
	 * 执行队列ID
	 */
	private String executeQueueId;
	/**
	 * 执行对列名称
	 */
	private String executeQueueName;
	/**
	 * 执行任务ID
	 */
	private String executeTaskId;
	/**
	 * 执行任务名称
	 */
	private String executeTaskName;
	/**
	 * 任务执行序号
	 */
	private Integer executeNum;
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

	public BatchQueueExecute() {
		this.executeQueueId = "";
		this.executeQueueName = "";
		this.executeTaskId = "";
		this.executeTaskName = "";
		this.executeNum = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 执行队列ID
	 * @return the executeQueueId
	 */
	public String getExecuteQueueId() {
		return executeQueueId;
	}
	/**
	 * 执行队列ID
	 * @param executeQueueId the executeQueueId to set
	 */
	public void setExecuteQueueId(String executeQueueId) {
		this.executeQueueId = executeQueueId;
	}
	/**
	 * 执行对列名称
	 * @return the executeQueueName
	 */
	public String getExecuteQueueName() {
		return executeQueueName;
	}
	/**
	 * 执行对列名称
	 * @param executeQueueName the executeQueueName to set
	 */
	public void setExecuteQueueName(String executeQueueName) {
		this.executeQueueName = executeQueueName;
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
	 * 任务执行序号
	 * @return the executeNum
	 */
	public Integer getExecuteNum() {
		return executeNum;
	}
	/**
	 * 任务执行序号
	 * @param executeNum the executeNum to set
	 */
	public void setExecuteNum(Integer executeNum) {
		this.executeNum = executeNum;
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
		return "BatchQueueExecute[" + 
		"executeQueueId=" + executeQueueId + ", executeQueueName=" + executeQueueName + ", executeTaskId=" + executeTaskId + ", executeTaskName=" + executeTaskName + 
		", executeNum=" + executeNum + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}