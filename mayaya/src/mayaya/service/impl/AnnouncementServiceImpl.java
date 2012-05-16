package mayaya.service.impl;

import mayaya.dao.AnnouncementDao;
import mayaya.dao.ChildDao;
import mayaya.dao.DaoFactory;
import mayaya.service.AnnouncementService;
import mayaya.service.ChildService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Announcement;

public class AnnouncementServiceImpl implements AnnouncementService {

	private DatabaseDao myDatabaseDao;
	private AnnouncementDao announcementDao;
	private static AnnouncementServiceImpl instance = new AnnouncementServiceImpl();
	
	private AnnouncementServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		announcementDao = myDatabaseDao.getEntityDao(AnnouncementDao.class);
	}
	
	public static AnnouncementService getInstance() {
		return instance;
	}

	@Override
	public Announcement getNotificationVOById(int announcementId) {
		return announcementDao.getNotificationVOById(announcementId);
	}

}
