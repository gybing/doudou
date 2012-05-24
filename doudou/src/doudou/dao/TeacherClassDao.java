package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.SchoolClass;
import doudou.vo.TeacherClass;

public interface TeacherClassDao extends EntityDao<TeacherClass, Integer>{
	//List<SchoolClass> getClassListByTeacherId(int teacherId);
	List<TeacherClass> getTeacherClassListByTeacherId(int teacherId);
}
