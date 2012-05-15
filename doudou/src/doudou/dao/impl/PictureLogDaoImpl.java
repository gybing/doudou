package doudou.dao.impl;

import doudou.dao.PictureLogDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.PictureLog;

public class PictureLogDaoImpl extends BaseEntityDao<PictureLog, Integer> implements PictureLogDao{

	public PictureLogDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "PictureLog";
	}
}
