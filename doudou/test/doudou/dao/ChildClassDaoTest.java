package doudou.dao;

import java.util.Date;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;
import doudou.vo.ChildClass;
import junit.framework.TestCase;

public class ChildClassDaoTest extends TestCase {
	DatabaseDao dao;
	ChildClassDao childClassDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		childClassDao = dao.getEntityDao(ChildClassDao.class);
	}
	
	public void testAddChildClass() {
		ChildClass cc = new ChildClass();
		cc.setChildId(1);
		cc.setClassId(1);
		cc.setEndTime(new Date());
		cc.setStartTime(new Date());
		
		
		int r = (Integer)childClassDao.create(cc);
		System.out.println(r);
	}
}
