package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Relations_Child_User;
import mayaya.vo.model.TagedChildInfo;

public interface RelationsChildUserDao extends
		EntityDao<Relations_Child_User, Integer> {
	List<Integer> getParentsIdByChildId(int childId);
	List<Integer> getRelatedUserIDByChildId(int childId);
	List<Relations_Child_User> getChildrenIdByUserId(int userId);
	List<TagedChildInfo> getTagedChildInfo(int userId);
	
	String getParentsNameByChildId(int childId, String gender);
}
