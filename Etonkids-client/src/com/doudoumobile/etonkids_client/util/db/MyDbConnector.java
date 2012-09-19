package com.doudoumobile.etonkids_client.util.db;

import java.util.ArrayList;

import com.doudoumobile.etonkids_client.model.Curriculum;
import com.doudoumobile.etonkids_client.model.Lesson;
import com.doudoumobile.etonkids_client.model.Material;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDbConnector{
	SQLiteDatabase db;
	String lessonTable;
	String curriculumTable;
	String materialTable;
	private static MyDbConnector mdc;
	
	public static MyDbConnector getMyDbConnector(Context context){
		if(mdc == null) {
			mdc = new MyDbConnector(context);
		}
		return mdc;
	}
	private MyDbConnector(Context context){
		lessonTable = MyDbOpenHelper.getTableLesson();
		curriculumTable = MyDbOpenHelper.getTableCurriculum();
		materialTable = MyDbOpenHelper.getTableMaterial();
		db = MyDbOpenHelper.getConnector(context).getWritableDatabase();
	}
	public long addLesson(Lesson lesson){
		ContentValues values = new ContentValues();
		values.put("id", lesson.getId());
		System.out.println("id = " + lesson.getId());
		values.put("title", lesson.getTitle());
		System.out.println("title = " + lesson.getTitle());
		values.put("curriculumId",lesson.getCurriculumId());
		System.out.println("curriId = " + lesson.getCurriculumId());
		values.put("endDate", lesson.getEndDate());
		System.out.println("endDate = " + lesson.getEndDate());
		values.put("available", 1);
		values.put("pdfPath", lesson.getPdfPath());
		System.out.println("pdfPath = " + lesson.getPdfPath());
		long i = db.insert(lessonTable, null, values);
		for (Material material : lesson.getMaterialList()) {
			addMaterial(material);
		}
		return i;
	}
	
	public long addMaterial(Material material) {
		ContentValues values = new ContentValues();
		values.put("id", material.getId());
		values.put("path", material.getPath());
		values.put("type", material.getType());
		values.put("lessonId",material.getLessonId());
		long i = db.insert(materialTable, null, values);
		return i;
	}
	
	public boolean isCurriculumExist(String curriName) {
		Cursor cs = db.rawQuery("SELECT * FROM "+curriculumTable+" where name = '" + curriName + "'" ,null);
		if(cs == null||cs.getCount() <= 0){
			return false;
		}
		return true;
	}
	
	public boolean isLessonExist(int lessonId) {
		Cursor cs = db.rawQuery("SELECT * FROM "+lessonTable+" where id = " + lessonId,null);
		if(cs == null||cs.getCount() <= 0){
			return false;
		}
		return true;
	}
	
	public long addCurriculum(Curriculum curriculum) {
		ContentValues values = new ContentValues();
		values.put("id", curriculum.getId());
		values.put("name", curriculum.getCurriculumName());
		values.put("imgPath",curriculum.getImgPath());
		System.out.println("curriculum : " + curriculum.getCurriculumName());
		long i = db.insert(curriculumTable, null, values);
		return i;
	}
	
	public ArrayList<Lesson> getLessonsByCurrId(int currId) {
		Cursor cs;
		if (currId == 0) {
			cs = db.rawQuery("SELECT * FROM "+lessonTable+" where available = 1 ORDER BY id",null);
		} else {
			cs = db.rawQuery("SELECT * FROM "+lessonTable+" where available = 1 and curriculumId = "+currId + "   ORDER BY id",null);
		}
		ArrayList<Lesson> temp = new ArrayList<Lesson>();
		cs.moveToFirst();
		while(!cs.isAfterLast()){
			Lesson object = new Lesson();
			object.setId(cs.getInt(0));
			object.setTitle(cs.getString(1));
			object.setCurriculumId(cs.getInt(2));
			object.setEndDate(cs.getString(3));
			object.setPdfPath(cs.getString(4));
			object.setAvailable(cs.getShort(5) != 0);
			object.setMaterialList(getMaterialsByLessonId(object.getId()));
			temp.add(object);
			cs.moveToNext();
		}
		return temp;
	}
	
	public ArrayList<Material> getMaterialsByLessonId(int lessonId) {
		Cursor cs;
		if (lessonId == 0) {
			cs = db.rawQuery("SELECT * FROM "+materialTable+" ORDER BY id",null);
		} else {
			cs = db.rawQuery("SELECT * FROM "+materialTable+" where  lessonId = "+lessonId + "   ORDER BY id",null);
		}
		ArrayList<Material> temp = new ArrayList<Material>();
		cs.moveToFirst();
		while(!cs.isAfterLast()){
			Material object = new Material();
			object.setId(cs.getInt(0));
			object.setPath(cs.getString(1));
			object.setType(cs.getInt(2));
			object.setLessonId(cs.getInt(3));
			temp.add(object);
			cs.moveToNext();
		}
		return temp;
	}
	
	public ArrayList<Curriculum> getDownloadedCurriculums() {
		Cursor cs = db.rawQuery("SELECT * FROM "+curriculumTable+" ORDER BY id",null);
		ArrayList<Curriculum> temp = new ArrayList<Curriculum>();
		cs.moveToFirst();
		while(!cs.isAfterLast()){
			Curriculum object = new Curriculum();
			object.setId(cs.getInt(0));
			object.setImgPath(cs.getString(1));
			object.setCurriculumName(cs.getString(2));
			
			ArrayList<Lesson> lessonList = getLessonsByCurrId(object.getId());
			object.setLessonList(lessonList);
			
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
