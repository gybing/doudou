package doudou.vo.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import doudou.vo.SchoolClass;
import doudou.vo.User;
import doudou.vo.type.PublishLevel;
import doudou.vo.type.UserType;

@SuppressWarnings("serial")
public class SessionData implements Serializable {

	private User user;
	private Map<String,TagedInfo> tagedInfoMap;
	private int schoolId;
	private PublishLevel currentPublishLevel;
	
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public void addTagedInfo(String teacherType, TagedInfo tagedInfo){
		tagedInfoMap.put(teacherType, tagedInfo);
	}
	public Map<String,TagedInfo> getTagedInfoMap() {
		return tagedInfoMap;
	}
	public void setTagedInfoMap(Map<String,TagedInfo> tagedInfoMap) {
		this.tagedInfoMap = tagedInfoMap;
	}
	public Set<SchoolClass> getSchoolClassList() {
		Set<SchoolClass> classSet = new HashSet<SchoolClass>();
		for (TagedInfo tagedInfo : tagedInfoMap.values()) {
			classSet.addAll(tagedInfo.getClassChildMap().keySet());
		}
		return classSet;
	}
	public PublishLevel getCurrentPublishLevel() {
		return currentPublishLevel;
	}
	public void setCurrentPublishLevel(PublishLevel currentPublishLevel) {
		this.currentPublishLevel = currentPublishLevel;
	}
	
	
}
