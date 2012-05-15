package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SchoolClass implements Serializable {
	private int id;
	private String schoolClassName;
	private String description;
	private int schoolId;
	private boolean available;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchoolClassName() {
		return schoolClassName;
	}
	public void setSchoolClassName(String schoolClassName) {
		this.schoolClassName = schoolClassName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
}
