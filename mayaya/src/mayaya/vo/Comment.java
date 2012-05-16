package mayaya.vo;

import java.io.Serializable;
import java.util.Date;

import mayaya.util.tool.DateUtil;

@SuppressWarnings("serial")
public class Comment implements Serializable {
	
	private int commentID;
	private String content;
	private Date publishTime;
	private int userID;
	private int pictureID;
	//
	private String userName;
	private String pushType;
	private String publishTimeString;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getPictureID() {
		return pictureID;
	}
	public void setPictureID(int pictureID) {
		this.pictureID = pictureID;
	}
	public String getPushType() {
		return "Comment";
	}
	public String getPublishTimeString() {
		return DateUtil.getInstance().toFullString(publishTime);
	}
}
