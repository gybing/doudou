package doudou.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.DoudouInfoTypeDao;
import doudou.dao.MessageDao;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.DoudouInfoType;
import doudou.vo.Event;
import doudou.vo.Message;
import doudou.vo.SchoolClass;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.SessionData;


@Service
public class MessageService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final MessageDao messageDao;
	private final DoudouInfoTypeDao doudouInfoTypeDao;
	
	private MessageService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
		doudouInfoTypeDao = myDatabaseDao.getEntityDao(DoudouInfoTypeDao.class);
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
		return result;
	}
	
	/**
	 * 班级管理员搜索消息列表
	 * TODO
	 * */
	public ListResult<Message> queryClassMessageList(SessionData sessionData, int offset, int count) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		ListResult<Message> result = messageDao.getMessageListByClassIdList(classIdList, offset, count);
		return result;
	}
	
	/**
	 * 查看单一事件
	 * 
	 * */
	public Message getMessageById(int id) {
		return messageDao.read(id);
	}
	
	/**
	 * 添加单一事件
	 * 
	 * */
	public int addMessage(MessagePubTask messageTask) {
		//完成对象属性填充...TOBE optimized
		messageTask.setChildrenListString(messageTask.generateAtChildrenListString());
		
		int result = (Integer)messageDao.create(messageTask.getMessage());
		if (result > 0) {
			DoudouBackendService.getInstance().publishTask(messageTask);
		} 
		return result;
		
	}
	
	/**
	 * 更新单一事件
	 * @param addedChildIdList 新添加的孩子id列表
	 * @param deletedChildIdList 删除的孩子id列表
	 * 
	 * */
	public boolean updateMessage(SessionData sessionData, Message message, List<Integer> addedChildIdList , List<Integer> deletedChildIdList) {
		//检查是否有权限 (是否为自己发的事件)
		if (sessionData.getUser().getId() == message.getUserId()) {
			
			
			return messageDao.update(message) > 0;
		} else {
			return false;
		}
	}
	public List<DoudouInfoType> getMessageTypeList(int schoolId) {
		return doudouInfoTypeDao.getMessageTypeBySchoolId(schoolId);
	}
	
}
