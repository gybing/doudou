package com.doudoumobile.etonkids_client.model;

import java.util.Date;

public class User {

	private Long id;

	private String userName;

	private String passWd;

	private String email;

	private String realName;

	private Date lastLoginTime;

	private Long teacherTypeId;

	private boolean available;
	
	private String ticket;

	private int role;

	private String curriList;
	
	private School schoolInfo;
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWd() {
		return passWd;
	}

	public void setPassword(String passWd) {
		this.passWd = passWd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getTeacherTypeId() {
		return teacherTypeId;
	}

	public void setTeacherTypeId(Long teacherTypeId) {
		this.teacherTypeId = teacherTypeId;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getCurriList() {
		return curriList;
	}

	public void setCurriList(String curriList) {
		this.curriList = curriList;
	}

	public School getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(School schoolInfo) {
		this.schoolInfo = schoolInfo;
	}
	
	

}