package com.doudoumobile.etonkids_client;

import java.util.List;

import org.androidpn.client.ServiceManager;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.User;
import com.doudoumobile.etonkids_client.util.DoudouJsonHelper;
import com.doudoumobile.etonkids_client.util.ErrorCodeHandler;
import com.doudoumobile.etonkids_client.util.UrlConstants;
import com.doudoumobile.etonkids_client.util.db.MyDbConnector;

public class IndexActivity extends Activity {

	AQuery aq;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        aq = new AQuery(this);
        
        init();
    }
    
    private void init() {
//    	loadLessonInfo();
//    	startXmppService();
    	//freshDownloadLessonList();
    	showLessonsToDownload(null);
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
    
    private void freshDownloadLessonList() {
    	String url = UrlConstants.getDownloadLessonListUrl();
    	aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                    if(json != null){
                        //successful ajax call, show status code and json content
                        List<Curriculum> result = DoudouJsonHelper.getInstance().getCurriculumArray(json.toString());
                        showLessonsToDownload(result);
                    }else{
                    	ErrorCodeHandler.ajaxCodeHandler(aq.getContext(),status.getCode());
                    }
            }
    	});
    }
    
    private void showLessonsToDownload(List<Curriculum> result) {
    	Toast.makeText(this, "课程数：" + result.size() + " " + result.get(0).getLessonList().size(), Toast.LENGTH_LONG).show();
    }
    
    private void downloadLesson(Lesson lesson) {
    	
    }
    
    

    
}
