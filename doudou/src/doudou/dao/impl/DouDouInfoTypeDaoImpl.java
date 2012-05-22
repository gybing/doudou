package doudou.dao.impl;

import java.util.List;

import doudou.dao.DoudouInfoTypeDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.DoudouInfoType;

public class DoudouInfoTypeDaoImpl extends
		BaseEntityDao<DoudouInfoType, Integer> implements DoudouInfoTypeDao {

	public DoudouInfoTypeDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "DoudouInfoType";
	}

	@Override
	public List<DoudouInfoType> getMessageTypeBySchoolId(int schoolId) {
		return reads("getMessageTypeBySchoolId",schoolId);
	}

	@Override
	public List<DoudouInfoType> getTeacherTypeBySchoolId(int schoolId) {
		return reads("getTeacherTypeBySchoolId",schoolId);
	}


}
