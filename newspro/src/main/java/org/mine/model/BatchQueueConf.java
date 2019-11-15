package org.mine.model;

/**
 * batch_queue_conf--执行队列定义表
 * @filename BatchQueueConf.java
 * @author wzaUsers
 * @date 2019-11-14 20:11:21
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
	 * 触发器ID
	 */
	private Long queueTriggerId;
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
		this.queueTriggerId = 0L;
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
	 * 触发器ID
	 * @return thequeueTriggerId
	 */
	public Long getQueueTriggerId() {
		return queueTriggerId;
	}
	/**
	 * 触发器ID
	 * @param queueTriggerId the queueTriggerId to set
	 */
	public void setQueueTriggerId(Long queueTriggerId) {
		this.queueTriggerId = queueTriggerId;
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
		"queueId=" + queueId + ", queueName=" + queueName + ", queueJobGroupId=" + queueJobGroupId + ", queueTriggerId=" + queueTriggerId + 
		", vaildStatus=" + vaildStatus + ", queueRemark=" + queueRemark + "]";
	}
}