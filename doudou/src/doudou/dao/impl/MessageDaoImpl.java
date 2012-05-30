package doudou.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import doudou.dao.MessageDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Message;
import doudou.vo.type.PublishLevel;

public class MessageDaoImpl extends BaseEntityDao<Message, Integer> implements MessageDao{

	public MessageDaoImpl(DatabaseDao database) {
		super(database);
	}
	
	@Override
	public String getNamespace() {
		return "Message";
	}


	@Override
	public List<Message> getSchoolMessageList(int offset, int count) {
		return null;
	}

	@Override
	public ListResult<Message> getMessageListByClassIdList(List<Integer> classIdList, int offset, int count) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		if (classIdList.size() > 0) {
			conditions.put("classIdList", classIdList);
			conditions.put("offset", offset);
			conditions.put("count", count);
		} else {
			return new ListResult<Message>();
		}
		List<Message> messageList = reads("getMessageListByClassIdList", conditions);
		int counts = count("getFoundRows",null);
		ListResult<Message> result = new ListResult<Message>(messageList, counts);
		return result;
	}

	@Override
	public ListResult<Message> getAllMessages() {
		
		List<Message> result = reads("readAll",null);
		int count = count("getFoundRows",null);
		return new ListResult<Message>(result,count);
	}

	@Override
	public int getFeedBackCount(int messageId) {
		return count("getFeedBackCount",messageId);
	}
	
	@Override
	public int getNotFeedBackCount(int messageId) {
		return count("getNotFeedBackCount",messageId);
	}

	@Override
	public int getReadCount(int messageId) {
		return count("getReadCount",messageId);
	}

	@Override
	public int getNotReadCount(int messageId) {
		return count("getNotReadCount",messageId);
	}

	@Override
	public ListResult<Message> queryClassMessageList(List<Integer> classIdList,
			int offset, int count, String title, PublishLevel publishLevel,
			Date beginTime, Date endTime, boolean mustFeedBack,
			int userId) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		if (classIdList.size() > 0) {
			conditions.put("classIdList", classIdList);
			conditions.put("offset", offset);
			conditions.put("count", count);
			conditions.put("mustFeedBack", mustFeedBack);
			conditions.put("userId", userId);
			if (null != title && !title.isEmpty()) {
	        	conditions.put("title", title);
	        }
	        if (null != publishLevel) {
				conditions.put("publishLevel", publishLevel);
			}
	        if (null != beginTime) {
	        	conditions.put("beginTime", beginTime);
			}
	        if (null != endTime) {
				conditions.put("endTime", endTime);
			}
		} else {
			return new ListResult<Message>();
		}
		List<Message> messageList = reads("queryClassMessageList", conditions);
		int counts = count("getFoundRows",null);
		ListResult<Message> result = new ListResult<Message>(messageList, counts);
		return result;
	}
	
}
