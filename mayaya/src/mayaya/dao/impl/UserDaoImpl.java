package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.UserDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.User;

public class UserDaoImpl extends BaseEntityDao<User, Integer> implements UserDao {

	public UserDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "User";
	}

	@Override
	public List<User> getAllUsers() {
		return reads("getAllUsers", null);
	}

	@Override
	public User getUserByTelephone(String telephone) {
		return read("getUserByTelephone",telephone);
	}

	@Override
	public String getUserEmailById(int id) {
		return read(id).getEmail();
	}

	@Override
	public int isExistLogin(String login) {
		User user = read("isExistLogin", login);
		if (null == user) {
			return -1;
		} else {
			return user.getUserId();
		}
	}

}
