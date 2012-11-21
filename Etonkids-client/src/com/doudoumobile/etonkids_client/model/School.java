package com.doudoumobile.etonkids_client.model;

import java.io.Serializable;

public class School implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5785292126210070248L;

	private Long id;
	
    private String address;
	
	private Long typeId;
	
	private String schoolType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	
	
}
