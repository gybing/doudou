package doudou.dao;

import java.util.Date;
import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Event;
import doudou.vo.type.PublishLevel;

public interface EventDao extends EntityDao<Event, Integer>{
	List<Event> getEventListByChildId(int childId);
	Event getNotificationVOById(int contentId);
	
	List<Event> queryList(String title, PublishLevel publishLevel, Date eventData, int offset, int count);
	int queryCount(String title, PublishLevel publishLevel, Date eventData);
}
