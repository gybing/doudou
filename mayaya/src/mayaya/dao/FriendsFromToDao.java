package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Friends_From_To;
import mayaya.vo.User;

public interface FriendsFromToDao extends EntityDao<Friends_From_To, Integer> {
	List<User> getFriendsByFromChildId(int toChildID);
}
