package mayaya.service.impl;

import mayaya.dao.DaoFactory;
import mayaya.dao.PictureDao;
import mayaya.service.PictureService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Picture;
import junit.framework.TestCase;

public class PictureServiceImplTest extends TestCase {

	DatabaseDao myDatabaseDao;
	PictureDao pd;
	PictureService ps;
	
	protected void setUp() throws Exception {
		super.setUp();
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		myDatabaseDao.startTransaction();
		ps = new PictureServiceImpl();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddPicture() {
		ps.addPicture(new Picture());
	}

}
