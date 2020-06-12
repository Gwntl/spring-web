package com.email.custom;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @Description: 邮件发送服务
 * @ClassName: EMailSendProvider
 * @author: wntl
 * @date: 2020年6月9日 下午3:14:46
 */
public class EMailSendProvider {
	/**
	 * 发送者邮箱账号
	 * @Fields senderAccount
	 */
	private static String senderAccount = "18860877642@163.com";
	/**
	 * 发送方邮箱授权码
	 * @Fields password
	 */
	private static String senderPassword = "UUUYVWELJLNRVIQZ";
	/**
	 * 接收方邮箱
	 * @Fields receptAddress
	 */
	private static String receptAddress = "wangzian@csii.com.cn";
	
	public static void main(String[] args) {
		EMailAuth mailAuth = new EMailAuth(senderAccount, senderPassword);
		Properties props = new Properties();
		try {
			props.load(EMailSendProvider.class.getResourceAsStream("e-mail.properties"));
			Session session = Session.getInstance(props, mailAuth);
			session.setDebug(true);
			MimeMessage msg = new MimeMessage(session);
			//设置发送者信息
			msg.setFrom(new InternetAddress(mailAuth.getUsername(), "wntl", "UTF-8"));
			//设置主题
			msg.setSubject("系统错误信息提醒", "UTF-8");
			msg.setContent("邮件正文","text/html;charset=UTF-8");
			//设置接收方
			msg.setRecipient(RecipientType.TO, new InternetAddress(receptAddress));
			msg.setSentDate(new Date());
			
			Transport.send(msg);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
	}
	
	static class EMailAuth extends Authenticator{
		private String username;
		private String password;
		
		public EMailAuth(String username, String password) {
			super();
			this.username = username;
			this.password = password;
		}
		
		/**
		 * @return
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new PasswordAuthentication(username, password);
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
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
		@Override
		public String toString() {
			return "EMailAuth [username=" + username + ", password=" + password + "]";
		}
	}
}
