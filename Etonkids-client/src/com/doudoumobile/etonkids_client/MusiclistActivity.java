package com.doudoumobile.etonkids_client;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MusiclistActivity extends LinearLayout{

	private ImageView bg_selected, listico;
	private TextView mname, mlength;
	public int index = -1;
	public int type = 0;
	
	private String musicPath = "";
	private String duration = "";
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	
	public String getMusicPath() {
		return musicPath;
	}
	public MusiclistActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MusiclistActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.element_mlist, this, true);
		bg_selected = (ImageView)findViewById(R.id.bg_selected);
		listico = (ImageView)findViewById(R.id.listico);
		mname = (TextView)findViewById(R.id.mname);
		mlength = (TextView)findViewById(R.id.mlength);
		
		
	}
	
	public void showSelected() { //选中一个item时bg_selected显示，其它隐藏;文字变白色
		bg_selected.setVisibility(VISIBLE);
		mname.setTextColor(Color.parseColor("ffffff"));
		mlength.setTextColor(Color.parseColor("ffffff"));
	}
	public void hideBg() {
		bg_selected.setVisibility(GONE);
		mname.setTextColor(Color.parseColor("514851"));
		mlength.setTextColor(Color.parseColor("514851"));
	}
	public void editName(String str) {
		mname.setText(str);
	}
	public void editLength(String str) {
		if (!"".equals(str)) {
			str += "/" + duration;
		}
		mlength.setText(str);
	}
	public void setListico(int id) {  //有4个标志，play和pause是选中播放时的状态，music和movie是没选中播放时的状态
		
	}

}
