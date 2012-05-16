package mayaya.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Relations_Event_User implements Serializable {
	
	private int relationID;
	private int eventID;
	private int toChildId;
	private boolean self;
	
	public int getRelationID() {
		return relationID;
	}
	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}
	public boolean isSelf() {
		return self;
	}
	public void setSelf(boolean self) {
		this.self = self;
	}
	
}
