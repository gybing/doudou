package mayaya.vo.model;

import java.io.Serializable;
import java.util.List;

import mayaya.vo.Child;
import mayaya.vo.enums.UserType;

@SuppressWarnings("serial")
public class UserChildrenInfo implements Serializable{
	
	private List<Child> childrenOfTeacher;
	private List<Child> childrenIfParents;
	private UserType userType;
	
	public List<Child> getChildrenOfTeacher() {
		return childrenOfTeacher;
	}
	public void setChildrenOfTeacher(List<Child> childrenOfTeacher) {
		this.childrenOfTeacher = childrenOfTeacher;
	}
	public List<Child> getChildrenIfParents() {
		return childrenIfParents;
	}
	public void setChildrenIfParents(List<Child> childrenIfParents) {
		this.childrenIfParents = childrenIfParents;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	} 
}
