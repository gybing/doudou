package com.doudoumobile.etonkids_client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.androidpn.client.ServiceManager;
import org.json.JSONObject;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.UrlConstants;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;
import com.doudoumobile.etonkids_client.util.download.Downloader;
import com.doudoumobile.etonkids_client.util.download.LoadInfo;

public class IndexActivity extends TabActivity {

	AQuery aq;
	private TabHost tabhost;
	private RadioGroup tabGroup;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        aq = new AQuery(this);
        
        init();
    }
    private class OnTabChangeListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			// TODO Auto-generated method stub
			//尤其需要注意这里，setCurrentTabByTag方法是纽带
			switch (id) {		
			case R.id.rad1:
				tabhost.setCurrentTabByTag("TAB1");
				break;
				case R.id.rad2:
					tabhost.setCurrentTabByTag("TAB2");
					break;
				case R.id.rad3:
					tabhost.setCurrentTabByTag("TAB3");
					break;
						}
			}
	} 
    private void init() {
        tabhost = getTabHost();
		tabGroup = (RadioGroup) findViewById(R.id.tab_group);
		Intent tab1 = new Intent(this, LessontabActivity.class);
		Intent tab2 = new Intent(this, DownloadtabActivity.class);
		Intent tab3 = new Intent(this, SettingtabActivity.class);
		tabhost.addTab(tabhost.newTabSpec("TAB1").setIndicator("Tab 1")
				.setContent(tab1));
		tabhost.addTab(tabhost.newTabSpec("TAB2").setIndicator("Tab 2")
				.setContent(tab2));
		tabhost.addTab(tabhost.newTabSpec("TAB3").setIndicator("Tab 3")
				.setContent(tab3));
		tabhost.setCurrentTabByTag("TAB1");
		
		tabGroup.setOnCheckedChangeListener((OnCheckedChangeListener) new OnTabChangeListener());

    	//startXmppService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_index, menu);
        return true;
    }
    
    private void startXmppService(){
    	ServiceManager serviceManager = new ServiceManager(this);
    	//serviceManager.setNotificationIcon(R.drawable.notification);
    	serviceManager.startService();
    }
}
