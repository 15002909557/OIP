package com.beyondsoft.expensesystem.time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.domain.system.SysUser;

public class SendMail {
	//修改标题“OIP Reminder” 为 “IBS OIP System Reminder” FWJ 2013-04-28
	static String subject = "IBS OIP System Reminder";
	
	static String host = "bj-smtp.beyondsoft.com";

	
	
	public static void sendMailTo(List<SysUser> sendlist ,String fromWhere ,String password,String fromWho){
		SendMailAuthenticator au = new SendMailAuthenticator();
		try {
			password = new DESPlus().decrypt(password);
			System.out.println("password="+password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		au.check(fromWho, password); //验证用户信息
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);//设置发送主机
		props.put("mail.smtp.auth", "true"); //用户验证须通过
		// Get session
		Session session = Session.getDefaultInstance(props, au);
		try
		{
			InternetAddress addfr = new InternetAddress(fromWhere);
			addfr.setPersonal("OIP");
			//格式化日期
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			for(int i=0;i<sendlist.size();i++){
				MimeMessage message = new MimeMessage(session);
				message.setFrom(addfr);//设置发件人
				message.setSubject(subject); //设置邮件主题
				//设置邮件内容：发html格式
				MimeMultipart multipart = new MimeMultipart("related");
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
				mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
				mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
				mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
				mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
				mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
				CommandMap.setDefaultCommandMap(mc);
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				System.out.println("System is mailing to ：" +sendlist.get(i).getEmail()+ " ......");
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendlist.get(i).getEmail()));//设置收件人
				String text="<font face='Arial'><strong>Hi "+sendlist.get(i).getUserName()+",<br/><br/>"
				             +"Your OIP password will be expired "
				             +"<font color='red' face='Arial'>"+(90-sendlist.get(i).getExpireDay())+"</font>"
				             +" days later. Please change it ASAP. Otherwise, you will not be granted access to the system.</strong><br/>Access Address: https://oip.beyondsoft.com:7001/BPD/system/login.jsp<br/>"
				             +"<font color='red' size='2'>This mail is sent by OIP automatically, please don't reply to it. Any problems or suggestions, please feel free to contact OIP: (oip@beyondsoft.com). Thank you!</font>"
				             +"<br/><br/><font size='2'>----"+sdf.format(new Date())+"----</font></font>";
				htmlBodyPart.setContent(text, "text/html;charset=gb2312");
				multipart.addBodyPart(htmlBodyPart);
				message.setContent(multipart);	
				@SuppressWarnings("unused")
				Transport transport = session.getTransport("smtp");//设置发送服务协议
				Transport.send(message);
				System.out.println("successfully!");
			}
		}catch (Exception e) {  
            e.printStackTrace(); 
            new BugXmlTimer().timerStop();
            System.out.println("Exception:"+e);
        }  
		
		
	}
	
	/**
	 * 
	 * @param sendlist
	 * @param fromWhere
	 * @param password
	 * @param fromWho
	 * @author hanxiaoyu01
	 * hanxiaoyu01 2013-02-21
	 * 每新建一个Project就给Data Approver发送通知
	 * 
	 */
	
	public static void sendMailTo2(List<SysUser> sendlist ,String fromWhere ,String password,String fromWho,Project project){
		SendMailAuthenticator au = new SendMailAuthenticator();
		try {
			password=new DESPlus().decrypt(password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		au.check(fromWho, password); //验证用户信息
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);//设置发送主机
		props.put("mail.smtp.auth", "true"); //用户验证须通过
		// Get session
		Session session = Session.getDefaultInstance(props, au);
		try
		{
			InternetAddress addfr = new InternetAddress(fromWhere);
			addfr.setPersonal("OIP");
			//格式化日期
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			for(int i=0;i<sendlist.size();i++){
				MimeMessage message = new MimeMessage(session);
				message.setFrom(addfr);//设置发件人
				message.setSubject(subject); //设置邮件主题
				//设置邮件内容：发html格式
				MimeMultipart multipart = new MimeMultipart("related");
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
				mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
				mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
				mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
				mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
				mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
				CommandMap.setDefaultCommandMap(mc);
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				System.out.println("System is mailing to ：" +sendlist.get(i).getEmail()+ " ......");
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendlist.get(i).getEmail()));//设置收件人
				String text="<font face='Calibri'><strong>Hi "+sendlist.get(i).getUserName()+",<br/><br/>"
				             +"Project <font color='red' face='Calibri'>"+project.getProjectID()+"</font> is created, need your confirm! "
				             +"</strong><br/><br/>"
				             +"<table style='font-family:Calibri;'>"
				             +"<tr><td align='right' width='100px;'>User:&nbsp;&nbsp;</td><td>"+project.getUsername()+"</td></tr>"
				             +"<tr><td align='right'>Component:&nbsp;&nbsp;</td><td>"+project.getComponent()+"</td></tr>"
				             +"<tr><td align='right'>Product:&nbsp;&nbsp;</td><td>"+project.getProduct()+"</td></tr>"
				             +"<tr><td align='right'>WBS:&nbsp;&nbsp;</td><td>"+project.getWBS()+"</td></tr>"
				             +"<tr><td align='right'>SkillLevel:&nbsp;&nbsp;</td><td>"+project.getSkillLevel()+"</td></tr>"
				             +"<tr><td align='right'>Location:&nbsp;&nbsp;</td><td>"+project.getLocation()+"</td></tr>"
				             +"<tr><td align='right'>OTType:&nbsp;&nbsp;</td><td>"+project.getOTType()+"</td></tr>"
				             +"<tr><td align='right'>Group:&nbsp;&nbsp;</td><td>"+project.getGroupName()+"</td></tr>"
				             +"</table><br/>Access Address: https://oip.beyondsoft.com:7001/BPD/system/login.jsp<br/><br/>"
				             +"<font color='red' size='2'>This mail is sent by OIP automatically, please don't reply to it. Any problems or suggestions, please feel free to contact OIP: (oip@beyondsoft.com). Thank you!</font>"
				             +"<br/><br/><font size='2'>----"+sdf.format(new Date())+"----</font></font>";
				htmlBodyPart.setContent(text, "text/html;charset=gb2312");
				multipart.addBodyPart(htmlBodyPart);
				message.setContent(multipart);	
				@SuppressWarnings("unused")
				Transport transport = session.getTransport("smtp");//设置发送服务协议
				Transport.send(message);
				System.out.println("successfully!");
			}
		}catch (Exception e) {  
            e.printStackTrace(); 
            new BugXmlTimer().timerStop();
            System.out.println("Exception:"+e);
        }  
		
		
	}
	
