package mayaya.util.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;

public class RangeExecutor {

	private final int beginId;
	private final int endId;
	private final SqlMapClient client;
	
	public RangeExecutor(int beginId, int endId, SqlMapClient client) {
		this.beginId = beginId;
		this.endId = endId;
		this.client = client;
	}
	
	public boolean contains(int id) {
		if(endId < 0) {
			return beginId <= id || id < 0;
		} else {
			return beginId <= id && id < endId;
		}
	}

	public int getBeginId() { return beginId; }
	public int getEndId() { return endId; }
	public SqlMapClient getClient() { return client; }

}
