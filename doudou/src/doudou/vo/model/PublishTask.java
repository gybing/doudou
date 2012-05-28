package doudou.vo.model;

import java.util.List;

import doudou.vo.Child;

public abstract class PublishTask {
	
	private TagedInfo tagedInfo;
	private int schoolId;
	
	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public TagedInfo getTagedInfo() {
		return tagedInfo;
	}

	public void setTagedInfo(TagedInfo tagedInfo) {
		this.tagedInfo = tagedInfo;
	}

	public String generateAtChildrenListString() {
		StringBuffer atChildList = new StringBuffer();
		
		for(List<Child> childList : tagedInfo.getClassChildMap().values()) {
			for (Child child : childList) {
				atChildList.append(child.getFirstName()+",");
			}
		}
		if (atChildList.length() > 1) {
			atChildList.deleteCharAt(atChildList.length()-1);
		}
		return atChildList.toString();
	}
	
	public abstract void setChildrenListString(String atChildList);
}
