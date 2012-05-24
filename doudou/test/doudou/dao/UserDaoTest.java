package doudou.dao;

import java.util.Date;

import com.soso.common.util.MD5;

import doudou.util.dao.DatabaseDao;
import doudou.vo.School;
import doudou.vo.User;
import junit.framework.TestCase;

public class UserDaoTest extends TestCase {
	DatabaseDao dao;
	UserDao userDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		userDao = dao.getEntityDao(UserDao.class);
	}
		
		
	public void testAddUser() {
		User u = new User();
		u.setFirstName("测试家长First");
		u.setLastName("测试家长Last");
		u.setGender("Male");
		u.setLastLoginTime(new Date());
		u.setLogin("Test2");
		u.setPassWd(MD5.getMD5("Test2"));
		u.setUserType("Parents");
		
		userDao.create(u);
	}
}
