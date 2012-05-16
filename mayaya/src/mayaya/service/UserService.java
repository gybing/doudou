package mayaya.service;

import java.util.List;

import mayaya.vo.Child;
import mayaya.vo.User;
import mayaya.vo.model.ChildInfo;

public interface UserService {
	List<User> getAllUsers();
	User getUserByTelephone(String telephone);
	List<Child> getChildListByUserId(int userId);
	List<ChildInfo> getChildInfoListByUserId(int userId);
	
	int update(User user);
	User getUserById(int userId);
}
