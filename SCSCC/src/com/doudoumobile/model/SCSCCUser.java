package com.doudoumobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scsccuser")
public class SCSCCUser implements Serializable {
	
	private static final long serialVersionUID = 8224641622310493478L;
	
	public static final int EMember = 0;
	public static final int VMember = 1;
	public static final int GMemeber= 2;
	public static final int Staff = 3;
	public static final int Secretary = 4;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "available")
    private boolean available;
    
    
    @Column(name = "username", nullable = false, length = 64, unique = true)
    private String userName;

    @Column(name = "password", length = 64)
    private String passWd;

    @Column(name = "realname", length = 64)
    private String realName;

    @Column(name = "photo", length = 64)
    private String photo;
    
    @Column(name = "company", length = 128)
    private String company;
    
    @Column(name = "position", length = 128)
    private String position;
    
    @Column(name = "gender")
    private int gender;
    
    @Column(name = "usertype")
    private int userType;
    
    public SCSCCUser () {
    	available = true;
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

	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String getGenderName() {
		switch(gender) {
		case 0:
			return "女";
		case 1:
			return "男";
		default:
			return "";
		}
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public String getUserTypeName() {
		switch(userType) {
		case 0:
			return "EMember";
		case 1:
			return "VMember";
		case 2:
			return "GMember";
		case 3:
			return "Staff";
		default:
			return "";
		}
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}


}
