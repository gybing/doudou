package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.DoudouInfoType;

public interface DoudouInfoTypeDao extends EntityDao<DoudouInfoType, Integer> {
	public List<DoudouInfoType> getMessageTypeBySchoolId(int schoolId);
	public List<DoudouInfoType> getTeacherTypeBySchoolId(int schoolId);
}
