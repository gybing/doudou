package com.doudoumobile.etonkids_client.util;

import com.androidquery.callback.AjaxStatus;

import android.content.Context;
import android.widget.Toast;

public class ErrorCodeHandler {
	public static void ajaxCodeHandler(Context context , int code) {
		switch(code) {
		case AjaxStatus.NETWORK_ERROR:
			Toast.makeText(context, "Connect server error, please check the network connection", Toast.LENGTH_LONG).show();
			break;
		case 500:
			Toast.makeText(context, "Server side exception", Toast.LENGTH_LONG).show();
			break;
		default :
			Toast.makeText(context, "ErrorCode : " + code, Toast.LENGTH_LONG).show();
		}
	}
}
