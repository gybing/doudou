package com.doudoumobile.etonkids_client.model;

import java.io.Serializable;

public class Material implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8057685236028257626L;

	private int id;
	
	private String path;
	
	private int type;
	
	private int lessonId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	
	
}
