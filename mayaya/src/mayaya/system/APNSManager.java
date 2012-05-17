package mayaya.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import mayaya.dao.DaoFactory;
import mayaya.dao.DeviceTokenDao;
import mayaya.dao.TodoDao;
import mayaya.util.MayayaConfig;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Todo;
import mayaya.vo.model.PushVO;

import org.apache.log4j.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

public class APNSManager implements Runnable{
	
	private ApnsService service;
	private LinkedBlockingQueue<PushVO> pushQueue;
	private static Logger logger = Logger.getLogger("APNSManager");
	private DeviceTokenDao deviceTokenDao;
	private TodoDao todoDao;
	
	private DatabaseDao myDatabaseDao;
	private MayayaConfig mayayaConfig = MayayaConfig.getConfig();
	
	public APNSManager(LinkedBlockingQueue<PushVO> pushQueue) {
		String cerPath = mayayaConfig.getAPNSCertificatePath();
		String cerDebugPath = mayayaConfig.getAPNSDebugPath();
		boolean apnsDebug = mayayaConfig.getAPNSDebug();
		if (apnsDebug) {
			service = APNS.newService().withCert(cerDebugPath, "mayaya@mayaya").withSandboxDestination().build();	
		} else {
			service = APNS.newService().withCert(cerPath, "mayaya@mayaya").withProductionDestination().build();
		}
		this.pushQueue = pushQueue;
		
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		deviceTokenDao = myDatabaseDao.getEntityDao(DeviceTokenDao.class);
		todoDao = myDatabaseDao.getEntityDao(TodoDao.class);
	}
	
	
//	public void pushFriend(String token, ) {
//		String payload = APNS.newPayload().alertBody("Can't be simpler than this!").build();
//        String token = "e8547d92 4a953878 9f9c64fe 2dd53dbd 33c14e74 db08a150 cdc7f73c e5eb3ecb";
//        service.push(token, payload);
//	}
	
//	// 自己评论过的内容有了新的评论
//	public void pushCommentOfComment(List<String> tokenList) {
//		if (null != tokenList && tokenList.size() > 0) {
//			for (String token : tokenList) {
//				String content = "You have a new comment.";
//				String payload = APNS.newPayload().alertBody(content).build();
//				service.push(token, payload);
//			}
//		}
//	}
	
	// 评论
	public void pushComment(int commentId, List<String> tokenList, String fromUser) {
		if (null != tokenList && tokenList.size() > 0) {
			for (String token : tokenList) {
				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new comment");
					pb.customField("ContentId", commentId);
					pb.customField("NotificationType", "Comment");
					String payload = pb.build();
					justPush(token, payload);
				}
			}
		}
	}
	
	// 图片Tag
	public void pushPicTaged(int picId, List<String> tokenList, String fromUser) {
		if (null!= tokenList && tokenList.size() > 0) {
			for (String token : tokenList) {
				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new photo");
					pb.customField("ContentId", picId);
					pb.customField("NotificationType", "PicTag");
					String payload = pb.build();
					System.out.println("push to deviceToken : " + token);
					justPush(token, payload);
				}
			}
		}
	}
	
	// 事件Tag
	public void pushEvtTaged(int evtId, List<String> tokenList, String fromUser) {
		if (null!= tokenList && tokenList.size() > 0) {
			for (String token : tokenList) {
				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new event");
					pb.customField("ContentId", evtId);
					pb.customField("NotificationType", "EvtTag");
					String payload = pb.build();
					justPush(token, payload);			
				}
			}
		}
	}
	
	public void pushAnnouncement(int announcementId, List<String> tokenList, String fromUser) {
		if (null!= tokenList && tokenList.size() > 0) {
			for (String token : tokenList) {
				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new message");
					pb.customField("ContentId", announcementId);
					pb.customField("NotificationType", "Announcement");
					String payload = pb.build();
					justPush(token, payload);
						
				}
			}
		}
	}
	private void justPush(String token, String pb){
		try{
			service.start();
			service.push(token,pb);		
			service.stop();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void push(PushVO pushVO, List<String> deviceTokenList) {
		try{
			deleteInactiveDevices();
			
			switch (pushVO.getTodoType()) {
			case Comment:
				pushComment(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
				break;
			case PicTag:
				pushPicTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
				break;
			case EvtTag:
				pushEvtTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
				break;
			case Announcement:
				pushAnnouncement(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}
	
	private void deleteInactiveDevices() {
		logger.info("deleteInactiveDevices!");
		Map<String,Date> result = service.getInactiveDevices();
		for (Map.Entry<String, Date> string : result.entrySet()) {
			String deviceTokenId = string.getKey();
			deviceTokenId = deviceTokenId.toLowerCase();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < deviceTokenId.length(); i++) {
				if (i >= 8 && i%8 == 0) {
					sb.append(" ");
				}
				sb.append(deviceTokenId.charAt(i));
			}
			System.out.println("To delete : " + sb.toString());
			deviceTokenDao.updateDeviceTokenInactive(sb.toString());
		}
	}

	@Override
	public void run() {
		System.out.println("APNSManager start succeed!");
		PushVO pushVO = null;			
		while(true){
			try {
				pushVO = pushQueue.take();
				for (int userId : pushVO.getUserIdList()) {
					List<String> deviceTokenList = deviceTokenDao.getDeviceTokenByUserId(userId);
					push(pushVO, deviceTokenList);
					Todo todo = new Todo();
					todo.setContentId(pushVO.getContentId());
					todo.setTodoType(pushVO.getTodoType());
					todo.setUserID(userId);
					todoDao.create(todo);
					logger.info(String.format("APNS Manager...push : %s to Parent id %d.", pushVO.getTodoType(),userId));							
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e,e);
			}
		}	
	}
	

//	public static void main(String[] args) {
//		String de = "705423555A875DF7F2D7049E831172F3E7EECBAA751C4D3F84D5EAF572FBB8A1";
//		de = de.toLowerCase();
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < de.length(); i++) {
//			if (i >= 8 && i%8 == 0) {
//				sb.append(" ");
//			}
//			sb.append(de.charAt(i));
//		}
//		System.out.println(sb.toString());
//		DatabaseDao myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
//		DeviceTokenDao deviceTokenDao = myDatabaseDao.getEntityDao(DeviceTokenDao.class);
//		deviceTokenDao.updateDeviceTokenInactive(sb.toString());
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
////        String token = "70542355 5a875df7 f2d7049e 831172f3 e7eecbaa 751c4d3f 84d5eaf5 72fbb8a1";
////        service.push(token, payload);
////        System.out.println(new Date());
//	}
}
