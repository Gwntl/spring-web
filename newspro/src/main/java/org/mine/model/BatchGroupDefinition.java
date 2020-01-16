package org.mine.model;

/**
 * batch_group_definition--批量任务组定义表(串行)
 * @filename BatchGroupDefinition.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:43
 * @version v1.0
*/
public class BatchGroupDefinition {
	/**
	 * 任务执行组ID
	 */
	private Long groupId;
	/**
	 * 任务执行组名称
	 */
	private String groupName;
	/**
	 * 任务组关联队列ID
	 */
	private Long groupAssociateQueueId;
	/**
	 * 组别执行序号
	 */
	private Integer groupExecutionNum;
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

	public BatchGroupDefinition() {
		this.groupId = 0L;
		this.groupName = "";
		this.groupAssociateQueueId = 0L;
		this.groupExecutionNum = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务执行组ID
	 * @return thegroupId
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * 任务执行组ID
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	/**
	 * 任务执行组名称
	 * @return thegroupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * 任务执行组名称
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 任务组关联队列ID
	 * @return thegroupAssociateQueueId
	 */
	public Long getGroupAssociateQueueId() {
		return groupAssociateQueueId;
	}
	/**
	 * 任务组关联队列ID
	 * @param groupAssociateQueueId the groupAssociateQueueId to set
	 */
	public void setGroupAssociateQueueId(Long groupAssociateQueueId) {
		this.groupAssociateQueueId = groupAssociateQueueId;
	}
	/**
	 * 组别执行序号
	 * @return thegroupExecutionNum
	 */
	public Integer getGroupExecutionNum() {
		return groupExecutionNum;
	}
	/**
	 * 组别执行序号
	 * @param groupExecutionNum the groupExecutionNum to set
	 */
	public void setGroupExecutionNum(Integer groupExecutionNum) {
		this.groupExecutionNum = groupExecutionNum;
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
		return "BatchGroupDefinition[" + 
		"groupId=" + groupId + ", groupName=" + groupName + ", groupAssociateQueueId=" + groupAssociateQueueId + ", groupExecutionNum=" + groupExecutionNum + 
		", createDate=" + createDate + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}