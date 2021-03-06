package doudou.vo;

import java.io.Serializable;
import java.util.Date;

import doudou.util.tool.DateUtil;
import doudou.vo.type.PublishLevel;

@SuppressWarnings("serial")
public class Message implements Serializable{
	
	private int id;
	private String title;
	private String content;
	private int messageTypeId;
	private int userId;
	private String atChildList;
	private Date publishTime;
	private PublishLevel publishLevel;
	private boolean mustFeedBack;
	private boolean available;
	
	private DoudouInfoType messageType;
	//
	private String userName;
	private int feedBackCount;
	private int notFeedBackCount;
	
	private int readedCount;
	private int notReadedCount;
	
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(int messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public PublishLevel getPublishLevel() {
		return publishLevel;
	}
	public void setPublishLevel(PublishLevel publishLevel) {
		this.publishLevel = publishLevel;
	}
	public boolean isMustFeedBack() {
		return mustFeedBack;
	}
	public void setMustFeedBack(boolean mustFeedBack) {
		this.mustFeedBack = mustFeedBack;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getPushType() {
		return "Message";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public DoudouInfoType getMessageType() {
		return messageType;
	}
	public void setMessageType(DoudouInfoType messageType) {
		this.messageType = messageType;
	}
	public int getFeedBackCount() {
		return feedBackCount;
	}
	public void setFeedBackCount(int feedBackCount) {
		this.feedBackCount = feedBackCount;
	}
	public int getNotFeedBackCount() {
		return notFeedBackCount;
	}
	public void setNotFeedBackCount(int notFeedBackCount) {
		this.notFeedBackCount = notFeedBackCount;
	}
	public int getReadedCount() {
		return readedCount;
	}
	public void setReadedCount(int readedCount) {
		this.readedCount = readedCount;
	}
	public int getNotReadedCount() {
		return notReadedCount;
	}
	public void setNotReadedCount(int notReadedCount) {
		this.notReadedCount = notReadedCount;
	}
	
}
