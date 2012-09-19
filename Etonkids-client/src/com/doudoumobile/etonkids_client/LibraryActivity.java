package com.doudoumobile.etonkids_client;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;

import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LibraryActivity extends Activity{

	private static String TAG = "LIBRARY_ACTIVITY";
	private String sdCard = Constants.SD_PATH;
	private Lesson lesson;
	ImageButton backBtn;
	private GridView picgrid;  //grid里面插入自定义控件picframe，插入之前要修改picframe中的图片资源。这个需要代码调试。你先试着先插入，有什么样式上的问题我再解决。
	private MediaplayerActivity mediaPlayer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        
        picgrid = (GridView)findViewById(R.id.picgrid);
        
        Bundle b=getIntent().getExtras();
        lesson = (Lesson)b.getSerializable("lesson");
        
        initButton();
        initMaterial();
    }
	
	private void initButton() {
		backBtn = (ImageButton)findViewById(R.id.imageButton1);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LibraryActivity.this, LessonActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("lesson", lesson);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		mediaPlayer = (MediaplayerActivity)findViewById(R.id.mediaplyerActivity1);
		
	}
	
	private void initMaterial() {
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
		ImageView imageView = new ImageView(this);
		Log.d(TAG, "Material path : " + path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		Log.i(TAG, "图片 高度:" + options.outHeight + " width: " + options.outWidth);
		int width = options.outWidth * 145 / options.outHeight;
		System.out.println("width : " + width);
		options.outHeight = 145;
		options.outWidth = width;
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,145);
		params.rightMargin = 10;
		imageView.setLayoutParams(params);
		imageView.setImageBitmap(bm);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				LayoutInflater inflater = LayoutInflater.from(arg0.getContext());
				View imgEntryView = inflater.inflate(R.layout.dialog_picture, null); // 加载自定义的布局文件
				final AlertDialog dialog = new AlertDialog.Builder(arg0.getContext()).create();
				ImageView imageV = (ImageView)imgEntryView.findViewById(R.id.large_image);
				imageV.setImageDrawable(((ImageView)arg0).getDrawable());
				dialog.setView(imgEntryView); // 自定义dialog
				dialog.show();
				imgEntryView.setOnClickListener(new OnClickListener() {
					public void onClick(View paramView) {
						dialog.cancel();
					}
				});
			}
		});
		//picgrid.addView(imageView);
	
	}
}
