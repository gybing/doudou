package doudou.dao.impl;

import java.util.List;

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

	@Override
	public List<MessageUser> getListByMessageId(int messageId) {
		return reads("getListByMessageId",messageId);
	}
	
}
