package mayaya.vo.model;

import java.io.Serializable;
import java.util.List;

import mayaya.vo.Event;
import mayaya.vo.Picture;

@SuppressWarnings("serial")
public class SimpleMainPageVO implements Serializable{
	
	private int picCount;
	private int eventCount;
	private List<Picture> picList;
	private List<Event> eventList;
	
	public int getPicCount() {
		return picCount;
	}
	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
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
	
}
