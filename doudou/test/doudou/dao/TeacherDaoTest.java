package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Parents;
import doudou.vo.School;
import doudou.vo.Teacher;
import junit.framework.TestCase;

public class TeacherDaoTest extends TestCase {
	DatabaseDao dao;
	TeacherDao teacherDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		teacherDao = dao.getEntityDao(TeacherDao.class);
	}
		
		
	public void testAddTeacher() {
		Teacher p = new Teacher();
		p.setTeacherId(1);
		p.setPrivateEmail("privateEmail");
		p.setContact1("contact1");
		p.setContact2("contact2");
		p.setDescription("description");
		p.setMobile("mobile");
		p.setOtherInfo("otherInfo");
		p.setTelephone("123456789");
		p.setWorkEmail("workEmail");
		
		teacherDao.create(p);
	}
}
