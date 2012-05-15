package doudou.util.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Property {

	private final Properties props;
	private Logger logger = Logger.getLogger(getClass());
	
	
	public Property(String propFile) {
		this.props = new Properties();
		InputStream is = null;
		try {
			logger.info(String.format("User PropFile:%s",propFile));
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
			props.load( is );	
		} catch (Exception e) {
		    String errMsg = String.format("Unable to open/load %s",propFile);
		    logger.error(errMsg,e);
		    throw new RuntimeException(errMsg,e);
		}finally{
		    if(is != null){
		        try {
                    is.close();
                } catch (IOException e) {    
                    String errMsg = String.format("Unable to close %s",propFile);
                    logger.error(errMsg,e);
                    throw new RuntimeException(errMsg,e);
                }
		    }
		}
	}

	public Property(Properties props) {
		this.props = props;
	}
	public Properties getProps() { return props;}

	public int getValueAsInt(String key, int defaultValue) {
		int value = defaultValue;
		String v = props.getProperty(key);
		if(v != null) {
			try { value = Integer.parseInt(v.trim()); } catch(Exception e) { e.printStackTrace(); }
		}
		return value;
	}
	
	public boolean getValueAsBool(String key, boolean defaultValue) {
		boolean value = defaultValue;
		String v = props.getProperty(key);
		if(v != null) {
			try { value = Boolean.parseBoolean(v.trim()); } catch(Exception e) { e.printStackTrace(); }
		}
		return value;
	}

	public String getValueAsString(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	
	public long getValueAsLong(String key, long defaultValue) {
		long value = defaultValue;
		String v = props.getProperty(key);
		if(v != null) {
			try { value = Long.parseLong(v.trim()); } catch(Exception e) { e.printStackTrace(); }
		}
		return value;
	}
	
	public int getValueAsInt(String key) { return getValueAsInt(key, 0); }
	public boolean getValueAsBool(String key) { return getValueAsBool(key, false); }
	public String getValueAsString(String key) { return props.getProperty(key); }
	public long getValueAsLong(String key) { return getValueAsLong(key, 0L); }

	public Enumeration<?> propertyNames() {
		return props.propertyNames();
	}
	
}
