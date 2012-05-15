package doudou.util.dao;

import doudou.util.client.DatabaseClient;

public interface DatabaseDao extends TransactionDao,SessionDao {

	<T> T getEntityDao(Class<T> t);
	
	DatabaseClient getClient();
	
}
