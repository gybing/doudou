package doudou.service;


import java.util.List;

import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.SchoolDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.School;


@Service
public class SchoolService {
	
	private final DatabaseDao myDatabaseDao; 
	private final SchoolDao schoolDao;
	
	public SchoolService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		schoolDao = myDatabaseDao.getEntityDao(SchoolDao.class);
	}
	
	public List<School> getSchoolList() {
		return schoolDao.getSchoolList();
	}
	
}
