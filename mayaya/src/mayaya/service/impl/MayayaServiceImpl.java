package mayaya.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mayaya.dao.AnnouncementDao;
import mayaya.dao.ChildDao;
import mayaya.dao.CommentDao;
import mayaya.dao.DaoFactory;
import mayaya.dao.EventDao;
import mayaya.dao.PictureDao;
import mayaya.dao.PushPictureUserDao;
import mayaya.dao.RelationsChildUserDao;
import mayaya.dao.RelationsEventUserDao;
import mayaya.dao.TodoDao;
import mayaya.dao.UserDao;
import mayaya.service.MayayaService;
import mayaya.system.CacheManager;
import mayaya.system.MayayaBackend;
import mayaya.util.dao.DatabaseDao;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Announcement;
import mayaya.vo.Child;
import mayaya.vo.Event;
import mayaya.vo.Picture;
import mayaya.vo.Push_Picture_User;
import mayaya.vo.Relations_Event_User;
import mayaya.vo.Todo;
import mayaya.vo.enums.TodoType;
import mayaya.vo.model.AnnouncePubTask;
import mayaya.vo.model.EmailTask;
import mayaya.vo.model.EvtPublishTask;
import mayaya.vo.model.MainPageVO;
import mayaya.vo.model.PhotoWallVO;
import mayaya.vo.model.PicPublishTask;
import mayaya.vo.model.PushVO;
import mayaya.vo.model.SimpleMainPageVO;
import mayaya.vo.model.TagedChildInfo;

import org.apache.log4j.Logger;

