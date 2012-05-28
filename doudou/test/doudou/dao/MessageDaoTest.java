package doudou.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import doudou.util.dao.DatabaseDao;
import doudou.util.vo.ListResult;
import doudou.vo.Message;

public class MessageDaoTest extends TestCase {
	DatabaseDao dao;
	MessageDao messageDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = DaoFactory.getInstance().getMyDatabaseDao();
		messageDao = dao.getEntityDao(MessageDao.class);
	}
	
//	public void testAddMessage() {
//		Message m = new Message();
//		m.setAtChildList("Test,Test2");
//		m.setContent("Message Content3");
//		m.setMessageType("测试type");
//		m.setMustFeedBack(false);
//		m.setPublishLevel(PublishLevel.Class);
//		m.setTitle("Title3");
//		m.setUserId(1);
//		m.setUserName("测试名字");
//		
//		messageDao.create(m);
//	}
	
//	public void testPageination() {
//		ListResult<Message> r = messageDao.getAllMessages();
//		System.out.println(r.getSize() + " " + r.getEntities().size());
//	}
	
	public void testGetMessageListByClassIdList() {
		List<Integer> classIdList = new ArrayList<Integer>();
		classIdList.add(1);
		classIdList.add(2);
		classIdList.add(3);
		ListResult<Message> r = messageDao.getMessageListByClassIdList(classIdList, 0, 2);
		System.out.println(r.getEntities().size());
	}
}
