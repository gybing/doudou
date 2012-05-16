package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.DaoFactory;
import mayaya.dao.RelationsChildUserDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Relations_Child_User;
import junit.framework.TestCase;

public class RelationsChildUserDaoImplTest extends TestCase {

	DatabaseDao dao;
	RelationsChildUserDao rcd; 
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		rcd = dao.getEntityDao(RelationsChildUserDao.class);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetChildrenIdByUserId() {
		List<Relations_Child_User> result = rcd.getChildrenIdByUserId(60);
		for (Relations_Child_User relations_Child_User : result) {
			System.out.println(relations_Child_User.getChildID());
		}
			
	}

}
