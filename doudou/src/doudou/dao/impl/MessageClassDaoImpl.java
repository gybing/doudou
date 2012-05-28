package doudou.dao.impl;

import doudou.dao.MessageClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.MessageClass;

public class MessageClassDaoImpl extends BaseEntityDao<MessageClass, Integer> implements MessageClassDao{

	public MessageClassDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "MessageClass";
	}
	
}
