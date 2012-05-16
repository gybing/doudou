package mayaya.util.ibatis;

import java.util.Properties;

import mayaya.util.tool.Property;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisManager {

	public static SqlMapClient makeSqlmapClient(String main, String dbProp, String dbCfg) {
		try {
			Property property = new Property(dbProp);
			
			String mainServer = property.getValueAsString(main.trim()+".server", "main").trim();
			String mainDriver = property.getValueAsString(mainServer+".driver", DRIVER).trim();
			String mainUrl = property.getValueAsString(mainServer+".url", URL).trim();
			String mainUsername = property.getValueAsString(mainServer+".username", NAME).trim();
			String mainPassword = property.getValueAsString(mainServer+".password", PASSWORD).trim();
			System.out.println("---------" + mainServer + mainDriver + mainUrl + mainUsername + mainPassword);
			return SqlMapClientBuilder.buildSqlMapClient(Resources.getResourceAsReader(dbCfg), getDbProperties(mainDriver, mainUrl, mainUsername, mainPassword));
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("---------------DATABASE ERROR--------------");
			throw new IllegalArgumentException("DB config error: main=" + main + ", dbProp=" + dbProp + ", dbCfg=" + dbCfg, e);
		}
	}
	
	public static RangeExecutor[] makeRangeSplitSqlmapClient(String split, String dbProp, String splitCfg) {
		try {
			split = split.trim();
			Property property = new Property(dbProp);

			RangeExecutor[] executors = null;
			String serverList = property.getValueAsString(split+".servers");
			if(serverList!=null && !serverList.trim().isEmpty()) {
				String[] servers = serverList.trim().split(",");
				executors = new RangeExecutor[servers.length];
				for(int i=0; i<servers.length; i++)
				{
					String s = servers[i].trim();
					if(s.isEmpty()) continue;
					
					String driver = property.getValueAsString(split+"."+s+".driver", DRIVER).trim();
					String url = property.getValueAsString(split+"."+s+".url", URL).trim();
					String username = property.getValueAsString(split+"."+s+".username", NAME).trim();
					String password = property.getValueAsString(split+"."+s+".password", PASSWORD).trim();
					int beginId = property.getValueAsInt(split+"."+s+".beginId", 0);
					int endId = property.getValueAsInt(split+"."+s+".endedId", 0);
					
					SqlMapClient splitClient = SqlMapClientBuilder.buildSqlMapClient(Resources.getResourceAsReader(splitCfg), getDbProperties(driver, url, username, password));
					executors[i] = new RangeExecutor(beginId, endId, splitClient);
				}
			}
			return executors;
			
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("---------------DATABASE ERROR--------------");
			throw new IllegalArgumentException("DB config error: split=" + split + ", dbProp=" + dbProp + ", splitCfg=" + splitCfg, e);
		}
	}
	
	public static SqlMapClient[] makeModSplitSqlmapClient(String split, String dbProp, String splitCfg) {
		try {
			split = split.trim();
			Property property = new Property(dbProp);

			SqlMapClient[] clients = null;
			String serverList = property.getValueAsString(split+".servers");
			if(serverList!=null && !serverList.trim().isEmpty()) {
				String[] servers = serverList.trim().split(",");
				clients = new SqlMapClient[servers.length];
				for(int i=0; i<servers.length; i++)
				{
					String s = servers[i].trim();
					if(s.isEmpty()) continue;
					
					String driver = property.getValueAsString(split+"."+s+".driver", DRIVER).trim();
					String url = property.getValueAsString(split+"."+s+".url", URL).trim();
					String username = property.getValueAsString(split+"."+s+".username", NAME).trim();
					String password = property.getValueAsString(split+"."+s+".password", PASSWORD).trim();
					
					clients[i] = SqlMapClientBuilder.buildSqlMapClient(Resources.getResourceAsReader(splitCfg), getDbProperties(driver, url, username, password));
				}
			}
			return clients;
			
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("---------------DATABASE ERROR--------------");
			throw new IllegalArgumentException("DB config error: split=" + split + ", dbProp=" + dbProp + ", splitCfg=" + splitCfg, e);
		}
	}
	

	private static Properties getDbProperties(String driver, String url, String username, String password) {
		Properties p = new Properties();
		p.setProperty("driver", driver);
		p.setProperty("url", url);
		p.setProperty("username", username);
		p.setProperty("password", password);
		return p;
	}
	
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1/mayaya?useUnicode=true&characterEncoding=UTF8&connectTimeout=10000&socketTimeout=20000&zeroDateTimeBehavior=convertToNull";
	private final static String NAME = "root";
	private final static String PASSWORD = "123";
	
}
