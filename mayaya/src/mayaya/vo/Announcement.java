package mayaya.vo;

import java.io.Serializable;
import java.util.Date;

import mayaya.util.tool.DateUtil;

@SuppressWarnings("serial")
public class Announcement implements Serializable{
	
	private int announcementId;
	private int userId;
	private String content;
	private String atChildList;
	private Date publishTime;
	
	//
	private String userName;
	
	
	private String pushType;
	private String publishTimeString;
	private String publishTimeS;
	
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getPublishTimeString() {
		return DateUtil.getInstance().toFullString(publishTime);
	}
	public String getPublishTimeS() {
		return DateUtil.getInstance().toAnnounceString(publishTime);
	}
	public String getAtChildList() {
		return atChildList; 
	}
	public void setAtChildList(String atChildList) {
		this.atChildList = atChildList;
	}
	public int getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(int announcementId) {
		this.announcementId = announcementId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPushType() {
		return "Announcement";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
