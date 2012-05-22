package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Child;
import doudou.vo.SchoolClass;

public interface SchoolClassDao extends EntityDao<SchoolClass, Integer>{
	public List<Child> getChildListByClassId(int classId);
}
