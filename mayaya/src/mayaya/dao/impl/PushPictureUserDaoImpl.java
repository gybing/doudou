package mayaya.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mayaya.dao.PushPictureUserDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Picture;
import mayaya.vo.Push_Picture_User;

public class PushPictureUserDaoImpl extends BaseEntityDao<Push_Picture_User, Integer> implements PushPictureUserDao {

	public PushPictureUserDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Push_Picture_User";
	}

	@Override
	public List<Picture> getPhotoWallData(Date date, int childId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("date", date);
		params.put("toChildId", childId);
		return readObjects("getPhotoWallData", params);
	}

	@Override
	public List<Picture> getMainPagePics(int childId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toChildId", childId);
		
		return readObjects("getMainPagePics", params);
	}

	@Override
	public int getPicturesCountByChildId(int childId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("toChildId", childId);
		
		return count("getPicturesCountByChildId",params);
	}

	@Override
	public List<Integer> getPushChildIdListByPicId(int picId) {
		return readObjects("getPushChildIdListByPicId", picId);
	}
}
