package doudou.dao.impl;

import java.util.List;

import doudou.dao.ParentsDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Child;
import doudou.vo.Parents;

public class ParentsDaoImpl extends BaseEntityDao<Parents, Integer> implements ParentsDao{

	public ParentsDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "Parents";
	}

	@Override
	public List<Child> getChildList(int parentId) {
		return (List<Child>)readObjects("getChildList",parentId);
	}
}
