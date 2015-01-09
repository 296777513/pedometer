package com.example.pedometer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PedometerOpenHelper extends SQLiteOpenHelper{
	
	/**
	 * 创建step表
	 */
	public static final String CREATE_STEP = "create table step("
			+ "id integer primary key autoincrement,"
			+ "number text,"
			+ "date integer,"
			+ "userId text)";
	
	/**
	 * 创建group表
	 */
	public static final String CREATE_GROUP = "create table group1("
			+ "id integer primary key autoincrement,"
			+ "total_number integer,"
			+ "member_number integer)";
	

	/**
	 * 创建user表
	 */
	public static final String CREATE_USER = "create table user("
			+ "objectId text ,"
			+ "name text,"
			+ "sex text,"
			+ "weight integer,"
			+ "step_length integer,"
			+ "sensitivity integer,"
			+ "picture blob,"
			+ "groupId integer,"
			+ "today_step integer)";
	
	/**
	 * 创建weather表
	 */
	public static final String CREATE_WEATHER = "create table weather("
			+ "cityid integer primary key,"
			+ "city text,"
			+ "temp1 text,"
			+ "temp2 text,"
			+ "weather text,"
			+ "ptime text)";
	
	/**
	 * 带参数的PedometerOpenHelper构造函数
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public PedometerOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	/**
	 * 创建数据库
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STEP);
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_WEATHER);
		db.execSQL(CREATE_GROUP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	

}
