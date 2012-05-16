package mayaya.vo;

import java.io.Serializable;

import mayaya.vo.enums.UserType;

@SuppressWarnings("serial")
public class Relations_Child_User implements Serializable {
	
	private int relationID;
	private int childID;
	private int userID;
	private UserType userType;
	
	public int getRelationID() {
		return relationID;
	}
	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}
	public int getChildID() {
		return childID;
	}
	public void setChildID(int childID) {
		this.childID = childID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public UserType getUserType() {
		return userType;
	}
	
}
