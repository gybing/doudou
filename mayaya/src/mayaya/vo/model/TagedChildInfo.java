package mayaya.vo.model;

import java.io.Serializable;

import mayaya.vo.enums.UserType;

@SuppressWarnings("serial")
public class TagedChildInfo implements Serializable{
	
	private int childId;
	private String firstName;
	private String lastName;
	private String photoURL;
	private UserType userType;
	
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
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
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}
