package com.bit.pedometer.db;

import java.util.ArrayList;
import java.util.List;

import com.bit.pedometer.model.Group;
import com.bit.pedometer.model.Step;
import com.bit.pedometer.model.User;
import com.bit.pedometer.model.Weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 对数据库pedometer里的各个表进行增删改查
 * 
 * @author 李垭超
 * 
 */
public class PedometerDB {



	public static final String DB_NAME = "pedometer.db";// 数据库名称

	public static final int VERSION = 1;// 数据版本

	private static PedometerDB pedometerDB;

	private SQLiteDatabase db;

	/**
	 * 将PedometerDB的构造方法设置为私有方法，在别的类里不能通过new来创建这个对象
	 * 
	 * @param context
	 */
	private PedometerDB(Context context) {
		PedometerOpenHelper pHelper = new PedometerOpenHelper(context, DB_NAME,
				null, VERSION);
		db = pHelper.getWritableDatabase();
	}

	/**
	 * 使用单例模式创建数据库
	 */
	public synchronized static PedometerDB getInstance(Context context) {
		if (pedometerDB == null) {
			pedometerDB = new PedometerDB(context);
		}
		return pedometerDB;
	}

	/**
	 * 增加user表里的数据
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("objectId", user.getObjectId());
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

	/**
	 * 根据user的id删除user表里的数据
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		if (user != null) {
			db.delete("user", "objectId = ?",
					new String[] { user.getObjectId() });
		}
	}

	/**
	 * 升级user表里的数据
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("objectId", user.getObjectId());
			values.put("name", user.getName());
			values.put("sex", user.getSex());
			values.put("picture", user.getPicture());
			values.put("weight", user.getWeight());
			values.put("sensitivity", user.getSensitivity());
			values.put("step_length", user.getStep_length());
			values.put("groupId", user.getGroupId());
			values.put("today_step", user.getToday_step());
			db.update("user", values, "objectId = ?",
					new String[] { user.getObjectId() });
		}
	}

	/**
	 * 升级user表里的数据
	 * 
	 * @param user
	 */
	public void changeObjectId(User user) {
		if (user != null) {
			ContentValues values = new ContentValues();
			values.put("objectId", user.getObjectId());
			db.update("user", values, null, null);
		}
	}

	/**
	 * 增加step表里的数据
	 * 
	 * @param step
	 */
	public void saveStep(Step step) {
		if (step != null) {
			ContentValues values = new ContentValues();
			values.put("number", step.getNumber());
			values.put("date", step.getDate());
			values.put("userId", step.getUserId());
			db.insert("step", null, values);
		}
	}

	/**
	 * 升级step表里的数据
	 * 
	 * @param step
	 */
	public void updateStep(Step step) {
		if (step != null) {
			ContentValues values = new ContentValues();
			values.put("number", step.getNumber());
			values.put("date", step.getDate());
			values.put("userId", step.getUserId());
			db.update("step", values, "userId = ? and date = ?", new String[] {
					step.getUserId(), step.getDate() });
		}
	}

	/**
	 * 升级step表里的数据
	 * 
	 * @param step
	 */
	public void changeuserId(Step step) {
		if (step != null) {
			ContentValues values = new ContentValues();
			// values.put("number", step.getNumber());
			// values.put("date", step.getDate());
			values.put("userId", step.getUserId());
			db.update("step", values, null, null);
		}
	}

	/**
	 * 增加group数据表里的数据
	 * 
	 * @param group
	 */
	public void saveGroup(Group group) {
		if (group != null) {
			ContentValues values = new ContentValues();
			values.put("total_number", group.getTotal_number());
			values.put("member_number", group.getMember_number());
			db.insert("group1", null, values);
		}
	}

	/**
	 * 升级group表里的数据
	 * 
	 * @param group
	 */
	public void updateGroup(Group group) {
		if (group != null) {
			ContentValues values = new ContentValues();
			values.put("total_number", group.getTotal_number());
			values.put("member_number", group.getMember_number());
			db.update("group1", values, "id = ?",
					new String[] { String.valueOf(group.getID()) });
		}
	}

