package mayaya.service.impl;

import java.util.List;

import mayaya.service.MayayaService;
import junit.framework.TestCase;

public class MayayaServiceImplTest extends TestCase {

	MayayaService mayayaService;
	
	protected void setUp() throws Exception {
		super.setUp();
		mayayaService = MayayaServiceImpl.getInstance();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance() {
		fail("Not yet implemented");
	}

	public void testPublishTaskPicPublishTask() {
		fail("Not yet implemented");
	}

	public void testPublishTaskEvtPublishTask() {
		fail("Not yet implemented");
	}

	public void testGetPhotoWallData() {
		fail("Not yet implemented");
	}

	public void testGetMainPageData() {
		fail("Not yet implemented");
	}

	public void testGetEventByDate() {
		fail("Not yet implemented");
	}

	public void testUpdateChildList() {
		fail("Not yet implemented");
	}

	public void testGetSimpleMainPageData() {
		fail("Not yet implemented");
	}

	public void testGetTagedChildInfo() {
		fail("Not yet implemented");
	}

	public void testGetNotificationVOListByUserId() {
		List<Object> r = mayayaService.getNotificationVOListByUserId(60, 1, 5);
		System.out.println(r.size());
	}

	public void testPublishTaskAnnouncePubTask() {
		fail("Not yet implemented");
	}

}
