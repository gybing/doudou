package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.School;
import junit.framework.TestCase;

public class SchoolDaoTest extends TestCase {
	DatabaseDao dao;
	SchoolDao schoolDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		schoolDao = dao.getEntityDao(SchoolDao.class);
	}
		
		
	public void testAddSchool() {
		School s = new School();
		s.setSchoolName("测试学校Doudou");
		s.setSuffix("DoudouT");
		s.setCityId(1);
		schoolDao.create(s);
	}
}
