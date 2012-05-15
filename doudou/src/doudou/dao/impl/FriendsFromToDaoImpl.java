package doudou.dao.impl;

import doudou.dao.FriendsFromToDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Friends_From_To;

public class FriendsFromToDaoImpl extends BaseEntityDao<Friends_From_To, Integer> implements FriendsFromToDao{

	public FriendsFromToDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "Friends_From_To";
	}
	
}
