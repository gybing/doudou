package doudou.vo.model;

import java.io.Serializable;
import java.util.List;

import doudou.vo.Picture;
import doudou.vo.User;

@SuppressWarnings("serial")
public class PicPublishTask implements Serializable {
	
	private Picture picture;
	private List<Integer> childrenList;
	
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	public List<Integer> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<Integer> childrenList) {
		this.childrenList = childrenList;
	}
	
}
