package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.School;

public interface SchoolDao extends EntityDao<School, Integer>{
	List<School> getSchoolList();
}
