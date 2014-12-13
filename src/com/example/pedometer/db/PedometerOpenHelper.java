package com.example.pedometer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PedometerOpenHelper extends SQLiteOpenHelper{
	
	public static final String CREATE_STEP = "create table step("
			+ "id integer primary key autoincrement,"
			+ "number text,"
			+ "date integer,"
			+ "userId integer,"
			+ "name text)";
	
	
	public static final String CREATE_USER = "create table user("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "sex text,"
			+ "height integer,"
			+ "weight integer,"
			+ "birthday integer,"
			+ "sensitivity integer,"
			+ "step_length integer)";
	public static final String CREATE_WEATHER = "create table weather("
			+ "cityid integer primary key,"
			+ "city text,"
			+ "temp1 text,"
			+ "temp2 text,"
			+ "weather text,"
			+ "ptime text)";

	public PedometerOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STEP);
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_WEATHER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	

}
