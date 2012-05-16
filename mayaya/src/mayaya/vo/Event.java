package mayaya.vo;

import java.io.Serializable;
import java.util.Date;

import mayaya.util.tool.DateUtil;

@SuppressWarnings("serial")
public class Event implements Serializable {
	
	private int eventID;
	private String content;
	private Date beginTime;
	private Date endTime;
	// School Event userID = 0
	private int userID;
	private String title;
	private String location;
	
	private boolean isSchool;
	private String atChildList;
	private Date publishTime;
	private boolean allday;
	
	// 
	private String userName;
	private String pushType;
	private String beginTimeString;
	private String endTimeString;
	private String publishTimeString;
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getBeginTimeString() {
		return DateUtil.getInstance().getStringFromDate(beginTime);
	}
	
	public String getEndTimeString() {
		return DateUtil.getInstance().getStringFromDate(endTime);
	}
	public String getPublishTimeString() {
		return DateUtil.getInstance().toFullString(publishTime);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public boolean isSchool() {
		return isSchool;
	}
	public void setSchool(boolean isSchool) {
		this.isSchool = isSchool;
	}
	public void setAtChildList(String atChildList) {
		this.atChildList = atChildList;
	}
	public String getAtChildList() {
		return atChildList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPushType() {
		return "EvtTag";
	}
	public boolean isAllday() {
		return allday;
	}
	public void setAllday(boolean allday) {
		this.allday = allday;
	}
}
