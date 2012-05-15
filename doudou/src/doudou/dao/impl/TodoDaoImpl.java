package doudou.dao.impl;

import java.util.HashMap;
import java.util.List;

import doudou.dao.TodoDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Todo;

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
