package doudou.dao.impl;

import java.util.List;

import doudou.dao.SchoolClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;
import doudou.vo.SchoolClass;

public class SchoolClassDaoImpl extends BaseEntityDao<SchoolClass, Integer> implements SchoolClassDao{

	public SchoolClassDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "SchoolClass";
	}

	@Override
	public List<Child> getChildListByClassId(int classId) {
		return (List<Child>)readObjects("getChildListByClassId",classId);
	}
	
}
