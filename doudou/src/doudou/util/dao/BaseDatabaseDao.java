package doudou.util.dao;

import doudou.util.client.DatabaseClient;

public abstract class BaseDatabaseDao implements DatabaseDao {

	private final DatabaseClient client;
	
	public BaseDatabaseDao(DatabaseClient client) {
		this.client = client;
	}
	
	@Override
	public DatabaseClient getClient() {
		return client;
	}
	
	@Override
	public final void startTransaction() {
		client.startTransaction();
	}
	
	@Override
	public final void commitTransaction() {
		client.commitTransaction();
	}

	@Override
	public final void endTransaction() {
		client.endTransaction();
	}
	
	@Override
	public final void startSession() {
		client.startSession();
	}

	@Override
	public final void endSession() {
		client.endSession();
	}
	
}
