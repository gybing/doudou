package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.TeacherClass;
import junit.framework.TestCase;

public class TeacherClassDaoTest extends TestCase {
	DatabaseDao dao;
	TeacherClassDao teacherClassDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		teacherClassDao = dao.getEntityDao(TeacherClassDao.class);
	}
		
		
	public void testAddTeacherClass() {
		TeacherClass tc = new TeacherClass();
		tc.setClassId(1);
		tc.setTeacherId(1);
		tc.setTeacherTypeId(1);
		
		teacherClassDao.create(tc);
	}
}
