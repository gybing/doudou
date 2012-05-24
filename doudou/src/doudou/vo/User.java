package doudou.vo;

import java.io.Serializable;
import java.util.Date;

import doudou.vo.type.UserType;


@SuppressWarnings("serial")
public class User implements Serializable {
	
	private int id;
	private String login;
	private String passWd;
	private String firstName;
	private String lastName;
	private String gender;
	private String userType;
	private Date lastLoginTime;
	private boolean available;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassWd() {
		return passWd;
	}
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isTeacher() {
		return getUserType().contains(UserType.Teacher.name());
	}
	
	public String[] getUserTypeArray() {
		String[] userTypeArray = getUserType().split(";");
		return userTypeArray;
	}
	
}