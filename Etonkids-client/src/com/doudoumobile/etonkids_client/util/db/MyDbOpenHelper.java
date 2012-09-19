package com.doudoumobile.etonkids_client.util.db;

import org.androidpn.client.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbOpenHelper extends SQLiteOpenHelper{
	private static String DATABASE_NAME = "Etonkids";
	private static final String TABLE_Lesson = "Lesson";
	private static final String TABLE_Curriculum = "Curriculum";
	private static final String TABLE_MATERIAL = "Material";
	
	private static MyDbOpenHelper mydbc;
	private MyDbOpenHelper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}
	
	public static MyDbOpenHelper getConnector(Context context){
		DATABASE_NAME += "_" + Constants.Current_Preference_Name + ".db";
		if(mydbc==null)
			mydbc = new MyDbOpenHelper(context);
		else
			return mydbc;
		return mydbc;
	}
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE Lesson (id INTEGER PRIMARY KEY ,title TEXT,curriculumId INTEGER,endDate Text,pdfPath Text, available INTEGER);");
		db.execSQL("CREATE TABLE Curriculum (id INTEGER PRIMARY KEY ,imgPath TEXT, name TEXT);");
		db.execSQL("CREATE TABLE Material(id INTEGER PRIMARY KEY , path TEXT, type INTEGER, lessonId INTEGER);");
		db.execSQL("create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
                + "start_pos integer, end_pos integer, compelete_size integer,url char);");
//
//		//init data for lesson
//		db.execSQL("insert into Lesson values(1,'Lesson 1 book 1',1,'2012-08-19','/c1/l1.etonpdf',1)");
//		db.execSQL("insert into Lesson values(2,'Lesson 2 book 2',1,'2012-08-19','/c1/l2.etonpdf',1)");
//		db.execSQL("insert into Lesson values(3,'Lesson 3 book 3',1,'2012-08-19','/c1/l3.etonpdf',1)");
//		
//		db.execSQL("insert into Lesson values(4,'Lesson 4 book 4',2,'2012-08-19','/c2/l4.etonpdf',1)");
//		db.execSQL("insert into Lesson values(5,'Lesson 5 book 5',2,'2012-08-19','/c2/l5.etonpdf',1)");
//		
//		db.execSQL("insert into Curriculum values(1,'K1-MG','K1-MG')");
//		db.execSQL("insert into Curriculum values(2,'K1-LL','K1-LL')");
//		//SOUND = 0;VIDEO = 1;PICTURE = 2;PDF = 3;
//		db.execSQL("insert into Material values(1,'/c1/come here.jpg',2,1)");
//		db.execSQL("insert into Material values(2,'/c1/count.jpg',2,1)");
//		//db.execSQL("insert into Material values(2,'/c1/count.jpg',2,1)");
//		System.out.println("onCreate......");
		
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