package org.mine.aplt.enumqz;

public enum JobExcutorEnum {
	STARTED("STARTED"), COMPLETING("COMPLETING"), SEECUSS("SEECUSS"),FAILED("FAILED"),UNKOWN("UNKOWN");
	
	/**
	 * JOB执行并发
	 * CURR_JOB
	 */
	public static final String CURR_JOB = "0";
	/**
	 * JOB执行不并发
	 * NO_CURR_JOB
	 */
	public static final String NO_CURR_JOB = "1";
	/**
	 * JOB自动执行
	 * AUTO_RUN
	 */
	public static final int AUTO_RUN = 0;
	/**
	 * JOB不自动执行
	 * NO_AUTO_RUN
	 */
	public static final int NO_AUTO_RUN = 1;
	/**
	 * 保存日志: 0-是
	 * SAVE_LOG
	 */
	public static final String SAVE_LOG = "0";
	/**
	 * 保存日志: 1-否
	 * NO_SAVE_LOG
	 */
	public static final String NO_SAVE_LOG = "1";
	
	private String id;
	
	private JobExcutorEnum(String id) {
		this.id = id;
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
}
