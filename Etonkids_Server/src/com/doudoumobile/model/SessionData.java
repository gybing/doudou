package com.doudoumobile.model;

import java.io.Serializable;

public class SessionData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 458599481360424324L;
	
	private EtonUser etonUser;
	
	public SessionData() {
		
	}

	public EtonUser getEtonUser() {
		return etonUser;
	}

	public void setEtonUser(EtonUser etonUser) {
		this.etonUser = etonUser;
	}
	
	
}
