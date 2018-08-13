package com.homiest.customer.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
  
public class SendMailUtil {
	private static final Log logger =   LogFactory.getLog(SendMailUtil.class);
	private static String host  = "";
	static boolean auth = false;
	static String contentType =  "text/html";
	static String subject = "";
	static String propFrom = "";
	static {
		host =FileUtil.readProperties("mail.properties", "mailhost");
		auth = Boolean.parseBoolean(FileUtil.readProperties("mail.properties", "auth"));
		contentType = FileUtil.readProperties("mail.properties", "contentType");
		propFrom = FileUtil.readProperties("mail.properties", "from");
	}
	
	public static void sendMail(String from,String to,String subject,String Content){
		  try{
			  if(from == null || from.isEmpty()) {
				  from = propFrom;
			  }
			   Properties properties = System.getProperties();
			      // �����ʼ�������
		      properties.setProperty("mail.smtp.host", host);
		      //properties.put("mail.smtp.auth", "true");
		      // ��ȡĬ��session����
		     Session session = Session.getDefaultInstance(properties,new Authenticator(){
		          public PasswordAuthentication getPasswordAuthentication()
		          {
		           return new PasswordAuthentication("595436259@qq.com", "mwiwtwevxhlcbajc"); //�������ʼ��û���������
		          }
		         }); 
	      	// ����Ĭ�ϵ� MimeMessage ����
		      
		      
		      
	         MimeMessage message = new MimeMessage(session);
	         // Set From: ͷ��ͷ�ֶ�
	         message.setFrom(new InternetAddress(from));
	         // Set To: ͷ��ͷ�ֶ�
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	         // Set Subject: ͷ�ֶ�
	         message.setSubject(subject);
	         // ���� HTML ��Ϣ, ���Բ���html��ǩ
	 
	         message.setContent(Content,contentType);
	         logger.error(contentType +" " +  host );
	         // ������Ϣ
	         Transport.send(message);
	         logger.error("send successful");
			  
	   }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
 
}