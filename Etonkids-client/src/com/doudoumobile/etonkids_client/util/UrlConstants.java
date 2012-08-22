package com.doudoumobile.etonkids_client.util;

import org.apache.http.NameValuePair;

import android.util.Log;

public class UrlConstants {
	public static String host = "http://192.168.1.109:8080/Etonkids_Server/";
	
	public static String login = host + "etonUser.do?action=login";
	public static String downloadLessonListUrl = host + "lesson.do?action=getLessonsToDownload";
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
}
