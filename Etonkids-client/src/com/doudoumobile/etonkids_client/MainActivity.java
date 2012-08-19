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

/**
 * This is the init activity, do the init work
 * 
 * Check if logined, goto Index page
 * 
 * else go to login page
 * */
public class MainActivity extends Activity {

	private NetCheckReceiver netCheckReceiver;
	private static String TAG = "MainActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
		
    }
    
    private void init() {
    	registerReceiver();
    	
    	if (checkLogin()) {
    		Log.i(TAG,"have login. Go to Index page");
			// set ticket info
        	setTicketInfo();
        	
        	// load db data
        	
        	// go to index page
        	Intent intent = new Intent(Intent.ACTION_VIEW);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    
    private void visitWeb() {
    	NameValuePair param1 = new BasicNameValuePair("username", "张三");
    	NameValuePair param2 = new BasicNameValuePair("password", "123456");
    	String s = DoudouHttpClient.get("http://www.baidu.com");
    	System.out.println(s);
    }
    
    private boolean checkLogin() {
    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    	boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
    	return isLogin;
    }
    
    private void setTicketInfo() {
    	SharedPreferences sharedPreferences = getSharedPreferences("doudou",Context.MODE_PRIVATE);
    	String doudouTicket = sharedPreferences.getString("Doudou","");
    	if (!"".equals(doudouTicket)) {
    		Log.i(TAG, doudouTicket);
			DoudouHttpClient.setDoudouTicket(doudouTicket);
		}
    }
    
    private void registerReceiver() {
    	netCheckReceiver = new NetCheckReceiver();
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
