package com.doudoumobile.etonkids_client;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudoumobile.etonkids_client.model.Lesson;

public abstract class BookitemActivity extends LinearLayout{

	private TextView bookname;
	private TextView tx2;  
	private TextView txbtn;
	private ImageButton btn_item;
	private ImageView ico_dl;
	
	private Lesson lesson;
	
	public int totalSize = 0;
	public int curruentSize = 0;
	
	public BookitemActivity(Context context) {
		super(context);
	}

	public BookitemActivity(Context context, AttributeSet attrs , Lesson lesson) {
		super(context, attrs);
		this.lesson = lesson;
		LayoutInflater.from(context).inflate(R.layout.element_bookitem, this, true);
		
		bookname = (TextView)findViewById(R.id.bookname);
		tx2 = (TextView)findViewById(R.id.tx2);
		btn_item = (ImageButton)findViewById(R.id.btn_item);  //有两套selector，item1和item2，教材部分只用item1，下载时未下载为item1，点击下载后换item2
		ico_dl = (ImageView)findViewById(R.id.ico_dl);
		txbtn = (TextView)findViewById(R.id.txbtn);  
		
		btn_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ("Download".equals(txbtn.getText())) {
					editTxbtn("Cancel");
				} else if ("Cancel".equals(txbtn.getText())) {
					editTxbtn("Download");
				} else if ("Read".equals(txbtn.getText())) {
					System.out.println("Read!");
				}
				buttonClick();
			}
		});
	}
	
	public abstract void buttonClick();
	
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	
	public Lesson getLesson() {
		return lesson;
	}
	
	public void editBookname(String str){  //修改书名
		bookname.setText(str);
	}
	public void editTx2(String str){  //tx2在教材界面是过期时间，在下载界面是状态
		tx2.setText(str);
	}
	public void editTxbtn(String str){  //txbtn在教材界面是read，在下载界面是download和cancel
		txbtn.setText(str);
		if ("Download".equals(str)) {
			txbtn.setTextSize(18);
			txbtn.setPadding(10, 0, 0, 0);
		}  else if ("Cancel".equals(str)){
			txbtn.setTextSize(20);
			txbtn.setPadding(15, 0, 0, 0);
		}
		else {
			txbtn.setTextSize(22);
			txbtn.setPadding(25, 8, 0, 0);
		}
		
	}
	public void hideico(){  //在教材界面dlico隐藏
		ico_dl.setVisibility(GONE);
	}
	public void showico(){  //在下载界面在下载时dlico显示
		ico_dl.setVisibility(VISIBLE);
	}
}
