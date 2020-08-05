package org.mine.aplt.notice;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Description: 短信通知
 * @ClassName: SmsNotification
 * @author: wntl
 * @date: 2020年6月29日 下午8:06:10
 */
public class SmsNotification {
	private String senderAccount = "18860877642@163.com";
	private String senderPassword = "UUUYVWELJLNRVIQZ";
	private String reciverAccount_1 = "1536183348@qq.com";
	private String reciverAccount = "wangzian@csii.com.cn";
	
	public void send(String msg, String reciptEmail) throws Exception{
		Properties props = new Properties();
		props.load(SmsNotification.class.getClassLoader().getResourceAsStream("config/properties/e-mail.properties"));
		Session session = Session.getInstance(props, new EMailAuthenticator(senderAccount, senderPassword));
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		//设置发送者
		message.setFrom(new InternetAddress(senderAccount,"系统发送","UTF-8"));
		//设置主题
		message.setSubject("TEST");
		//设置内容
		message.setContent(msg, "text/html;charset=UTF-8");
		//设置接收者
//		message.setRecipient(RecipientType.TO, new InternetAddress(reciptEmail, reciptEmail, "UTF-8"));
		message.setRecipient(RecipientType.TO, new InternetAddress(reciverAccount, reciverAccount, "UTF-8"));
		//设定发送时间
		message.setSentDate(new Date());
		//保存邮件
		message.saveChanges();
		Transport.send(message);
	}
	
	public void send(String message, List<Address> recipts, List<Address> ccs){
		Properties prop = new Properties();
		try {
			prop.load(SmsNotification.class.getClassLoader().getResourceAsStream("config/properties/e-mail.properties"));
			Session session = Session.getInstance(prop, new EMailAuthenticator(senderAccount, senderPassword));
			session.setDebug(true);
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(senderAccount,"系统发送","UTF-8"));
			mimeMessage.setSubject("系统信息通知");
			mimeMessage.setContent(message, "text/html;charset=UTF-8");
//			mimeMessage.setRecipients(RecipientType.TO, (Address[]) recipts.toArray());
			mimeMessage.setRecipients(RecipientType.TO, new Address[]{new InternetAddress(reciverAccount, reciverAccount, "UTF-8"), new InternetAddress(reciverAccount_1, reciverAccount_1, "UTF-8")});
			mimeMessage.setSentDate(new Date());
			mimeMessage.saveChanges();
			Transport.send(mimeMessage);
		} catch (IOException | MessagingException e) {
		}
	}
	
	public static void main(String[] args) throws Exception {
		SmsNotification notification = new SmsNotification();
		notification.send("", "");
		notification.send("sendMore", null, null);
	}
	
	class EMailAuthenticator extends Authenticator{
		private String username;
		private String password;
		
		public EMailAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
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
		protected PasswordAuthentication getPasswordAuthentication() {
			// TODO Auto-generated method stub
			return new PasswordAuthentication(username, password);
		}
		
		@Override
		public String toString() {
			return "EMailAuthenticator [username=" + username + ", password=" + password + "]";
		}
	}
}
