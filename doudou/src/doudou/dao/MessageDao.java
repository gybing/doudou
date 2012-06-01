package doudou.dao;

import java.util.Date;
import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.util.vo.ListResult;
import doudou.vo.Message;
import doudou.vo.type.PublishLevel;

public interface MessageDao extends EntityDao<Message, Integer>{
	List<Message> getSchoolMessageList(int offset, int count);
	ListResult<Message> getMessageListByClassIdList(List<Integer> classIdList, int offset, int count);
	
	ListResult<Message> getAllMessages();
	int getFeedBackCount(int messageId);
	int getNotFeedBackCount(int messageId);
	
	int getReadCount(int messageId);
	int getNotReadCount(int messageId);
	
	ListResult<Message> queryClassMessageList(List<Integer> classIdList, int offset, int count,
			String title, PublishLevel publishLevel, Date beginTime, Date endTime, boolean mustFeedBack, int userId);

	void updateUnavailable(int messageId);
	
	Message getNextMessage(List<Integer> classIdList,int currentMessageId);
	Message getPreviousMessage(List<Integer> classIdList,int currentMessageId);
}
