package doudou.dao.impl;

import doudou.dao.MessageLogDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.MessageLog;

public class MessageLogDaoImpl extends BaseEntityDao<MessageLog, Integer> implements MessageLogDao{

	public MessageLogDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "MessageLog";
	}
	
}
