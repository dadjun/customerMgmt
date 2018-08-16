package com.customer.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.customer.model.UserBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailUtil {
	private static final Log logger =   LogFactory.getLog(SendMailUtil.class);
	private static String host  = "";
	static String auth = "";
	static String contentType =  "text/html";
	static String subject = "";
	static String propFrom = "";
	static String psw = "";

	static {
		/*Properties propMail = FileUtil.loadProperties("mail.properties");
		contentType = propMail.getProperty("contentType");
		auth = propMail.getProperty("auth");
		psw = propMail.getProperty("psw");
		host = propMail.getProperty("mailhost");*/
	}

	public static void setSenderParam(UserBean user,String myContentType){
		host = user.getMailHost();
		psw = user.getMailPsw();
		auth = user.getEmail();
		contentType =myContentType;
	}
	public static boolean sendMail(String from,String to,String subject,String Content){
		try{
			if(from == null || from.isEmpty()) {
				from = auth;
			}
			Properties props = System.getProperties();

			//使用SSL，企业邮箱必需！
			//开启安全协议
			MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
			}
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.host", host);
			props.setProperty("mail.smtp.auth", "true");
			// 获取默认session对象
			Session session = Session.getDefaultInstance(props,new Authenticator(){
				public PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(auth, psw); //发件人邮件用户名、密码
				}
			});

			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
			// 创建默认的 MimeMessage 对象。
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));
			// Set Subject: 头字段
			message.setSubject(subject);
			// 发送 HTML 消息, 可以插入html标签
			message.saveChanges();
			message.setContent(Content,contentType);
			// 发送消息
			Transport.send(message);
			logger.error("from " + from + "----to: " + to + "  --success！！ ");
			return true;
		}catch (MessagingException mex) {
			logger.error("from " + from + "----to: " + to + "-- faild！！");
			mex.printStackTrace();
		}
		return false;
	}

}