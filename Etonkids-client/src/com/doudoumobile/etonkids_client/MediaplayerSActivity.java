package com.doudoumobile.etonkids_client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import com.doudoumobile.etonkids_client.model.Material;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MediaplayerSActivity extends LinearLayout{  //小播放器

	private ImageButton btn_play, btn_pre, btn_next;
	private LinearLayout mlist;
	
	private ArrayList<String> musicList;
	private Context context;
	
	private static String TAG = "MediaPlayerSActivity";
	private MediaPlayer mMediaPlayer = null;
	private Timer mTimer;
	private MusiclistSActivity currentListItem = null;
	
	private boolean isPlaying = false;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			int ms = msg.arg1;
			String text = formatMS(ms);
			currentListItem.editLength(text);
		}
	};
	
	public MediaplayerSActivity(Context context) {
		super(context);
		this.context = context;
	}
	
	public MediaplayerSActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.element_mplayer_s, this, true);
		
		btn_play = (ImageButton)findViewById(R.id.btn_play);
		btn_play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		btn_pre = (ImageButton)findViewById(R.id.btn_pre);
		btn_next = (ImageButton)findViewById(R.id.btn_next);
		mlist = (LinearLayout)findViewById(R.id.mlist);
		
		musicList = new ArrayList<String>();
	}
	
	public void insertMlist(List<Material> materialList) {  //插入多媒体列表
		musicList.clear();
		musicList.add("/mnt/sdcard/etonkids/mp3/Weather Song.m4a");
		musicList.add("/mnt/sdcard/etonkids/mp3/Na na na goodbye song.m4a");
		mlist.removeAllViews();
		//musicList.addAll(materialList);
		for (String musicPath : musicList) {
			MusiclistSActivity listItem = new MusiclistSActivity(context , null);
			File musicFile = new File(musicPath);
			String name = getMusicName(musicFile);
			
			listItem.editName(name);
			listItem.setMusicPath(musicPath);
			listItem.editLength("");
			listItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					System.out.println("Yo man here goes");
					if (currentListItem == (MusiclistSActivity)arg0) {
						pause();
					} else {
						pause();
						play(((MusiclistSActivity)arg0).getMusicPath() , (MusiclistSActivity)arg0);
					}
				}
			});
			
			mlist.addView(listItem);
		}
	}
	
	private void stop() {
		isPlaying = false;
		stopTimer();
		stopMusic();
	}
	
	private void stopTimer() {
		if (null != mTimer) {
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	private void play(String path , MusiclistSActivity v){ 
		try{     
			currentListItem = v;
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.reset();     
			mMediaPlayer.setDataSource(path);     
			mMediaPlayer.prepare();
			mMediaPlayer.start();    
			isPlaying = true;
			v.setDuration(formatMS(mMediaPlayer.getDuration()));
			mTimer = new Timer();    
			TimerTask mTimerTask = new TimerTask() {    
                @Override    
                public void run() {     
                    if(null != mMediaPlayer && isPlaying == true) {   
                    	int ms = mMediaPlayer.getCurrentPosition();
                    	Message m = new Message();
                    	m.arg1 = ms;
                    	mHandler.sendMessage(m);
                    } 
                }    
            };   
            mTimer.schedule(mTimerTask, 0, 50);   
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener(){     
				public void onCompletion(MediaPlayer arg0){     
					stopMusic();
				}     
			});     
		}catch (IOException e){
			Log.v("playMusic","playMusic failed");
		}     
	}  
	
	private void stopMusic() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	private void pause() {
			stop();
	}
	
	private String getMusicName(File musicFile) {
		String fullName = musicFile.getName();
		StringTokenizer st = new StringTokenizer(fullName,".");
		if (st.countTokens() == 2) {
			return st.nextToken();
		} else {
			return "Unknown";
		}
//		String[] ss = fullName.split(".");
//		if (ss.length == 2) {
//			return ss[0];
//		} else {
//			return "Unknown";
//		}
	}
	
	public  static String formatMS( int ms) 
    { 
         int s = ms / 1000; //  秒 
         int m = s / 60; //  分 
         int add = s % 60; //  秒     
        String con = ""; 
         if (add >= 10 && m >= 10) 
            con = m + ":" + add; 
         else  if (add < 10 && m >= 10) 
            con = m + ":0" + add; 
         else  if (m < 10&& add < 10) 
            con = "0" + m + ":0" + add; 
         else  if (m >= 10 && add < 10) 
            con = "" + m + ":0" + add;     
         else  if (m < 10 &&  add > 10) 
            con = "0" + m + ":" + add ;     
         return con; 
    }
}
