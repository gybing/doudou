package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Event;

public interface EventDao extends EntityDao<Event, Integer>{
	List<Event> getEventListByChildId(int childId);
	Event getNotificationVOById(int contentId);
}
