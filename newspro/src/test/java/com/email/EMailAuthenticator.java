package com.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EMailAuthenticator extends Authenticator{
	/**
	 * 用户名
	 * @Fields userName
	 */
	private String userName;
	/**
	 * 密码
	 * @Fields password
	 */
	private String password;
	
	public EMailAuthenticator(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * <p>Description: </p>
	 * <p>Title: getPasswordAuthentication</p>
	 * @return
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(userName, password);
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * <p>Description: </p>
	 * <p>Title: toString</p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EMailAuthenticator [userName=" + userName + ", password=" + password + "]";
	}
}
