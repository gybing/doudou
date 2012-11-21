package com.doudoumobile.etonkids_client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.androidpn.client.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;
import com.doudoumobile.etonkids_client.util.DoudouHttpClient;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.LocalFileEraser;
import com.doudoumobile.etonkids_client.util.NetCheckReceiver;
import com.doudoumobile.etonkids_client.util.UrlConstants;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;

/**
 * This is the init activity, do the init work
 * 
 * Check if logined, goto Index page
 * 
 * else go to login page
 * */
public class MainActivity extends Activity {

	AQuery aq;
	private NetCheckReceiver netCheckReceiver = new NetCheckReceiver();
	private static String TAG = "MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        aq = new AQuery(this);
        registerReceiver();
        boolean exit = getIntent().getBooleanExtra("EXIT", false);
        if (!exit) {
        	System.out.println("Init....");
        	init();
		} else {
			System.out.println("Exit...");
			finish();
		}
    }
    
    private boolean mkdir() {
    	if (checkSDCard()) {
    		String dir = Constants.SD_PATH;
        	File dirFile = new File(dir);
        	if (!dirFile.exists()) {
    			boolean creaResult = dirFile.mkdirs();
    			if (creaResult) {
    				Log.i("TAG", "Create Dir Success!");
    				return true;
    			} else {
    				Log.i("TAG", "Create Dir Failure!");
    				return false;
    			}
    		} else {
    			return true;
    		}
		} else {
			Toast.makeText(this, "Please check the SD Card !", Toast.LENGTH_LONG).show();
			return false;
		}
    	
    }
    
    private void init() {
    	if (!mkdir()) {
    		finish();
		}
    	String lastLoginName = checkLoginName();
    	if (!lastLoginName.equals("")) {
    		Log.i(TAG,"have login. Go to Index page");
    		// 读取用户的信息配置文件
    		loadProperties(lastLoginName);
    		
    		// set ticket info
        	setTicketInfo();
        	
        	// delete expired files
        	startDeleteExpiredFiles();
        	
        	// go to index page
        	Intent intent = new Intent(Intent.ACTION_VIEW);
        	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        intent.setClass(this,IndexActivity.class);
	        startActivity(intent);
         	
		} else {
			Log.i(TAG,"Not login...Go to log in page");
			// go to login page
			Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.setClass(this,LoginActivity.class);
	        startActivity(intent);
	        
		}
    }
    
    private void loadProperties(String name) {
    	Constants.Current_Preference_Name = name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private String checkLoginName() {
    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    	String lastLoginName = sharedPreferences.getString("isLogin", "");
    	return lastLoginName;
    }
    
    private void setTicketInfo() {
    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.Current_Preference_Name,Context.MODE_PRIVATE);
    	String doudouTicket = sharedPreferences.getString("Doudou","");
    	if (!"".equals(doudouTicket)) {
    		Log.i(TAG, doudouTicket);
			//DoudouHttpClient.setDoudouTicket(doudouTicket);
    		UrlConstants.setDoudouTicket(doudouTicket);
		}
    }
    
    private void registerReceiver() {
    	IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    	registerReceiver(netCheckReceiver,filter);
    }
    
    @Override
    public void onDestroy() {
    	unRegisterReceiver();
    	super.onDestroy();
    }
    
    private void unRegisterReceiver() {
    	unregisterReceiver(netCheckReceiver);
    }
    
    public static boolean checkSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
    
    private void goDelete(String nowDate) {
    	Log.i("FileEraser", "delete expired files start! now date = " + nowDate);
    	if ("".equals(nowDate)) {
			return;
		}
    	List<Lesson> expiredLessonList = MyDbConnector.getMyDbConnector(this).getExpiredLessons(nowDate);
    	for (Lesson lesson : expiredLessonList) {
    		MyDbConnector.getMyDbConnector(this).deleteLesson(lesson.getId());
			for (Material material : lesson.getMaterialList()) {
				LocalFileEraser.delete(material.getPath());
			}
		}
    	
    }
    
    private void startDeleteExpiredFiles() {
    	String url = UrlConstants.getNowTimeUrl();
    	aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String json, AjaxStatus status) {
            	String nowDate = "";
                    if(json != null){
                    	nowDate = json;
                    	System.out.println("获取服务器时间：" + nowDate);
                    }else{
                    	nowDate = getDateString(new Date());
                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
                    	System.out.println("没有获取服务器时间，拿本地时间：" + nowDate);
                    }
                    goDelete(nowDate);
            }
    	});
    	
    }
    
    private String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		result = sdf.format(date);
		return result;
	}
    
    
}
