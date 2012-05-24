package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.SchoolClass;
import junit.framework.TestCase;

public class SchoolClassDaoTest extends TestCase {
	DatabaseDao dao;
	SchoolClassDao schoolClassDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		schoolClassDao = dao.getEntityDao(SchoolClassDao.class);
	}
		
		
	public void testAddClass() {
		SchoolClass sc = new SchoolClass();
		sc.setSchoolClassName("测试班级1");
		sc.setSchoolId(1);
		sc.setDescription("这是测试班级");
		schoolClassDao.create(sc);
		
	}
}
