package com.doudoumobile.util;

import java.text.SimpleDateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonHelper {
	
	private JsonConfig jsonConfig = new JsonConfig();
	
	private static JsonHelper instance = new JsonHelper();
	
	private JsonHelper() {
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new SqlDateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new DateJsonValueProcessor());
	}
	
	public JSONObject getJson(Object obj) {
		return JSONObject.fromObject(obj,jsonConfig);
	}
	
	public JSONArray getJsonArray(Object obj) {
		return JSONArray.fromObject(obj,jsonConfig);
	}
	
	public static JsonHelper getInstance() {
		return instance;
	}
	
	 
	class DateJsonValueProcessor implements JsonValueProcessor {
	    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	    	return null;
	    }

		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			// TODO Auto-generated method stub
			return process(value);
		}

		private Object process(Object obj) {
			if (obj == null) {
				return "";
			} else {
				return sd.format(obj);
			}
		}
	}
	class SqlDateJsonValueProcessor implements JsonValueProcessor {
	    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	    	return null;
	    }

		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			// TODO Auto-generated method stub
			return process(value);
		}

		private Object process(Object obj) {
			if (obj == null) {
				return "";
			} else {
				return sd.format(obj);
			}
		}
	}
}
