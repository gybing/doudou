package doudou.dao.impl;

import doudou.dao.TeacherClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.TeacherClass;

public class TeacherClassDaoImpl extends BaseEntityDao<TeacherClass, Integer> implements TeacherClassDao{

	public TeacherClassDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "TeacherClass";
	}
}
