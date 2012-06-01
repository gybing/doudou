package doudou.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.DoudouInfoTypeDao;
import doudou.dao.MessageClassDao;
import doudou.dao.MessageDao;
import doudou.dao.MessageUserDao;
import doudou.system.DoudouBackend;
import doudou.util.DoudouUtil;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.DoudouInfoType;
import doudou.vo.Message;
import doudou.vo.MessageClass;
import doudou.vo.MessageUser;
import doudou.vo.SchoolClass;
import doudou.vo.model.MsgPublishTask;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;
import doudou.vo.type.TodoType;
import edu.emory.mathcs.backport.java.util.Collections;


@Service
public class MessageService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final MessageDao messageDao;
	private final MessageClassDao messageClassDao;
	private final MessageUserDao messageUserDao;
	private final DoudouInfoTypeDao doudouInfoTypeDao;
	
	public MessageService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
		doudouInfoTypeDao = myDatabaseDao.getEntityDao(DoudouInfoTypeDao.class);
		messageClassDao = myDatabaseDao.getEntityDao(MessageClassDao.class);
		messageUserDao = myDatabaseDao.getEntityDao(MessageUserDao.class);
	}
	
	/**
	 * 班级管理员获取信息列表
	 * 
	 * */
	public ListResult<Message> getClassMessageList(SessionData sessionData, int offset, int count) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		ListResult<Message> result = messageDao.getMessageListByClassIdList(classIdList, offset, count);
		fulfillFeedBackOrReadStatusInfo(result.getEntities());
		return result;
	}
	
	/**
	 * 班级管理员搜索消息列表
	 * 
	 * */
	public ListResult<Message> queryClassMessageList(SessionData sessionData, int offset, int count,
			String title, PublishLevel publishLevel, Date beginTime, Date endTime, boolean mustFeedBack, boolean isUserSelf) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		int userId = -1;
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		if (isUserSelf) {
			userId = sessionData.getUser().getId();
		}
		ListResult<Message> result = messageDao.queryClassMessageList(classIdList, offset, count, title,
				publishLevel, beginTime, endTime, mustFeedBack, userId);
		fulfillFeedBackOrReadStatusInfo(result.getEntities());
		return result;
	}
	
	/**
	 * 查看单一消息
	 * 
	 * */
	public Message getMessageById(int id) {
		return messageDao.read(id);
	}
	
	public List<MessageUser> getListByMessageId(int id) {
		return messageUserDao.getListByMessageId(id);
	}
	
	/**
	 * 添加单一消息
	 * 
	 * */
	public int addMessage(Message message, List<Integer> childIdList, List<Integer> classIdList, int schoolId) {
		//完成对象属性填充...TOBE optimized
		//messageTask.setChildrenListString(messageTask.generateAtChildrenListString());
		
		int result = (Integer)messageDao.create(message);
		if (result > 0) {
			// 保证发布者能查看到新添加的事件
			for (Integer classId : classIdList) {
				MessageClass messageClass = new MessageClass();
				messageClass.setClassId(classId);
				messageClass.setMessageId(message.getId());
				messageClass.setSchoolId(schoolId);
				messageClassDao.create(messageClass);
			}
			MsgPublishTask task = new MsgPublishTask();
			task.setMessage(message);
			task.setTargetChildIdList(childIdList);
			task.setTodoType(TodoType.NewMessage);
			//Attention not service
			DoudouBackend.getInstance().publishTask(task);
		} 
		return result;
		
	}
	
	/**
	 * 更新单一消息
	 * @param newMessage 新消息
	 * @param oldMessage 原消息
	 * @param newChildIdList 新添加的孩子id列表
	 * @param newClassIdList 新添加的班级id列表
	 * @param schoolId 学校Id
	 * 
	 * */
	public int updateMessage(Message newMessage, Message oldMessage, List<Integer> newChildIdList , List<Integer> newClassIdList, int schoolId) {
		boolean contentDiff = newMessage.getTitle().equals(oldMessage.getTitle()) 
						&& newMessage.getContent().equals(oldMessage.getContent()) 
						&& newMessage.getMessageTypeId() == oldMessage.getMessageTypeId();
		List<Integer> oldChildIdList = DoudouUtil.getInstance().getChildIdListFromString(oldMessage.getAtChildList());
		List<Integer> oldClassIdList = DoudouUtil.getInstance().getClassIdListFromChildIdList(oldChildIdList);
		//新添加的孩子
		List<Integer> addedChildIdList = new ArrayList<Integer>(newChildIdList.size());
		Collections.copy(addedChildIdList, newChildIdList);
		addedChildIdList.removeAll(oldChildIdList);

		//删除的孩子
		List<Integer> removedChildIdList = new ArrayList<Integer>(oldChildIdList.size());
		Collections.copy(removedChildIdList, oldChildIdList);
		addedChildIdList.removeAll(newChildIdList);

		//未变的孩子
		List<Integer> unChangedChildIdList = new ArrayList<Integer>(newChildIdList.size());
		Collections.copy(unChangedChildIdList, newChildIdList);
		addedChildIdList.retainAll(oldChildIdList);
		
		//新添加的班级
		List<Integer> addedClassIdList = new ArrayList<Integer>(newClassIdList.size());
		Collections.copy(addedClassIdList, newClassIdList);
		addedClassIdList.removeAll(oldClassIdList);
		// 保证发布者能查看到新添加的事件
		for (Integer classId : addedClassIdList) {
			MessageClass messageClass = new MessageClass();
			messageClass.setClassId(classId);
			messageClass.setMessageId(newMessage.getId());
			messageClass.setSchoolId(schoolId);
			messageClassDao.create(messageClass);
		}
		//删除的班级
		List<Integer> removedClassIdList = new ArrayList<Integer>(oldClassIdList.size());
		Collections.copy(removedClassIdList, oldClassIdList);
		removedClassIdList.removeAll(newClassIdList);
		// 保证发布者能查看bu到更改过的事件
		for (Integer classId : removedClassIdList) {
			MessageClass messageClass = new MessageClass();
			messageClass.setClassId(classId);
			messageClass.setMessageId(newMessage.getId());
			messageClass.setSchoolId(schoolId);
			messageClassDao.updateMCUnavailable(messageClass);
		}
		
		int result = 0;
		
		//内容一样，孩子列表一样
		if (contentDiff && addedChildIdList.size()==0 && removedChildIdList.size() == 0) {
			result = 0;
		} else if (contentDiff) {//内容一样，孩子列表发生变化
			MsgPublishTask task = new MsgPublishTask();
			task.setMessage(newMessage);
			task.setTargetChildIdList(addedChildIdList);
			task.setTodoType(TodoType.NewMessage);
			DoudouBackend.getInstance().publishTask(task);
			
			MsgPublishTask delTask = new MsgPublishTask();
			delTask.setMessage(newMessage);
			delTask.setTargetChildIdList(removedChildIdList);
			delTask.setTodoType(TodoType.DelMessage);
			DoudouBackend.getInstance().publishTask(task);
			result = messageDao.update(newMessage);
		} else if (addedChildIdList.size()==0 && removedChildIdList.size() == 0) {//内容变化，孩子列表没变
			MsgPublishTask task = new MsgPublishTask();
			task.setMessage(newMessage);
			task.setTargetChildIdList(oldChildIdList);
			task.setTodoType(TodoType.ModMessage);
			DoudouBackend.getInstance().publishTask(task);
			result = messageDao.update(newMessage);
		} else {//都变化
			MsgPublishTask task = new MsgPublishTask();
			task.setMessage(newMessage);
			task.setTargetChildIdList(addedChildIdList);
			task.setTodoType(TodoType.NewMessage);
			DoudouBackend.getInstance().publishTask(task);
			
			MsgPublishTask delTask = new MsgPublishTask();
			delTask.setMessage(newMessage);
			delTask.setTargetChildIdList(removedChildIdList);
			delTask.setTodoType(TodoType.DelMessage);
			DoudouBackend.getInstance().publishTask(task);
			
			MsgPublishTask modTask = new MsgPublishTask();
			modTask.setMessage(newMessage);
			modTask.setTargetChildIdList(unChangedChildIdList);
			modTask.setTodoType(TodoType.ModMessage);
			DoudouBackend.getInstance().publishTask(task);
			
			result = messageDao.update(newMessage);
		}

		return result;
	}
	
	/**
	 * 删除消息
	 * */
	public void deleteMessage(int messageId) {
		messageDao.updateUnavailable(messageId);
	}
	public List<DoudouInfoType> getMessageTypeList(int schoolId) {
		return doudouInfoTypeDao.getMessageTypeBySchoolId(schoolId);
	}
	
	private void fulfillFeedBackOrReadStatusInfo(List<Message> messageList) {
		for (Message message : messageList) {
			if (message.isMustFeedBack()) {
				message.setFeedBackCount(messageDao.getFeedBackCount(message.getId()));
				message.setNotFeedBackCount(messageDao.getNotFeedBackCount(message.getId()));
			} else {
				message.setReadedCount(messageDao.getReadCount(message.getId()));
				message.setNotReadedCount(messageDao.getNotReadCount(message.getId()));
			}
			
		}
	}
	
	public Message getNextMessage(SessionData sessionData, int messageId) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		return messageDao.getNextMessage(classIdList,messageId);
	}
	public Message getPreviousMessage(SessionData sessionData, int messageId) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		return messageDao.getPreviousMessage(classIdList,messageId);
	}
	
}
