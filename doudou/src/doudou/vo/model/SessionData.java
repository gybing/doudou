package doudou.vo.model;


import java.io.Serializable;

import doudou.vo.User;

@SuppressWarnings("serial")
public class SessionData implements Serializable {

	private User user;
	//private List<TagedChildInfo> childrenToTag;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	public List<TagedChildInfo> getChildrenToTag() {
//		return childrenToTag;
//	}
//	public void setChildrenToTag(List<TagedChildInfo> childrenToTag) {
//		this.childrenToTag = childrenToTag;
//	}
}
