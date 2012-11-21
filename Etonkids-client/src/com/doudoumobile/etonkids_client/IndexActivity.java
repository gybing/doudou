package com.doudoumobile.etonkids_client;

import java.util.List;

import org.androidpn.client.ServiceManager;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.UrlConstants;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

public class IndexActivity extends TabActivity {

	
	private TabHost tabhost;
	private RadioGroup tabGroup;
	AQuery aq;
	private ImageView newInfo;
	private TextView newCount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        init();
    }
    private class OnTabChangeListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int id) {
			switch (id) {		
			case R.id.rad1:
				tabhost.setCurrentTabByTag("TAB1");
				break;
				case R.id.rad2:
					showNews(0);
					tabhost.setCurrentTabByTag("TAB2");
					break;
				case R.id.rad3:
					tabhost.setCurrentTabByTag("TAB3");
					break;
						}
			}
	} 
    private void init() {
    	aq = new AQuery(this);
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

		
		newInfo = (ImageView)findViewById(R.id.newInfo);
		newCount = (TextView)findViewById(R.id.newCount);
		showNews(0);
		
		if (!isServiceRunning()) {
			startXmppService();
		}
		
		freshDownloadLessonList();
    }
    
    private void freshDownloadLessonList() {
    	String url = UrlConstants.getDownloadLessonListUrl();
    	aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String json, AjaxStatus status) {
                    if(json != null){
                        //successful ajax call, show status code and json content
                        List<Curriculum> result = DoudouJsonHelper.getInstance().getCurriculumArray(json.toString());
                        Log.i("IndexActivity", "result size = " + result.size() );
                        int count = getNews(result);
                        showNews(count);
                    }else{
                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
                    }
            }
    	});
    }
    
    private int getNews(List<Curriculum> result) {
    	int count = 0;
    	for (Curriculum curriculum : result) {
			for (Lesson lesson : curriculum.getLessonList()) {
				 if (!MyDbConnector.getMyDbConnector(this).isLessonExist(lesson.getId())) {
					 System.out.println("Not existed : lesson id : " + lesson.getId());
					 count ++ ;
				 }
			}
		}
    	System.out.println("News : " + count);
    	return count;
    }
    private void showNews(int count) {
    	if (!(count == 0)) {
    		newInfo.setVisibility(View.VISIBLE);
    		newCount.setVisibility(View.VISIBLE);
    		if (count < 10) {
    			newCount.setText(" "+count);
			} else {
				newCount.setText(""+count);
			}
		} else {
			newInfo.setVisibility(View.INVISIBLE);
    		newCount.setVisibility(View.INVISIBLE);
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_index, menu);
        return true;
    }
    
    private void startXmppService(){
    	ServiceManager serviceManager = new ServiceManager(this);
    	serviceManager.setNotificationIcon(R.drawable.notification);
    	serviceManager.startService();
    }
    
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("org.androidpn.client.NotificationService".equals(service.service.getClassName())) {
            	System.out.println("yeah it equals");
                return true;
            }
        }
        return false;
    }
    
    
}
