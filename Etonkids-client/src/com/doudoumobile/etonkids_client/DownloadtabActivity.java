package com.doudoumobile.etonkids_client;

import java.util.HashMap;
import java.util.List;

import org.androidpn.client.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Curriculum.CurriculumType;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.UrlConstants;
import com.doudoumobile.etonkids_client.util.ZipUtil;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;
import com.doudoumobile.etonkids_client.util.download.Downloader;
import com.doudoumobile.etonkids_client.util.download.LoadInfo;

public class DownloadtabActivity extends Activity{

	public static final String TAG = "DownloadtabActivity";
	
	AQuery aq;
	LinearLayout downloadCurriList;
	
	ImageButton refreshBtn;
	
    private static final String URL = UrlConstants.downloadUrl;
    // 固定存放下载的音乐的路径：SD卡目录下
    private static final String SD_PATH = Constants.SD_PATH;
    // 存放各个下载器
    private HashMap<String, Downloader> downloaders = new HashMap<String, Downloader>();
	private HashMap<String, BookitemActivity> progresses = new HashMap<String, BookitemActivity>() ;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabdownload);
        
        init();
    }
	
	private void init() {
		aq = new AQuery(this);
		downloadCurriList = (LinearLayout)findViewById(R.id.downloadCurriList);
		
		refreshBtn = (ImageButton)findViewById(R.id.btn_refresh2);
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				freshDownloadLessonList();
			}
		});
		freshDownloadLessonList();
	}
	
	/**
     * 83 * 响应开始下载按钮的点击事件 84
     */
    public void startDownload(BookitemActivity v ) {
    		Lesson lesson = v.getLesson();
            //= ((TextView) layout .findViewById(R.id.tv_resouce_name)).getText().toString();
            String urlstr = UrlConstants.getDownloadPath(lesson.getPdfPath());
            String localfile = SD_PATH + lesson.getPdfPath();
            // 设置下载线程数为1，这里是我为了方便随便固定的
            int threadcount = 1;
            // 初始化一个downloader下载器
            Downloader downloader = downloaders.get(urlstr);
            if (downloader == null) {
                    downloader = new Downloader(urlstr, localfile, threadcount, this,mHandler);
                    System.out.println("New Downloader");
                    downloaders.put(urlstr, downloader);
            }
            if (downloader.isdownloading()) {
            	downloader.pause();
            	return;
            }
            showProgress(urlstr, v);
            new MyDownloadThread(downloader, urlstr, v) .start();
           
    }
    
    class MyDownloadThread extends Thread {
    	
    	Downloader downloader;
    	String urlstr;
    	BookitemActivity v;
    	
    	MyDownloadThread(Downloader downloader, String urlstr, BookitemActivity v) {
    		this.downloader = downloader;
    		this.urlstr = urlstr;
    		this.v = v;
    	}
    	
    	 @Override
         public void run() {
    		 // 得到下载信息类的个数组成集合
             LoadInfo loadInfo = downloader.getDownloaderInfors();
             // 调用方法开始下载
             downloader.download();
    	 }
    }
    
    /**
     * 显示进度条
     */
    private void showProgress(String url, BookitemActivity v) {
    	BookitemActivity item = progresses.get(url);
    	if (null == item) {
			progresses.put(url, v);
			v.editTx2("Downloading..." );
		}
    	
    }

    /**
     * 31 * 利用消息处理机制适时更新进度条 32
     */
    private Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                            String url = (String) msg.obj;
                            double curruentSize = msg.arg1;
                            double fileSize = msg.arg2;
                            String localFile = msg.getData().getString("localFile");
                            BookitemActivity item = progresses.get(url);
                            if (item != null) {
                            	item.editTx2("Downloading..." + ((double)((int)(curruentSize*1000/fileSize)))/10 + "%");
                            	if (curruentSize >= fileSize) {
                            		Toast.makeText(DownloadtabActivity.this, "下载完成！", Toast.LENGTH_LONG).show();
                            		item.editTx2("下载完成");
                            		
                            		downloaders.get(url).delete(url);
                            		downloaders.get(url).reset();
                            		downloaders.remove(url);
                            		
                            		afterDownloadSuccess(url,localFile);
                            		progresses.remove(url);
								}
                            }
                    }
            }
    };
    
    private void afterDownloadSuccess(String url, String localFilePath) {
    	//unzip lesson关联
    	System.out.println("localFilePath : " + localFilePath);
    	ZipUtil.unZip(localFilePath);
    	Lesson lesson = progresses.get(url).getLesson();
    	MyDbConnector.getMyDbConnector(this).addLesson(lesson);
    }
    
	private void showLessonsToDownload(List<Curriculum> result) {
		downloadCurriList.removeAllViews();
		downloaders.clear();
		progresses.clear();
		
		int index = 0;
		for (Curriculum curriculum : result) {
			BookgroupActivity curriculumAc = new BookgroupActivity(aq.getContext(), null) {
				@Override
				public void processDlAll() {
					// Download all
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
			curriculumAc.showBtndl();
			curriculumAc.setCurriculum(curriculum);

			for (Lesson lesson : curriculum.getLessonList()) {
				if (MyDbConnector.getMyDbConnector(this).isLessonExist(lesson.getId())) {
					continue;
				}
				BookitemActivity lessonItem = new BookitemActivity(this, null,lesson) {

					@Override
					public void buttonClick() {
						// TODO Auto-generated method stub
						startDownload(this);
					}

				};
				lessonItem.editBookname(lesson.getTitle());
				lessonItem.editTx2(lesson.getEndDate());
				lessonItem.editTxbtn("Download");
				lessonItem.hideico();
				lessonItem.setLesson(lesson);

				curriculumAc.addLesson(lessonItem);
			}

			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 	LayoutParams.WRAP_CONTENT);
			downloadCurriList.addView(curriculumAc, params);
		}

	}
    
    private void freshDownloadLessonList() {
    	String url = UrlConstants.getDownloadLessonListUrl();
    	aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String json, AjaxStatus status) {
                    if(json != null){
                        //successful ajax call, show status code and json content
                        List<Curriculum> result = DoudouJsonHelper.getInstance().getCurriculumArray(json.toString());
                        Log.i(TAG, "result size = " + result.size() );
                        addCurriculumToDB(result);
                        showLessonsToDownload(result);
                    }else{
                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
                    }
            }
    	});
    }
    
    private void addCurriculumToDB(List<Curriculum> curriList) {
    	for (Curriculum curriculum : curriList) {
			if (!MyDbConnector.getMyDbConnector(this).isCurriculumExist(curriculum.getCurriculumName())) {
				long id = MyDbConnector.getMyDbConnector(this).addCurriculum(curriculum);
				Log.i(TAG, "Add curriculum to db, id : " + id);
			}
		}
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
