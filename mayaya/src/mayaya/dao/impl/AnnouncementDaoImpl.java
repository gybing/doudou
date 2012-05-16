package mayaya.dao.impl;

import mayaya.dao.AnnouncementDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Announcement;

public class AnnouncementDaoImpl extends BaseEntityDao<Announcement, Integer> implements AnnouncementDao {

	public AnnouncementDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Announcement";
	}

	@Override
	public Announcement getNotificationVOById(int contentId) {
		return read("getNotificationVOById", contentId);
	}

}
