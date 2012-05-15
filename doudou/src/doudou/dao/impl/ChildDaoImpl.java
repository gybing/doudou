package doudou.dao.impl;

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

	

}
