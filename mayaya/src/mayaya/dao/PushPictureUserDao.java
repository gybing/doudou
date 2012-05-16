package mayaya.dao;

import java.util.Date;
import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Picture;
import mayaya.vo.Push_Picture_User;

public interface PushPictureUserDao extends EntityDao<Push_Picture_User, Integer> {
	List<Picture> getPhotoWallData(Date date, int childId);
	List<Picture> getMainPagePics(int childId);
	int getPicturesCountByChildId(int childId);
	List<Integer> getPushChildIdListByPicId(int picId);
	
}
