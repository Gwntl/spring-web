package com.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EMailConstant {
	public static final String MAIL_USER = "*******"; // 公共邮箱
	
	public static final String MAIL_PWD = "*******"; // 公共邮箱密码
	
	public static final boolean MAIL_IFDEBUG = true;
	
	public static final String MAIL_CONTENT_CHARSET = "text/html;charset=utf-8";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
	
	public static final String MAIL_TITLE = "****8" + sdf.format(new Date());// 邮件标题

//	public static Object getMailContent(List<MessageEntity> list) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(
//				"<div style='width:1024px;height:auto;margin:0px auto;background-color:#66CCFF;font-size:14px;font-family:微软雅黑;border-radius:5px;padding:5px;'><center><h1>");
//		sb.append("</h1></center><div style='margin-left:20px;margin-bottom:10px;'><b>邮件标题</b><br/><br/>");
//		sb.append("<table border='1'>");
//		for (int i = 0; i < list.size(); i++) {
//			sb.append("<tr>" + "<td>" + list.get(i).getName() + "</td>" + "<td>" + list.get(i).getpName() + "</td>"
//					+ "<td>" + list.get(i).getAge() + "</td>" + "<td>" + list.get(i).getAddress() + "</td>" + "</tr>");
//		}
//		sb.append("</table>");
//		sb.append("</div></div>");
//		return sb.toString();
//	}
}
