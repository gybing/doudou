package doudou.service;

import java.util.List;

import doudou.vo.MessageUser;

import junit.framework.TestCase;

public class MessageServiceTest extends TestCase {
	MessageService service = new MessageService();
	
	public void testGetListByMessageId() {
		List<MessageUser> result = service.getListByMessageId(18);
		System.out.println(result.size() + result.get(0).getChildName());
	}
}
