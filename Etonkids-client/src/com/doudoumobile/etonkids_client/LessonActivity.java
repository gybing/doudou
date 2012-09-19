package com.doudoumobile.etonkids_client;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.client.Constants;
import org.miscwidgets.interpolator.BackInterpolator;
import org.miscwidgets.interpolator.EasingType.Type;
import org.vudroid.core.DecodeService;
import org.vudroid.core.DecodeServiceBase;
import org.vudroid.core.DocumentView;
import org.vudroid.core.models.CurrentPageModel;
import org.vudroid.core.models.DecodingProgressModel;
import org.vudroid.core.models.ZoomModel;
import org.vudroid.pdfdroid.codec.PdfContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.doudoumobile.etonkids_client.Panel.OnPanelListener;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;

public class LessonActivity extends Activity implements OnPanelListener{

	private static String TAG = "LESSON_ACTIVITY";
	
	private LinearLayout picview;
	private LinearLayout lessonLayout;
	
	private String sdCard = Constants.SD_PATH;
	
	private DecodeService decodeService;
	private DocumentView documentView;
	private CurrentPageModel currentPageModel;

	ImageButton backBtn;
	ImageButton materialListBtn;
	private Panel resourcePanel;
	private MediaplayerSActivity smallMediaPlayer;
	
	private Lesson lesson;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        picview = (LinearLayout)findViewById(R.id.picview);
        lessonLayout = (LinearLayout)findViewById(R.id.lessonLayout);
        smallMediaPlayer = (MediaplayerSActivity)findViewById(R.id.mediaplayerSActivity1);
        
        Bundle b=getIntent().getExtras();
        lesson = (Lesson)b.getSerializable("lesson");
        
        initButton();
        initPdfService();
        initMaterial();
    }
	
	private void initButton() {
		backBtn = (ImageButton)findViewById(R.id.imageButton1);
        materialListBtn = (ImageButton)findViewById(R.id.imageButton2);
        
        resourcePanel = (Panel)findViewById(R.id.bottomPanel);
        resourcePanel.setOnPanelListener(this);
        resourcePanel.setInterpolator(new BackInterpolator(Type.OUT, 2));
        
        backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LessonActivity.this, IndexActivity.class);
				startActivity(intent);
			}
		});
        
        materialListBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LessonActivity.this, LibraryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("lesson", lesson);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	private void initPdfService() {
		initDecodeService();
		final ZoomModel zoomModel = new ZoomModel();
		final DecodingProgressModel progressModel = new DecodingProgressModel();
		progressModel.addEventListener(this);
		currentPageModel = new CurrentPageModel();
		currentPageModel.addEventListener(this);
		documentView = new DocumentView(this, zoomModel, progressModel,
				currentPageModel);
		zoomModel.addEventListener(documentView);
		documentView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		decodeService.setContentResolver(getContentResolver());
		decodeService.setContainerView(documentView);
		documentView.setDecodeService(decodeService);
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
				showPdf(material.getPath());
			}
		}
		initMediaPlayer(musicList);
	}
	
	private void showPdf(String path) {
		String lessonPath = "file://" + sdCard + path;
		Log.i(TAG, "lesson pdf path : " + lessonPath);
		Uri u = Uri.parse(lessonPath);
		decodeService.open(u);

		lessonLayout.addView(documentView);

		documentView.goToPage(0);
		documentView.showDocument();
	}
	
	public void initMediaPlayer(List<Material> materialList) {
		smallMediaPlayer.insertMlist(materialList);
	}
	
	public void insertPic(Material material) {  //是向抽屉中添加图片。这个图片要控制一下大小再插入,主要是高度，在140-150之间，这个值我不是很确定。那个白色背景是方便调试的，使用时去掉。
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
			picview.addView(imageView);
		
	}
	
	private void initDecodeService()
    {
        if (decodeService == null)
        {
            decodeService = new DecodeServiceBase(new PdfContext());
        }
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        decodeService.recycle();
        decodeService = null;
        super.onDestroy();
    }
    
    public void onPanelClosed(Panel panel) {
	}
	public void onPanelOpened(Panel panel) {
	}
	
	@Override
    public void onBackPressed() {
    	System.out.println("back pressed");
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,IndexActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
	
}
