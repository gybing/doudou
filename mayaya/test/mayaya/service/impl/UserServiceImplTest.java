package mayaya.service.impl;

import java.util.List;

import junit.framework.TestCase;
import mayaya.service.UserService;
import mayaya.vo.Child;

public class UserServiceImplTest extends TestCase {

	UserService us = UserServiceImpl.getInstance();

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetChildListByUserId() {
		List<Child> result = us.getChildListByUserId(54);
		for (Child childInfo : result) {
			System.out.print("Mother : " + childInfo.getMother());
			System.out.println(" Father : " + childInfo.getFather());
		}
	}
	
}
