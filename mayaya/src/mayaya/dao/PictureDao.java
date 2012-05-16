package mayaya.dao;

import java.util.Date;
import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Picture;

public interface PictureDao extends EntityDao<Picture, Integer>{
	
	List<Picture> getPicturesByDate(Date date);
	Picture getNotificationVOById(int contentId);
}
