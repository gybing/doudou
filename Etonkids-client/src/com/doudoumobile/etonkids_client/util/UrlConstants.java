package com.doudoumobile.etonkids_client.util;

import org.apache.http.NameValuePair;

import android.util.Log;

public class UrlConstants {
	private static String host = "http://192.168.1.110:8080/Etonkids_Server/";
	public static String downloadUrl = host + "download.do?action=download";
	
	private static String login = host + "etonUser.do?action=login";
	private static String downloadLessonListUrl = host + "lesson.do?action=getLessonsToDownload";
	private static String modifyPwdUrl = host + "etonUser.do?action=modifyPwd";
	private static String doudouTicket = "";
	
	public static String getLoginUrl(NameValuePair... params) {
		String url = login + doudouTicket;
		String paramS = "";
		for (NameValuePair p : params) {
		 	paramS = p.getName() + "=" + p.getValue();
        	url += "&" + paramS;
        }
		Log.i("paramUrl = " , url);
		return url;
	}
	
	public static String getDownloadLessonListUrl() {
		return downloadLessonListUrl + doudouTicket;
	}
		
	public static void setDoudouTicket(String ticket) {
		doudouTicket = "&doudouTicket=" + ticket;
	}
	
	public static String getModifyPwdUrl(NameValuePair... params) {
		String url = modifyPwdUrl + doudouTicket;
		String paramS = "";
		for (NameValuePair p : params) {
		 	paramS = p.getName() + "=" + p.getValue();
        	url += "&" + paramS;
        }
		Log.i("paramUrl = " , url);
		return url;
	}
	
	public static String getDownloadPath(String filePath) {
		String url = downloadUrl + doudouTicket + "&filePath=" + filePath;
		System.out.println("download url = " + url);
		//String url = host + "upload/" + filePath;
		return url;
	}
}
