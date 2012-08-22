package com.doudoumobile.etonkids_client;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Toast;

public class DownloadTestActivity extends Activity {
	
	private DownloadManager mgr = null; 
    private long lastDownloadId = 0;  
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_test);
        mgr = (DownloadManager)getSystemService(DOWNLOAD_SERVICE); 
        BroadcastReceiver onComplete =  new BroadcastReceiver() {  
            public void onReceive(Context context, Intent intent) { 
               Toast.makeText(context, "下载完成" + intent.getDataString(), Toast.LENGTH_LONG);
            } 
        }; 
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        //registerReceiver(onComplete, new IntentFilter(DownloadManager.STATUS_FAILED));
        startDownload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_download_test, menu);
        return true;
    }
    
    public void startDownload(){  
        Uri uri = Uri.parse("http://commonsware.com/misc/test.mp4");
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        lastDownloadId = mgr.enqueue(new DownloadManager.Request(uri) 
           .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI) 
           .setAllowedOverRoaming(false)
           .setTitle("MyTest")
           .setDescription("Something Useful")
           .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test.mp4")
           .setVisibleInDownloadsUi(false)
           .setShowRunningNotification(true));
        Toast.makeText(this, "下载开始", Toast.LENGTH_LONG).show();
    }

    
}
