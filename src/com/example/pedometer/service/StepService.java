package com.example.pedometer.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

public class StepService extends Service {
	public static Boolean flag = false;
	private SensorManager sensorManager;
	private StepDetector stepDetector;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		new Thread(new Runnable() {

			public void run() {
				startStepDetector();
			}
		}).start();

	}

	private void startStepDetector() {
		flag = true;
		stepDetector = new StepDetector(this);
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(stepDetector,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
		if (stepDetector != null) {
			sensorManager.unregisterListener(stepDetector);
		}

	}
}
