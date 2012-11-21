package com.doudoumobile.etonkids_client;

import com.doudoumobile.etonkids_client.model.Curriculum;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class BookgroupActivity extends LinearLayout{

	private Curriculum curriculum;
	
	private ImageView cover;
	private LinearLayout booklist;
	private ImageButton btn_dlall;
	private ImageView segline;
	
	private boolean download = false;
	
	public BookgroupActivity(Context context, AttributeSet attrs ) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.element_bookgroup, this, true);
		
		cover = (ImageView)findViewById(R.id.cover);
		booklist = (LinearLayout)findViewById(R.id.booklist);
		btn_dlall = (ImageButton)findViewById(R.id.btn_dlall);   //在res中定义了两个selector，一个dlall一个celall，点击的时候变换
		segline = (ImageView)findViewById(R.id.segline);
		
		btn_dlall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO change view state
				if (download) {
					btn_dlall.setImageResource(R.drawable.btn_dlall_selector);
					download = false;
				} else {
					btn_dlall.setImageResource(R.drawable.btn_cel_selector);
					download = true;
				}
				processDlAll();
			}
		});
	}
	
	public abstract void processDlAll();
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public void hideSegline()   //第一个加载的group隐藏分割线
	{
		segline.setVisibility(GONE);
	}
	public void changeCover(int id) //换封面,id从0-10
	{
		cover.setImageResource(R.drawable.cover01 + id);
	}
	public void hideBtndl()  //在教材界面中隐藏下载全部btn
	{
		btn_dlall.setVisibility(GONE);
	}
	public void showBtndl()  //在下载界面中显示下载全部btn
	{
		btn_dlall.setVisibility(VISIBLE);
	}

	public void clearLEssonListView() {
		booklist.removeAllViews();
	}
	
	public void addLesson(BookitemActivity lessonItem) {
		LinearLayout.LayoutParams params = new LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		booklist.addView(lessonItem, params);
	}
	
	public BookitemActivity getLessonAt(int index) {
		return (BookitemActivity)booklist.getChildAt(index);
	}
	
	public int getLessonCount() {
		return booklist.getChildCount();
	}
	
	public void remove(BookitemActivity item) {
		booklist.removeView(item);
	}

}
