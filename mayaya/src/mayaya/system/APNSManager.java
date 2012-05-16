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
					service.push(token, payload);
				}
			}
		}
	}
	
	// 图片Tag
	public void pushPicTaged(int picId, List<String> tokenList, String fromUser) {
		if (null!= tokenList && tokenList.size() > 0) {
			for (String token : tokenList) {
				if (null != token && !"".equals(token) && !"(null)".equals(token) && !"null".equals(token)) {
					System.out.println(new Date());
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new photo");
					pb.customField("ContentId", picId);
					pb.customField("NotificationType", "PicTag");
					String payload = pb.build();
					System.out.println("push to deviceToken : " + token);
					service.push(token, payload);
					System.out.println(new Date());
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
					service.push(token, payload);			
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
					service.push(token, payload);			
				}
			}
		}
	}
	
	private void push(PushVO pushVO, List<String> deviceTokenList) {
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
	
	public static void loadInactiveDevices() {
		
//		 String filePathAndName = MayayaConfig.getConfig();
//		 String fileContent;
//
//		try {
//			String filePath = filePathAndName;
//			filePath = filePath.toString();
//			File myFilePath = new File(filePath);
//			if (!myFilePath.exists()) {
//				myFilePath.createNewFile();
//			}
//			FileWriter resultFile = new FileWriter(myFilePath);
//			PrintWriter myFile = new PrintWriter(resultFile);
//			String strContent = fileContent;
//			myFile.println(strContent);
//			resultFile.close();
//
//		} catch (Exception e) {
//			System.out.println("新建目录操作出错");
//			e.printStackTrace();
//
//		}
			  
		
		logger.info("Inactive Device Fetcher execute!!");
		System.out.println("Start Time : " + new Date());
		ApnsService service = APNS.newService().withCert(MayayaConfig.getConfig().getAPNSCertificatePath(), "mayaya@mayaya").withProductionDestination().build();
		Map<String,Date> result = service.getInactiveDevices();
		for (Map.Entry<String, Date> string : result.entrySet()) {
			System.out.println(string.getKey());
			System.out.println(string.getValue());
			System.out.println();
		}
		System.out.println("End Time : " + new Date());
	}
	
	public static void main(String[] args) {
//		PayloadBuilder pb = APNS.newPayload();
//		pb.alertBody("You have a new Tagged");
//		pb.customField("secret", "what do you think?");
//		pb.customField("secret2", "what do you think2?");
//		String payload = pb.build();
//		
////		String payload = APNS.newPayload().badge(3).customField("secret", "what do you think?")
////        .localizedKey("GAME_PLAY_REQUEST_FORMAT")
////        .localizedArguments("Jenna", "Frank")
////        .actionKey("Play").build();
//        String token = "70542355 5a875df7 f2d7049e 831172f3 e7eecbaa 751c4d3f 84d5eaf5 72fbb8a1";
//        service.push(token, payload);
//        System.out.println(new Date());
	}
}
