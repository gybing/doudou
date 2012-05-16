package mayaya.util.client;

import java.sql.SQLException;
import java.util.List;

import mayaya.util.exception.DataAccessException;
import mayaya.util.log.MYLog;

import com.ibatis.sqlmap.client.SqlMapClient;

public class SimpleDatabaseClient implements DatabaseClient {

	private final SqlMapClient client;
	private MYLog log = new MYLog("SimpleDatabaseClient");
	
	public SimpleDatabaseClient(SqlMapClient client) {
		this.client = client;
	}

	@Override
	public Object insert(String id, Object parameterObject) throws DataAccessException {
		String msg = "insert:" + id + "[" + parameterObject + "]=";
		long time = System.currentTimeMillis();
		try {
			Object result = client.insert(id, parameterObject);
			log.info(time, msg+result);
			return result;
		} catch (SQLException e) {
			log.error(time, msg, e);
			return -1;
		}
	}

	@Override
	public int update(String id, Object parameterObject) throws DataAccessException {
		String msg = "update:" + id + "[" + parameterObject + "]=";
		long time = System.currentTimeMillis();
		try {
			int result = client.update(id, parameterObject);
			log.info(time, msg+result);
			return result;
		} catch (SQLException e) {
			log.error(time, msg, e);
			return -1;
		}
	}

	@Override
	public int delete(String id, Object parameterObject) throws DataAccessException {
		String msg = "delete:" + id + "[" + parameterObject + "]=";
		long time = System.currentTimeMillis();
		try {
			int result = client.delete(id, parameterObject);
			log.info(time, msg+result);
			return result;
		} catch (SQLException e) {
			log.error(time, msg, e);
			return -1;
		}
	}

	@Override
	public Object queryForObject(String id, Object parameterObject) throws DataAccessException {
		String msg = "queryForObject:" + id + "[" + parameterObject + "]=";
		long time = System.currentTimeMillis();
		try {
			Object result = client.queryForObject(id, parameterObject);
			log.info(time, msg+result);
			return result;
		} catch (SQLException e) {
			log.error(time, msg, e);
			return -1;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryForList(String id, Object parameterObject) throws DataAccessException {
		String msg = "queryForList:" + id + "[" + parameterObject + "]=";
		long time = System.currentTimeMillis();
		try {
			List result = client.queryForList(id, parameterObject);
			log.info(time, msg+(result==null?-1:result.size()));
			return result;
		} catch (SQLException e) {
			System.out.println(e);
			log.error(time, msg, e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryForList(String id) throws DataAccessException {
		String msg = "queryForList:" + id + "[]=";
		long time = System.currentTimeMillis();
		try {
			List result = client.queryForList(id);
			log.info(time, msg+(result==null?-1:result.size()));
			return result;
		} catch (SQLException e) {
			log.error(time, msg, e);
			return null;
		}
	}

	
	@Override
	public void startTransaction() throws DataAccessException {
		try {
			client.startTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commitTransaction() throws DataAccessException {
		try {
			client.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void endTransaction() throws DataAccessException {
		try {
			client.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			// throw translator.translate(e, DatabaseOperation.EndTransaction);
		}
	}

	@Override
	public void startSession() {
	}

	@Override
	public void endSession() {
	}
}
