package doudou.vo;

import java.io.Serializable;
import java.util.Date;

import doudou.util.tool.DateUtil;

@SuppressWarnings("serial")
public class PictureComment implements Serializable {
	
	private int id;
	private String content;
	private Date publishTime;
	private int userId;
	private int pictureId;
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	public String getPushType() {
		return "Comment";
	}
	public String getPublishTimeString() {
		return DateUtil.getInstance().toFullString(publishTime);
	}
}
