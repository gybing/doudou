package com.doudoumobile.etonkids_client;

import org.androidpn.client.Constants;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
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
		deviceId = sp.getString(Constants.DEVICE_ID, "");
		
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
						Toast.makeText(SettingtabActivity.this, "新密码不一致", Toast.LENGTH_LONG).show();
					}
					else {
						BasicNameValuePair p1 = new BasicNameValuePair("oldPwd", MD5.encode(oldPwd));
						BasicNameValuePair p3 = new BasicNameValuePair("newPwd", MD5.encode(newPwd));
						String url = UrlConstants.getModifyPwdUrl(p1,p3);
						aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
				            @Override
				            public void callback(String url, JSONObject json, AjaxStatus status) {
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
				            //do nothing - it will close on its own
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
				
			}
		});
		
		
		
		
	}
	
	private void handleResult(String result) {
		if (null != result && !"".equals(result)) {
			switch(Integer.parseInt(result)) {
			case 0 : 
				Toast.makeText(aq.getContext(), "用户名密码验证失败", Toast.LENGTH_LONG).show();
				break;
			case 1 : 
				Toast.makeText(aq.getContext(), "修改成功", Toast.LENGTH_LONG).show();
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
