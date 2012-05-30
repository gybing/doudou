package doudou.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



import org.apache.log4j.Logger;

import doudou.dao.ChildDao;
import doudou.dao.DaoFactory;
import doudou.dao.EventClassDao;
import doudou.dao.EventDao;
import doudou.dao.EventUserDao;
import doudou.dao.MessageClassDao;
import doudou.dao.MessageDao;
import doudou.dao.MessageUserDao;
import doudou.dao.ParentsChildDao;
import doudou.dao.ParentsDao;
import doudou.dao.PictureDao;
import doudou.dao.PictureUserDao;
import doudou.dao.TodoDao;
import doudou.dao.UserDao;
import doudou.system.DoudouBackend;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;
import doudou.vo.Event;
import doudou.vo.EventClass;
import doudou.vo.EventUser;
import doudou.vo.Message;
import doudou.vo.MessageClass;
import doudou.vo.MessageUser;
import doudou.vo.Parents;
import doudou.vo.Picture;
import doudou.vo.PictureUser;
import doudou.vo.SchoolClass;
import doudou.vo.Todo;
import doudou.vo.model.EmailTask;
import doudou.vo.model.EvtPublishTask;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.PicPublishTask;
import doudou.vo.model.APNSPushVO;
import doudou.vo.type.TodoType;
/*
 * For doudou backend threads
 * 
 * */
