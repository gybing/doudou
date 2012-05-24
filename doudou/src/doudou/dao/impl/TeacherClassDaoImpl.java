package doudou.dao.impl;

import java.util.List;

import doudou.dao.TeacherClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.SchoolClass;
import doudou.vo.TeacherClass;

public class TeacherClassDaoImpl extends BaseEntityDao<TeacherClass, Integer> implements TeacherClassDao{

	public TeacherClassDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "TeacherClass";
	}

//	@Override
//	public List<SchoolClass> getClassListByTeacherId(int teacherId) {
//		return (List<SchoolClass>)readObjects("getClassListByTeacherId",teacherId);
//	}

	@Override
	public List<TeacherClass> getTeacherClassListByTeacherId(int teacherId) {
		return reads("getTeacherClassListByTeacherId",teacherId);
	}
}
