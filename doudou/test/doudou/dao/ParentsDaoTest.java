package doudou.dao;

import doudou.util.dao.DatabaseDao;
import doudou.vo.Parents;
import doudou.vo.User;
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
		User u = p.getUser();
		System.out.println(u.getFirstName());
		System.out.println(p.getUser().getFirstName());
		System.out.println(p.getTelephone());
	}
	
//	public void testAddParents() {
//		Parents p = new Parents();
//		p.setParentId(1);
//		p.setPrivateEmail("privateEmail");
//		p.setContact1("contact1");
//		p.setContact2("contact2");
//		p.setDescription("description");
//		p.setMobile("mobile");
//		p.setOtherInfo("otherInfo");
//		p.setTelephone("123456789");
//		p.setWorkEmail("workEmail");
//		
//		parentsDao.create(p);
//	}

}
