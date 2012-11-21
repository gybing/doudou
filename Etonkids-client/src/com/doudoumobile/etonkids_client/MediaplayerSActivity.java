package com.doudoumobile.etonkids_client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import org.androidpn.client.Constants;

import com.doudoumobile.etonkids_client.model.Material;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MediaplayerSActivity extends LinearLayout{  //小播放器

	private ImageButton btn_play, btn_pre, btn_next;
	private LinearLayout mlist_s;
	
	private ArrayList<Material> musicList;
	private Context context;
	
	private static String TAG = "MediaPlayerSActivity";
	private MediaPlayer mMediaPlayer = null;
	private Timer mTimer;
	private MusiclistSActivity currentListItem = null;
	
	private enum Player_State {
		INIT, PLAYING, PAUSE;
	}
	
	private Player_State currentState = Player_State.INIT;
	
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
				if (currentState == Player_State.INIT) {
					if (null != currentListItem) {
							play(currentListItem);
					} else {
						if (!musicList.isEmpty()) {
							play((MusiclistSActivity)mlist_s.getChildAt(0));
						}
					}
				} else if (currentState == Player_State.PLAYING) {
					pause();
				} else {
					resume();
				}
				
			}
		});
		btn_pre = (ImageButton)findViewById(R.id.btn_pre);
		btn_pre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				playPre();
			}
		});
		
		btn_next = (ImageButton)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				playNext();
			}
		});
		
		mlist_s = (LinearLayout)findViewById(R.id.mlist_s);
		
		musicList = new ArrayList<Material>();
	}
	
	public void insertMlist(List<Material> materialList) {  //插入多媒体列表
		musicList.clear();
		mlist_s.removeAllViews();
		musicList.addAll(materialList);
		int i = 0;
		for (Material musicPath : musicList) {
			MusiclistSActivity listItem = new MusiclistSActivity(context , null);
			File musicFile = new File(musicPath.getPath());
			String name = getMusicName(musicFile);
			if (musicPath.getType() == 1) {
				listItem.editName(name + "(Video)");
			} else {
				listItem.editName(name);
			}
			listItem.index = i++;
			listItem.type = musicPath.getType();
			listItem.setMusicPath(musicPath.getPath());
			listItem.editLength("");
			listItem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (currentListItem == (MusiclistSActivity)arg0) {
						if (currentState == Player_State.PLAYING) {
							pause();
						} else {
							resume();
						}
					} else {
						if (null != currentListItem) {
							currentListItem.editLength("");
						}
						stop();
						play((MusiclistSActivity)arg0);
					}
				}
			});
			mlist_s.addView(listItem);
			
		}
	}
	
	private void showVideo(String path) {
		 Intent it = new Intent(Intent.ACTION_VIEW); 
		 Uri uri = Uri.parse(path); 
	     it.setDataAndType(uri , "video/mp4"); 
	     context.startActivity(it);
	}
	
	public void stop() {
		currentState = Player_State.INIT;
		btn_play.setImageResource(R.drawable.btn_play_selector);
		stopTimer();
		stopMusic();
	}
	
	private void stopTimer() {
		if (null != mTimer) {
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	private void play(MusiclistSActivity v){ 
		try{     
			currentListItem = v;
			if( v.type == 1) {
				 String videoPath = v.getMusicPath();
				 System.out.println("Video : path = " + "file://" + Constants.SD_PATH + videoPath);
			     showVideo("file://" + Constants.SD_PATH + videoPath);
			     return;
			}
			btn_play.setImageResource(R.drawable.btn_pause_selector);
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.reset();     
			mMediaPlayer.setDataSource(Constants.SD_PATH + v.getMusicPath());     
			mMediaPlayer.prepare();
			mMediaPlayer.start();    
			currentState = Player_State.PLAYING;
			v.setDuration(formatMS(mMediaPlayer.getDuration()));
			mTimer = new Timer();    
			TimerTask mTimerTask = new TimerTask() {    
                @Override    
                public void run() {     
                    if(null != mMediaPlayer && currentState == Player_State.PLAYING) {   
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
					stop();
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
	
	private void resume() {
		mMediaPlayer.start();    
		currentState = Player_State.PLAYING;
		mTimer = new Timer();    
		TimerTask mTimerTask = new TimerTask() {    
            @Override    
            public void run() {     
                if(null != mMediaPlayer && currentState == Player_State.PLAYING) {   
                	int ms = mMediaPlayer.getCurrentPosition();
                	Message m = new Message();
                	m.arg1 = ms;
                	mHandler.sendMessage(m);
                } 
            }    
        };   
        mTimer.schedule(mTimerTask, 0, 50);   
        btn_play.setImageResource(R.drawable.btn_pause_selector);
	}
	
	private void pause() {
		currentState = Player_State.PAUSE;
		btn_play.setImageResource(R.drawable.btn_play_selector);
		stopTimer();
		pauseMusic();
	}
	
	private void pauseMusic() {
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
		}
	}
	private void playPre() {
		if (null == currentListItem) {
			return;
		} else if (currentListItem.index == 0) {
			Toast.makeText(context, "This is the first song", Toast.LENGTH_SHORT).show();
		} else {
			currentListItem.editLength("");
			stop();
			play((MusiclistSActivity)mlist_s.getChildAt(currentListItem.index-1));
		}
	}
	
	private void playNext() {
		if (null == currentListItem) {
			return;
		} else if (currentListItem.index == mlist_s.getChildCount()-1) {
			Toast.makeText(context, "This is the last song", Toast.LENGTH_LONG).show();
		} else {
			currentListItem.editLength("");
			stop();
			play((MusiclistSActivity)mlist_s.getChildAt(currentListItem.index+1));
		}
	}
	private String getMusicName(File musicFile) {
		String fullName = musicFile.getName();
		StringTokenizer st = new StringTokenizer(fullName,".");
		if (st.countTokens() == 2) {
			String s = st.nextToken();
			String[] ss = s.split("%");
			if (ss.length > 1) {
				return ss[1];
			} else {
				System.out.println("music name exception : " + fullName);
				return ss[0];
			}
		} else {
			return "Unknown";
		}
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
         else  if (m < 10 &&  add >= 10) 
            con = "0" + m + ":" + add ;     
         return con; 
    }
}
