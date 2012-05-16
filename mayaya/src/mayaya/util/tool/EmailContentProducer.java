package mayaya.util.tool;

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

import mayaya.system.ICSCalendarManager;
import mayaya.util.MayayaConfig;
import mayaya.vo.Announcement;
import mayaya.vo.Event;
import mayaya.vo.Picture;
import mayaya.vo.model.EmailTask;

public class EmailContentProducer {
	
	static MayayaConfig config = MayayaConfig.getConfig();
	
	public static BodyPart getContent(EmailTask task) {
		BodyPart content = new MimeBodyPart();
		try {
			Multipart bodyMultipart = new MimeMultipart("mixed");
			content.setContent(bodyMultipart);
			BodyPart htmlPart = new MimeBodyPart();
			bodyMultipart.addBodyPart(htmlPart);
			switch(task.getTodoType()) {
			case PicTag: 
				htmlPart.setContent(getPicEmailContent(task),"text/html;charset=GBK");
				break;
			case EvtTag: 
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
			case Announcement: 
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
		case PicTag:
			title = task.getFromUser() + " 发送的新照片 - DouDou兜兜";
			break;
		case EvtTag:
			title = task.getFromUser() + " 发送的新日历通知 - DouDou兜兜";
			break;
		case Announcement:
			title = task.getFromUser() + " 发送的新通知 - DouDou兜兜";
			break;
		}

		return title;
	}
	
	private static String getPicEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		addHTMLHead(sb);
		sb.append("<title>A New Photo</title>");
		sb.append("<meta name=\"keywords\" content=\"DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events\" /> ");
		sb.append("<meta name=\"description\" content=\"A new photo from DouDou!\" /> ");
		sb.append("<link rel=\"shortcut icon\"  href=\"favicon.ico\" /> ");
		//addPhotoCSS(sb);
		addPhotoBody(sb, task);
		addFooter(sb);
		return sb.toString();
	}
	
	private static String getAnnounceEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		addHTMLHead(sb);
		sb.append("<title>A New Message</title>");
		sb.append("<meta name=\"keywords\" content=\"DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events\" /> ");
		sb.append("<meta name=\"description\" content=\"A new message from DouDou!\" /> ");
		sb.append("<link rel=\"shortcut icon\"  href=\"favicon.ico\" /> ");
		//addMsgCSS(sb);
		addMsgBody(sb, task);
		addFooter(sb);
		
