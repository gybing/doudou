package mayaya.dao.impl;

import java.util.Date;
import java.util.List;

import mayaya.dao.PictureDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Picture;

public class PictureDaoImpl extends BaseEntityDao<Picture, Integer> implements PictureDao {

	public PictureDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Picture";
	}

	
	public List<Picture> getPictures() {
		return reads("getPictures",null);
	}

	@Override
	public List<Picture> getPicturesByDate(Date date) {
		return reads("getPicturesByDate", date);
	}

	@Override
	public Picture getNotificationVOById(int contentId) {
		return read("getNotificationVOById",contentId);
	}
}
