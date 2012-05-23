package doudou.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.util.Constants;
import doudou.util.tool.Base64;
import doudou.vo.User;
import doudou.vo.model.SessionData;

@Service
public class DoudouService {
	private Logger logger = Logger.getLogger(getClass());
	
	public String getToken(User user) {
		String tokenString = Constants.DOUDOU_TICKET + "/" + user.getId() + "/" + user.getLogin();
		System.out.println("Before Encode : " + tokenString);
		String token = Base64.encode(tokenString.getBytes());
		System.out.println("After Encode : token = " + token);
		return token;
	}
	
	public SessionData getSessionData(User user) {
		SessionData sessionData = new SessionData();
		sessionData.setUser(user);
		
		
		return sessionData;
	}
}
