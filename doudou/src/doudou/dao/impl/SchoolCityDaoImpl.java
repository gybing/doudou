package doudou.dao.impl;

import doudou.dao.SchoolCityDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.SchoolCity;

public class SchoolCityDaoImpl extends BaseEntityDao<SchoolCity, Integer> implements SchoolCityDao {

	public SchoolCityDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "SchoolCity";
	}

}
