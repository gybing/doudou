package mayaya.util.client;

import java.util.List;

import mayaya.util.exception.DataAccessException;

public interface DatabaseClient {

	Object insert(String id, Object parameterObject) throws DataAccessException;

	int update(String id, Object parameterObject) throws DataAccessException;

	int delete(String id, Object parameterObject) throws DataAccessException;

	Object queryForObject(String id, Object parameterObject) throws DataAccessException;

	@SuppressWarnings("rawtypes")
    List queryForList(String id, Object parameterObject) throws DataAccessException;
	
	@SuppressWarnings("rawtypes")
    List queryForList(String id) throws DataAccessException;

	void startTransaction() throws DataAccessException;

	void commitTransaction() throws DataAccessException;

	void endTransaction() throws DataAccessException;
	
	void startSession();

	void endSession();

}