	/**
	 * 存储从网站上抓取的天气数据
	 * 
	 * @param weather
	 */
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
	 * 根据group组的id取数据
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
				group.setTotal_number(cursor.getInt(cursor
						.getColumnIndex("total_number")));
				group.setMember_number(cursor.getInt(cursor
						.getColumnIndex("member_number")));
			} while (cursor.moveToNext());

		}
		return group;

	}

	/**
	 * 根据user表的userid和date来取数据
	 * 
	 * @param userId
	 * @param date
	 * @return
	 */
	public Step loadSteps(String userId, String date) {
		Step step = null;
		Cursor cursor = db.query("step", null, "userId = ? and date = ?",
				new String[] { userId, date }, null, null, null);
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

	/**
	 * 取出group1中的所有数据 因为sqlit中自带group表，所以不能创建group组
	 * 
	 * @return
	 */
	public List<Group> loadListGroup() {
		Group group = null;
		List<Group> list = new ArrayList<Group>();
		Cursor cursor = db.rawQuery("select * from group1", null);
		if (cursor.moveToFirst()) {
			do {
				group = new Group();
				group.setID(cursor.getInt(cursor.getColumnIndex("id")));
				group.setTotal_number(cursor.getInt(cursor
						.getColumnIndex("total_number")));
				group.setMember_number(cursor.getInt(cursor
						.getColumnIndex("member_number")));
				list.add(group);
			} while (cursor.moveToNext());

		}
		return list;

	}

	/**
	 * 取出user中所有的数据，按照步数的降序取出
	 * 
	 * @return
	 */
	public List<User> lodListUsers() {
		List<User> list = null;
		Cursor cursor = db.rawQuery(
				"select * from user  order by today_step desc", null);
		if (cursor.moveToFirst()) {
			list = new ArrayList<User>();
			do {
				User user = new User();
				user.setObjectId(cursor.getString(cursor
						.getColumnIndex("objectId")));
				user.setGroupId(cursor.getInt(cursor.getColumnIndex("groupId")));
				user.setName(cursor.getString(cursor.getColumnIndex("name")));
				user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				user.setPicture(cursor.getBlob(cursor.getColumnIndex("picture")));
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

	/**
	 * 更具date取出所有的step数据
	 * 
	 * @param date
	 * @return
	 */
	public List<Step> loadListSteps() {
		List<Step> list = new ArrayList<Step>();

		Cursor cursor = db.rawQuery("select * from step order by number desc",
				null);
		if (cursor.moveToFirst()) {
			do {
				Step step = new Step();
				step.setId(cursor.getInt(cursor.getColumnIndex("id")));
				step.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
				step.setDate(cursor.getString(cursor.getColumnIndex("date")));
				step.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
				list.add(step);
			} while (cursor.moveToNext());

		}

		return list;
	}

	/**
	 * 根据id取出user数据
	 * 
	 * @param id
	 * @return
	 */
	public User loadUser(String objectId) {
		User user = null;
		Cursor cursor = db.query("user", null, "objectId = ?",
				new String[] { objectId }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				user = new User();
				user.setName(cursor.getString(cursor.getColumnIndex("name")));
				user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
				user.setObjectId(objectId);
				user.setPicture(cursor.getBlob(cursor.getColumnIndex("picture")));
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

	/**
	 * 取出第一个用户，也就是用此app的用户
	 * 
	 * @param id
	 * @return
	 */
	public User loadFirstUser() {
		User user = null;
		Cursor cursor = db.query("user", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			user = new User();
			user.setName(cursor.getString(cursor.getColumnIndex("name")));
			user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			user.setObjectId(cursor.getString(cursor.getColumnIndex("objectId")));
			user.setPicture(cursor.getBlob(cursor.getColumnIndex("picture")));
			user.setSensitivity(cursor.getInt(cursor
					.getColumnIndex("sensitivity")));
			user.setStep_length(cursor.getInt(cursor
					.getColumnIndex("step_length")));
			user.setWeight(cursor.getInt(cursor.getColumnIndex("weight")));
			user.setGroupId(cursor.getInt(cursor.getColumnIndex("groupId")));
			user.setToday_step(cursor.getInt(cursor
					.getColumnIndex("today_step")));
		} else {
			Log.i("tag", "User is null!");
		}
		return user;
	}

	/**
	 * 更具日期取出天气数据
	 * 
	 * @param date
	 * @return
	 */
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
