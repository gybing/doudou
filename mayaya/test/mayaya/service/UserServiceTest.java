package mayaya.service;

import java.util.List;

import mayaya.service.impl.UserServiceImpl;
import mayaya.vo.User;
import junit.framework.TestCase;

public class UserServiceTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetAllUsers() {
		List<User> userList = UserServiceImpl.getInstance().getAllUsers();
		for (User user : userList) {
			System.out.println(user.getFirstName());
		}
		System.out.println(userList.size());
	}

}
