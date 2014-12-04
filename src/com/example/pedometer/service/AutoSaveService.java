package com.example.pedometer.service;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AutoSaveService extends Service {
	private PedometerDB pedometerDB;
	private Step step;
	private int userid;
	private String date;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		init();

		if (StepService.flag) {
			step.setNumber(StepDetector.CURRENT_SETP);
			// 测试阶段
			step.setDate(date);
			step.setUserId(userid);
			pedometerDB.saveStep(step);
		} else if (pedometerDB.loadSteps(userid, date) == null) {
			step.setNumber(0);
			step.setDate(date);
			step.setUserId(userid);
			pedometerDB.saveStep(step);
		}
		return super.onStartCommand(intent, flags, startId);

	}

	private void init() {
		Log.i("info", "你好啊");
		step = new Step();
		pedometerDB = PedometerDB.getInstance(this);
		userid = 1;
		date = "20141130";

	}

}
