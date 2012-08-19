package com.doudoumobile.etonkids_client.util;

import java.io.File;

import android.util.Log;

public class LocalFileEraser {
	
	private static final String TAG = "LocalFileEraser";
	
	public static boolean delete(String path) {
		boolean result = true;
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		} else {
			Log.e(TAG, "文件不存在！");
			return false;
		}
		return result;
	}
}
