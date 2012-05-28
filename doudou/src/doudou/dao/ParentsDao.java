package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.Child;
import doudou.vo.Parents;

public interface ParentsDao extends EntityDao<Parents, Integer>{
	List<Child> getChildListByParentId(int parentId); 
	List<Parents> getParentsListByChildId(int childId);
}
