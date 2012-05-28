package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.util.vo.ListResult;
import doudou.vo.Message;

public interface MessageDao extends EntityDao<Message, Integer>{
	List<Message> getSchoolMessageList(int offset, int count);
	ListResult<Message> getMessageListByClassIdList(List<Integer> classIdList, int offset, int count);
	
	ListResult<Message> getAllMessages();
}
