package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Parents;
import junit.framework.TestCase;

public class ParentsDaoTest extends TestCase {

	DatabaseDao dao;
	ParentsDao parentsDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		parentsDao = dao.getEntityDao(ParentsDao.class);
	}
		
	public void testGetChildList() {
		//parentsDao.getChildList(1);
	}
	
	public void testGetParents() {
		Parents p = parentsDao.read(2);
		System.out.println(p.getUser().getFirstName());
		System.out.println(p.getTelephone());
	}

}
