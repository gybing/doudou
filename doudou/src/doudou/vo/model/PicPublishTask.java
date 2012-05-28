package doudou.vo.model;

import doudou.vo.Picture;

public class PicPublishTask extends PublishTask {
	
	private Picture picture;
	
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
	@Override
	public void setChildrenListString(String atChildList) {
		picture.setAtChildList(atChildList);
	}
	
}
