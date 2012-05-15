package doudou.dao.impl;

import doudou.dao.MessageDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Message;

public class MessageDaoImpl extends BaseEntityDao<Message, Integer> implements MessageDao{

	public MessageDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "Message";
	}
	
}
