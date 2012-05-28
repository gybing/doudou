package doudou.dao.impl;

import doudou.dao.EventClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.EventClass;

public class EventClassDaoImpl extends BaseEntityDao<EventClass, Integer> implements
		EventClassDao {

	public EventClassDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "EventClass";
	}

	
}
