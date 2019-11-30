package org.mine.aplt.codeGenerator.dto;

import java.util.regex.Pattern;

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
	
	public static void main(String[] args) {
		//yyyyMMdd
		String pattern = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
				+ "(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|"
				+ "(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
				+ "|[2468][048]|[3579][26])00))0229)$";
		System.out.println(Pattern.matches(pattern, "20010430"));
		
		String timeRegex1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
				+ "-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|"
				+ "(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
				+ "|[2468][048]|[3579][26])00))-02-29)$";
		System.out.println(Pattern.matches(timeRegex1, "2001-04-30"));
		
		//yyyyMMdd
		String pattern1= "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
				+ "(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|"
				+ "(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]"
				+ "|[2468][048]|[3579][26])00))0229))"
				+ "\\s([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
		System.out.println(Pattern.matches(pattern1, "20010430 11:59:00"));
	}
}
