package com.example.pedometer.service;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

	}

	private void init() {
		step = new Step();
		pedometerDB = PedometerDB.getInstance(this);
		userid = 1;
		date = "20141119";

	}

}
