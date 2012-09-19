package com.doudoumobile.etonkids_client;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Curriculum.CurriculumType;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;

public class LessontabActivity extends Activity{

	ImageButton refreshBtn;
	LinearLayout curriculumList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablesson);
        
        init();
    }
	
	private void init() {
		refreshBtn = (ImageButton)findViewById(R.id.btn_refresh1);
		refreshBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				loadLessonInfo();
			}
		});
		
		curriculumList = (LinearLayout)findViewById(R.id.curriculumList);
		
		loadLessonInfo();
	}
	
	private void loadLessonInfo() {
    	ArrayList<Curriculum> curriList = MyDbConnector.getMyDbConnector(this).getDownloadedCurriculums();
    	showDownloadedCurriList(curriList);
    }
	
	private void showDownloadedCurriList(ArrayList<Curriculum> curriList) {
		//remove first
		curriculumList.removeAllViews();
		int index = 0;
		for (Curriculum curriculum : curriList) {
			BookgroupActivity curriculumAc = new BookgroupActivity(this, null) {
				@Override
				public void processDlAll() {
					// TODO Auto-generated method stub
				}
				
			};
			if (index == 0) {
				curriculumAc.hideSegline();
				index++;
			}
			CurriculumType type = CurriculumType.fromText(curriculum.getImgPath());
			if (null != type) {
				curriculumAc.changeCover(type.toValue());
			} else {
				//TODO unknown cover
				curriculumAc.changeCover(0);
			}
			curriculumAc.hideBtndl();
			curriculumAc.setCurriculum(curriculum);
			
			for (Lesson lesson : curriculum.getLessonList()) {
				BookitemActivity lessonItem = new BookitemActivity(this, null, lesson) {
					@Override
					public void buttonClick() {
						startRead(this);
					}
					
				};
				lessonItem.editBookname(lesson.getTitle());
				lessonItem.editTx2(lesson.getEndDate());
				lessonItem.editTxbtn("Read");
				lessonItem.hideico();
				lessonItem.setLesson(lesson);
				
				curriculumAc.addLesson(lessonItem);
			}
			
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			curriculumList.addView(curriculumAc, params);
		}
		
	}
	
	private void startRead(BookitemActivity v) {
		Lesson lesson = v.getLesson();
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,LessonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        intent.putExtras(bundle);
        
        startActivity(intent);
	}
    @Override
    public void onBackPressed() {
    	System.out.println("back pressed");
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    	
    	finish();
    }
}