public class MayayaServiceImpl implements MayayaService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static MayayaService instance = new MayayaServiceImpl(); 
	
	private final DatabaseDao myDatabaseDao; 
	
	private final PictureDao pictureDao;
	private final EventDao eventDao;
	private final PushPictureUserDao pushPictureUserDao;
	private final RelationsEventUserDao relationsEventUserDao;
	private final ChildDao childDao;
	private final RelationsChildUserDao relationsChildUserDao;
	private final CommentDao commentDao;
	private final TodoDao todoDao;
	private final AnnouncementDao announcementDao;
	private final UserDao userDao;
	
	private static Comparator<PhotoWallVO> photoWallComparator = null;
	
	private static Comparator<PhotoWallVO> getComparatorForPhotoWallVO() {
        if (null == photoWallComparator) {
        	photoWallComparator = new Comparator<PhotoWallVO>() {

                @Override
                public int compare(PhotoWallVO o1, PhotoWallVO o2) {
                    String date1 = o1.getDate();
                    String date2 = o2.getDate();
                    
                    return date1.compareTo(date2);
                }
                
            };
        }
        
        return photoWallComparator;
    }
	
	private MayayaServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
		pictureDao = myDatabaseDao.getEntityDao(PictureDao.class);
		pushPictureUserDao = myDatabaseDao.getEntityDao(PushPictureUserDao.class);
		relationsEventUserDao = myDatabaseDao.getEntityDao(RelationsEventUserDao.class); 
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
		relationsChildUserDao = myDatabaseDao.getEntityDao(RelationsChildUserDao.class);
		commentDao = myDatabaseDao.getEntityDao(CommentDao.class);
		todoDao = myDatabaseDao.getEntityDao(TodoDao.class);
		announcementDao = myDatabaseDao.getEntityDao(AnnouncementDao.class);
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		
		photoWallComparator = getComparatorForPhotoWallVO();
	}
	
	public static MayayaService getInstance() {
		return instance;
	}

	@Override
	public void publishTask(PicPublishTask task) {
		StringBuffer atChildList = new StringBuffer();
		logger.info("task child list size : " + task.getChildrenList().size());
		for(Integer childId : task.getChildrenList()) {
			atChildList.append(childDao.read(childId).getFirstName()+",");
		}
		if (atChildList.length() > 1) {
			atChildList.deleteCharAt(atChildList.length()-1);
		}
		Picture pic = task.getPicture();
		pic.setAtChildList(atChildList.toString());
		logger.info("atChildList : " + atChildList.toString());
		Set<Integer> relatedIdSet = new HashSet<Integer>();
//		try{
//			myDatabaseDao.startTransaction();
			if ((Integer)pictureDao.create(pic) > 0) {
				//不push一个给自己的照片关系了
//				Push_Picture_User self = new Push_Picture_User();
//				self.setPictureID(pic.getPictureID());
//				self.setPublishTime(pic.getPublishTime());
//				self.setSelf(true);
//				self.setToChildId(pic.getChildId());
//				pushPictureUserDao.create(self);
				
				PushVO pushVO = new PushVO();
				pushVO.setTodoType(TodoType.PicTag);
				pushVO.setContentId(pic.getPictureID());
				for (Integer childId : task.getChildrenList()) {
					Push_Picture_User ppu = new Push_Picture_User();
					ppu.setPictureID(pic.getPictureID());
					ppu.setPublishTime(pic.getPublishTime());
					ppu.setToChildId(childId);
					pushPictureUserDao.create(ppu);
					
					// APNS
					
					List<Integer> relatedIdList = relationsChildUserDao.getRelatedUserIDByChildId(childId);
					relatedIdSet.addAll(relatedIdList);
				}
				for (int userId : relatedIdSet) {
					CacheManager.getInstance().putPic(userId);
				}
				String fromUser = userDao.read(pic.getUserId()).getFirstName();
				relatedIdSet.remove(pic.getUserId());
				pushVO.setUserIdList(relatedIdSet);
				pushVO.setFromUser(fromUser);
				logger.info(String.format("publish picture task, relatedIdSet = %s;remove userId : %d",relatedIdSet,pic.getUserId()));
				MayayaBackend.getInstance().addPushVO(pushVO);
				
				// Email
				EmailTask emailTask = new EmailTask();
				emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
				emailTask.setTodoType(TodoType.PicTag);
				emailTask.setFromUser(fromUser);
				emailTask.setContent(pic);
				MayayaBackend.getInstance().addEmailTask(emailTask);
			}
//			myDatabaseDao.commitTransaction();
//		} catch( DataAccessException e) {
//			e.printStackTrace();
//			logger.error(e);
//			myDatabaseDao.endTransaction();
//		}
		
	}
	
	@Override
	public void publishTask(EvtPublishTask task) {
		StringBuffer atChildList = new StringBuffer();
		logger.info("task child list size : " + task.getChildIdList().size());
		for(Integer childId : task.getChildIdList()) {
			atChildList.append(childDao.read(childId).getFirstName()+",");
		}
		if (atChildList.length() > 1) {
			atChildList.deleteCharAt(atChildList.length()-1);
		}
		Event evt = task.getEvent();
		evt.setAtChildList(atChildList.toString());
		Set<Integer> relatedIdSet = new HashSet<Integer>();

		if ((Integer) eventDao.create(evt) > 0) {
			PushVO pushVO = new PushVO();
			pushVO.setTodoType(TodoType.EvtTag);
			pushVO.setContentId(evt.getEventID());
			for (Integer childId : task.getChildIdList()) {
				Relations_Event_User reu = new Relations_Event_User();
				reu.setEventID(evt.getEventID());
				// input the childID
				reu.setToChildId(childId);
				reu.setSelf(false);
				relationsEventUserDao.create(reu);

				// APNS
				List<Integer> relatedIdList = relationsChildUserDao.getRelatedUserIDByChildId(childId);
				relatedIdSet.addAll(relatedIdList);
			}
			for (int userId : relatedIdSet) {
				CacheManager.getInstance().putEvt(userId);
			}
			relatedIdSet.remove(evt.getUserID());
			String fromUser = userDao.read(evt.getUserID()).getFirstName();
			pushVO.setUserIdList(relatedIdSet);
			pushVO.setFromUser(fromUser);
			logger.info(String.format("publish Event task, relatedIdSet = %s;remove userId : %d",
					relatedIdSet, evt.getUserID()));
			MayayaBackend.getInstance().addPushVO(pushVO);
			
			// Email
			EmailTask emailTask = new EmailTask();
			emailTask.setTodoType(TodoType.EvtTag);
			emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
			emailTask.setFromUser(fromUser);
			emailTask.setContent(evt);
			MayayaBackend.getInstance().addEmailTask(emailTask);
		}
	}
	
	@Override
	public List<PhotoWallVO> getPhotoWallData(Date date, int childId) {
		List<Picture> pushList = pushPictureUserDao.getPhotoWallData(date, childId);
		HashMap<String,PhotoWallVO> resultSet = new HashMap<String,PhotoWallVO>();
		for (Picture ppu : pushList) {
			Date publishTime = ppu.getPublishTime();
			String dateS = DateUtil.getInstance().toYearMonthString(publishTime);
			if (resultSet.containsKey(dateS)) {
				resultSet.get(dateS).getPicList().add(ppu);
			} else {
				PhotoWallVO pwVO = new PhotoWallVO(); 
				pwVO.getPicList().add(ppu);
				pwVO.setDate(dateS);
				resultSet.put(dateS, pwVO);
			}
		}
		List<PhotoWallVO> result = new ArrayList<PhotoWallVO>(resultSet.values());
		Collections.sort(result,photoWallComparator);
		return result;
		
	}

	@Deprecated
	@Override
	public MainPageVO getMainPageData(int childId, Date date) {
		Child childInfo = childDao.read(childId);
		//childInfo.setPictureCount(pushPictureUserDao.getPicturesCountByChildId(childId));
		//childInfo.setEventCount(relationsEventUserDao.getEventsCountByChildId(childId));
		List<Picture> pics = pushPictureUserDao.getMainPagePics(childId);
		List<Event> eventList = relationsEventUserDao.getMainPageEvents(childId, date);
		String mother = relationsChildUserDao.getParentsNameByChildId(childId,"Female");
		String father = relationsChildUserDao.getParentsNameByChildId(childId,"Male");
		
		MainPageVO mpvo = new MainPageVO();
		mpvo.setEventList(eventList);
		mpvo.setPicList(pics);
		mpvo.setChildInfo(childInfo);
		mpvo.setMother(mother);
		mpvo.setFather(father);
		return mpvo;
	}

	@Override
	public List<Event> getEventByDate(int childId, Date date) {
		return relationsEventUserDao.getEventByDate(childId, date);
	}

	@Override
	public int updateChildList(List<Child> childList) {
		for (Child child : childList) {
			childDao.update(child);
		}
		return 1;
	}

	@Override
	public SimpleMainPageVO getSimpleMainPageData(int childId, Date date) {
		int picCount = pushPictureUserDao.getPicturesCountByChildId(childId);
		int evtCount = relationsEventUserDao.getEventsCountByChildId(childId)+relationsEventUserDao.getSchoolEventCount();
		List<Picture> pics = pushPictureUserDao.getMainPagePics(childId);
		List<Event> eventList = relationsEventUserDao.getMainPageEvents(childId, date);
		
		SimpleMainPageVO mpvo = new SimpleMainPageVO();
		mpvo.setEventList(eventList);
		mpvo.setPicList(pics);
		mpvo.setPicCount(picCount);
		mpvo.setEventCount(evtCount);

		return mpvo;
	}

	@Override
	public List<TagedChildInfo> getTagedChildInfo(int userId) {
		return relationsChildUserDao.getTagedChildInfo(userId);
	}

	private Object getNotificationVOByContentId(int contentId,
			String notificationType) {
		Object result = null;
		if ("Comment".equals(notificationType)) {
			result = commentDao.getNotificationVOById(contentId);
		} else if ("PicTag".equals(notificationType)) {
			result = pictureDao.getNotificationVOById(contentId);
		} else if ("EvtTag".equals(notificationType)) {
			result = eventDao.getNotificationVOById(contentId);
		} else if ("Announcement".equals(notificationType)) {
			result = announcementDao.getNotificationVOById(contentId);
		} 
		return result;
	}

	@Override
	public List<Object> getNotificationVOListByUserId(int userId, int pageIndex, int pageSize) {
		List<Object> result = new ArrayList<Object>();
		List<Todo> todoResult = todoDao.getTodoListByUserId(userId,pageIndex,pageSize);
		for (Todo todo : todoResult) {
			int contentId = todo.getContentId();
			TodoType tt = todo.getTodoType();
			Object vo = getNotificationVOByContentId(contentId,tt.toString());
			result.add(vo);
		}
		return result;
	}

	@Override
	public void publishTask(AnnouncePubTask task) {
		StringBuffer atChildList = new StringBuffer();
		logger.info("announce task child list size : " + task.getChildrenList().size());
		for(Integer childId : task.getChildrenList()) {
			atChildList.append(childDao.read(childId).getFirstName()+",");
		}
		if (atChildList.length() > 1) {
			atChildList.deleteCharAt(atChildList.length()-1);
		}
		Announcement evt = task.getAnnouncement();
		evt.setAtChildList(atChildList.toString());
		
		if ((Integer) announcementDao.create(evt) > 0) {
			PushVO pushVO = new PushVO();
			pushVO.setTodoType(TodoType.Announcement);
			pushVO.setContentId(evt.getAnnouncementId());
			Set<Integer> relatedIdSet = new HashSet<Integer>();
			
			//使得通知发布人自己也能看见自己的通知
			Todo todo = new Todo();
			todo.setContentId(evt.getAnnouncementId());
			todo.setTodoType(TodoType.Announcement);
			todo.setUserID(evt.getUserId());
			todoDao.create(todo);
			
			for (Integer childId : task.getChildrenList()) {
				List<Integer> relatedIdList = relationsChildUserDao
						.getRelatedUserIDByChildId(childId);
				relatedIdSet.addAll(relatedIdList);
			}
			for (int userId : relatedIdSet) {
				CacheManager.getInstance().putAnnounce(userId);
			}
			String fromUser = userDao.read(evt.getUserId()).getFirstName();
			relatedIdSet.remove(evt.getUserId());
			pushVO.setFromUser(fromUser);
			pushVO.setUserIdList(relatedIdSet);
			logger.info(String.format("publish announcement task, relatedIdSet = %s;remove userId : %d",
							relatedIdSet, evt.getUserId()));
			MayayaBackend.getInstance().addPushVO(pushVO);
			
			// Email
			EmailTask emailTask = new EmailTask();
			emailTask.setTodoType(TodoType.Announcement);
			emailTask.setTo(getEmailListFromIdSet(relatedIdSet));
			emailTask.setFromUser(fromUser);
			emailTask.setContent(evt);
			MayayaBackend.getInstance().addEmailTask(emailTask);
			
		}
	}
	
	private String[] getEmailListFromIdSet(Set<Integer> relatedIdSet) {
		String[] emailArray = new String[relatedIdSet.size()];
		int index = 0;
		for (Integer integer : relatedIdSet) {
			String email = userDao.getUserEmailById(integer);
			emailArray[index++] = email;
		}
		return emailArray;
	}
	
	
	
	
}
