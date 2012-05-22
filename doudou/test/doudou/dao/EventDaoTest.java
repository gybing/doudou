package doudou.dao;

import java.util.Date;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Event;
import doudou.vo.type.PublishLevel;
import junit.framework.TestCase;

public class EventDaoTest extends TestCase {
	DatabaseDao dao;
	EventDao eventDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		eventDao = dao.getEntityDao(EventDao.class);
	}
	
	public void testAddEvent() {
		Event e = new Event();
		e.setTitle("This is title");
		e.setContent("This is content");
		e.setBeginTime(new Date());
		e.setEndTime(new Date());
		e.setAtChildList("Test,ddd");
		e.setLocation("This is location");
		e.setPublishLevel(PublishLevel.School);
		int r = (Integer)eventDao.create(e);
		System.out.println(r);
	}
}
