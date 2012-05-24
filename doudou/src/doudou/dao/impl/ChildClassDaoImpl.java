package doudou.dao.impl;

import doudou.dao.ChildClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.ChildClass;

public class ChildClassDaoImpl extends BaseEntityDao<ChildClass, Integer> implements
		ChildClassDao {

	public ChildClassDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "ChildClass";
	}

}
