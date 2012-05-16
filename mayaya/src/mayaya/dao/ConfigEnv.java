package mayaya.dao;

public class ConfigEnv {

	public static final String ENV_TEST = "test";
	public static final String ENV_DEV = "dev";
	public static final String ENV_STAGING = "staging";
	public static final String ENV_PROD = "prod";
	public static final String ENV_CI = "ci";
	public static final String ENV_UNITTEST = "unittest";
		
	private static final ConfigEnv instance = new ConfigEnv();	
		
	public static volatile String env = System.getProperty("env", ENV_DEV);
	
	
	public static ConfigEnv getInstance(){
	    return instance;
	}
	
	/**
	 * 根据运行环境得到配置文件�?br/>
	 * in Environment prod/dev:<br/>
	 * getConfig("db","properties")=db.properties<br/>
	 * getConfig("db",null)=db.properties<br/>
	 * getConfig("db","xml")=db.xml<br/>
	 * in Environment test/staging/ci/unittest:<br/>
	 * getConfig("db","properties")=db-xx.properties<br/>
	 * @param name 		文件�?
	 * @param postfix   后缀，默认为properties  
	 * @return
	 */
	public String getConfig(String name, String postfix){
	    
		if (null == postfix || postfix.trim().length()==0) {
			postfix = "properties";
		}
		postfix = postfix.trim();		
		
		if(ENV_DEV.equals(env)){//ENV_PROD.equals(env) || 
		    return name + "." + postfix;			
		}else{
			return name + "-" + env + "." + postfix;			
		}		
	}
	
	/**
	 * 与getConfig(name, "properties") 相同
	 * @param name
	 * @return
	 */
	public String getConfig(String name){
		return getConfig(name, "properties");
	}
	
	public static void main(String[] args) {
		System.out.println("env=" + env);
		String dbf = instance.getConfig("db");
		if(dbf.equals("db.properties"))
			System.out.println("ok");
		dbf = instance.getConfig("db", "xml");
		if(dbf.equals("db.xml"))
			System.out.println("ok");
		
		System.out.println(ConfigEnv.getInstance().getConfig("service"));
		
	}
	
	public static boolean isProdEnv() {
		return ENV_PROD.equalsIgnoreCase(env);
	}
	
	public static boolean isDevEnv() {
		return ENV_DEV.equalsIgnoreCase(env);
	}
}
