package mayaya.dao;

import mayaya.util.client.SimpleDatabaseClient;
import mayaya.util.dao.DatabaseDao;
import mayaya.util.ibatis.IbatisManager;

public class DaoFactory {
	
    private final DatabaseDao myDatabaseDao;
    
    private static class Holder{
		static final DaoFactory instance = new DaoFactory();
	}
    
    public static DaoFactory getInstance() {
    	return Holder.instance;
    }
    
    static private String getProfileFile() {
		return ConfigEnv.getInstance().getConfig("db");
	}

    public DatabaseDao getMyDatabaseDao() {
		return myDatabaseDao;
	}
    
    private DaoFactory() {
        String PROP_FILE_NAME = getProfileFile();
        myDatabaseDao = new MyDatabaseDao(new SimpleDatabaseClient(IbatisManager.makeSqlmapClient("mayaya", PROP_FILE_NAME, "mayaya/dao/config/sql-map-config.xml")));
    }
    
}
