package mayaya.dao.impl;

import java.util.HashMap;
import java.util.List;

import mayaya.dao.TodoDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Todo;

public class TodoDaoImpl extends BaseEntityDao<Todo, Integer> implements
		TodoDao {

	public TodoDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Todo";
	}

	@Override
	public List<Todo> getTodoListByUserId(int userId, int pageIndex, int pageSize) {
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("offset", (pageIndex-1)*pageSize);
		params.put("limit", pageSize);
		return reads("getTodoListByUserId",params);
	}


}
