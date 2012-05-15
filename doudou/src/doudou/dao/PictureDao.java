package doudou.dao;

import java.util.Date;
import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Picture;

public interface PictureDao extends EntityDao<Picture, Integer>{
	
	List<Picture> getPicturesByDate(Date date);
	Picture getNotificationVOById(int contentId);
}
