package doudou.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.EventDao;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.SchoolClass;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;


@Service
public class EventService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final EventDao eventDao;
	
	public EventService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
	}
	
	//TODO
	public int addEvent(Event newObject) {
		return (Integer)eventDao.create(newObject);
	}
	
	/**
	 * 班级管理员获得某一天事件列表
	 * 
	 * */
	public List<Event> getClassEventListByDate(SessionData sessionData,Date date) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		List<Event> result = eventDao.getEventListByClassIdListAndDate(classIdList, date);
		
		return result;
	}
	
	/**
	 * 班级管理员获得获取所有事件
	 * 
	 * */
	public ListResult<Event> getClassAllEventList(SessionData sessionData, int offset, int count) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		ListResult<Event> result = eventDao.getClassAllEventList(classIdList, offset, count);
		
		return result;
	}
	
	/**
	 * 班级管理员查询事件
	 * @param title 标题 
	 * @param publishLevel 日历性质
	 * 
	 * */
	public ListResult<Event> queryClassEventList(SessionData sessionData, String title, PublishLevel publishLevel, int offset, int count) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		
		ListResult<Event> result = eventDao.queryClassEventList(classIdList, title, publishLevel, offset, count);
		return result;
	}
	
}
