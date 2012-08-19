package com.doudoumobile.etonkids_client;

import org.androidpn.client.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.doudoumobile.etonkids_client.model.User;
import com.doudoumobile.etonkids_client.util.DoudouHttpClient;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.MD5;

import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity {

	String userName = "admin@etonkids.com";
	String passWd = "admin";
	
	private static String TAG = "LoginActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        processLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    private void processLogin() {
    	passWd = new String(MD5.encode(passWd));
    	NameValuePair loginType = new BasicNameValuePair("doudouTicket", "login");
    	NameValuePair p1 = new BasicNameValuePair("userName", userName);
    	NameValuePair p2 = new BasicNameValuePair("passWd", passWd);
    	//String jsonString = DoudouHttpClient.get(getString(R.string.loginAction), loginType, p1, p2);
    	String jsonString = "{\"available\":true,\"email\":\"admin@etonkids.com\",\"id\":1,\"lastLoginTime\":\"2012-08-16 23:41:04\",\"online\":false,\"password\":\"888\",\"realName\":\"我是管理员\",\"role\":0,\"teacherTypeId\":0,\"username\":\"admin@etonkids.com\"}";
    	User user = DoudouJsonHelper.getInstance().getUser(jsonString);
    	if (null != user) {
			loginSuccess(user);
		} else {
			loginFailure();
		}
    }
    
    private void loginFailure() {
    	System.out.println("Failure");
    	Toast.makeText(this, "用户名或密码错误" , Toast.LENGTH_SHORT).show();
    	Log.i(TAG,"login failure");
    }
    
    private void loginSuccess(User user) {
    	// store
    	SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.putString(Constants.ETON_USERNAME, userName);
    	editor.putString(Constants.ETON_PASSWD, passWd);
    	editor.putLong(Constants.ETON_USERID, user.getId());
    	editor.putString(Constants.DOUDOU_TICKET, user.getTicket());
    	editor.putBoolean("isLogin", true);
    	editor.commit();
    	Log.i(TAG,"Success: userName:" + userName + "passWd : " + passWd);
    	
    	// Go to IndexActivity
    	Toast.makeText(this, "登陆成功" , Toast.LENGTH_SHORT).show();
    	
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,IndexActivity.class);
        //startActivity(intent);
    }

    
}
