package doudou.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doudou.dao.UserDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.User;

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
		return reads("getAllUsers",null);
	}

	@Override
	public User verifyUserNamePwd(String userName, String passWd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("passWd", passWd);
		return read("verifyUserNamePwd", params);
	}


}
