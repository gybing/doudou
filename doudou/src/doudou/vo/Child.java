package doudou.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Child implements Serializable {
	
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private Date birthDate;
	private String description;
	private String photoURL;
	private String cover;
	
	// Not related data in db
	private String birthDateString;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getBirthDateString() {
		return birthDateString;
	}
	public void setBirthDateString(String birthDateString) {
		this.birthDateString = birthDateString;
	}
	
}
