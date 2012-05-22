package doudou.vo;

import java.io.Serializable;
import java.util.Date;

import doudou.util.tool.DateUtil;
import doudou.vo.type.PublishLevel;

@SuppressWarnings("serial")
public class Picture implements Serializable {
	
	private int id;
	private Date publishTime;
	// 发布者Id
	private int userId;
	private String pictureURL;
	private String description;
	private String atChildList;
	private PublishLevel publishLevel;
	// not related column in db
	private String userName;
	private String pushType;
	private String publishTimeString;
	private String pictureSmallURL;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public String getPictureSmallURL() {
		StringBuffer result = new StringBuffer(); 
		String[] strs = pictureURL.split("[.]");
		for (int i = 0 ; i < strs.length - 1 ; i++) {
			result.append(strs[i] + ".");
		}
		result.deleteCharAt(result.length()-1);
		result.append("_small.");
		result.append(strs[strs.length-1]);
		return result.toString();
	}
	
	public String getPublishTimeString() {
		return DateUtil.getInstance().toFullString(publishTime);
	}
	public String getAtChildList() {
		return atChildList;
	}
	public void setAtChildList(String atChildList) {
		this.atChildList = atChildList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPushType() {
		return "PicTag";
	}
	
}
