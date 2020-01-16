package org.mine.model;

/**
 * batch_queue_definition--批量执行队列定义表(串行)
 * @filename BatchQueueDefinition.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:43
 * @version v1.0
*/
public class BatchQueueDefinition {
	/**
	 * 队列ID
	 */
	private Long queueId;
	/**
	 * 对列名称
	 */
	private String queueName;
	/**
	 * 是否为定时任务. 0-是,1-否
	 */
	private Integer queueTimingtaskFlag;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
	 */
	private String vaildStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchQueueDefinition() {
		this.queueId = 0L;
		this.queueName = "";
		this.queueTimingtaskFlag = 1;
		this.createDate = "";
		this.vaildStatus = "0";
		this.remark = "";
	}

	/**
	 * 队列ID
	 * @return thequeueId
	 */
	public Long getQueueId() {
		return queueId;
	}
	/**
	 * 队列ID
	 * @param queueId the queueId to set
	 */
	public void setQueueId(Long queueId) {
		this.queueId = queueId;
	}
	/**
	 * 对列名称
	 * @return thequeueName
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
	 * 是否为定时任务. 0-是,1-否
	 * @return thequeueTimingtaskFlag
	 */
	public Integer getQueueTimingtaskFlag() {
		return queueTimingtaskFlag;
	}
	/**
	 * 是否为定时任务. 0-是,1-否
	 * @param queueTimingtaskFlag the queueTimingtaskFlag to set
	 */
	public void setQueueTimingtaskFlag(Integer queueTimingtaskFlag) {
		this.queueTimingtaskFlag = queueTimingtaskFlag;
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
	 * @return thevaildStatus
	 */
	public String getVaildStatus() {
		return vaildStatus;
	}
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
	 * @param vaildStatus the vaildStatus to set
	 */
	public void setVaildStatus(String vaildStatus) {
		this.vaildStatus = vaildStatus;
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
		return "BatchQueueDefinition[" + 
		"queueId=" + queueId + ", queueName=" + queueName + ", queueTimingtaskFlag=" + queueTimingtaskFlag + ", createDate=" + createDate + 
		", vaildStatus=" + vaildStatus + ", remark=" + remark + "]";
	}
}