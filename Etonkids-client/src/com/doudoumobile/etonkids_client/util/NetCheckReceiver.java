package com.doudoumobile.etonkids_client.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetCheckReceiver extends BroadcastReceiver{

    
    @Override
    public void onReceive(Context context, Intent intent) {

       ConnectivityManager connectionManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo wifiNetInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       if (!wifiNetInfo.isConnected()) {
    	   Toast.makeText(context, "网络连接不可用", Toast.LENGTH_SHORT).show();
       }
    }
}
