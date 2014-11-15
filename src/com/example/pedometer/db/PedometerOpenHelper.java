package com.example.pedometer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PedometerOpenHelper extends SQLiteOpenHelper{
	
	public static final String CREATE_STEP = "create table step("
			+ "id integer primary key autoincrement,"
			+ "number integer,"
			+ "date integer,"
			+ "userId integer)";
	
	
	public static final String CREATE_USER = "create table user("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "sex text,"
			+ "height integer,"
			+ "weight integer,"
			+ "birthday integer,"
			+ "sensitivity integer,"
			+ "step_length integer)";

	public PedometerOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STEP);
		db.execSQL(CREATE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	

}
