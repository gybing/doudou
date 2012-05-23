package doudou.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import doudou.dao.DaoFactory;
import doudou.dao.MessageDao;
import doudou.dao.TeacherClassDao;
import doudou.dao.UserDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Message;
import doudou.vo.SchoolClass;
import doudou.vo.User;


@Service
public class MessageService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final MessageDao messageDao;
	private final TeacherClassDao teacherClassDao; 
	
	private MessageService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		messageDao = myDatabaseDao.getEntityDao(MessageDao.class);
		teacherClassDao = myDatabaseDao.getEntityDao(TeacherClassDao.class);
	}
	
	/**
	 * 班级管理员获取信息列表
	 * 
	 * */
	public List<Message> getClassMessageListByTeacherId(int userId) {
		//获取该老师相关的班级id List
		List<Integer> classIdList = teacherClassDao.getClassIdListByTeacherId(userId);
		
		Set<Integer> messageIdSet = new HashSet<Integer>();
		for (Integer classId : classIdList) {
			List<Integer> messageIdList = messageDao.getMessageIdListByClassId(classId);
			messageIdSet.addAll(messageIdList);
		}
	}
}
