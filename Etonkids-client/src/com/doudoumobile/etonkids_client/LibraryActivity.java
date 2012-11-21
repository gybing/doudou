package com.doudoumobile.etonkids_client;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;
import com.doudoumobile.etonkids_client.view.myImageView;

public class LibraryActivity extends Activity implements OnGestureListener {

	private static String TAG = "LIBRARY_ACTIVITY";
	private String sdCard = Constants.SD_PATH;
	private Lesson lesson;
	ImageButton backBtn;
	private LinearLayout picview;  
	private LinearLayout mainLayout;
	private MediaplayerActivity mediaPlayer;
	private static int PIC_HEIGHT = 250;
	private ViewFlipper viewFlipper;
	private GestureDetector detector; 
	private boolean viewFipperShowing = false;
	private int imageIndex = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        
        picview = (LinearLayout)findViewById(R.id.picView2);
        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
        Bundle b=getIntent().getExtras();
        lesson = (Lesson)b.getSerializable("lesson");
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        viewFlipper.setVisibility(View.INVISIBLE);
        detector = new GestureDetector(this); 

        initButton();
        initMaterial();
    }
	
	@Override 
	public boolean onTouchEvent(MotionEvent event) { 
		return detector.onTouchEvent(event); 
	} 

	
	private void initButton() {
		backBtn = (ImageButton)findViewById(R.id.imageButton1);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				backToLesson();
			}
		});
		mediaPlayer = (MediaplayerActivity)findViewById(R.id.mediaplyerActivity1);
		
	}
	
	private void backToLesson() {
		mediaPlayer.stop();
		recycle();
		Intent intent = new Intent(LibraryActivity.this, LessonActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("lesson", lesson);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	private void initMaterial() {
		imageIndex = 0;
		List<Material> resourceList = lesson.getMaterialList();
		ArrayList<Material> musicList = new ArrayList<Material>();
		for (Material material : resourceList) { 
			//picture
			if (material.getType() == 2) {
				insertPic(material);
			} else if (material.getType() == 0 || material.getType() == 1){ //mp3
				musicList.add(material);
				//initMediaPlayer(material);
			} else if (material.getType() == 3) {//pdf
			}
		}
		initMediaPlayer(musicList);
	}
	
	public void initMediaPlayer(List<Material> materialList) {
		mediaPlayer.insertMlist(materialList);
	}
	
	public void insertPic(Material material) {  
		String path = sdCard + material.getPath();
		myImageView imageView = new myImageView(this , null);
		Log.d(TAG, "Material path : " + path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		Log.i(TAG, "图片 高度:" + options.outHeight + " width: " + options.outWidth);
		int width = options.outWidth * PIC_HEIGHT / options.outHeight;
		System.out.println("width : " + width);
		options.outHeight = PIC_HEIGHT;
		options.outWidth = width;
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,PIC_HEIGHT);
		params.rightMargin = 20;
		imageView.setLayoutParams(params);
		imageView.setImageBitmap(bm);
		imageView.id = imageIndex++;
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				viewFlipper.setVisibility(View.VISIBLE);
				int i = ((myImageView)arg0).id;
				viewFlipper.setDisplayedChild(i);
			    mainLayout.setVisibility(View.INVISIBLE);
			    viewFipperShowing = true;
			}
		});
		ImageView imageV = new ImageView(this);
		picview.addView(imageView);
		imageV.setImageBitmap(bm);
		LinearLayout parent = new LinearLayout(this);
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		parent.setLayoutParams(param);
		parent.setGravity(Gravity.CENTER);
		parent.addView(imageV);
		viewFlipper.addView(parent);
	}
	private void recycle() {
		System.out.println("Recycle resources");
		if (picview != null) {
			picview.removeAllViews();
			picview = null;
		}
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		} 
		if (viewFlipper != null) {
			viewFlipper.removeAllViews();
			viewFlipper = null;
		}
        System.gc();
	}
	@Override
    protected void onDestroy() {
		recycle();
		System.out.println("LibraryActivity destroy");
        super.onDestroy();
    }

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		if (e1.getX() - e2.getX() > 120) { 
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in)); 
		    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out)); 
		    viewFlipper.showNext(); 
		    return true; 
		} else if (e1.getX() - e2.getX() < -120) { 
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in)); 
			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out)); 
			viewFlipper.showPrevious(); 
			return true; 
		} 
		return false; 
	}


	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		System.out.println("onScroll");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		mainLayout.setVisibility(View.VISIBLE);
		viewFlipper.setVisibility(View.INVISIBLE);
		viewFipperShowing = false;
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
	}
	
	@Override
	public void onBackPressed() {
		if (viewFipperShowing) {
			mainLayout.setVisibility(View.VISIBLE);
			viewFlipper.setVisibility(View.INVISIBLE);
			viewFipperShowing = false;
		} else {
			backToLesson();
		}
	}
	
}
