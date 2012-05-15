package doudou.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doudou.dao.EventDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Event;

public class EventDaoImpl extends BaseEntityDao<Event, Integer> implements
		EventDao {

	public EventDaoImpl(DatabaseDao database) {
		super(database);
		
	}

	@Override
	public String getNamespace() {
		return "Event";
	}

	@Override
	public List<Event> getEventListByChildId(int childId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toChildId", childId);
		return reads("getEventListByChildId",params);
	}

	@Override
	public Event getNotificationVOById(int contentId) {
		return read("getNotificationVOById",contentId);
	}

	
}
