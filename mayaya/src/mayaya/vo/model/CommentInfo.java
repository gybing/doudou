package mayaya.vo.model;

import java.io.Serializable;

import mayaya.vo.Comment;

@SuppressWarnings("serial")
public class CommentInfo implements Serializable{
	
	private Comment comment;
	private String userPicUrl;
	private String userName;//用户的firstName

	public CommentInfo(Comment comment){
		this.comment = comment;
	}
	
	public CommentInfo() {
		
	}
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
