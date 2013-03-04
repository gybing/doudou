package com.doudoumobile.model;

import java.io.Serializable;

public class SessionData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 458599481360424324L;
	
	private SCSCCUser etonUser;
	
	public SessionData() {
		
	}

	public SCSCCUser getEtonUser() {
		return etonUser;
	}

	public void setEtonUser(SCSCCUser etonUser) {
		this.etonUser = etonUser;
	}
	
	
}
