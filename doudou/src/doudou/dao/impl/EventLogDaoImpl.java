package doudou.dao.impl;

import doudou.dao.EventLogDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.EventLog;

public class EventLogDaoImpl extends BaseEntityDao<EventLog, Integer> implements
		EventLogDao {

	public EventLogDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "EventLog";
	}

	
}
