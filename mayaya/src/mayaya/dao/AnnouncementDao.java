package mayaya.dao;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Announcement;

public interface AnnouncementDao extends EntityDao<Announcement, Integer> {
	Announcement getNotificationVOById(int contentId);
}
