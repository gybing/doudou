package com.doudoumobile.etonkids_client.model;

import java.util.List;

public class Lesson {
	
	private int id;
	
	private String title;
	
	private int curriculumId;
	
	private String expireTime;
	
	private String pdfPath;
	
	private boolean available;
	
	private List<Material> materialLesson;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(int curriculumId) {
		this.curriculumId = curriculumId;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<Material> getMaterialLesson() {
		return materialLesson;
	}

	public void setMaterialLesson(List<Material> materialLesson) {
		this.materialLesson = materialLesson;
	}
	
	
	
}
