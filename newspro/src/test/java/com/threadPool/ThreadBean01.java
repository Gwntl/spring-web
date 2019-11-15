package com.threadPool;

public class ThreadBean01 extends ThreadBean{

	private String id;
	private String numStr;
	
	public ThreadBean01() {
		// TODO Auto-generated constructor stub
		this.id="";
		this.numStr="";
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the numStr
	 */
	public String getNumStr() {
		return numStr;
	}
	/**
	 * @param numStr the numStr to set
	 */
	public void setNumStr(String numStr) {
		this.numStr = numStr;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThreadBean01 [id=" + id + ", numStr=" + numStr + "]";
	}
}
