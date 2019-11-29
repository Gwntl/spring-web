package org.mine.aplt.codeGenerator.dto;

public class DelegationDto {

	private StringBuffer buffer;
	
	private Integer tabsCount;

	public DelegationDto() {
		super();
		this.buffer = new StringBuffer();
		this.tabsCount = 0;
	}

	/**
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * @return the tabsCount
	 */
	public Integer getTabsCount() {
		return tabsCount;
	}

	/**
	 * @param tabsCount the tabsCount to set
	 */
	public void setTabsCount(Integer tabsCount) {
		this.tabsCount = tabsCount;
	}
}
