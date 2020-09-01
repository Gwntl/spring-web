package org.mine.model;

/**
 * batch_job_execute--批量作业执行表(并发)
 * @filename BatchJobExecute.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:19
 * @version v1.0
*/
public class BatchJobExecute {
	/**
	 * 执行作业ID
	 */
	private String executeJobId;
	/**
	 * 执行作业名称
	 */
	private String executeJobName;
	/**
	 * 关联步骤ID
	 */
	private String executeStepId;
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
		this.executeJobId = "";
		this.executeJobName = "";
		this.executeStepId = "";
		this.executeStepNum = 0;
		this.executeStrongDepen = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
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
	 * 执行作业名称
	 * @return the executeJobName
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
	 * @return the executeStepId
	 */
	public String getExecuteStepId() {
		return executeStepId;
	}
	/**
	 * 关联步骤ID
	 * @param executeStepId the executeStepId to set
	 */
	public void setExecuteStepId(String executeStepId) {
		this.executeStepId = executeStepId;
	}
	/**
	 * 步骤执行序号
	 * @return the executeStepNum
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
	 * @return the executeStrongDepen
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
		return "BatchJobExecute[" + 
		"executeJobId=" + executeJobId + ", executeJobName=" + executeJobName + ", executeStepId=" + executeStepId + ", executeStepNum=" + executeStepNum + 
		", executeStrongDepen=" + executeStrongDepen + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}