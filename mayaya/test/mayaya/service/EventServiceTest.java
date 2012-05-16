package mayaya.service;

import mayaya.service.impl.EventServiceImpl;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Event;
import junit.framework.TestCase;

public class EventServiceTest extends TestCase {

	EventService es;
	
	protected void setUp() throws Exception {
		es = EventServiceImpl.getInstance();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetEvents() {
		fail("Not yet implemented");
	}

	public void testAddEvent() {
		Event event = new Event();
		event.setContent("测试内容");
		event.setEventID(0);
		event.setBeginTime(DateUtil.getInstance().fromString("2000-12-12 12:12:12"));
		event.setEndTime(DateUtil.getInstance().fromString("2000-12-12 12:12:12"));
		event.setTitle("标题");
		event.setLocation("loc");
		
		es.addEvent(event);
	}

}
