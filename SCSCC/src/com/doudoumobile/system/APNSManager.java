package com.doudoumobile.system;

import org.directwebremoting.util.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;


public class APNSManager{
	
	private static APNSManager instance = new APNSManager();
	
	private ApnsService service;
	private Logger log = Logger.getLogger(getClass());
	
	private APNSManager() {
		String cerPath = "D:/Certificates.p12";
		service = APNS.newService().withCert(cerPath, "scscc").withSandboxDestination().build();
	}
	
	public static APNSManager getInstance() {
		return instance;
	}
	
	public void pushOffline(String token, String content) {
		if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
			log.info("Going to push " + content + " to token :" + token);
			String payload = APNS.newPayload().alertBody(content).build();
			service.push(token, payload);
		}
		else {
			log.warn("token = " + token);
		}
	}
//	
//	// 图片Tag
//	public void pushPicTaged(int picId, List<String> tokenList, String fromUser) {
//		if (null!= tokenList && tokenList.size() > 0) {
//			for (String token : tokenList) {
//				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
//					PayloadBuilder pb = APNS.newPayload();
//					pb.alertBody(fromUser + " sent you a new photo");
//					pb.customField("ContentId", picId);
//					pb.customField("NotificationType", "PicTag");
//					String payload = pb.build();
//					service.push(token, payload);
//				}
//			}
//		}
//	}
//	
//	// 事件Tag
//	public void pushEvtTaged(int evtId, List<String> tokenList, String fromUser) {
//		if (null!= tokenList && tokenList.size() > 0) {
//			for (String token : tokenList) {
//				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
//					PayloadBuilder pb = APNS.newPayload();
//					pb.alertBody(fromUser + " sent you a new event");
//					pb.customField("ContentId", evtId);
//					pb.customField("NotificationType", "EvtTag");
//					String payload = pb.build();
//					service.push(token, payload);			
//				}
//			}
//		}
//	}
//	
//	public void pushAnnouncement(int announcementId, List<String> tokenList, String fromUser) {
//		if (null!= tokenList && tokenList.size() > 0) {
//			for (String token : tokenList) {
//				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
//					PayloadBuilder pb = APNS.newPayload();
//					pb.alertBody(fromUser + " sent you a new message");
//					pb.customField("ContentId", announcementId);
//					pb.customField("NotificationType", "Announcement");
//					String payload = pb.build();
//					service.push(token, payload);			
//				}
//			}
//		}
//	}
//	
//	private void push(PushVO pushVO, List<String> deviceTokenList) {
//		switch (pushVO.getTodoType()) {
//		case Comment:
//			pushComment(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
//			break;
//		case PicTag:
//			pushPicTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
//			break;
//		case EvtTag:
//			pushEvtTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
//			break;
//		case Announcement:
//			pushAnnouncement(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
//		}
//	}
//
//	
////	public static void main(String[] args) {
////		ApnsService service = APNS.newService().withCert("d:/Certificates.p12", "scscc").withSandboxDestination().build();
////		PayloadBuilder pb = APNS.newPayload();
////		pb.alertBody("You have a new Tagged");
////		pb.customField("secret", "what do you think?");
////		pb.customField("secret2", "what do you think2?");
////		String payload = pb.build();
////		
//////		String payload = APNS.newPayload().badge(3).customField("secret", "what do you think?")
//////        .localizedKey("GAME_PLAY_REQUEST_FORMAT")
//////        .localizedArguments("Jenna", "Frank")
//////        .actionKey("Play").build();
////		System.out.println(payload);
////        String token = "af71e26c 93f60a25 f4cc3f49 0ecf72fa c3f55585 b180ecfe 48e3bd3c 6386f39f";
////        service.push(token, payload);
//	}
	
}
