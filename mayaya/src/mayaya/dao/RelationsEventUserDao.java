package mayaya.dao;

import java.util.Date;
import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Event;
import mayaya.vo.Relations_Event_User;

public interface RelationsEventUserDao extends EntityDao<Relations_Event_User, Integer> {
	
	List<Event> getMainPageEvents(int childId, Date date);
	
	List<Event> getEventByDate(int childId, Date date);
	
	int getEventsCountByChildId(int childId);
	
	int getSchoolEventCount();
}
