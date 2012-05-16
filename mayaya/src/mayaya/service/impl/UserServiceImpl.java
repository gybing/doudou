package mayaya.service.impl;

import java.util.ArrayList;
import java.util.List;

import mayaya.dao.ChildDao;
import mayaya.dao.DaoFactory;
import mayaya.dao.PushPictureUserDao;
import mayaya.dao.RelationsChildUserDao;
import mayaya.dao.RelationsEventUserDao;
import mayaya.dao.UserDao;
import mayaya.service.UserService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Child;
import mayaya.vo.Relations_Child_User;
import mayaya.vo.User;
import mayaya.vo.model.ChildInfo;

public class UserServiceImpl implements UserService {

	private DatabaseDao myDatabaseDao;
	private UserDao userDao;
	private RelationsChildUserDao relationsChildUserDao;
	private ChildDao childDao;
	private PushPictureUserDao pushPictureUserDao;
	private RelationsEventUserDao relationsEventUserDao;
	
	private static UserService instance = new UserServiceImpl();
	
	private UserServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		relationsChildUserDao = myDatabaseDao.getEntityDao(RelationsChildUserDao.class);
		pushPictureUserDao = myDatabaseDao.getEntityDao(PushPictureUserDao.class);
		relationsEventUserDao = myDatabaseDao.getEntityDao(RelationsEventUserDao.class);
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
	}
	
	public static UserService getInstance() {
		return instance;
	}
	
	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public User getUserByTelephone(String telephone) {
		return userDao.getUserByTelephone(telephone);
	}

	@Override
	public List<Child> getChildListByUserId(int userId) {
		List<Child> result = new ArrayList<Child>();
		List<Relations_Child_User> childList = relationsChildUserDao.getChildrenIdByUserId(userId);
		for (Relations_Child_User child : childList) {
			int childId = child.getChildID();
			Child childInfo = childDao.read(childId);
			childInfo.setMother(relationsChildUserDao.getParentsNameByChildId(childId,"Female"));
			childInfo.setFather(relationsChildUserDao.getParentsNameByChildId(childId,"Male"));
			
			result.add(childInfo);
		}
		
		return result;
		
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public List<ChildInfo> getChildInfoListByUserId(int userId) {
		List<ChildInfo> result = new ArrayList<ChildInfo>();
		List<Relations_Child_User> childList = relationsChildUserDao.getChildrenIdByUserId(userId);
		for (Relations_Child_User child : childList) {
			int childId = child.getChildID();
			ChildInfo childInfo = new ChildInfo();
			childInfo.setChild(childDao.read(childId));
			childInfo.setPictureCount(pushPictureUserDao.getPicturesCountByChildId(childId));
			childInfo.setEventCount(relationsEventUserDao.getEventsCountByChildId(childId)+relationsEventUserDao.getSchoolEventCount());
			
			result.add(childInfo);
		}
		
		return result;
	}

	@Override
	public User getUserById(int userId) {
		return userDao.read(userId);
	}

}
