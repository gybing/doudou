package doudou.dao;

import java.util.Date;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;
import doudou.vo.Event;
import doudou.vo.type.PublishLevel;
import junit.framework.TestCase;

public class ChildDaoTest extends TestCase {
	DatabaseDao dao;
	ChildDao childDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		childDao = dao.getEntityDao(ChildDao.class);
	}
	
	public void testAddChild() {
		Child c = new Child();
		c.setFirstName("Test First Name2");
		c.setLastName("Test Last Name2");
		c.setBirthDate(new Date());
		c.setGender("Female");
		c.setPhotoURL("user/2.png");
		c.setDescription("描述2");
		c.setCover("user/cover2.png");
		
		
		int r = (Integer)childDao.create(c);
		System.out.println(r);
	}
}
