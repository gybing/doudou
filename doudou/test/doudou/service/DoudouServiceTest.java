package doudou.service;

import junit.framework.TestCase;
import doudou.vo.User;
import doudou.vo.model.SessionData;

public class DoudouServiceTest extends TestCase {

	DoudouService doudouService = new DoudouService();
	
//	public void testGetToken() {
//		User u = new User();
//		u.setId(1);
//		u.setGender("Male");
//		u.setLogin("Test");
//		String token = doudouService.getToken(u);
//		System.out.println("token =" + token);
//	}

	public void testGetSessionData() {
		User u = new User();
		u.setId(1);
		u.setUserType("Teacher");
		u.setFirstName("测试老师1");
		SessionData sd = doudouService.getSessionData(u);
		System.out.println(sd.getTagedInfoMap().size());
	}

}
