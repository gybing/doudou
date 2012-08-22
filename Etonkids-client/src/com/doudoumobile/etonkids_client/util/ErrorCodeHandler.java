package com.doudoumobile.etonkids_client.util;

import com.androidquery.callback.AjaxStatus;

import android.content.Context;
import android.widget.Toast;

public class ErrorCodeHandler {
	public static void ajaxCodeHandler(Context context , int code) {
		switch(code) {
		case AjaxStatus.NETWORK_ERROR:
			Toast.makeText(context, "请确保网络连接正常", Toast.LENGTH_LONG).show();
			break;
		default :
			Toast.makeText(context, "ErrorCode : " + code, Toast.LENGTH_LONG).show();
		}
	}
}
