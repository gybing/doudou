package mayaya.dao.impl;

import mayaya.dao.DaoFactory;
import mayaya.dao.RelationsEventUserDao;
import junit.framework.TestCase;

public class RelationsEventUserDaoImplTest extends TestCase {
	
	RelationsEventUserDao relationsEventUserDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		relationsEventUserDao = DaoFactory.getInstance().getMyDatabaseDao().getEntityDao(RelationsEventUserDao.class);
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetEventsCountByChildId() {
		int count = relationsEventUserDao.getEventsCountByChildId(0);
		System.out.println(count);
	}

}
