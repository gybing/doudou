package doudou.dao.impl;

import doudou.dao.PasswdLogDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.PasswdLog;

public class PasswdLogDaoImpl extends BaseEntityDao<PasswdLog, Integer> implements PasswdLogDao{

	public PasswdLogDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "PasswdLog";
	}
}
