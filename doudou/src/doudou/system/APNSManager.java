package doudou.system;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import doudou.dao.DaoFactory;
import doudou.dao.DeviceTokenDao;
import doudou.dao.TodoDao;
import doudou.util.DoudouConfig;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Todo;
import doudou.vo.model.APNSPushVO;

import org.apache.log4j.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

public class APNSManager implements Runnable{
	
	private ApnsService service;
	private LinkedBlockingQueue<APNSPushVO> pushQueue;
	private Logger logger = Logger.getLogger(getClass());
	private DeviceTokenDao deviceTokenDao;
	private TodoDao todoDao;
	
	private DatabaseDao myDatabaseDao;
	private DoudouConfig doudouConfig = DoudouConfig.getConfig();
	
	public APNSManager(LinkedBlockingQueue<APNSPushVO> pushQueue) {
		String cerPath = doudouConfig.getAPNSCertificatePath();
		service = APNS.newService().withCert(cerPath, "mayaya@mayaya").withProductionDestination().build();
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
					PayloadBuilder pb = APNS.newPayload();
					pb.alertBody(fromUser + " sent you a new photo");
					pb.customField("ContentId", picId);
					pb.customField("NotificationType", "PicTag");
					String payload = pb.build();
					service.push(token, payload);
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
	
	private void push(APNSPushVO pushVO, List<String> deviceTokenList) {
		switch (pushVO.getTodoType()) {
		case Comment:
			pushComment(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
			break;
		case NewPicture:
			pushPicTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
			break;
		case NewEvent:
			pushEvtTaged(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
			break;
		case NewMessage:
			pushAnnouncement(pushVO.getContentId(),deviceTokenList, pushVO.getFromUser());
		}
	}

	@Override
	public void run() {
		System.out.println("APNSManager start succeed!");
		APNSPushVO pushVO = null;			
		while(true){
			try {
				pushVO = pushQueue.take();
				for (int userId : pushVO.getUserIdList()) {
					List<String> deviceTokenList = deviceTokenDao.getDeviceTokenByUserId(userId);
					push(pushVO, deviceTokenList);
					Todo todo = new Todo();
					todo.setContentId(pushVO.getContentId());
					todo.setTodoType(pushVO.getTodoType());
					todo.setUserId(userId);
					todoDao.create(todo);
					logger.info(String.format("APNS Manager...push : %s to Parent id %d.", pushVO.getTodoType(),userId));							
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}	
	}
	
	public static void main(String[] args) {
		ApnsService service = APNS.newService().withCert("d:/mayayaCerficate.p12", "mayaya@mayaya").withSandboxDestination().build();
		PayloadBuilder pb = APNS.newPayload();
		pb.alertBody("You have a new Tagged");
		pb.customField("secret", "what do you think?");
		pb.customField("secret2", "what do you think2?");
		String payload = pb.build();
		
//		String payload = APNS.newPayload().badge(3).customField("secret", "what do you think?")
//        .localizedKey("GAME_PLAY_REQUEST_FORMAT")
//        .localizedArguments("Jenna", "Frank")
//        .actionKey("Play").build();
		System.out.println(payload);
        String token = "af71e26c 93f60a25 f4cc3f49 0ecf72fa c3f55585 b180ecfe 48e3bd3c 6386f39f";
        service.push(token, payload);
	}
	
}
