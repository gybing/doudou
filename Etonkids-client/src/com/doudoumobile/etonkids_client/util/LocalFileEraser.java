package com.doudoumobile.etonkids_client.util;

import java.io.File;

import org.androidpn.client.Constants;

import android.util.Log;

public class LocalFileEraser {
	
	private static final String sdCard = Constants.SD_PATH;
	private static final String TAG = "LocalFileEraser";
	
	public static boolean delete(String path) {
		boolean result = true;
		if (!path.startsWith(sdCard)) {
			path = sdCard + path;
		}
		System.out.println("File to delete : " + path);
		File file = new File(path);
		
		if (file.exists()) {
			file.delete();
			Log.i(TAG, "删除文件成功！ path = " + path);
		} else {
			Log.e(TAG, "文件不存在！path = " + path);
			return false;
		}
		return result;
	}
	
	public static boolean remoteWipeAll() {
		String dir = sdCard;
		File dirF = new File(dir);
		System.out.println(dir);
		if (dirF.exists()) {
			File[] fileList = dirF.listFiles();
			for (File file : fileList) {
				if (file.isDirectory()) {
					for (File m : file.listFiles()) {
						Log.w(TAG, "To delete : " + m.getName());
						boolean r = m.delete();
						Log.w(TAG, String.valueOf(r));
					}
				} else {
					Log.w(TAG, "To delete : " + file.getName());
					boolean r = file.delete();
					Log.w(TAG, String.valueOf(r));
				}
				
			}
		}
		return true;
	}
}
