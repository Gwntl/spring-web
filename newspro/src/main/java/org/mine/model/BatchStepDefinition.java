package org.mine.model;

/**
 * batch_step_definition--
 * @filename BatchStepDefinition.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/
public class BatchStepDefinition {
	/**
	 * 作业步骤ID
	 */
	private Long stepId;
	/**
	 * 作业步骤名称
	 */
	private String stepName;
	/**
	 * 步骤执行器
	 */
	private String stepActuator;
	/**
	 * 日志文件名
	 */
	private String stepLogMdcValue;
	/**
	 * 初始化值
	 */
	private String stepInitValue;
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

	public BatchStepDefinition() {
		this.stepId = 0L;
		this.stepName = "";
		this.stepActuator = "";
		this.stepLogMdcValue = "";
		this.stepInitValue = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 作业步骤ID
	 * @return thestepId
	 */
	public Long getStepId() {
		return stepId;
	}
	/**
	 * 作业步骤ID
	 * @param stepId the stepId to set
	 */
	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}
	/**
	 * 作业步骤名称
	 * @return thestepName
	 */
	public String getStepName() {
		return stepName;
	}
	/**
	 * 作业步骤名称
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * 步骤执行器
	 * @return thestepActuator
	 */
	public String getStepActuator() {
		return stepActuator;
	}
	/**
	 * 步骤执行器
	 * @param stepActuator the stepActuator to set
	 */
	public void setStepActuator(String stepActuator) {
		this.stepActuator = stepActuator;
	}
	/**
	 * 日志文件名
	 * @return thestepLogMdcValue
	 */
	public String getStepLogMdcValue() {
		return stepLogMdcValue;
	}
	/**
	 * 日志文件名
	 * @param stepLogMdcValue the stepLogMdcValue to set
	 */
	public void setStepLogMdcValue(String stepLogMdcValue) {
		this.stepLogMdcValue = stepLogMdcValue;
	}
	/**
	 * 初始化值
	 * @return thestepInitValue
	 */
	public String getStepInitValue() {
		return stepInitValue;
	}
	/**
	 * 初始化值
	 * @param stepInitValue the stepInitValue to set
	 */
	public void setStepInitValue(String stepInitValue) {
		this.stepInitValue = stepInitValue;
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
		return "BatchStepDefinition[" + 
		"stepId=" + stepId + ", stepName=" + stepName + ", stepActuator=" + stepActuator + ", stepLogMdcValue=" + stepLogMdcValue + 
		", stepInitValue=" + stepInitValue + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}