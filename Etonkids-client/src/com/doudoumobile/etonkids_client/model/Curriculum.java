package com.doudoumobile.etonkids_client.model;

import java.util.List;

public class Curriculum {
	
	private int id;
	
	private String imgPath;
	
	private String name;

	private List<Lesson> lessonList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Lesson> getLessonList() {
		return lessonList;
	}

	public void setLessonList(List<Lesson> lessonList) {
		this.lessonList = lessonList;
	}
	
	
}
