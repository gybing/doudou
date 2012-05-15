package doudou.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



import org.apache.log4j.Logger;

import doudou.dao.ChildDao;
import doudou.dao.DaoFactory;
import doudou.dao.EventDao;
import doudou.dao.PictureDao;
import doudou.dao.TodoDao;
import doudou.dao.UserDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Message;
import doudou.vo.Picture;
import doudou.vo.Todo;
import doudou.vo.model.EmailTask;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.PicPublishTask;
import doudou.vo.model.PushVO;
import doudou.vo.type.TodoType;
/*
 * For mayaya backend threads
 * 
 * */
public class DoudouBackendService {
	private static DoudouBackendService instance = new DoudouBackendService();
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final PictureDao pictureDao;
	private final EventDao eventDao;
	//private final PictureUserDao pictureUserDao;
	//private final EventUserDao eventUserDao;
	private final ChildDao childDao;
	private final TodoDao todoDao;
	//private final MessageDao messageDao;
	private final UserDao userDao;
	
	private DoudouBackendService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
		pictureDao = myDatabaseDao.getEntityDao(PictureDao.class);
		//pictureUserDao = myDatabaseDao.getEntityDao(PictureUserDao.class);
		//eventUserDao = myDatabaseDao.getEntityDao(RelationsEventUserDao.class); 
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
		todoDao = myDatabaseDao.getEntityDao(TodoDao.class);
		//messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
	}
	
	public static DoudouBackendService getInstance() {
		return instance;
	}
	
	
