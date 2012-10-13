package com.doudoumobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EtonUser")
public class EtonUser implements Serializable {
	
	private static final long serialVersionUID = 8224641622310493478L;
	
	public static final int Admin = 0;
	public static final int RD = 1;
	public static final int ED = 2;
	public static final int Teacher = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "username", nullable = false, length = 64, unique = true)
    private String userName;

    @Column(name = "password", length = 64)
    private String passWd;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "realName", length = 64)
    private String realName;
        
    @Column(name = "teacherTypeId")
    private Long teacherTypeId;

    @Column(name = "available")
    private boolean available;
    
    @Column(name = "role")
    private int role;

    @Column(name = "notes", length = 128)
    private String notes;
    
    @Column(name = "createdBy", length = 64)
    private String createdBy;
    
    @Column(name = "schoolId")
    private int schoolId;
    
    public EtonUser () {
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
    
    
}
