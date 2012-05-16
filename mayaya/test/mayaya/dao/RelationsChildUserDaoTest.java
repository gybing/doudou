package mayaya.dao;

import java.util.List;

import mayaya.vo.Relations_Child_User;

import junit.framework.TestCase;

public class RelationsChildUserDaoTest extends TestCase {

	private RelationsChildUserDao relationsChildUserDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		relationsChildUserDao = DaoFactory.getInstance().getMyDatabaseDao().getEntityDao(RelationsChildUserDao.class);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetParentsIdByChildId() {
		List<Integer> t = relationsChildUserDao.getParentsIdByChildId(2);
		for(Integer i : t) {
			System.out.println(i);
		}
	}

	public void testGetChildrenIdByUserId() {
		List<Relations_Child_User> t = relationsChildUserDao.getChildrenIdByUserId(48);
		for(Relations_Child_User i : t) {
			System.out.println(i.getChildID());
		}
	}

}
