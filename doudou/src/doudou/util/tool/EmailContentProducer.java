package doudou.util.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import doudou.system.ICSCalendarManager;
import doudou.vo.Message;
import doudou.vo.Event;
import doudou.vo.Picture;
import doudou.vo.model.EmailTask;

public class EmailContentProducer {
	public static BodyPart getContent(EmailTask task) {
		BodyPart content = new MimeBodyPart();
		try {
			Multipart bodyMultipart = new MimeMultipart("mixed");
			content.setContent(bodyMultipart);
			BodyPart htmlPart = new MimeBodyPart();
			bodyMultipart.addBodyPart(htmlPart);
			switch(task.getTodoType()) {
			case Picture: 
				htmlPart.setContent(getPicEmailContent(task),"text/html;charset=GBK");
				break;
			case Event: 
				BodyPart attachment = new MimeBodyPart();
				bodyMultipart.addBodyPart(attachment);
				String icsPath = ICSCalendarManager.getInstance().icsProducer((Event)task.getContent());
				//icsPath = "D:/picture/1.jpg";
				DataSource gifDs = new FileDataSource(new File(icsPath));
				DataHandler gifDh = new DataHandler(gifDs);
				attachment.setDataHandler(gifDh);
				attachment.setHeader("Content-Type", "application/ics");
				try {
					attachment.setFileName(MimeUtility.encodeWord(gifDs.getName(), "UTF-8",null));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				htmlPart.setContent(getEvtEmailContent(task),"text/html;charset=GBK");
				break;
			case Message: 
				htmlPart.setContent(getAnnounceEmailContent(task),"text/html;charset=GBK");
				break;
			}
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static String getTitle(EmailTask task) {
		String title = "";

		switch (task.getTodoType()) {
		case Picture:
			title = task.getFromUser() + " 发送的新照片 - DouDou兜兜";
			break;
		case Event:
			title = task.getFromUser() + " 发送的新日历通知 - DouDou兜兜";
			break;
		case Message:
			title = task.getFromUser() + " 发送的新通知 - DouDou兜兜";
			break;
		}

		return title;
	}
	
	private static String getPicEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		Picture p = (Picture)task.getContent();
		sb.append("Hi ");
		sb.append("<br /><br />");
		sb.append(task.getFromUser() + " 发送给 " + p.getAtChildList() + " 的新照片:<br />");
		sb.append("<br /><img src=\'www.doudoumobile.com/" + p.getPictureURL() + "' />");
		sb.append("<br /><br />照片的标题: " + p.getDescription()+"<br />相关的孩子: " + p.getAtChildList());
		sb.append("<br /><br />Hi，亲爱的家长！手机也可以接收这些信息哦！更加方便地查看孩子在学校的照片、日历、通知，就下载手机软件DouDou兜兜");
		sb.append("<br /><br /><br />DouDou兜兜");
		return sb.toString();
	}
	
	private static String getAnnounceEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		Message p = (Message)task.getContent();
		sb.append("Hi ");
		sb.append("<br /><br />");
		sb.append(task.getFromUser() + " 发送给 " + p.getAtChildList() + " 的新通知:<br />");
		sb.append("<br />" + p.getContent());
		sb.append("<br /><br />相关的孩子:" + p.getAtChildList());
		sb.append("<br /><br />Hi，亲爱的家长！手机也可以接收这些信息哦！更加方便地查看孩子在学校的照片、日历、通知，就下载手机软件DouDou兜兜");
		sb.append("<br /><br /><br />DouDou兜兜");
		return sb.toString();
	}
	
	private static String getEvtEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		Event p = (Event)task.getContent();
		sb.append("Hi ");
		sb.append("<br /><br />");
		sb.append(task.getFromUser() + " 发送给 " + p.getAtChildList() + " 的新日历通知:<br />");
		sb.append("<br />日历标题:" + p.getTitle());
		sb.append("<br />开始时间:" + DateUtil.getInstance().toFullString(p.getBeginTime()));
		sb.append("<br />结束时间:" + DateUtil.getInstance().toFullString(p.getEndTime()));
		sb.append("<br /><br />相关的孩子:" + p.getAtChildList());
		sb.append("<br /><br />Hi，亲爱的家长！手机也可以接收这些信息哦！更加方便地查看孩子在学校的照片、日历、通知，就下载手机软件DouDou兜兜");
		sb.append("<br /><br /><br />DouDou兜兜");
		return sb.toString();
	}
}
