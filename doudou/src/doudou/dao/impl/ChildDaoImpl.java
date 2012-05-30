package doudou.dao.impl;

import java.util.HashMap;
import java.util.List;

import doudou.dao.ChildDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;

public class ChildDaoImpl extends BaseEntityDao<Child, Integer> implements
		ChildDao {

	public ChildDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Child";
	}

	@Override
	public List<Child> getChildListByClassId(int classId) {
		return reads("getChildListByClassId",classId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getClassIdListByChildIdList(List<Integer> childIdList) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("childIdList", childIdList);
		return readObjects("getClassIdListByChildIdList", conditions);
	}

	

}
