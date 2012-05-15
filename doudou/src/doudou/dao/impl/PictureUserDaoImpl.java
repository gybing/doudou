package doudou.dao.impl;

import doudou.dao.PictureUserDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.PictureUser;

public class PictureUserDaoImpl extends BaseEntityDao<PictureUser, Integer> implements PictureUserDao{

	public PictureUserDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "PictureUser";
	}
	
}
