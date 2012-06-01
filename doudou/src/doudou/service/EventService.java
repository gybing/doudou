package doudou.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.EventClassDao;
import doudou.dao.EventDao;
import doudou.system.DoudouBackend;
import doudou.util.DoudouUtil;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.EventClass;
import doudou.vo.Message;
import doudou.vo.SchoolClass;
import doudou.vo.model.EvtPublishTask;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;
import doudou.vo.type.TodoType;
import edu.emory.mathcs.backport.java.util.Collections;


@Service
public class EventService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final EventDao eventDao;
	private final EventClassDao eventClassDao;
	
	public EventService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
		eventClassDao = myDatabaseDao.getEntityDao(EventClassDao.class);
	}
	
	public int addEvent(Event event, List<Integer> childIdList, List<Integer> classIdList, int schoolId) {
		//完成对象属性填充...TOBE optimized
		//eventTask.setChildrenListString(eventTask.generateAtChildrenListString());
		
		int result = (Integer)eventDao.create(event);
		if (result > 0) {
			// 保证发布者能查看到新添加的事件
			for (Integer classId : classIdList) {
				EventClass eventClass = new EventClass();
				eventClass.setClassId(classId);
				eventClass.setEventId(event.getId());
				eventClass.setSchoolId(schoolId);
				eventClassDao.create(eventClass);
			}
			EvtPublishTask task = new EvtPublishTask();
			task.setEvent(event);
			task.setTargetChildIdList(childIdList);
			task.setTodoType(TodoType.NewEvent);
			DoudouBackend.getInstance().publishTask(task);
		} 
		return result;
		
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
	/**
	 * 更新Event
	 * @param childIdList 新添加的孩子列表
	 * @param deletedChildIdList 删除的孩子id列表
	 * */
	public boolean updateEvent(SessionData sessionData, Event event, List<Integer> addedChildIdList , List<Integer> deletedChildIdList) {
		//检查是否有权限 (是否为自己发的事件)
		if (sessionData.getUser().getId() == event.getUserId()) {
			//TODO
			
			return eventDao.update(event) > 0;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除事件
	 * */
	public void deleteEvent(int eventId) {
		eventDao.updateUnavailable(eventId);
	}
	
	/**
	 * 获取下一个事件
	 * */
	public Event getNextEvent(SessionData sessionData, int eventId) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		return eventDao.getNextEvent(classIdList,eventId);
	}
	
	/**
	 * 获取上一个事件
	 * */
	public Event getPreviousEvent(SessionData sessionData, int eventId) {
		//获取该老师相关的班级id List
		Set<SchoolClass> classSet = sessionData.getSchoolClassList();
		List<Integer> classIdList = new ArrayList<Integer>();
		
		for (SchoolClass schoolClass : classSet) {
			classIdList.add(schoolClass.getId());
		}
		return eventDao.getPreviousEvent(classIdList,eventId);
	}
	
	/**
	 * 更新单一事件
	 * @param newEvent 新事件
	 * @param oldEvent 原事件
	 * @param newChildIdList 新添加的孩子id列表
	 * @param newClassIdList 新添加的班级id列表
	 * @param schoolId 学校Id
	 * 
	 * */
	public int updateEvent(Event newEvent, Event oldEvent, List<Integer> newChildIdList , List<Integer> newClassIdList, int schoolId) {
		boolean contentDiff = newEvent.getTitle().equals(oldEvent.getTitle()) 
						&& newEvent.getContent().equals(oldEvent.getContent()) 
						&& newEvent.getLocation().equals(oldEvent.getLocation())
						&& newEvent.getBeginTime().equals(oldEvent.getBeginTime())
						&& newEvent.getEndTime().equals(oldEvent.getEndTime())
						&& newEvent.isAllday() == oldEvent.isAllday();
		List<Integer> oldChildIdList = DoudouUtil.getInstance().getChildIdListFromString(oldEvent.getAtChildList());
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
			EventClass eventClass = new EventClass();
			eventClass.setClassId(classId);
			eventClass.setEventId(newEvent.getId());
			eventClass.setSchoolId(schoolId);
			eventClassDao.create(eventClass);
		}
		//删除的班级
		List<Integer> removedClassIdList = new ArrayList<Integer>(oldClassIdList.size());
		Collections.copy(removedClassIdList, oldClassIdList);
		removedClassIdList.removeAll(newClassIdList);
		// 保证发布者能查看bu到更改过的事件
		for (Integer classId : removedClassIdList) {
			EventClass eventClass = new EventClass();
			eventClass.setClassId(classId);
			eventClass.setEventId(newEvent.getId());
			eventClass.setSchoolId(schoolId);
			eventClassDao.updateECUnavailable(eventClass);
		}
		
		int result = 0;
		
		//内容一样，孩子列表一样
		if (contentDiff && addedChildIdList.size()==0 && removedChildIdList.size() == 0) {
			result = 0;
		} else if (contentDiff) {//内容一样，孩子列表发生变化
			EvtPublishTask task = new EvtPublishTask();
			task.setEvent(newEvent);
			task.setTargetChildIdList(addedChildIdList);
			task.setTodoType(TodoType.NewEvent);
			DoudouBackend.getInstance().publishTask(task);
			
			EvtPublishTask delTask = new EvtPublishTask();
			delTask.setEvent(newEvent);
			delTask.setTargetChildIdList(removedChildIdList);
			delTask.setTodoType(TodoType.DelEvent);
			DoudouBackend.getInstance().publishTask(task);
			result = eventDao.update(newEvent);
		} else if (addedChildIdList.size()==0 && removedChildIdList.size() == 0) {//内容变化，孩子列表没变
			EvtPublishTask task = new EvtPublishTask();
			task.setEvent(newEvent);
			task.setTargetChildIdList(oldChildIdList);
			task.setTodoType(TodoType.ModEvent);
			DoudouBackend.getInstance().publishTask(task);
			result = eventDao.update(newEvent);
		} else {//都变化
			EvtPublishTask task = new EvtPublishTask();
			task.setEvent(newEvent);
			task.setTargetChildIdList(addedChildIdList);
			task.setTodoType(TodoType.NewEvent);
			DoudouBackend.getInstance().publishTask(task);
			
			EvtPublishTask delTask = new EvtPublishTask();
			delTask.setEvent(newEvent);
			delTask.setTargetChildIdList(removedChildIdList);
			delTask.setTodoType(TodoType.DelEvent);
			DoudouBackend.getInstance().publishTask(task);
			
			EvtPublishTask modTask = new EvtPublishTask();
			modTask.setEvent(newEvent);
			modTask.setTargetChildIdList(unChangedChildIdList);
			modTask.setTodoType(TodoType.ModEvent);
			DoudouBackend.getInstance().publishTask(task);
			
			result = eventDao.update(newEvent);
		}

		return result;
	}
	
	/**
	 * 查看单一事件
	 * 
	 * */
	public Event getEventById(int id) {
		return eventDao.read(id);
	}
	
}
