package doudou.dao.impl;

import java.util.List;

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

	@Override
	public List<Integer> getMessageIdListByClassId(int classId) {
		return (List<Integer>)readObjects("getMessageIdListByClassId",classId);
	}

	@Override
	public List<Message> getSchoolMessageList(int offset, int count) {
		return null;
	}
	
}
