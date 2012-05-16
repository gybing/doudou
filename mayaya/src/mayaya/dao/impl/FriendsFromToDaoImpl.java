package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.FriendsFromToDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Friends_From_To;
import mayaya.vo.User;

public class FriendsFromToDaoImpl extends
		BaseEntityDao<Friends_From_To, Integer> implements FriendsFromToDao {

	public FriendsFromToDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Friends_From_To";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriendsByFromChildId(int toChildID) {
		return (List<User>)readObjects("getFriendsByFromUserId", toChildID);
	}
}
