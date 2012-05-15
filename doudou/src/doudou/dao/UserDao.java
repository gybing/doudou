package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.User;

public interface UserDao extends EntityDao<User, Integer> {
	List<User> getAllUsers();
	User verifyUserNamePwd(String userName, String passWd);
}
