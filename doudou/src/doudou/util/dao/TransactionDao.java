package doudou.util.dao;

/**
 * A Transaction DAO interface
 */

public interface TransactionDao {

	void startTransaction();

	void commitTransaction();

	void endTransaction();

}
