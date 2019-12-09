package org.mine.quartz.job.logic;

public class CallableResult {
	/**
	 * 运行ID
	 * runnerid
	 */
	private String runnerid;
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
	 * @return the runnerid
	 */
	public String getRunnerid() {
		return runnerid;
	}
	/**
	 * @param runnerid the runnerid to set
	 */
	public void setRunnerid(String runnerid) {
		this.runnerid = runnerid;
	}
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
		return "CallableResult [runnerid=" + runnerid + ", result=" + result + ", mseeage=" + mseeage + "]";
	}
}
