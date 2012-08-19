package com.doudoumobile.etonkids_client;

import org.androidpn.client.ServiceManager;

import com.doudoumobile.etonkids_client.util.db.MyDbConnector;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class IndexActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        
        init();
    }
    
    private void init() {
    	loadLessonInfo();
    	startXmppService();
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
    
    private void loadLessonInfo() {
    	MyDbConnector.getMyDbConnector(this).getLessons();
    	
    }

    
}
