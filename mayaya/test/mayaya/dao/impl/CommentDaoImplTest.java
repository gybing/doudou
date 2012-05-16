package mayaya.dao.impl;

import junit.framework.TestCase;
import mayaya.dao.CommentDao;
import mayaya.dao.DaoFactory;
import mayaya.vo.Comment;

public class CommentDaoImplTest extends TestCase {

	CommentDao cd;
	
	protected void setUp() throws Exception {
		super.setUp();
		cd = DaoFactory.getInstance().getMyDatabaseDao().getEntityDao(CommentDao.class);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetNotificationVOById() {
		Comment vo = cd.getNotificationVOById(1);
		System.out.println(vo.getUserName()+ " " + vo.getContent());
	}

}
