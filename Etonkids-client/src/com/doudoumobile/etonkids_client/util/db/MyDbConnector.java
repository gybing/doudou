package com.doudoumobile.etonkids_client.util.db;

import java.util.ArrayList;

import com.doudoumobile.etonkids_client.model.Lesson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDbConnector{
	SQLiteDatabase db;
	String lessonTable;
	String curriculumTable;
	private static MyDbConnector mdc;
	
	public static MyDbConnector getMyDbConnector(Context context){
		if(mdc == null)
			new MyDbConnector(context);
		return mdc;
	}
	private MyDbConnector(Context context){
		lessonTable = MyDbOpenHelper.getTableLesson();
		curriculumTable = MyDbOpenHelper.getTableCurriculum();
		db = MyDbOpenHelper.getConnector(context).getWritableDatabase();
		mdc = this;
	}
	public long addLesson(Lesson lesson){
		ContentValues values = new ContentValues();
		values.put("id", lesson.getId());
		values.put("title", lesson.getTitle());
		values.put("curriculumId",lesson.getCurriculumId());
		values.put("expireTime", lesson.getExpireTime());
		long i = db.insert(lessonTable, null, values);
		return i;
	}
	
	public ArrayList<Lesson> getLessons() {
		Cursor cs = db.rawQuery("SELECT * FROM "+lessonTable+" where available = 1 ORDER BY id",null);
		ArrayList<Lesson> temp = new ArrayList<Lesson>();;
		cs.moveToFirst();
		while(!cs.isAfterLast()){
			Lesson object = new Lesson();
			object.setId(cs.getInt(0));
			object.setTitle(cs.getString(1));
			object.setCurriculumId(cs.getInt(2));
			temp.add(object);
			cs.moveToNext();
		}
		return temp;
	}
//	public void updateNote(Content no,long index){
//		ContentValues values = new ContentValues();
//		values.put("date", no.getDate());
//		values.put("content", no.getContent());
//		values.put("imgUrl", no.getImageUrl());
//		values.put("time", no.getTime());
//		db.update(tableName1, values, "_id =" + index, null);
//	}
//	public void deleteNote(long id){
//		db.delete(tableName1, "_id = "+id,null);
//	}
//	public Cursor getAllNotesByID(){
//		Cursor cs = db.rawQuery("SELECT * FROM "+tableName1+" ORDER BY _id",null);
//		return cs;
//	}
//	public ArrayList<Content> getNotesArray(){
//		Cursor cs = getAllNotesByID();
//		ArrayList<Content> temp = new ArrayList<Content>();;
//		cs.moveToFirst();
//		while(!cs.isAfterLast()){
//			Content object = new Content();
//			object.setDate(cs.getString(1));object.setContent(cs.getString(2));object.setImageUrl(cs.getString(3));object.setId(cs.getLong(0));
//			object.setTime(cs.getString(4));
//			temp.add(object);
//			cs.moveToNext();
//		}
//		return temp;
//	}
//	public Boolean dbEmpty(){
//		Cursor cs = getAllNotesByID();
//		if(cs == null||cs.getCount() <= 0){
//			return true;
//		}
//		else 
//			return false;
//	}
//	public void addSticky(StickyNoteInfo sni){
//		ContentValues values = new ContentValues();
//		values.put("tabNum", sni.getTabNum());
//		values.put("stickyText", sni.getStickyText());
//		if(sni.getReload())
//			values.put("reload",1);
//		else
//			values.put("reload",0);
//		
//		db.insert(tableName2, null, values);
//	}
//	public void updateSticky(StickyNoteInfo sni){
//		ContentValues values = new ContentValues();
//		values.put("tabNum", sni.getTabNum());
//		values.put("stickyText", sni.getStickyText());
//		if(sni.getReload())
//			values.put("reload",1);
//		else
//			values.put("reload",0);
//		db.update(tableName2, values, "_id =" + String.valueOf(sni.getTabNum()+1), null);
//	}
//	public Cursor getAllStickyByID(){
//		Cursor cs = db.rawQuery("SELECT * FROM "+tableName2+" ORDER BY _id",null);
//		return cs;
//	}
//	public StickyNoteInfo[] getStickyArray(){
//		Cursor cs = getAllStickyByID();
//		StickyNoteInfo[] temp = new StickyNoteInfo[4];;
//		cs.moveToFirst();
//		int i = 0;
//		while(!cs.isAfterLast()){
//			StickyNoteInfo object = new StickyNoteInfo();
//			object.setTabNum(cs.getInt(1));object.setStickyText(cs.getString(2));object.setReload(cs.getShort(3)==1);
//			temp[i++] = object;
//			cs.moveToNext();
//		}
//		return temp;
//	}
//	public Boolean StickyEmpty(){
//		Cursor cs = getAllStickyByID();
//		if(cs == null||cs.getCount() <= 0){
//			return true;
//		}
//		else 
//			return false;
//	}
}
