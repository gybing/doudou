package com.doudoumobile.etonkids_client;

import java.util.zip.Inflater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class PicframeActivity extends LinearLayout{

	public PicframeActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.element_picframe, this, true);
	}

}
