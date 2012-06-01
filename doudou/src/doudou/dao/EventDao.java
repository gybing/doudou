package doudou.dao;

import java.util.Date;
import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.type.PublishLevel;

public interface EventDao extends EntityDao<Event, Integer>{
	
	List<Event> getEventListByClassIdListAndDate(List<Integer> classIdList, Date date);
	
	ListResult<Event> getClassAllEventList(List<Integer> classIdList, int offset, int count);
	
	ListResult<Event> queryClassEventList(List<Integer> classIdList, String title, PublishLevel publishLevel, int offset, int count);
	
	void updateUnavailable(int eventId);
	
	Event getNextEvent(List<Integer> classIdList,int currentEventId);
	Event getPreviousEvent(List<Integer> classIdList,int currentEventId);
}
