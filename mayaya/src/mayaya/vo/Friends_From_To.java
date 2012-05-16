package mayaya.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Friends_From_To implements Serializable {
	
	private int friendsID;
	private int fromUserID;
	private int toChildID;
	private boolean confirmed;
	
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public int getFriendsID() {
		return friendsID;
	}
	public void setFriendsID(int friendsID) {
		this.friendsID = friendsID;
	}
	public int getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(int fromUserID) {
		this.fromUserID = fromUserID;
	}
	public int getToChildID() {
		return toChildID;
	}
	public void setToChildID(int toChildID) {
		this.toChildID = toChildID;
	}
	
}
