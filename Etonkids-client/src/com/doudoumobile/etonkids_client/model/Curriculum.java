package com.doudoumobile.etonkids_client.model;

import java.io.Serializable;
import java.util.List;

public class Curriculum implements Serializable{
	
	/**
	 */
	private static final long serialVersionUID = 1244704799154156400L;

	private int id;
	
	private String imgPath;
	
	private String curriculumName;

	private List<Lesson> lessonList;
	
	public enum CurriculumType {
		K1MG(0, "K1-MG"), 
		K1LL(1,"K1-LL"), 
		K1EM(2,"K1-EM"), 
		EEE1(3,"EEE-1"),
		EEE2(4,"EEE-2"),
		EEE3(5,"EEE-3"),
		EEE4(6,"EEE-4"),
		THEMATICN(7,"THEMATIC-N"),
		THEMATICC(8,"THEMATIC-C"),
		CDN(9,"CD-N"),
		CDC(10,"CD-C");
		
		private final int value;
		private final String text;
		
		private CurriculumType(int value, String text) {
			this.value = value;
			this.text = text;
		}
		
		public int toValue() {
			return value;
		}
		
		public String toText() {
			return text;
		}
		
		public static CurriculumType fromText(String str) {
			for(CurriculumType s : CurriculumType.values()) {
				if(str.equalsIgnoreCase(s.text)) 
					return s;
			}
			return null;
		}
		
	}
	
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

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String name) {
		this.curriculumName = name;
	}

	public List<Lesson> getLessonList() {
		return lessonList;
	}

	public void setLessonList(List<Lesson> lessonList) {
		this.lessonList = lessonList;
	}
	
}
