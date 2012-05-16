package mayaya.system;

import java.util.List;

import org.apache.log4j.Logger;

import mayaya.service.UserService;
import mayaya.service.impl.UserServiceImpl;
import mayaya.vo.User;

class CacheTime{
	public long pictureLastModifiedTime = System.currentTimeMillis();
	public long eventLastModifiedTime = System.currentTimeMillis();
	public long childLastModifiedTime = System.currentTimeMillis();
	public long userLastModifiedTime = System.currentTimeMillis();
	public long commentLastModifiedTime = System.currentTimeMillis();
	public long announceLastModifiedTime = System.currentTimeMillis();
}
public class CacheManager {
	CacheTime[] cacheArray;
	
	private UserService userService;
	private static CacheManager instance = new CacheManager();
	private Logger logger = Logger.getLogger(getClass());
	
	private CacheManager() {
		userService = UserServiceImpl.getInstance();
		List<User> userList = userService.getAllUsers();
		logger.info(String.format("userList size : %d",userList.size()));
		int arraySize = userList.get(userList.size()-1).getUserId()+1;
		logger.info(String.format("arraySize : %d", arraySize));
		cacheArray = new CacheTime[arraySize];
		for (User user : userList) {
			CacheTime ct = new CacheTime();
			cacheArray[user.getUserId()] = ct;
		}
	}
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	public void putPic(int userId) {
		cacheArray[userId].pictureLastModifiedTime = System.currentTimeMillis();
	}
	
	public void putEvt(int userId) {
		cacheArray[userId].eventLastModifiedTime = System.currentTimeMillis();
	}
	
	public void putChild(int userId) {
		cacheArray[userId].childLastModifiedTime = System.currentTimeMillis();
	}
	
	public void putUser(int userId) {
		cacheArray[userId].userLastModifiedTime = System.currentTimeMillis();
	}
	
	public void putComment(int userId) {
		cacheArray[userId].commentLastModifiedTime = System.currentTimeMillis();
	}
	
	public void putAnnounce(int userId) {
		cacheArray[userId].announceLastModifiedTime = System.currentTimeMillis();
	}
	
	public long getPic(int userId) {
		return cacheArray[userId].pictureLastModifiedTime;
	}
	
	public long getEvt(int userId) {
		return cacheArray[userId].eventLastModifiedTime;
	}
	
	public long getChild(int userId) {
		return cacheArray[userId].childLastModifiedTime;
	}
	
	public long getUser(int userId) {
		return cacheArray[userId].userLastModifiedTime;
	}
	
	public long getComment(int userId) {
		return cacheArray[userId].commentLastModifiedTime;
	}
	
	public long getAnnounce(int userId) {
		return cacheArray[userId].announceLastModifiedTime;
	}
}
