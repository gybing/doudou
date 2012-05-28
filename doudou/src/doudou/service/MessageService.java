package doudou.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.MessageDao;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Message;
import doudou.vo.SchoolClass;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.SessionData;


@Service
public class MessageService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final MessageDao messageDao;
	
	private MessageService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
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
	
	
	public int addMessage(MessagePubTask messageTask) {
		//完成对象属性填充...TOBE optimized
		messageTask.setChildrenListString(messageTask.generateAtChildrenListString());
		
		int result = (Integer)messageDao.create(messageTask.getMessage());
		if (result > 0) {
			DoudouBackendService.getInstance().publishTask(messageTask);
		} 
		return result;
		
	}
	
}
