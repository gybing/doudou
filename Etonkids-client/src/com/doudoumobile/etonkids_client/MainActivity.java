package com.doudoumobile.etonkids_client;

import org.androidpn.client.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.doudoumobile.etonkids_client.util.DoudouHttpClient;
import com.doudoumobile.etonkids_client.util.NetCheckReceiver;
import com.doudoumobile.etonkids_client.util.UrlConstants;

/**
 * This is the init activity, do the init work
 * 
 * Check if logined, goto Index page
 * 
 * else go to login page
 * */
public class MainActivity extends Activity {

	private NetCheckReceiver netCheckReceiver = new NetCheckReceiver();
	private static String TAG = "MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //TODO
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
    
    private void init() {
    	String lastLoginName = checkLoginName();
    	if (!lastLoginName.equals("")) {
    		Log.i(TAG,"have login. Go to Index page");
    		// 读取用户的信息配置文件
    		loadProperties(lastLoginName);
    		
    		// set ticket info
    		
        	setTicketInfo();
        	
        	// load db data
        	
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
}
