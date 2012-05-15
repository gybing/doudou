package doudou.dao.impl;

import doudou.dao.SchoolDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.School;

public class SchoolDaoImpl extends BaseEntityDao<School, Integer> implements SchoolDao{
	
	public SchoolDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "School";
	}

}
