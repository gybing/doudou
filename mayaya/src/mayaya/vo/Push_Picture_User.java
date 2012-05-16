package mayaya.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Push_Picture_User implements Serializable {
	
	private int push_Pic_UserID;
	private int pictureID;
	private int toChildId;
	private Date publishTime;
	private boolean self;
	
	public int getPush_Pic_UserID() {
		return push_Pic_UserID;
	}
	public void setPush_Pic_UserID(int push_Pic_UserID) {
		this.push_Pic_UserID = push_Pic_UserID;
	}
	public int getPictureID() {
		return pictureID;
	}
	public void setPictureID(int pictureID) {
		this.pictureID = pictureID;
	}
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public boolean isSelf() {
		return self;
	}
	public void setSelf(boolean self) {
		this.self = self;
	}
	
}
