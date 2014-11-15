package com.example.pedometer.db;



import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PedometerDB {

	public static final String DB_NAME = "pedometer.db";

	public static final int VERSION = 1;

	private static PedometerDB pedometerDB;

	private SQLiteDatabase db;

	private PedometerDB(Context context) {
		PedometerOpenHelper pHelper = new PedometerOpenHelper(context, DB_NAME,
				null, VERSION);
		db = pHelper.getWritableDatabase();
	}

	public synchronized static PedometerDB getInstance(Context context) {
		if (pedometerDB == null) {
			pedometerDB = new PedometerDB(context);
		}
		return pedometerDB;
	}

	public void saveUser(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("name", user.getName());
			values.put("sex", user.getSex());
			values.put("height", user.getHeight());
			values.put("weight", user.getWeight());
			values.put("birthday", user.getBirthday());
			values.put("sensitivity", user.getSensitivity());
			values.put("step_length", user.getStep_length());
			db.insert("user", null, values);
		}
	}

	public void saveStep(Step step) {
		if (step != null) {
			ContentValues values = new ContentValues();
			values.put("number", step.getNumber());
			values.put("date", step.getDate());
			values.put("userId", step.getUserId());
			db.insert("step", null, values);
		}
	}

	public Step loadSteps(int userId, int date) {
		Step step = new Step();
		Cursor cursor = db.query("step", null, "userId = ? and date = ?",
				new String[] { String.valueOf(userId), String.valueOf(date) },
				null, null, null);
		if (cursor.moveToFirst()) {
			do {
				step.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
				step.setDate(cursor.getInt(cursor.getColumnIndex("date")));
				step.setUserId(userId);
			} while (cursor.moveToNext());

		}
		return step;
	}
}
