package doudou.dao.impl;

import doudou.dao.SchoolClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.SchoolClass;

public class SchoolClassDaoImpl extends BaseEntityDao<SchoolClass, Integer> implements SchoolClassDao{

	public SchoolClassDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "SchoolClass";
	}
	
}
