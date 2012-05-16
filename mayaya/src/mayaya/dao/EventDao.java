package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Event;

public interface EventDao extends EntityDao<Event, Integer>{
	List<Event> getEventListByChildId(int childId);
	Event getNotificationVOById(int contentId);
}
