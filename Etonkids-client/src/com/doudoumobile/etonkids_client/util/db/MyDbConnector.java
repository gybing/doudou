package com.doudoumobile.etonkids_client.util.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
	public ArrayList<Lesson> getExpiredLessons(String dateString) {
		Cursor cs = db.rawQuery("SELECT * FROM "+lessonTable+" where endDate < '"  + dateString+ "' ORDER BY id",null);
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
	
	 public void deleteLesson(long id){
		 System.out.println("Delete lesson id = " + id);
		 db.delete(lessonTable, " id = "+id,null);
	 }
	
	 public void truncate() {
		 String sql = "DELETE FROM " + lessonTable +";";
	     db.execSQL(sql);
	     String sql2 = "DELETE FROM " + materialTable +";";
	     db.execSQL(sql2);
	 }
	
}
