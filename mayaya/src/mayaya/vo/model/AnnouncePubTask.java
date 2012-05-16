package mayaya.vo.model;

import java.util.List;

import mayaya.vo.Announcement;

public class AnnouncePubTask {
	private Announcement announcement;
	private List<Integer> childrenList;
	
	public Announcement getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
	public List<Integer> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<Integer> childrenList) {
		this.childrenList = childrenList;
	}

}
