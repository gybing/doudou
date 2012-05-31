package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MessageUser implements Serializable {
	
	private int id;
	private int messageId;
	private int toChildId;
	private boolean readStatus;
	private String readerName;
	private String feedbackContent; 
	private String feedbackUser;
	private boolean feedbackStatus;
	
	// not related in db
	private String childName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}
	public boolean isReadStatus() {
		return readStatus;
	}
	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getFeedbackUser() {
		return feedbackUser;
	}
	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}
	public boolean isFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(boolean feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	
}
