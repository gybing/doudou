package com.doudoumobile.etonkids_client.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class DoudouJsonHelper {
	private static GsonBuilder gsonb = new GsonBuilder();
	private DateDeserializer ds = new DateDeserializer();
	private Gson gson;
	
	private static DoudouJsonHelper instance = new DoudouJsonHelper();
	
	private DoudouJsonHelper() {
		gsonb.registerTypeAdapter(Date.class, ds);
		gson = gsonb.create();
	}
	
	public static DoudouJsonHelper getInstance() {
		return instance;
	}
	
	public User getUser(String jsonString) {
		if ("".equals(jsonString) || jsonString.equals("null")) {
			return null;
		}
		return gson.fromJson(jsonString, User.class);
		
	}
	
	public <T> T getObject(String jsonString, Class<T> className) {
		if ("".equals(jsonString) || jsonString.equals("null")) {
			return null;
		}
		return gson.fromJson(jsonString, className);
	}
	
	public List<Curriculum> getCurriculumArray(String jsonString) {
		Type type = new TypeToken<List<Curriculum>>(){}.getType();
		return gson.fromJson(jsonString, type);
	}
	
	class DateDeserializer implements JsonDeserializer<Date> {
	    public Date deserialize(JsonElement json, Type typeOfT,
	            JsonDeserializationContext context) throws JsonParseException {
        
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	try {
				return sd.parse(json.getAsString());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
	    }
	}
	
	
	public static void main(String[] args) {
		User user = DoudouJsonHelper.getInstance().getUser("{\"available\":true,\"email\":\"admin@etonkids.com\",\"id\":1,\"lastLoginTime\":{\"date\":16,\"day\":4,\"hours\":23,\"minutes\":41,\"month\":7,\"nanos\":0,\"seconds\":4,\"time\":1345131664000,\"timezoneOffset\":-480,\"year\":112},\"online\":false,\"password\":\"888\",\"realName\":\"我是管理员\",\"role\":0,\"teacherTypeId\":0,\"username\":\"admin@etonkids.com\"}");
		System.out.println(user.getEmail());
	}
}
