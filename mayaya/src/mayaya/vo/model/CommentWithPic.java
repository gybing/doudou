package mayaya.vo.model;

import java.io.Serializable;

import mayaya.vo.Comment;
import mayaya.vo.Picture;

@SuppressWarnings("serial")
public class CommentWithPic implements Serializable{
	private Comment comment;
	private Picture picture;

	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}
