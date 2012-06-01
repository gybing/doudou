package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.MessageUser;

public interface MessageUserDao extends EntityDao<MessageUser, Integer>{
	List<MessageUser> getListByMessageId(int messageId);
	void updateMUUnavailable(MessageUser messageUser);
	
}
