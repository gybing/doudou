package com.doudoumobile.etonkids_client.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbOpenHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "Etonkids.db";
	private static final String TABLE_Lesson = "Lesson";
	private static final String TABLE_Curriculum = "Curriculum";
	private static final String TABLE_MATERIAL = "Material";
	
	private static MyDbOpenHelper mydbc;
	private MyDbOpenHelper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}
	
	public static MyDbOpenHelper getConnector(Context context){
		if(mydbc==null)
			mydbc = new MyDbOpenHelper(context);
		else
			return mydbc;
		return mydbc;
	}
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE Lesson (id INTEGER PRIMARY KEY ,title TEXT,curriculumId INTEGER,expireTime Text,pdfPath Text, available Short);");
		db.execSQL("CREATE TABLE Curriculum (id INTEGER PRIMARY KEY ,imgPath TEXT, name TEXT)");
		db.execSQL("CREATE TABLE Material(id INTEGER PRIMARY KEY , path TEXT, type INTEGER, lessonId INTEGER)");
//		try {
//			FileOutputStream fos = new FileOutputStream("/data/data/android.note/sysyinfo.txt");
//			fos.write(1);
//			fos.close();
//		} 
//		catch (FileNotFoundException e){e.printStackTrace();}
//		catch (IOException e) {e.printStackTrace();}
		// init test data
		db.execSQL("insert into Lesson values(2,'title2',1,'2012-08-19 22:22:22','Path/path',1)");
		System.out.println("onCreate......");
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS Lesson");
		db.execSQL("DROP TABLE IF EXISTS Curriculum");
		db.execSQL("DROP TABLE IF EXISTS Material");
        onCreate(db);
	}
	public static String getDatabaseName(){
		return DATABASE_NAME;
	}
	public static String getTableCurriculum(){
		return TABLE_Curriculum;
	}
	public static String getTableLesson(){
		return TABLE_Lesson;
	}
	public static String getTableMaterial() {
		return TABLE_MATERIAL;
	}
}