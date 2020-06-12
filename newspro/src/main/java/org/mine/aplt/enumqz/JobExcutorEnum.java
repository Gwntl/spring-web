package org.mine.aplt.enumqz;

public enum JobExcutorEnum {
	NEW("NEW"), COMPLETING("COMPLETING"), SUCCESS("SUCCESS"),FAILED("FAILED"),UNKOWN("UNKOWN"),CANCEL("CANCLE_MANUAL");
	
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
	 * TASK自动执行-定时任务
	 * AUTO_RUN
	 */
	public static final int TASK_AUTO_RUN = 0;
	/**
	 * TASK不自动执行-非定时任务
	 * NO_AUTO_RUN
	 */
	public static final int TASK_NO_AUTO_RUN = 1;
	/**
	 * 队列手动执行
	 * @Fields QUEUE_MANUAL
	 */
	public static final int QUEUE_MANUAL = 0;
	/**
	 * 队列自动执行
	 * @Fields QUEUE_AUTO
	 */
	public static final int QUEUE_AUTO = 1;
	/**
	 * 队列手/自动执行
	 * @Fields QUEUE_HAND_OR_AUTO
	 */
	public static final int QUEUE_HAND_OR_AUTO = 2;
	
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
	
	private String value;
	
	private JobExcutorEnum(String value) {
		this.value = value;
	}
	
	/**
	 * @return the id
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param id the id to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
