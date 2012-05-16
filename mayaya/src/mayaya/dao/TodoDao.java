package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Todo;

public interface TodoDao extends EntityDao<Todo, Integer> {
	List<Todo> getTodoListByUserId(int userId, int pageIndex, int pageSize);
}
