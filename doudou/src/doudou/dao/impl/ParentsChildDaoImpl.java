package doudou.dao.impl;

import doudou.dao.ParentsChildDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.ParentsChild;

public class ParentsChildDaoImpl extends BaseEntityDao<ParentsChild, Integer> implements ParentsChildDao{

	public ParentsChildDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "ParentsChild";
	}
}
