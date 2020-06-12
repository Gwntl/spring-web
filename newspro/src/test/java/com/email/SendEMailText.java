package com.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mine.aplt.exception.MineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEMailText {
	private static final Logger logger = LoggerFactory.getLogger(SendEMailText.class);

	public static String senderAdress = "18860877642@163.com";
	
	public static String recipientAdress = "1536183348@qq.com";
	
	public static String senderAccount = "18860877642@163.com";
	//密码使用授权码
	public static String senderPassword = "UUUYVWELJLNRVIQZ";
	/** 
     * 邮件服务器登录验证 
     */  
//    private transient static EMailAuthenticator authenticator;
	
	public static MimeMessage getMimeMessage(Session session){
		MimeMessage msg = null;
		try {
//			authenticator = new EMailAuthenticator(senderAccount, senderPassword);
			//创建一封邮件的实例
			msg = new MimeMessage(session);
			//设置发送者邮箱地址
//			msg.setSender(new InternetAddress(authenticator.getUserName(), "wntl", "UTF-8"));
			msg.setFrom(new InternetAddress(senderAccount, "wntl", "UTF-8"));
			/**
	         * 设置收件人地址(可以增加多个收件人、抄送、密送)
	         * MimeMessage.RecipientType.TO:发送
	         * MimeMessage.RecipientType.CC：抄送
	         * MimeMessage.RecipientType.BCC：密送
	         */
			msg.setRecipients(MimeMessage.RecipientType.TO, new Address[]{new InternetAddress(recipientAdress)});
			//设置主题
			msg.setSubject("");
			//设置邮件正文
			msg.setContent("邮件正文","text/html;charset=UTF-8");
			//设置邮件发送时间, 立即发送
			msg.setSentDate(new Date());
		} catch (MessagingException | UnsupportedEncodingException e) {
			logger.error("error message : {}", MineException.getStackTrace(e));
			throw new RuntimeException("运行时异常....");
		}
		return msg;
	}
	
	public static void main(String[] args) {
		//1.连接邮件服务器的参数配置
		Properties props = new Properties();
		//设置用户认证方式
		props.setProperty("mail.smtp.auth", "true");
		//设置传输协议
		props.setProperty("mail.transport.protocol", "smtp");
		//设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.163.com");
        //2、创建整个应用程序所需环境新信息的Session对象
        Session session = Session.getInstance(props);
      //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = null;
		try {
			transport = session.getTransport();
			//设置发件人的账户名和密码
			transport.connect(senderAccount, senderPassword);
			//发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(msg,msg.getAllRecipients());
			//如果只想发送给指定的人，可以如下写法
			//transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			//5、关闭邮件连接
			try {
				if (transport != null) transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
