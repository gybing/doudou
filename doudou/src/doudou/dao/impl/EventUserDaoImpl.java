package doudou.dao.impl;

import doudou.dao.EventUserDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.EventUser;

public class EventUserDaoImpl extends BaseEntityDao<EventUser, Integer> implements EventUserDao {

	public EventUserDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "EventUser";
	}

	@Override
	public void updateEUUnavailable(EventUser eventUser) {
		update("updateEUUnavailable",eventUser);
	}
	
}
