package com.doudoumobile.etonkids_client;

import org.androidpn.client.Constants;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.User;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.MD5;
import com.doudoumobile.etonkids_client.util.UrlConstants;

public class LoginActivity extends Activity {

	AQuery aq;
	EditText userNameET;
	EditText passWdET;
	ImageButton submit;
	
	private static String TAG = "LoginActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aq = new AQuery(this);
        
        userNameET = (EditText)findViewById(R.id.username);
        passWdET = (EditText)findViewById(R.id.password);
        submit = (ImageButton)findViewById(R.id.imageButton1);
        
        submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				Log.i(TAG,"Clicked");
				String userName = userNameET.getText().toString();
				String passWd = passWdET.getText().toString();
				
				processLogin(userName, passWd);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    private void processLogin(String userName, String passWd) {
    	passWd = MD5.encode(passWd);
    	UrlConstants.setDoudouTicket("login");
    	NameValuePair p1 = new BasicNameValuePair("userName", userName);
    	NameValuePair p2 = new BasicNameValuePair("passWd", passWd);
    	String url = UrlConstants.getLoginUrl(p1,p2);
    	aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
            		submit.setEnabled(true);
                    if(json != null){
                        //successful ajax call, show status code and json content
                        Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
                        User user = DoudouJsonHelper.getInstance().getUser(json.toString());
                    	if (null != user) {
                			loginSuccess(user);
                		} else {
                			loginFailure();
                		}
                    }else{
                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
                    }
            }
    	});
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
    	editor.putString("isLogin", user.getUserName());
    	editor.commit();
    	
    	Constants.Current_Preference_Name = user.getUserName();
    	sharedPreferences = getSharedPreferences(Constants.Current_Preference_Name,Context.MODE_PRIVATE);
    	editor = sharedPreferences.edit();
    	editor.putString(Constants.ETON_USERNAME, user.getUserName());
    	editor.putString(Constants.ETON_PASSWD, user.getPassWd());
    	editor.putLong(Constants.ETON_USERID, user.getId());
    	editor.putString(Constants.DOUDOU_TICKET, user.getTicket());
    	editor.putString(Constants.ETON_CURRILIST, user.getCurriList());
    	editor.putString(Constants.ETON_SCHOOLINFO, user.getSchoolInfo());
    	editor.putString(Constants.ETON_REALNAME, user.getRealName());
    	editor.commit();
    	
    	Log.i(TAG,"Success: userName:" + user.getUserName() + "passWd : " + user.getPassWd() + "Ticket : " + user.getTicket());
    	
    	UrlConstants.setDoudouTicket(user.getTicket());
    	// Go to IndexActivity
    	Toast.makeText(this, "登陆成功" , Toast.LENGTH_SHORT).show();
    	
    	Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(this,IndexActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    

    
}
