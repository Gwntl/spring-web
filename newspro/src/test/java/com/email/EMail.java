package com.email;

public class EMail {
	/**
	 * 主题
	 * @Fields subject
	 */
	private String  subject;
	
	/**
	 * 内容
	 * @Fields content
	 */
	private String content;

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * <p>Description: </p>
	 * <p>Title: toString</p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EMail [subject=" + subject + ", content=" + content + "]";
	}
}
