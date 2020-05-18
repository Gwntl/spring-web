package org.mine.model;

/**
 * batch_job_execute--
 * @filename BatchJobExecute.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:15
 * @version v1.0
*/
public class BatchJobExecute {
	/**
	 * 执行作业ID
	 */
	private Long executeJobId;
	/**
	 * 执行作业名称
	 */
	private String executeJobName;
	/**
	 * 关联步骤ID
	 */
	private Long executeStepId;
	/**
	 * 步骤执行序号
	 */
	private Integer executeStepNum;
	/**
	 * 是否强依赖上一步
	 */
	private Integer executeStrongDepen;
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

	public BatchJobExecute() {
		this.executeJobId = 0L;
		this.executeJobName = "";
		this.executeStepId = 0L;
		this.executeStepNum = 0;
		this.executeStrongDepen = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
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
	 * 执行作业名称
	 * @return theexecuteJobName
	 */
	public String getExecuteJobName() {
		return executeJobName;
	}
	/**
	 * 执行作业名称
	 * @param executeJobName the executeJobName to set
	 */
	public void setExecuteJobName(String executeJobName) {
		this.executeJobName = executeJobName;
	}
	/**
	 * 关联步骤ID
	 * @return theexecuteStepId
	 */
	public Long getExecuteStepId() {
		return executeStepId;
	}
	/**
	 * 关联步骤ID
	 * @param executeStepId the executeStepId to set
	 */
	public void setExecuteStepId(Long executeStepId) {
		this.executeStepId = executeStepId;
	}
	/**
	 * 步骤执行序号
	 * @return theexecuteStepNum
	 */
	public Integer getExecuteStepNum() {
		return executeStepNum;
	}
	/**
	 * 步骤执行序号
	 * @param executeStepNum the executeStepNum to set
	 */
	public void setExecuteStepNum(Integer executeStepNum) {
		this.executeStepNum = executeStepNum;
	}
	/**
	 * 是否强依赖上一步
	 * @return theexecuteStrongDepen
	 */
	public Integer getExecuteStrongDepen() {
		return executeStrongDepen;
	}
	/**
	 * 是否强依赖上一步
	 * @param executeStrongDepen the executeStrongDepen to set
	 */
	public void setExecuteStrongDepen(Integer executeStrongDepen) {
		this.executeStrongDepen = executeStrongDepen;
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
		return "BatchJobExecute[" + 
		"executeJobId=" + executeJobId + ", executeJobName=" + executeJobName + ", executeStepId=" + executeStepId + ", executeStepNum=" + executeStepNum + 
		", executeStrongDepen=" + executeStrongDepen + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}