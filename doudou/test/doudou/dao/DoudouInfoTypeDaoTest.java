package doudou.dao;

import java.util.Date;

import doudou.util.dao.DatabaseDao;
import doudou.vo.DoudouInfoType;
import doudou.vo.type.InfoType;
import junit.framework.TestCase;

public class DoudouInfoTypeDaoTest extends TestCase {
	DatabaseDao dao;
	DoudouInfoTypeDao doudouInfoTypeDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		doudouInfoTypeDao = dao.getEntityDao(DoudouInfoTypeDao.class);
	}
	
	public void testAddDoudouInfoTypeDao() {
		DoudouInfoType type = new DoudouInfoType();
		type.setInfoType(InfoType.TeacherType);
		type.setSchoolId(1);
		type.setTypeName("校管老师");
		doudouInfoTypeDao.create(type);
	}
}
