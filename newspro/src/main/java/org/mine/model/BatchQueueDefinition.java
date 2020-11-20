package org.mine.model;

/**
 * batch_queue_definition--批量执行队列定义表(串行)
 * @filename BatchQueueDefinition.java
 * @author wzaUsers
 * @date 2020-09-17 20:09:12
 * @version v1.0
*/
public class BatchQueueDefinition {
	/**
	 * 队列ID
	 */
	private String queueId;
	/**
	 * 对列名称
	 */
	private String queueName;
	/**
	 * 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 */
	private Integer queueExcFlag;
	/**
	 * 关联触发器ID
	 */
	private String queueTriggerId;
	/**
	 * 队列执行器
	 */
	private String queueExecutor;
	/**
	 * 队列初始值
	 */
	private String queueInitValue;
	/**
	 * 队列执行序号
	 */
	private Integer queueExecutionNum;
	/**
	 * 是否同时运行
	 */
	private String queueSameTimeRun;
	/**
	 * 最大并发执行数
	 */
	private Long queueMaxRunNum;
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

	public BatchQueueDefinition() {
		this.queueId = "";
		this.queueName = "";
		this.queueExcFlag = 0;
		this.queueTriggerId = "";
		this.queueExecutor = "";
		this.queueInitValue = "";
		this.queueExecutionNum = 0;
		this.queueSameTimeRun = "";
		this.queueMaxRunNum = 0L;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 队列ID
	 * @return the queueId
	 */
	public String getQueueId() {
		return queueId;
	}
	/**
	 * 队列ID
	 * @param queueId the queueId to set
	 */
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	/**
	 * 对列名称
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}
	/**
	 * 对列名称
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	/**
	 * 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 * @return the queueExcFlag
	 */
	public Integer getQueueExcFlag() {
		return queueExcFlag;
	}
	/**
	 * 任务执行标志. 0-手动, 1-自动, 2-手/自.
	 * @param queueExcFlag the queueExcFlag to set
	 */
	public void setQueueExcFlag(Integer queueExcFlag) {
		this.queueExcFlag = queueExcFlag;
	}
	/**
	 * 关联触发器ID
	 * @return the queueTriggerId
	 */
	public String getQueueTriggerId() {
		return queueTriggerId;
	}
	/**
	 * 关联触发器ID
	 * @param queueTriggerId the queueTriggerId to set
	 */
	public void setQueueTriggerId(String queueTriggerId) {
		this.queueTriggerId = queueTriggerId;
	}
	/**
	 * 队列执行器
	 * @return the queueExecutor
	 */
	public String getQueueExecutor() {
		return queueExecutor;
	}
	/**
	 * 队列执行器
	 * @param queueExecutor the queueExecutor to set
	 */
	public void setQueueExecutor(String queueExecutor) {
		this.queueExecutor = queueExecutor;
	}
	/**
	 * 队列初始值
	 * @return the queueInitValue
	 */
	public String getQueueInitValue() {
		return queueInitValue;
	}
	/**
	 * 队列初始值
	 * @param queueInitValue the queueInitValue to set
	 */
	public void setQueueInitValue(String queueInitValue) {
		this.queueInitValue = queueInitValue;
	}
	/**
	 * 队列执行序号
	 * @return the queueExecutionNum
	 */
	public Integer getQueueExecutionNum() {
		return queueExecutionNum;
	}
	/**
	 * 队列执行序号
	 * @param queueExecutionNum the queueExecutionNum to set
	 */
	public void setQueueExecutionNum(Integer queueExecutionNum) {
		this.queueExecutionNum = queueExecutionNum;
	}
	/**
	 * 是否同时运行
	 * @return the queueSameTimeRun
	 */
	public String getQueueSameTimeRun() {
		return queueSameTimeRun;
	}
	/**
	 * 是否同时运行
	 * @param queueSameTimeRun the queueSameTimeRun to set
	 */
	public void setQueueSameTimeRun(String queueSameTimeRun) {
		this.queueSameTimeRun = queueSameTimeRun;
	}
	/**
	 * 最大并发执行数
	 * @return the queueMaxRunNum
	 */
	public Long getQueueMaxRunNum() {
		return queueMaxRunNum;
	}
	/**
	 * 最大并发执行数
	 * @param queueMaxRunNum the queueMaxRunNum to set
	 */
	public void setQueueMaxRunNum(Long queueMaxRunNum) {
		this.queueMaxRunNum = queueMaxRunNum;
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
		return "BatchQueueDefinition[" + 
		"queueId=" + queueId + ", queueName=" + queueName + ", queueExcFlag=" + queueExcFlag + ", queueTriggerId=" + queueTriggerId + 
		", queueExecutor=" + queueExecutor + ", queueInitValue=" + queueInitValue + ", queueExecutionNum=" + queueExecutionNum + 
		", queueSameTimeRun=" + queueSameTimeRun + ", queueMaxRunNum=" + queueMaxRunNum + ", createDate=" + createDate + 
		", validStatus=" + validStatus + ", remark=" + remark + "]";
	}
}