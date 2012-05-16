package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.User;

public interface UserDao extends EntityDao <User, Integer>{
	List<User> getAllUsers();
	User getUserByTelephone(String telephone);
	String getUserEmailById(int id);
	
	int isExistLogin(String login);
}
