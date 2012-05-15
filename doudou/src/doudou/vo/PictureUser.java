package doudou.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PictureUser implements Serializable {
	
	private int id;
	private int pictureId;
	private int toChildId;
	
	public int getToChildId() {
		return toChildId;
	}
	public void setToChildId(int toChildId) {
		this.toChildId = toChildId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	
	
}
