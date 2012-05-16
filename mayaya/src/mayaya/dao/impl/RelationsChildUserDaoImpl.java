package mayaya.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mayaya.dao.RelationsChildUserDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Relations_Child_User;
import mayaya.vo.model.TagedChildInfo;

public class RelationsChildUserDaoImpl extends
		BaseEntityDao<Relations_Child_User, Integer> implements RelationsChildUserDao{

	public RelationsChildUserDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Relations_Child_User";
	}

	@Override
	public List<Integer> getParentsIdByChildId(int childId) {
		return readObjects("getParentsIdByChildId",childId);
	}

	@Override
	public List<Relations_Child_User> getChildrenIdByUserId(int userId) {
		return reads("getChildrenIdByUserId",userId);
	}

	@Override
	public String getParentsNameByChildId(int childId, String gender) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("childId", childId);
		params.put("gender",gender);
		return (String)readObject("getParentsNameByChildId",params);
	}

	@Override
	public List<TagedChildInfo> getTagedChildInfo(int userId) {
		return readObjects("getTagedChildInfo",userId);
	}

	@Override
	public List<Integer> getRelatedUserIDByChildId(int childId) {
		return readObjects("getRelatedUserIdByChildId",childId);
	}

}
