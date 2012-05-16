package mayaya.util.tool;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mayaya.vo.Announcement;
import mayaya.vo.enums.TodoType;
import mayaya.vo.model.EmailTask;

import org.apache.log4j.Logger;

public class EmailManager implements Runnable{
	private LinkedBlockingQueue<EmailTask> queue;
	private static Logger logger = Logger.getLogger("EmailManager");
	
	private static Session session;
	private static Transport transport;
	
	public static String d_email = "doudoumobileinfo@gmail.com", d_password = "mayaya@mayaya.com",
	d_host = "smtp.gmail.com", d_port = "465";
	
	public EmailManager(LinkedBlockingQueue<EmailTask> queue) {
		this.queue = queue;
		initEmail();
	}
	
	private void initEmail(){
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties();
		// Properties props=System.getProperties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		if (!"".equals(d_port)) {
			props.put("mail.smtp.port", d_port);
			props.put("mail.smtp.socketFactory.port", d_port);
		}
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.debug", "false");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.ssl",  "false");
		
		try {
			session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(d_email, d_password);
				}
			});
			session.setDebug(true);
		} catch (Exception mex) {
			mex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Thread t = new Thread(new EmailManager(null));
		t.start();
	}
	@Override
	public void run() {
		try {
			EmailTask task = null;			
			while(true){
				task = queue.take();
				sendMail(task);
				logger.info(String.format("Email Manager ---> send mail %s ", task));							
			}			
		} catch (InterruptedException e) {
			logger.error("EmailManager is going to be terminated for caught of InterruptedException exception",e);
		}
//		for (int i = 0; i < 1; i++) {
//			EmailTask email = new EmailTask();
//			email.setTo(new String[]{"imagine_gc@msn.com","imagine.gaochao@gmail.com","510809978@qq.com"});
//			
//			email.setFromUser("Chao");
//			email.setTodoType(TodoType.Announcement);
//			Announcement p = new Announcement();
//			p.setAtChildList("Mayaya,Jian,Qiu,Song");
//			p.setContent("这个是测试通知内容asd fdsasdfawetadgasdfwaefasdfasdfawer");
//			p.setPublishTime(new Date());
//			p.setUserId(40);
////			Picture p = new Picture();
////			p.setAtChildList("Mayaya,Jian,Qiu,Song");
////			p.setPictureURL("upload/Pic_20120313144832.jpg");
////			p.setDescription("这个是标题描述");
////			Event e = new Event();
////			e.setAtChildList("Mayaya,Jian,Qiu,Song");
////			e.setBeginTime(new Date());
////			e.setEndTime(new Date());
////			e.setTitle("这个是事件title");
////			e.setContent("这个是事件内容，描述");
//			email.setContent(p);
//			sendMail(email);
//		}
	}
	
	public synchronized static boolean sendMail(EmailTask email) {
		try{
			transport = session.getTransport("smtps");
			transport.connect(d_host, d_email, d_password);
			
			MimeMessage msg = new MimeMessage(session);
			
			//Multipart mp = new MimeMultipart("mixed");
			Multipart mp = new MimeMultipart();
			msg.setContent(mp);

			mp.addBodyPart(EmailContentProducer.getContent(email));
			
			msg.setSubject(EmailContentProducer.getTitle(email));
			msg.setFrom(new InternetAddress("DouDouMobile"));
			for (int i = 0; i < email.getTo().length; i++) {
				String emailAdd = email.getTo()[i];
				if (null != emailAdd && !"".equals(emailAdd) && emailAdd.contains("@")) {
					logger.info("Sending Email to " + emailAdd);
					msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailAdd));					
				}
			}
			
			msg.saveChanges();
			
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			return true;
		} catch ( Exception e ) {
			e.printStackTrace();
			return false;
		}
		
	}

}
