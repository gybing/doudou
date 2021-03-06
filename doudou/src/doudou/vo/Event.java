package doudou.vo;

import java.io.Serializable;
import java.util.Date;

import doudou.util.tool.DateUtil;
import doudou.vo.type.PublishLevel;

@SuppressWarnings("serial")
public class Event implements Serializable {
	
	private int id;
	private String content;
	private Date beginTime;
	private Date endTime;
	private int userId;
	private String title;
	private String location;
	private String atChildList;
	private Date publishTime;
	private boolean allday;
	private PublishLevel publishLevel;
	private boolean available;
	
	// not related 
	private String userName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAtChildList() {
		return atChildList;
	}

	public void setAtChildList(String atChildList) {
		this.atChildList = atChildList;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public boolean isAllday() {
		return allday;
	}

	public void setAllday(boolean allday) {
		this.allday = allday;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
	
	public String getPushType() {
		return "EvtTag";
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
	public PublishLevel getPublishLevel() {
		return publishLevel;
	}

	public void setPublishLevel(PublishLevel publishLevel) {
		this.publishLevel = publishLevel;
	}
	
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
}
