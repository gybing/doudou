package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SchoolCity implements Serializable {
	private int id;
	private String cityName;
	private int parentId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}
