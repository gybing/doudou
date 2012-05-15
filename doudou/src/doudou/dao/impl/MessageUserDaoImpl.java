package doudou.dao.impl;

import doudou.dao.MessageUserDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.MessageUser;

public class MessageUserDaoImpl extends BaseEntityDao<MessageUser, Integer> implements MessageUserDao{

	public MessageUserDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "MessageUser";
	}
	
}
