package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Todo;

public interface TodoDao extends EntityDao<Todo, Integer> {
	List<Todo> getTodoListByUserId(int userId, int pageIndex, int pageSize);
}
