package com.doudoumobile.etonkids_client;

import java.util.List;

import com.doudoumobile.etonkids_client.model.Material;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MediaplayerActivity extends LinearLayout{

	private ImageButton btn_play, btn_pre, btn_next;
	private LinearLayout mlist;
	
	public MediaplayerActivity(Context context) {
		super(context);
	}
	
	public MediaplayerActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.element_mplayer, this, true);
		
		btn_play = (ImageButton)findViewWithTag(R.id.btn_play);
		btn_pre = (ImageButton)findViewWithTag(R.id.btn_pre);
		btn_next = (ImageButton)findViewWithTag(R.id.btn_next);
		mlist = (LinearLayout)findViewById(R.id.mlist);
	}
	
	public void insertMlist(List<Material> materialList) {  //插入多媒体列表
		
	}
}
