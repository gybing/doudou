package com.doudoumobile.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Curriculum")
public class Curriculum implements Serializable {
	
	private static final long serialVersionUID = -2917094260283416305L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "curriculumName", nullable = false, length = 64)
    private String curriculumName;

    @Column(name = "parentCurriculumId")
    private Long parentCurriculumId;

    @Column(name = "createdTime", updatable = false)
    private Date createdTime = new Date();

    @Column(name = "imgPath", length = 128)
    private String imgPath;
    
    @Column(name = "parentCurriName", length = 64)
    private String parentCurriName;
    
    @Transient
    private List<Lesson> lessonList;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public Long getParentCurriculumId() {
		return parentCurriculumId;
	}

	public void setParentCurriculumId(Long parentCurriculumId) {
		this.parentCurriculumId = parentCurriculumId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((curriculumName == null) ? 0 : curriculumName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curriculum other = (Curriculum) obj;
		if (curriculumName == null) {
			if (other.curriculumName != null)
				return false;
		} else if (!curriculumName.equals(other.curriculumName))
			return false;
		return true;
	}
	
	public void setLessonList(List<Lesson> lessonList) {
		if (null == lessonList) {
			this.lessonList = new ArrayList<Lesson>();
		}
		this.lessonList = lessonList;
	}
	
	public List<Lesson> getLessonList() {
		return lessonList;
	}

	public String getParentCurriName() {
		return parentCurriName;
	}

	public void setParentCurriName(String parentCurriName) {
		this.parentCurriName = parentCurriName;
	}
	
}
