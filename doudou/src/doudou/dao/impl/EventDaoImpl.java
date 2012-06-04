package doudou.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import doudou.dao.EventDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.Message;
import doudou.vo.type.PublishLevel;

public class EventDaoImpl extends BaseEntityDao<Event, Integer> implements EventDao {

	public EventDaoImpl(DatabaseDao database) {
		super(database);
		
	}

	@Override
	public String getNamespace() {
		return "Event";
	}


	@Override
	public List<Event> getEventListByClassIdListAndDate(
			List<Integer> classIdList, Date date) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("classIdList", classIdList);
		conditions.put("date", date);
		return reads("getEventListByClassIdListAndDate",conditions);
	}

	@Override
	public ListResult<Event> getClassAllEventList(List<Integer> classIdList,
			int offset, int count) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("classIdList", classIdList);
		conditions.put("offset", offset);
		conditions.put("count", count);
		
		List<Event> entities = reads("getClassAllEventList",conditions);
		int counts = count("getFoundRows", null);
		
		return new ListResult<Event>(entities,counts);
	}

	@Override
	public ListResult<Event> queryClassEventList(List<Integer> classIdList,
			String title, PublishLevel publishLevel, int offset, int count) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("classIdList", classIdList);
		conditions.put("offset", offset);
		conditions.put("count", count);
		if (null != title && !title.isEmpty()) {
        	conditions.put("title", title);
        }
        if (null != publishLevel) {
			conditions.put("publishLevel", publishLevel);
		}
		List<Event> entities = reads("queryClassEventList",conditions);
		int counts = count("getFoundRows", null);
		return new ListResult<Event>(entities,counts);
	}

	@Override
	public boolean updateUnavailable(int eventId) {
		return update("updateUnavailable",eventId) > 0;
	}
	
	/**
	 * For班级管理员
	 * */
	@Override
	public Event getNextEvent(List<Integer> classIdList, int currentEventId) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		if (classIdList.size() > 0) {
			conditions.put("classIdList", classIdList);
		} else {
			return null;
		}
		conditions.put("currentEventId", currentEventId);
		return read("getNextEventForClass",conditions);
	}

	@Override
	public Event getPreviousEvent(List<Integer> classIdList, int currentEventId) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		if (classIdList.size() > 0) {
			conditions.put("classIdList", classIdList);
		} else {
			return null;
		}
		conditions.put("currentEventId", currentEventId);
		return read("getPreviousEventForClass",conditions);
	}
	
}
