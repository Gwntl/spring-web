package org.mine.quartz.dto;

public class CallableResultDto {
	/**
	 * 运行结果
	 * result
	 */
	private boolean result;
	/**
	 * 返回信息
	 * mseeage
	 */
	private String mseeage;
	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	/**
	 * @return the mseeage
	 */
	public String getMseeage() {
		return mseeage;
	}
	/**
	 * @param mseeage the mseeage to set
	 */
	public void setMseeage(String mseeage) {
		this.mseeage = mseeage;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CallableResultDto [result=" + result + ", mseeage=" + mseeage + "]";
	}
}
