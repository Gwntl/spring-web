package org.mine.model;

/**
 * batch_queue_conf--执行队列定义表
 * @filename BatchQueueConf.java
 * @author wzaUsers
 * @date 2019-11-26 15:11:20
 * @version v1.0
*/
public class BatchQueueConf {
	/**
	 * 队列ID
	 */
	private Long queueId;
	/**
	 * 队列名
	 */
	private String queueName;
	/**
	 * JOB作业组ID
	 */
	private Long queueJobGroupId;
	/**
	 * 是否自动执行 0-是 1-否
	 */
	private Integer queueAutoFlag;
	/**
	 * 有效状态
	 */
	private String vaildStatus;
	/**
	 * 备注
	 */
	private String queueRemark;

	public BatchQueueConf() {
		this.queueId = 0L;
		this.queueName = "";
		this.queueJobGroupId = 0L;
		this.queueAutoFlag = 0;
		this.vaildStatus = "0";
		this.queueRemark = "";
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
	 * 队列名
	 * @return thequeueName
	 */
	public String getQueueName() {
		return queueName;
	}
	/**
	 * 队列名
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	/**
	 * JOB作业组ID
	 * @return thequeueJobGroupId
	 */
	public Long getQueueJobGroupId() {
		return queueJobGroupId;
	}
	/**
	 * JOB作业组ID
	 * @param queueJobGroupId the queueJobGroupId to set
	 */
	public void setQueueJobGroupId(Long queueJobGroupId) {
		this.queueJobGroupId = queueJobGroupId;
	}
	/**
	 * 是否自动执行 0-是 1-否
	 * @return thequeueAutoFlag
	 */
	public Integer getQueueAutoFlag() {
		return queueAutoFlag;
	}
	/**
	 * 是否自动执行 0-是 1-否
	 * @param queueAutoFlag the queueAutoFlag to set
	 */
	public void setQueueAutoFlag(Integer queueAutoFlag) {
		this.queueAutoFlag = queueAutoFlag;
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
	 * @return thequeueRemark
	 */
	public String getQueueRemark() {
		return queueRemark;
	}
	/**
	 * 备注
	 * @param queueRemark the queueRemark to set
	 */
	public void setQueueRemark(String queueRemark) {
		this.queueRemark = queueRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchQueueConf[" + 
		"queueId=" + queueId + ", queueName=" + queueName + ", queueJobGroupId=" + queueJobGroupId + ", queueAutoFlag=" + queueAutoFlag + 
		", vaildStatus=" + vaildStatus + ", queueRemark=" + queueRemark + "]";
	}
}