/**
 * 更新monthly expense 的cost或者PO balance时发送邮件给对应的PM
 * @param sendlist
 * @param fromWhere
 * @param password
 * @param fromWho
 * @param po
 */
	//added on 2013-03-29	
	public static void sendMailTo3(List<String> sendlist ,String fromWhere ,String password,String fromWho,ProjectOrder po){
		SendMailAuthenticator au = new SendMailAuthenticator();
		try {
			password=new DESPlus().decrypt(password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		au.check(fromWho, password); 
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true"); 
		// Get session
		Session session = Session.getDefaultInstance(props, au);
		try
		{
			InternetAddress addfr = new InternetAddress(fromWhere);
			addfr.setPersonal("OIP");
			for(int i=0;i<sendlist.size();i++)
			{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(addfr);
				message.setSubject(subject);
				MimeMultipart multipart = new MimeMultipart("related");
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
				mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
				mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
				mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
				mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
				mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
				CommandMap.setDefaultCommandMap(mc);
				MimeBodyPart htmlBodyPart = new MimeBodyPart();
				System.out.println("System is mailing to " +sendlist.get(i)+ " ......");
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendlist.get(i)));
				String text="<div style='font-family:Calibri; font-size:13px; line-height:24px;'><p><font color='green' size='3'>Balance value of [PO: "+po.getPONumber()
				+"] is lower than its Alert value!</font></p>"+
				"<hr /><p><H4>DETAILS:</H4></p>"
				+"<ul><li>PO ID: "+po.getPOID()+"</li><li>PO Number: "+po.getPONumber()+"</li><li>PO Budget($): "+po.getPOAmount()+"</li><li>PO Used Balance($): "+po.getPoUsed()+"</li><li>PO Remaining Balance($): "+po.getPoBalance()+"</li><li>PO Alert Balance($): "+po.getAlertBalance()+"</li><li>Lock/Unlock: "+po.getLock()+"</li><li>Status: "+po.getPOStatus()+"</li><li>"
				+"Start Date: "+po.getPOStartDate()+"</li><li>End Date: "+po.getPOEndDate()+"</li>"+
				"<li>Manager: "+po.getPOManager()+"</li><li>Description: "+po.getDescription()+"</li></ul>"
				+"<br />Access Address: https://oip.beyondsoft.com:7001/BPD/system/login.jsp<hr /><p><font color='red'>This mail is sent by the system automatically, please don't reply it.<br />"
				+"</font></p></div>";
				htmlBodyPart.setContent(text, "text/html;charset=gb2312");
				multipart.addBodyPart(htmlBodyPart);
				message.setContent(multipart);	
				@SuppressWarnings("unused")
				Transport transport = session.getTransport("smtp");
				Transport.send(message);
				System.out.println("successfully!");
			}
		}catch (Exception e) 
		{  
            e.printStackTrace(); 
            new BugXmlTimer().timerStop();
            System.out.println("Exception:"+e);
        }  
		
	}
	
}