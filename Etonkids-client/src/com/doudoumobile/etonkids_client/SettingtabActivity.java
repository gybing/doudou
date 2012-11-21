package com.doudoumobile.etonkids_client;

import org.androidpn.client.Constants;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.MD5;
import com.doudoumobile.etonkids_client.util.UrlConstants;

public class SettingtabActivity extends Activity{

	String name;
	String curriList;
	String schoolInfo;
	String deviceId;
	
	TextView nametx;
	TextView curriculumtx;
	TextView schooltx;
	TextView idtx;
	
	ImageButton logout;
	ImageButton confirm;
	ImageButton cancel;
	EditText password_o;
	EditText password_n;
	EditText password_r;
	
	AQuery aq;
	
	static String TAG = "SettingTabActivity";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabset);
        
        init();
	}
	
	private void init() {
		SharedPreferences sp = getSharedPreferences(Constants.Current_Preference_Name, Context.MODE_PRIVATE);
		name = sp.getString(Constants.ETON_REALNAME, "");
		curriList = sp.getString(Constants.ETON_CURRILIST, "");
		schoolInfo = sp.getString(Constants.ETON_SCHOOLINFO, "");
		
		SharedPreferences deviceSp = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		deviceId = deviceSp.getString(Constants.DEVICE_ID, "");
		
		nametx = (TextView)findViewById(R.id.nametx);
		curriculumtx = (TextView)findViewById(R.id.curriculumtx);
		schooltx = (TextView)findViewById(R.id.schooltx);
		idtx = (TextView)findViewById(R.id.idtx);
		
		nametx.setText(name);
		curriculumtx.setText(curriList);
		schooltx.setText(schoolInfo);
		idtx.setText(deviceId);
		
		aq = new AQuery(this);
		password_o = (EditText)findViewById(R.id.password_o);
		password_n = (EditText)findViewById(R.id.password_n);
		password_r = (EditText)findViewById(R.id.password_r);
		
		logout = (ImageButton)findViewById(R.id.btn_logout);
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				logout();
				//showNotification();
			}
		});
		confirm = (ImageButton)findViewById(R.id.btn_confirm);
		confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String newPwd = password_n.getText().toString();
				String retypePwd = password_r.getText().toString();
				String oldPwd = password_o.getText().toString();
				
				if (!"".equals(retypePwd) && !"".equals(newPwd) && !"".equals(oldPwd)) {
					if (!newPwd.equals(retypePwd)) {
						Toast.makeText(SettingtabActivity.this, "The new password not the same", Toast.LENGTH_LONG).show();
					}
					else {
						BasicNameValuePair p1 = new BasicNameValuePair("oldPwd", MD5.encode(oldPwd));
						BasicNameValuePair p3 = new BasicNameValuePair("newPwd", MD5.encode(newPwd));
						String url = UrlConstants.getModifyPwdUrl(p1,p3);
						aq.ajax(url, String.class, new AjaxCallback<String>() {
				            @Override
				            public void callback(String url, String json, AjaxStatus status) {
				            		confirm.setEnabled(true);
				                    if(json != null){
				                        //successful ajax call, show status code and json content
				                        //Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
				                        handleResult(json.toString());
				                    }else{
				                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
				                    }
				            }
				    	});
					}
				} else {
					new AlertDialog.Builder(SettingtabActivity.this) .setTitle("警告").setMessage("密码不能为空")
				    .setNegativeButton("Close", new DialogInterface.OnClickListener() {   
				        @Override
				        public void onClick(DialogInterface dialog, int which) {
				        }
				     })
				   .show();
				}
			}
		});
		cancel = (ImageButton)findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearPwdText();
			}
		});
		
	}
	
	private void showNotification() {
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		 Notification notification = new Notification();
         notification.icon = 2130837594;
         notification.defaults = Notification.DEFAULT_LIGHTS;
             notification.defaults |= Notification.DEFAULT_SOUND;
             notification.defaults |= Notification.DEFAULT_VIBRATE;
         notification.flags |= Notification.FLAG_AUTO_CANCEL;
         notification.when = System.currentTimeMillis();
         notification.tickerText = "Test message";

         Intent intent = new Intent(this,
                 NotificationDetailsActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
         intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

         PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                 intent, PendingIntent.FLAG_UPDATE_CURRENT);

         notification.setLatestEventInfo(this, "Title", "Message",
                 contentIntent);
         notificationManager.notify(0, notification);
	}
	
	private void clearPwdText() {
		password_o.setText("");
		password_n.setText("");
		password_r.setText("");
	}
	
	private void handleResult(String result) {
		if (null != result && !"".equals(result)) {
			switch(Integer.parseInt(result)) {
			case 0 : 
				Toast.makeText(aq.getContext(), "Wrong old password! ", Toast.LENGTH_LONG).show();
				clearPwdText();
				break;
			case 1 : 
				Toast.makeText(aq.getContext(), "Modified success!", Toast.LENGTH_LONG).show();
				clearPwdText();
				break;
			}
		}
	}
	
	private void logout() {
		SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("isLogin", "");
		editor.commit();
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(this,LoginActivity.class);
		startActivity(intent);
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
