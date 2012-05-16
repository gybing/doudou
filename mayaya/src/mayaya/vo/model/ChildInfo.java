package mayaya.vo.model;

import java.io.Serializable;

import mayaya.vo.Child;

@SuppressWarnings("serial")
public class ChildInfo implements Serializable{
	
	private Child child;
	private int pictureCount;
	private int eventCount;
	
	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	
	public int getPictureCount() {
		return pictureCount;
	}
	public void setPictureCount(int pictureCount) {
		this.pictureCount = pictureCount;
	}
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
}