		return sb.toString();
	}
	
	
	private static void addMsgBody(StringBuffer sb, EmailTask task) {
		Announcement a = (Announcement)task.getContent();
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style=\"margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;border-width: 1px; border-style: solid; border-color: rgb(125,125,125); \">");
		sb.append("<div style=\"width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;height: 85px;clear: both;background-image: url("+config.getDouDouIP()+"/img/img_emailhead.png);background-repeat: no-repeat;\">");
		sb.append("</div>");
		sb.append("<div style=\"width: 590px;margin-left: 0px;margin-right: 0px;margin-bottom: 0px;height: auto;\">");
		sb.append("<div style=\"margin-top: 24px;margin-bottom: 24px;\"><span id=\"name\" style=\"color:#b4da39;font-size:16pt;margin-left: 24px;margin-right: 5px;\">");
		sb.append(task.getFromUser());
		sb.append("</span><span style=\"color: #272727;font-size: 16pt;\"> sent you a new message.</span></div><div>" +
				"<div style=\"float: left;text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;height: auto;margin-bottom: 16px;\">" +
				"<div id=\"msg\" style=\"float: left;color: #464646;font-size: 24pt;text-align: left;margin-top: 20px;margin-left: 15px;margin-bottom: 30px;width: 540px;\">\"" +
				a.getContent() + "\"</div><div style=\"text-align: left;font-size: 14pt;color: white;margin-left: 15px;margin-bottom: 20px;width: 500px;\">Tagged:<span id=\"child\" style=\"text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;\">" +
				a.getAtChildList() + "</span></div></div></div></div>");
	}
	
	private static String getEvtEmailContent(EmailTask task) {
		StringBuffer sb = new StringBuffer();
		addHTMLHead(sb);
		sb.append("<title>A New Event</title>");
		sb.append("<meta name=\"keywords\" content=\"DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events\" /> ");
		sb.append("<meta name=\"description\" content=\"A new event from DouDou!\" /> ");
		sb.append("<link rel=\"shortcut icon\"  href=\"favicon.ico\" /> ");
		//addEventCSS(sb);
		addEventBody(sb, task);
		addFooter(sb);
		
		return sb.toString();
	}
	
	private static void addHTMLHead(StringBuffer sb) {
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"); 
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"> ");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> ");
	}
	
	private static void addFooter(StringBuffer sb) {
		sb.append("<div style=\"width: 590px;margin-left: 0px;margin-right: 0px;margin-top: 0px;float: left\"><img src=\""+ config.getDouDouIP() + "/img/img_dotsp.png\" style=\"float: left;width: 590px;margin-bottom: 16px;\"/>");
		sb.append("<div style=\"font-size: 10pt;color: #aaaaaa;text-align:left;margin-left: 30px;margin-right: 20px;\"><p>\"DouDou\" app lets you stay informed about your kids' school life anywhere and anytime.  You can review and manage all your parent-teacher communications, keep track of school events and receive up-to-date photos of your child.  Your child's account has been set up by the school and your teacher has started using it.   Click " + 
				"<a href=\"http://www.doudoumobile.com\" target=\"_blank\" style=\" color: #df5a49;\">here</a> to download it to your iPhone/iPod or iPad for FREE. </p>");
		sb.append("</div><div style=\"font-size: 8pt;color: #aaaaaa;text-align:left;margin-left: 30px;margin-right: 20px;\"> <p>使用“兜兜”手机APP，即使再忙，您也可以参与到孩子每天的快乐成长中。您能够更加方便及时地查看孩子在学校的日历、通知和照片。您的学校已经为您的孩子设立了电子文件夹，学校的老师也已经开始使用“兜兜”给您发送消息。" +
				"<a href=\"http://www.doudoumobile.com\" target=\"_blank\" style=\" color: #df5a49;\">点击这里</a>免费下载“兜兜”到iPhone/iPod 或是iPad上。</p></div></div></div></body></html>");
	}
	
	private static void addEventBody(StringBuffer sb, EmailTask task){
		Event event = (Event)task.getContent();
		
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style=\"margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;border-width: 1px; border-style: solid; border-color: rgb(125,125,125);\">");
		sb.append("<div style=\"width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;height: 85px;clear: both;background-image: url(" + config.getDouDouIP() + "/img/img_emailhead.png);background-repeat: no-repeat;\">");
		sb.append("</div>");
		sb.append("<div style=\"width: 590px;margin-left: 0px;margin-right: 0px;margin-bottom: 0px;height: auto;\">");
		sb.append("<div style=\"margin-top: 24px;margin-bottom: 24px;\"><span id=\"name\" style=\"color:#b4da39;font-size:16pt;margin-top: 24px;margin-bottom: 24px;	margin-left: 24px;margin-right: 5px;\">");
		sb.append(task.getFromUser());
		sb.append("</span><span style=\"color: #272727;font-size: 16pt; margin-top: 24px;margin-bottom: 24px;\"> sent you a new event.</span>" +
				" </div> <div> <div style=\"float: left;text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;height: auto;margin-bottom: 16px;\"> <div style=\"float: left;color: #464646;font-size: 24pt;text-align: left;margin-top: 20px;margin-left: 15px;margin-bottom: 30px;width: 500px;\">" +
				" Title:<div id=\"title\" style=\"float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;\">");
		sb.append(event.getTitle());
		sb.append("</div></div><div style=\"float: left;color: #464646;font-size: 24pt;text-align: left;margin-left: 15px;margin-bottom: 10px;width: 500px;\">From:<div id=\"from\" style=\"float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;\">");
		sb.append(DateUtil.getInstance().getStringFromDate(event.getBeginTime()));
		sb.append("</div></div><div style=\"float: left;color: #464646;font-size: 24pt;text-align: left;margin-left: 15px;margin-bottom: 30px;width: 500px;\">To:<div id=\"to\" style=\"float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;\">");
		sb.append(DateUtil.getInstance().getStringFromDate(event.getEndTime()));
		sb.append("</div></div><div style=\"text-align: left;font-size: 14pt;color: white;margin-left: 15px;margin-bottom: 20px;width: 500px;\"> Tagged:<span id=\"child\" style=\"text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;\">");
		sb.append(event.getAtChildList());
		sb.append("</span></div></div></div></div>");
	}
	
	private static void addPhotoBody(StringBuffer sb, EmailTask task) {
		Picture p = (Picture)task.getContent();
		
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style=\"margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;border-width: 1px; border-style: solid; border-color: rgb(125,125,125);\">" +
				"<div style=\"width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;height: 85px;clear: both;background-image: url("+ config.getDouDouIP() + "/img/img_emailhead.png);background-repeat: no-repeat;\"></div>" +
						"<div style=\"width: 590px;margin-left: 0px;margin-right: 0px;margin-bottom: 0px;height: auto;\">" +
				"<div style=\"margin-top: 24px;margin-bottom: 24px;\"><span id=\"name\" style=\"color:#b4da39;font-size:16pt;margin-top: 24px;margin-bottom: 24px;	margin-left: 24px;margin-right: 5px;\">");
		sb.append(task.getFromUser());
		sb.append("</span><span style=\"color: #272727;font-size: 16pt; margin-top: 24px;margin-bottom: 24px;\"> posted a new photo of your child to Photowall.</span>");
		sb.append("</div><div style=\"background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;height: auto;margin-bottom: 16px;\"><div style=\"text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;	height: auto;margin-bottom: 16px;\">" +
				"<div id=\"caption\" style=\"text-align: center;color: #464646;font-size: 24pt;margin-top: 16px;margin-left: 16px;margin-bottom: 16px;width: 530px;\">");
		sb.append(p.getDescription());
		
		sb.append("</div><div id=\"photo\" style=\"text-align: center\"><img style=\"margin-bottom: 24px;max-width: 500px;\" src=\""+ config.getDouDouIP() + "/" +
				p.getPictureURL() + "\"/></div><div style=\"text-align: left\"><span style=\"font-size: 14pt;color: white;margin-left: 30px;margin-bottom: 20px;width: 500px;\">");
		sb.append("Tagged:<span id=\"child\" style=\"text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;\">" +
				p.getAtChildList() + "</span></div></div></div></div>");
	}
	
	private static void addEventCSS(StringBuffer sb) {
		sb.append("<style type=\"text/css\">");
		sb.append(".body{margin:0 auto;background-color:#c3c3c3;color:#949494;font-family:Arial,\"Arial Black\", Gadget, sans-serif;font-family:Myriad Pro,Verdana, Geneva, sans-serif;font-family: \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size:12px;width: 670px;height: auto;}");
		sb.append(".main-container{margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;}");
		sb.append(".header{width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;	height: 85px;clear: both;background-image: url("+ config.getDouDouIP() + "/img/img_emailhead.png);background-repeat: no-repeat;}");
		sb.append(".body{width: 590px;margin-left: 0px;	margin-right: 0px;	margin-bottom: 0px;	height: auto;}");
		sb.append(".footer{width: 590px;margin-left: 0px;margin-right: 0px;	margin-top: 0px;float: left	}");
		sb.append(".content{float: left;text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;height: auto;margin-bottom: 16px;}");
		sb.append(".name{float: left;color: #b4da39;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;	font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;");
		sb.append("margin-top: 24px;margin-bottom: 24px;margin-left: 24px;margin-right: 5px;}");
		sb.append(".notice{float: left;color: #272727;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;margin-top: 24px;	margin-bottom: 24px;}");
		sb.append(".title{float: left;color: #464646;font-size: 24pt;text-align: left;margin-top: 20px;	margin-left: 15px;	margin-bottom: 30px;width: 500px;}");
		sb.append(".titlecon{float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;}");
		sb.append(".from{float: left;color: #464646;font-size: 24pt;text-align: left;margin-left: 15px;	margin-bottom: 10px;width: 500px;}");
		sb.append(".fromtime{float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;}");
		sb.append(".to{float: left;	color: #464646;	font-size: 24pt;text-align: left;margin-left: 15px;	margin-bottom: 30px;width: 500px;}");
		sb.append(".totime{float: right;color: #464646;font-size: 24pt;text-align: left;margin-top: 0px;margin-right: 0px;width: 400px;}");
		sb.append(".tag{float: left;font-size: 14pt;color: white;margin-left: 15px;margin-bottom: 20px;	width: 500px;}");
		sb.append(".taggedchild{float: right;text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;}");
		sb.append(".img_dot{float: left;  width: 590px;  margin-bottom: 16px;}");
		sb.append(".ance_eng{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 10pt;color: #aaaaaa;text-align:left;margin-left: 56px;}");
		sb.append(".ance_mrd{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 8pt;color: #aaaaaa;text-align:left;margin-left: 50px;}");
		sb.append(".linkcolor{color: #df5a49;}");
		sb.append("</style>");
	}
	
	private static void addMsgCSS(StringBuffer sb) {
		sb.append("<style type=\"text/css\">");
		sb.append(".body{margin:0 auto;background-color:#c3c3c3;color:#949494;font-family:Arial,\"Arial Black\", Gadget, sans-serif;font-family:Myriad Pro,Verdana, Geneva, sans-serif;font-family: \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size:12px;width: 670px;height: auto;}");
		sb.append(".main-container{margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;}");
		sb.append(".header{width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;	height: 85px;clear: both;background-image: url("+ config.getDouDouIP() + "/img/img_emailhead.png);background-repeat: no-repeat;}");
		sb.append(".body{width: 590px;margin-left: 0px;	margin-right: 0px;	margin-bottom: 0px;	height: auto;}");
		sb.append(".footer{width: 590px;margin-left: 0px;margin-right: 0px;	margin-top: 0px;float: left	}");
		sb.append(".content{float: left;text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;height: auto;margin-bottom: 16px;}");
		sb.append(".name{float: left;color: #b4da39;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;	font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;");
		sb.append("margin-top: 24px;margin-bottom: 24px;margin-left: 24px;margin-right: 5px;}");
		sb.append(".notice{float: left;color: #272727;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;margin-top: 24px;	margin-bottom: 24px;}");
//		msg
		sb.append(".msg{float: left;color: #464646;	font-size: 24pt;text-align: left;margin-top: 20px;margin-left: 15px;margin-bottom: 30px;width: 540px;}");
// 		msg		
		sb.append(".tag{float: left;font-size: 14pt;color: white;margin-left: 15px;margin-bottom: 20px;	width: 500px;}");
		sb.append(".taggedchild{float: right;text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;}");
		sb.append(".img_dot{float: left;  width: 590px;  margin-bottom: 16px;}");
		sb.append(".ance_eng{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 10pt;color: #aaaaaa;text-align:left;margin-left: 56px;}");
		sb.append(".ance_mrd{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 8pt;color: #aaaaaa;text-align:left;margin-left: 50px;}");
		sb.append(".linkcolor{color: #df5a49;}");
		sb.append("</style>");
	}
	
	
	private static void addPhotoCSS(StringBuffer sb) {
		sb.append("<style type=\"text/css\">");
		sb.append(".body{margin:0 auto;background-color:#c3c3c3;color:#949494;font-family:Arial,\"Arial Black\", Gadget, sans-serif;font-family:Myriad Pro,Verdana, Geneva, sans-serif;font-family: \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size:12px;width: 670px;height: auto;}");
		sb.append(".main-container{margin:0 auto;padding-top:0px;width: 590px;clear:both;overflow: hidden;background-color: white;}");
		sb.append(".header{width: 590px;margin-left:0px;margin-right:0px;margin-bottom:0px;	height: 85px;clear: both;background-image: url("+ config.getDouDouIP() + "/img/img_emailhead.png);background-repeat: no-repeat;}");
		sb.append(".body{width: 590px;margin-left: 0px;	margin-right: 0px;	margin-bottom: 0px;	height: auto;}");
		sb.append(".footer{width: 590px;margin-left: 0px;margin-right: 0px;	margin-top: 0px;float: left	}");
		sb.append(".name{float: left;color: #b4da39;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;	font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;");
		sb.append("margin-top: 24px;margin-bottom: 24px;margin-left: 24px;margin-right: 5px;}");
		sb.append(".notice{float: left;color: #272727;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";font-size: 16pt;margin-top: 24px;	margin-bottom: 24px;}");
//		Photo
		sb.append(".content{float: left;text-align: center;background-color: #e8e8e8;width: 570px;margin-left: 10px;margin-right: 10px;	height: auto;margin-bottom: 16px;}");
		sb.append(".caption{float: left;text-align: center;font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";" +
				"color: #464646;font-size: 24pt;margin-top: 16px;margin-left: 16px;	margin-bottom: 16px;width: 530px;}");
		sb.append(".photo{margin-bottom: 24px;max-width: 500px;}");
//		Photo				
		sb.append(".tag{float: left;font-size: 14pt;color: white;margin-left: 15px;margin-bottom: 20px;	width: 500px;}");
		sb.append(".taggedchild{float: right;text-align: left;font-size: 14pt;color: #b4da39;margin-left: 10px;width: 400px;}");
		sb.append(".img_dot{float: left;  width: 590px;  margin-bottom: 16px;}");
		sb.append(".ance_eng{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 10pt;color: #aaaaaa;text-align:left;margin-left: 56px;}");
		sb.append(".ance_mrd{font-family:\"Arial Black\", \"Gadget\", sans-serif;font-family:\"Myriad Pro\",\"Verdana\", \"Geneva\", sans-serif;font-family: \"Helvetica Neue\", \"Arial\", \"微软雅黑\" \"黑体\" \"宋体\";");
		sb.append("font-size: 8pt;color: #aaaaaa;text-align:left;margin-left: 50px;}");
		sb.append(".linkcolor{color: #df5a49;}");
		sb.append(".center{text-align: center;}");
		sb.append("</style>");
	}
}
