package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Message;

public interface MessageDao extends EntityDao<Message, Integer>{
	List<Integer> getMessageIdListByClassId(int classId);
	List<Message> getSchoolMessageList(int offset, int count);
}
