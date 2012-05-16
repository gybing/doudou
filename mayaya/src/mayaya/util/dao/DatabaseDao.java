package mayaya.util.dao;

import mayaya.util.client.DatabaseClient;

public interface DatabaseDao extends TransactionDao,SessionDao {

	<T> T getEntityDao(Class<T> t);
	
	DatabaseClient getClient();
	
}
