package org.mine.quartz.dto;

import java.util.Map;

import org.mine.aplt.util.CommonUtils;

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
	 * 传递值
	 * @Fields map
	 */
	private Map<String, Object> map;
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
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "CallableResultDto [result=" + result + ", mseeage=" + mseeage + ", map=" + CommonUtils.toString(map) + "]";
	}
}
