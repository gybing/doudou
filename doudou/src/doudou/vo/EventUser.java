package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventUser implements Serializable {
	
	private int id;
	private int eventId;
	private int toChildId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}

}
