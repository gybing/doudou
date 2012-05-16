package mayaya.vo.model;

import java.io.Serializable;
import java.util.List;

import mayaya.vo.Child;
import mayaya.vo.Event;
import mayaya.vo.Picture;

@SuppressWarnings("serial")
public class MainPageVO implements Serializable{
	
	private Child childInfo;
	private String mother;
	private String father;
	private List<Picture> picList;
	private List<Event> eventList;

	public Child getChildInfo() {
		return childInfo;
	}
	public void setChildInfo(Child childInfo) {
		this.childInfo = childInfo;
	}
	public List<Picture> getPicList() {
		return picList;
	}
	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	public String getMother() {
		return mother;
	}
	public void setMother(String mother) {
		this.mother = mother;
	}
	public String getFather() {
		return father;
	}
	public void setFather(String father) {
		this.father = father;
	}
}
