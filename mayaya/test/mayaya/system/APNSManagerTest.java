package mayaya.system;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import mayaya.vo.model.PushVO;
import junit.framework.TestCase;

public class APNSManagerTest extends TestCase {

	APNSManager instance = new APNSManager(new LinkedBlockingQueue<PushVO>());
	List<String> tokenList = new ArrayList<String>();
	
	Thread t = new Thread(instance);
	
	public void testPushComment() {
		t.start();
		System.out.println("Start!!!");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tokenList.add("70542355 5a875df7 f2d7049e 831172f3 e7eecbaa 751c4d3f 84d5eaf5 72fbb8a1");
		instance.pushComment(21, tokenList, "Test User");
	}

	public void testPushPicTaged() {
		fail("Not yet implemented");
	}

	public void testPushEvtTaged() {
		fail("Not yet implemented");
	}

	public void testPushAnnouncement() {
		fail("Not yet implemented");
	}

}