public class DoudouBackendService {
	private static DoudouBackendService instance = new DoudouBackendService();
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final PictureDao pictureDao;
	private final EventDao eventDao;
	private final EventClassDao eventClassDao;
	private final PictureUserDao pictureUserDao;
	private final EventUserDao eventUserDao;
	private final ChildDao childDao;
	private final TodoDao todoDao;
	private final MessageDao messageDao;
	private final MessageUserDao messageUserDao;
	private final UserDao userDao;
	private final MessageClassDao messageClassDao;
	private final ParentsDao parentsDao;
	
	
	private DoudouBackendService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
		eventClassDao = myDatabaseDao.getEntityDao(EventClassDao.class);
		pictureDao = myDatabaseDao.getEntityDao(PictureDao.class);
		pictureUserDao = myDatabaseDao.getEntityDao(PictureUserDao.class);
		eventUserDao = myDatabaseDao.getEntityDao(EventUserDao.class); 
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
		todoDao = myDatabaseDao.getEntityDao(TodoDao.class);
		messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
		messageUserDao = myDatabaseDao.getEntityDao(MessageUserDao.class);
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		messageClassDao = myDatabaseDao.getEntityDao(MessageClassDao.class);
		parentsDao = myDatabaseDao.getEntityDao(ParentsDao.class);
	}
	
	public static DoudouBackendService getInstance() {
		return instance;
	}
	
	
	public void publishTask(MessagePubTask task) {
		Message message = task.getMessage();
		
		APNSPushVO pushVO = new APNSPushVO();
		pushVO.setTodoType(TodoType.NewMessage);
		pushVO.setContentId(message.getId());
		Set<Integer> relatedIdSet = new HashSet<Integer>();
		Set<String> relatedEmailSet = new HashSet<String>();
		
		for (Entry<SchoolClass,List<Child>> entrySet : task.getTagedInfo().getClassChildMap().entrySet()) {
			SchoolClass sc = entrySet.getKey();
			//插入消息和班级的对应数据
			MessageClass mc = new MessageClass();
			mc.setMessageId(message.getId());
			mc.setClassId(sc.getId());
			mc.setSchoolId(task.getSchoolId());
			logger.info(String.format("Message PublishTask....messageId : %d,classId : %d,schoolId:%d.", message.getId(),sc.getId(),task.getSchoolId()));
			messageClassDao.create(mc);
			
			for (Child child : entrySet.getValue()) {
				//插入班级和孩子对应信息
				MessageUser mu = new MessageUser();
				mu.setMessageId(message.getId());
				mu.setToChildId(child.getId());
				messageUserDao.create(mu);

				List<Parents> relatedParentsList = parentsDao.getParentsListByChildId(child.getId());
				for (Parents parents : relatedParentsList) {
					//这里可以do more
					relatedIdSet.add(parents.getParentId());
					relatedEmailSet.add(parents.getPrivateEmail());
				}
			}
				
		}
		String fromUser = userDao.read(message.getUserId()).getFirstName();
//		relatedIdSet.remove(message.getUserId()); 不刪除自己 TODO
		pushVO.setFromUser(fromUser);
		pushVO.setUserIdList(relatedIdSet);
		logger.info(String.format("publish message task, relatedIdSet = %s;fromUser :%s",
							relatedIdSet, fromUser));
		DoudouBackend.getInstance().addPushVO(pushVO);
			
		// Email
		EmailTask emailTask = new EmailTask();
		emailTask.setTodoType(TodoType.NewMessage);
		emailTask.setTo(relatedEmailSet.toArray(new String[0]));
		emailTask.setFromUser(fromUser);
		emailTask.setContent(message);
		DoudouBackend.getInstance().addEmailTask(emailTask);
			
	}
	
	public void publishTask(PicPublishTask task) {
		Picture picture = task.getPicture();
		
		APNSPushVO pushVO = new APNSPushVO();
		pushVO.setTodoType(TodoType.NewPicture);
		pushVO.setContentId(picture.getId());
		Set<Integer> relatedIdSet = new HashSet<Integer>();
		Set<String> relatedEmailSet = new HashSet<String>();
		
		for (Entry<SchoolClass,List<Child>> entrySet : task.getTagedInfo().getClassChildMap().entrySet()) {
			SchoolClass sc = entrySet.getKey();
			//插入消息和班级的对应数据
			MessageClass mc = new MessageClass();
			mc.setMessageId(picture.getId());
			mc.setClassId(sc.getId());
			mc.setSchoolId(task.getSchoolId());
			logger.info(String.format("Message PublishTask....messageId : %d,classId : %d,schoolId:%d.", picture.getId(),sc.getId(),task.getSchoolId()));
			messageClassDao.create(mc);
			
			for (Child child : entrySet.getValue()) {
				//插入班级和孩子对应信息
				MessageUser mu = new MessageUser();
				mu.setMessageId(picture.getId());
				mu.setToChildId(child.getId());
				messageUserDao.create(mu);

				List<Parents> relatedParentsList = parentsDao.getParentsListByChildId(child.getId());
				for (Parents parents : relatedParentsList) {
					//这里可以do more
					relatedIdSet.add(parents.getParentId());
					relatedEmailSet.add(parents.getPrivateEmail());
				}
			}
				
		}
		String fromUser = userDao.read(picture.getUserId()).getFirstName();
//		relatedIdSet.remove(message.getUserId()); 不刪除自己 TODO
		pushVO.setFromUser(fromUser);
		pushVO.setUserIdList(relatedIdSet);
		logger.info(String.format("publish message task, relatedIdSet = %s;fromUser :%s",
							relatedIdSet, fromUser));
		DoudouBackend.getInstance().addPushVO(pushVO);
			
		// Email
		EmailTask emailTask = new EmailTask();
		emailTask.setTodoType(TodoType.NewMessage);
		emailTask.setTo(relatedEmailSet.toArray(new String[0]));
		emailTask.setFromUser(fromUser);
		emailTask.setContent(picture);
		DoudouBackend.getInstance().addEmailTask(emailTask);
	}
	
	public void publishTask(EvtPublishTask task) {
		Event event = task.getEvent();
		
		APNSPushVO pushVO = new APNSPushVO();
		pushVO.setTodoType(TodoType.NewEvent);
		pushVO.setContentId(event.getId());
		Set<Integer> relatedIdSet = new HashSet<Integer>();
		Set<String> relatedEmailSet = new HashSet<String>();
		
		for (Entry<SchoolClass,List<Child>> entrySet : task.getTagedInfo().getClassChildMap().entrySet()) {
			SchoolClass sc = entrySet.getKey();
			//插入消息和班级的对应数据
			EventClass ec = new EventClass();
			ec.setEventId(event.getId());
			ec.setClassId(sc.getId());
			ec.setSchoolId(task.getSchoolId());
			logger.info(String.format("Message PublishTask....messageId : %d,classId : %d,schoolId:%d.", event.getId(),sc.getId(),task.getSchoolId()));
			eventClassDao.create(ec);
			
			for (Child child : entrySet.getValue()) {
				//插入班级和孩子对应信息
				EventUser mu = new EventUser();
				mu.setEventId(event.getId());
				mu.setToChildId(child.getId());
				eventUserDao.create(mu);

				List<Parents> relatedParentsList = parentsDao.getParentsListByChildId(child.getId());
				for (Parents parents : relatedParentsList) {
					//这里可以do more
					relatedIdSet.add(parents.getParentId());
					relatedEmailSet.add(parents.getPrivateEmail());
				}
			}
				
		}
		String fromUser = userDao.read(event.getUserId()).getFirstName();
//		relatedIdSet.remove(message.getUserId()); 不刪除自己 TODO
		pushVO.setFromUser(fromUser);
		pushVO.setUserIdList(relatedIdSet);
		logger.info(String.format("publish event task, relatedIdSet = %s;fromUser :%s",
							relatedIdSet, fromUser));
		DoudouBackend.getInstance().addPushVO(pushVO);
			
		// Email
		EmailTask emailTask = new EmailTask();
		emailTask.setTodoType(TodoType.NewEvent);
		emailTask.setTo(relatedEmailSet.toArray(new String[0]));
		emailTask.setFromUser(fromUser);
		emailTask.setContent(event);
		DoudouBackend.getInstance().addEmailTask(emailTask);
	}
	
}
