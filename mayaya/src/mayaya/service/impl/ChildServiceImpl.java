package mayaya.service.impl;

import mayaya.dao.ChildDao;
import mayaya.dao.DaoFactory;
import mayaya.service.ChildService;
import mayaya.util.dao.DatabaseDao;

public class ChildServiceImpl implements ChildService {

	private DatabaseDao myDatabaseDao;
	private ChildDao childDao;
	private static ChildService instance = new ChildServiceImpl();
	
	private ChildServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
	}
	
	public static ChildService getInstance() {
		return instance;
	}
	
	@Override
	public int updateHeadPic(int childId, String headPicUrl) {
		return childDao.updateHeadPic(childId, headPicUrl);
	}

	@Override
	public int updateCover(int childId, String coverUrl) {
		return childDao.updateCover(childId, coverUrl);
	}

}