//	public void publishTask(MessagePubTask task) {
//		StringBuffer atChildList = new StringBuffer();
//		logger.info("announce task child list size : " + task.getChildrenList().size());
//		for(Integer childId : task.getChildrenList()) {
//			atChildList.append(childDao.read(childId).getFirstName()+",");
//		}
//		if (atChildList.length() > 1) {
//			atChildList.deleteCharAt(atChildList.length()-1);
//		}
//		Message evt = task.getMessage();
//		evt.setAtChildList(atChildList.toString());
//		
//		if ((Integer) messageDao.create(evt) > 0) {
//			PushVO pushVO = new PushVO();
//			pushVO.setTodoType(TodoType.Message);
//			pushVO.setContentId(evt.getId());
//			Set<Integer> relatedIdSet = new HashSet<Integer>();
//			
//			//使得通知发布人自己也能看见自己的通知
//			Todo todo = new Todo();
//			todo.setContentId(evt.getId());
//			todo.setTodoType(TodoType.Message);
//			todo.setUserID(evt.getUserId());
//			todoDao.create(todo);
//			
//			for (Integer childId : task.getChildrenList()) {
//				List<Integer> relatedIdList = childUserDao
//						.getRelatedUserIDByChildId(childId);
//				relatedIdSet.addAll(relatedIdList);
//			}
//			String fromUser = userDao.read(evt.getUserId()).getFirstName();
//			relatedIdSet.remove(evt.getUserId());
//			pushVO.setFromUser(fromUser);
//			pushVO.setUserIdList(relatedIdSet);
//			logger.info(String.format("publish announcement task, relatedIdSet = %s;remove userId : %d",
//							relatedIdSet, evt.getUserId()));
//			DoudouBackend.getInstance().addPushVO(pushVO);
//			
//			// Email
//			EmailTask emailTask = new EmailTask();
//			emailTask.setTodoType(TodoType.Message);
//			emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
//			emailTask.setFromUser(fromUser);
//			emailTask.setContent(evt);
//			DoudouBackend.getInstance().addEmailTask(emailTask);
//			
//		}
//	}
//	
//	public void publishTask(PicPublishTask task) {
//		StringBuffer atChildList = new StringBuffer();
//		logger.info("task child list size : " + task.getChildrenList().size());
//		for(Integer childId : task.getChildrenList()) {
//			atChildList.append(childDao.read(childId).getFirstName()+",");
//		}
//		if (atChildList.length() > 1) {
//			atChildList.deleteCharAt(atChildList.length()-1);
//		}
//		Picture pic = task.getPicture();
//		pic.setAtChildList(atChildList.toString());
//		logger.info("atChildList : " + atChildList.toString());
//		Set<Integer> relatedIdSet = new HashSet<Integer>();
//			if ((Integer)pictureDao.create(pic) > 0) {
//				//不push一个给自己的照片关系了
////				Push_Picture_User self = new Push_Picture_User();
////				self.setPictureID(pic.getPictureID());
////				self.setPublishTime(pic.getPublishTime());
////				self.setSelf(true);
////				self.setToChildId(pic.getChildId());
////				pushPictureUserDao.create(self);
//				
//				PushVO pushVO = new PushVO();
//				pushVO.setTodoType(TodoType.Picture);
//				pushVO.setContentId(pic.getId());
//				for (Integer childId : task.getChildrenList()) {
//					Push_Picture_User ppu = new Push_Picture_User();
//					ppu.setPictureID(pic.getPictureID());
//					ppu.setPublishTime(pic.getPublishTime());
//					ppu.setToChildId(childId);
//					pushPictureUserDao.create(ppu);
//					
//					// APNS
//					
//					List<Integer> relatedIdList = relationsChildUserDao.getRelatedUserIDByChildId(childId);
//					relatedIdSet.addAll(relatedIdList);
//				}
//				String fromUser = userDao.read(pic.getUserId()).getFirstName();
//				relatedIdSet.remove(pic.getUserId());
//				pushVO.setUserIdList(relatedIdSet);
//				pushVO.setFromUser(fromUser);
//				logger.info(String.format("publish picture task, relatedIdSet = %s;remove userId : %d",relatedIdSet,pic.getUserId()));
//				MayayaBackend.getInstance().addPushVO(pushVO);
//				
//				// Email
//				EmailTask emailTask = new EmailTask();
//				emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
//				emailTask.setTodoType(TodoType.PicTag);
//				emailTask.setFromUser(fromUser);
//				emailTask.setContent(pic);
//				MayayaBackend.getInstance().addEmailTask(emailTask);
//			}
//	}
//	
//	public void publishTask(EvtPublishTask task) {
//		StringBuffer atChildList = new StringBuffer();
//		logger.info("task child list size : " + task.getChildIdList().size());
//		for(Integer childId : task.getChildIdList()) {
//			atChildList.append(childDao.read(childId).getFirstName()+",");
//		}
//		if (atChildList.length() > 1) {
//			atChildList.deleteCharAt(atChildList.length()-1);
//		}
//		Event evt = task.getEvent();
//		evt.setAtChildList(atChildList.toString());
//		Set<Integer> relatedIdSet = new HashSet<Integer>();
//
//		if ((Integer) eventDao.create(evt) > 0) {
//			PushVO pushVO = new PushVO();
//			pushVO.setTodoType(TodoType.EvtTag);
//			pushVO.setContentId(evt.getEventID());
//			for (Integer childId : task.getChildIdList()) {
//				Relations_Event_User reu = new Relations_Event_User();
//				reu.setEventID(evt.getEventID());
//				// input the childID
//				reu.setToChildId(childId);
//				reu.setSelf(false);
//				relationsEventUserDao.create(reu);
//
//				// APNS
//				List<Integer> relatedIdList = relationsChildUserDao.getRelatedUserIDByChildId(childId);
//				relatedIdSet.addAll(relatedIdList);
//			}
////			for (int userId : relatedIdSet) {
////				CacheManager.getInstance().putEvt(userId);
////			}
//			relatedIdSet.remove(evt.getUserID());
//			String fromUser = userDao.read(evt.getUserID()).getFirstName();
//			pushVO.setUserIdList(relatedIdSet);
//			pushVO.setFromUser(fromUser);
//			logger.info(String.format("publish Event task, relatedIdSet = %s;remove userId : %d",
//					relatedIdSet, evt.getUserID()));
//			MayayaBackend.getInstance().addPushVO(pushVO);
//			
//			// Email
//			EmailTask emailTask = new EmailTask();
//			emailTask.setTodoType(TodoType.EvtTag);
//			emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
//			emailTask.setFromUser(fromUser);
//			emailTask.setContent(evt);
//			MayayaBackend.getInstance().addEmailTask(emailTask);
//		}
//	}
//	
//	private String[] getEmailListFromIdSet(Set<Integer> relatedIdSet) {
//		String[] emailArray = new String[relatedIdSet.size()];
//		int index = 0;
//		for (Integer integer : relatedIdSet) {
//			String email = userDao.getUserEmailById(integer);
//			emailArray[index++] = email;
//		}
//		return emailArray;
//	}
	
}
