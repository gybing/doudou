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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchoolClass other = (SchoolClass) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
