package doudou.service;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import doudou.dao.DaoFactory;
import doudou.dao.UserDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.User;


@Service
public class UserService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final UserDao userDao;
	
	private UserService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
	}
	
	public User verifyUserNamePwd(String userName, String passWd) {
		return userDao.verifyUserNamePwd(userName, passWd);
	}
}
