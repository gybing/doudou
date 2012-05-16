package mayaya.service.impl;

import java.util.Date;
import java.util.List;

import mayaya.dao.DaoFactory;
import mayaya.dao.EventDao;
import mayaya.service.EventService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Event;

public class EventServiceImpl implements EventService {

	private DatabaseDao myDatabaseDao;
	private EventDao eventDao;
	
	private static EventService instance = new EventServiceImpl();
	
	public static EventService getInstance() {
		return instance;
	}
	
	private EventServiceImpl(){
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		eventDao = myDatabaseDao.getEntityDao(EventDao.class);
	}
	
	@Override
	public List<Event> getEventsByChildId(int childId) {
		return eventDao.getEventListByChildId(childId);
	}

	@Override
	public void addEvent(Event event) {
		eventDao.create(event);
	}

	@Override
	public Event getEventById(int eventId) {
		return eventDao.read(eventId);
	}

}
