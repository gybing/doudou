package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Child;

public interface ChildDao extends EntityDao<Child, Integer> {
	List<Child> getChildListByClassId(int classId);
}
