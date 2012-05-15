package doudou.dao.impl;

import doudou.dao.DouDouInfoTypeDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.DouDouInfoType;

public class DouDouInfoTypeDaoImpl extends
		BaseEntityDao<DouDouInfoType, Integer> implements DouDouInfoTypeDao {

	public DouDouInfoTypeDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "DouDouInfoType";
	}


}
