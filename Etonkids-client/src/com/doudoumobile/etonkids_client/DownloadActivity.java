package com.doudoumobile.etonkids_client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
/**
 * This activity is just test for download 
 * 
 * */
public class DownloadActivity extends Activity {

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressBar progressBar;
	private TextView tv;
	private Button downloadBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        
        downloadBtn = (Button)findViewById(R.id.button1);
        downloadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startDownload();
			}
		});
        
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tv = (TextView)findViewById(R.id.currentProgress);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_download, menu);
        return true;
    }
    
    private void startDownload() {
    	String url = "http://image-7.verycd.com/0d7596f5db7df1742236466dc91311b1435702(120x120)/thumb.jpg";
    	new DownloadFileAsync().execute(url);
    }
    
    class DownloadFileAsync extends AsyncTask< String, String, String> {
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    	}
    	@Override
    	protected String doInBackground(String... aurl) {
    		int count;
    		try {
    			URL url = new URL(aurl[0]);
    			URLConnection conexion = url.openConnection();
    			conexion.connect();
    			int lenghtOfFile = conexion.getContentLength();
    			Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
    			InputStream input = new BufferedInputStream(url.openStream());
    			OutputStream output = new FileOutputStream("/mnt/sdcard/picture.jpg");
    			byte data[] = new byte[1024];
    			long total = 0;
    			while ((count = input.read(data)) != -1) {
    				total += count;
    				publishProgress(""+(int)((total*100)/lenghtOfFile));
    				output.write(data, 0, count);
    			}
    			output.flush();
    			output.close();
    			input.close();
    		} catch (Exception e) {
    			Log.e("error",e.getMessage().toString());
    			System.out.println(e.getMessage().toString());
    		}
    		return "finish";
    	}
    	protected void onProgressUpdate(String... progress) {
    		Log.d("ANDRO_ASYNC",progress[0]);
    		progressBar.setProgress(Integer.parseInt(progress[0]));
    		tv.setText(progress[0] + "%");
    		super.onProgressUpdate(progress);
    		
    	}
    	@Override
    	protected void onPostExecute(String unused) {
    		tv.setText(unused);
    		super.onPostExecute(unused);
    	}
    }
    
}
