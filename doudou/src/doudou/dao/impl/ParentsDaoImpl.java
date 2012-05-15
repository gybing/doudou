package doudou.dao.impl;

import doudou.dao.ParentsDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Parents;

public class ParentsDaoImpl extends BaseEntityDao<Parents, Integer> implements ParentsDao{

	public ParentsDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "Parents";
	}
}
