package doudou.service;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.util.dao.DatabaseDao;


@Service
public class ChildService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	
	public ChildService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
	}
	
}
