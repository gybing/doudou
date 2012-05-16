package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.ChildDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Child;

public class ChildDaoImpl extends BaseEntityDao<Child, Integer> implements ChildDao {

	public ChildDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Child";
	}

	@Override
	public List<Child> getChildListByUserId(int userId) {
		return reads("getChildListByUserId", userId);
	}

	@Override
	public int updateHeadPic(int childId, String headPicUrl) {
		Child c = new Child();
		c.setChildID(childId);
		c.setPhotoURL(headPicUrl);
		
		return update("updateHeadPic",c);
	}

	@Override
	public int updateCover(int childId, String coverUrl) {
		Child c = new Child();
		c.setChildID(childId);
		c.setCover(coverUrl);
		
		return update("updateCover",c);
	}

}
