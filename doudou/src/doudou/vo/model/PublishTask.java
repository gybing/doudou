package doudou.vo.model;

import java.util.List;

import doudou.vo.Child;

public abstract class PublishTask {
	private List<Child> childrenList;

	public List<Child> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Child> childrenList) {
		this.childrenList = childrenList;
	}
	
	public String generateAtChildrenListString() {
		StringBuffer atChildList = new StringBuffer();
		
		for(Child child : childrenList) {
			atChildList.append(child.getFirstName()+",");
		}
		if (atChildList.length() > 1) {
			atChildList.deleteCharAt(atChildList.length()-1);
		}
		return atChildList.toString();
	}
	
	public abstract void setChildrenListString(String atChildList);
}
