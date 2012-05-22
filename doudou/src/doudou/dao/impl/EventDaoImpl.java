package doudou.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doudou.dao.EventDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Event;
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
	public List<Event> getEventListByChildId(int childId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toChildId", childId);
		return reads("getEventListByChildId",params);
	}

	@Override
	public Event getNotificationVOById(int contentId) {
		return read("getNotificationVOById",contentId);
	}

	@Override
	public List<Event> queryList(String title, PublishLevel publishLevel,Date eventData, int offset, int count) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (null != title && !title.isEmpty()) {
        	conditions.put("title", title);
        }
        if (null != eventData) {
        	conditions.put("date", eventData);
        }
        if (null != publishLevel) {
			conditions.put("publishLevel", publishLevel);
		}
		
        conditions.put("offset", offset);
        conditions.put("count", count);

		return reads("queryList", conditions);
	}

	@Override
	public int queryCount(String title, PublishLevel publishLevel,Date eventData) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (null != title && !title.isEmpty()) {
        	conditions.put("title", title);
        }
        if (null != eventData) {
        	conditions.put("date", eventData);
        }
        if (null != publishLevel) {
			conditions.put("publishLevel", publishLevel);
		}
		
		return count("queryCount",conditions);
	}

	
}
