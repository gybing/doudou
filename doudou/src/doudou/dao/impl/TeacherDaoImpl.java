package doudou.dao.impl;

import doudou.dao.TeacherDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Teacher;

public class TeacherDaoImpl extends BaseEntityDao<Teacher, Integer> implements TeacherDao {

	public TeacherDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "Teacher";
	}

}
