package mayaya.vo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mayaya.vo.Picture;

@SuppressWarnings("serial")
public class PhotoWallVO implements Serializable,Comparable {
	
	private String date;
	private List<Picture> picList;
	
	public PhotoWallVO() {
		date = "";
		picList = new ArrayList<Picture>();
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Picture> getPicList() {
		return picList;
	}
	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}

	@Override
	public int compareTo(Object arg0) {
        String date2 = ((PhotoWallVO)arg0).getDate();
        
        return date2.compareTo(date);
	}
}
