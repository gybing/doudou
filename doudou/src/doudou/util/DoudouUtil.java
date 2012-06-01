package doudou.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import doudou.dao.ChildDao;
import doudou.dao.DaoFactory;
import doudou.util.dao.DatabaseDao;

public class DoudouUtil {
	
	private static DoudouUtil instance = new DoudouUtil();
	private Logger logger = Logger.getLogger(getClass());
	private final ChildDao childDao;
	private final DatabaseDao myDatabaseDao; 
	
	private DoudouUtil() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
	}
	
	public static DoudouUtil getInstance() {
		return instance;
	}
	
	public List<Integer> getChildIdListFromString(String idString) {
		logger.info("Parse idString : " + idString);
		String[] idArray = idString.split(",");
		List<Integer> result = new ArrayList<Integer>();
		for (String idS : idArray) {
			result.add(Integer.parseInt(idS));
		}
		return result;
	}
	
	public List<Integer> getClassIdListFromChildIdList(List<Integer> childIdList) {
		List<Integer> classIdList = childDao.getClassIdListByChildIdList(childIdList);
		logger.info(String.format("Get child class Id : %s",classIdList));
		return classIdList;
	}
	
}
