package com.example.pedometer.db;

import java.util.ArrayList;
import java.util.List;

import com.example.pedometer.model.Group;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;
import com.example.pedometer.model.Weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
			values.put("picture", user.getPicture());
			values.put("weight", user.getWeight());
			values.put("sensitivity", user.getSensitivity());
			values.put("step_length", user.getStep_length());
			values.put("groupId", user.getGroupId());
			values.put("today_step", user.getToday_step());
			db.insert("user", null, values);
		}
	}

	public void updateUser(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("name", user.getName());
			values.put("sex", user.getSex());
			values.put("picture", user.getPicture());
			values.put("weight", user.getWeight());
			values.put("sensitivity", user.getSensitivity());
			values.put("step_length", user.getStep_length());
			values.put("groupId", user.getGroupId());
			values.put("today_step", user.getToday_step());
			db.update("user", values, "id = ?",
					new String[] { String.valueOf(user.getId()) });
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

	public void updateStep(Step step) {
		if (step != null) {
			ContentValues values = new ContentValues();
			values.put("number", step.getNumber());
			values.put("date", step.getDate());
			values.put("userId", step.getUserId());
			db.update("step", values, "userId = ? and date = ?", new String[] {
					String.valueOf(step.getUserId()), step.getDate() });
		}
	}

	public void updateGroup(Group group) {
		if (group != null) {
			ContentValues values = new ContentValues();
			values.put("total_number", group.getAverage_number());
			values.put("member_number", group.getMember_number());
			db.update("group1", values, "id = ?",
					new String[] { String.valueOf(group.getID()) });
		}
	}

	public void saveGroup(Group group) {
		if (group != null) {
			ContentValues values = new ContentValues();
			values.put("total_number", group.getAverage_number());
			values.put("member_number", group.getMember_number());
			db.insert("group1", null, values);
		}
	}

	public void saveWeather(Weather weather) {
		if (weather != null) {
			ContentValues values = new ContentValues();
			values.put("cityid", weather.getCityid());
			values.put("city", weather.getCity());
			values.put("temp1", weather.getTemp1());
			values.put("temp2", weather.getTemp2());
			values.put("weather", weather.getWeather());
			values.put("date", weather.getWeather());
			db.insert("weather", null, values);
		}
	}

	/**
	 * 根据组的id取数据
	 * 
	 * @param weather
	 */
	public Group loadGroup(int id) {
		Group group = null;
		Cursor cursor = db.query("group1", null, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				group = new Group();
				group.setID(cursor.getInt(cursor.getColumnIndex("id")));
				group.setAverage_number(cursor.getInt(cursor
						.getColumnIndex("total_number")));
				group.setMember_number(cursor.getInt(cursor
						.getColumnIndex("member_number")));
			} while (cursor.moveToNext());

		}
		return group;

	}

	public Step loadSteps(int userId, String date) {
		Step step = null;
		Cursor cursor = db
				.query("step", null, "userId = ? and date = ?", new String[] {
						String.valueOf(userId), date }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				step = new Step();
				step.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
				step.setDate(cursor.getString(cursor.getColumnIndex("date")));
				step.setUserId(userId);
			} while (cursor.moveToNext());

		} else {
			Log.i("tag", "step is null!");
		}
		return step;
	}

	public List<Group> loadListGroup() {
		Group group = null;
		List<Group> list = new ArrayList<Group>();
		Cursor cursor = db.rawQuery("select * from group1", null);
		if (cursor.moveToFirst()) {
			do {
				group = new Group();
				group.setID(cursor.getInt(cursor.getColumnIndex("id")));
				group.setAverage_number(cursor.getInt(cursor
						.getColumnIndex("total_number")));
				group.setMember_number(cursor.getInt(cursor
						.getColumnIndex("member_number")));
				list.add(group);
			} while (cursor.moveToNext());

		}
		return list;

	}

	public List<User> lodListUsers() {
		List<User> list = new ArrayList<User>();
		Cursor cursor = db.rawQuery(
				"select * from user  order by today_step desc", null);
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setGroupId(cursor.getInt(cursor.getColumnIndex("groupId")));
				user.setName(cursor.getString(cursor.getColumnIndex("name")));
				user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				user.setPicture(cursor.getString(cursor
						.getColumnIndex("picture")));
				user.setSensitivity(cursor.getInt(cursor
						.getColumnIndex("sensitivity")));
				user.setStep_length(cursor.getInt(cursor
						.getColumnIndex("step_length")));
				user.setWeight(cursor.getInt(cursor.getColumnIndex("weight")));
				user.setToday_step(cursor.getInt(cursor
						.getColumnIndex("today_step")));
				list.add(user);
			} while (cursor.moveToNext());
		}
		return list;

	}

	public List<Step> loadListSteps(String date) {
		List<Step> list = new ArrayList<Step>();

		Cursor cursor = db.rawQuery(
				"select * from step where date = ? order by number desc",
				new String[] { date });
		if (cursor.moveToFirst()) {
			do {
				Step step = new Step();
				step.setId(cursor.getInt(cursor.getColumnIndex("id")));
				step.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
				step.setDate(cursor.getString(cursor.getColumnIndex("date")));
				step.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
				list.add(step);
			} while (cursor.moveToNext());

		}

		return list;
	}

	public User loadUser(int id) {
		User user = null;
		Cursor cursor = db.query("user", null, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				user = new User();
				user.setName(cursor.getString(cursor.getColumnIndex("name")));
				user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				user.setId(id);
				user.setPicture(cursor.getString(cursor
						.getColumnIndex("picture")));
				user.setSensitivity(cursor.getInt(cursor
						.getColumnIndex("sensitivity")));
				user.setStep_length(cursor.getInt(cursor
						.getColumnIndex("step_length")));
				user.setWeight(cursor.getInt(cursor.getColumnIndex("weight")));
				user.setGroupId(cursor.getInt(cursor.getColumnIndex("groupId")));
				user.setToday_step(cursor.getInt(cursor
						.getColumnIndex("today_step")));
			} while (cursor.moveToNext());
		} else {
			Log.i("tag", "User is null!");
		}
		return user;
	}

	public Weather loadWeather(String date) {
		Weather weather = new Weather();
		Cursor cursor = db.query("weather", null, "date = ?",
				new String[] { date }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				weather.setCity(cursor.getString(cursor.getColumnIndex("city")));
				weather.setTemp1(cursor.getString(cursor
						.getColumnIndex("temp1")));
				weather.setTemp2(cursor.getString(cursor
						.getColumnIndex("temp2")));
				weather.setWeather(cursor.getString(cursor
						.getColumnIndex("weather")));
			} while (cursor.moveToNext());

		} else {

		}

		return weather;

	}
}
