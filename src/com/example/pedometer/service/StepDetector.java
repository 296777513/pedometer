package com.example.pedometer.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 这是一个实现了信号监听的记步的类
 * 这是从谷歌找来的一个记步的算法，看不太懂
 * @author Liyachao Date:2015-1-6
 *
 */
public class StepDetector implements SensorEventListener {

	public static int CURRENT_SETP = 0;
	public static float SENSITIVITY = 10; // SENSITIVITY灵敏度
	private float mLastValues[] = new float[3 * 2];
	private float mScale[] = new float[2];
	private float mYOffset;
	private static long end = 0;
	private static long start = 0;
	/**
	 * 最后加速度方向
	 */
	private float mLastDirections[] = new float[3 * 2];
	private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
	private float mLastDiff[] = new float[3 * 2];
	private int mLastMatch = -1;

	/**
	 * 传入上下文的构造函数
	 * 
	 * @param context
	 */
	public StepDetector(Context context) {
		super();
		int h = 480;
		mYOffset = h * 0.5f;
		mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
		mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
	}

	//当传感器检测到的数值发生变化时就会调用这个方法
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		synchronized (this) {
			if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

				float vSum = 0;
				for (int i = 0; i < 3; i++) {
					final float v = mYOffset + event.values[i] * mScale[1];
					vSum += v;
				}
				int k = 0;
				float v = vSum / 3;

				float direction = (v > mLastValues[k] ? 1
						: (v < mLastValues[k] ? -1 : 0));
				if (direction == -mLastDirections[k]) {
					// Direction changed
					int extType = (direction > 0 ? 0 : 1); // minumum or
															// maximum?
					mLastExtremes[extType][k] = mLastValues[k];
					float diff = Math.abs(mLastExtremes[extType][k]
							- mLastExtremes[1 - extType][k]);

					if (diff > SENSITIVITY) {
						boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
						boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
						boolean isNotContra = (mLastMatch != 1 - extType);

						if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough
								&& isNotContra) {
							end = System.currentTimeMillis();
							if (end - start > 500) {// 此时判断为走了一步

								CURRENT_SETP++;
								mLastMatch = extType;
								start = end;
							}
						} else {
							mLastMatch = -1;
						}
					}
					mLastDiff[k] = diff;
				}
				mLastDirections[k] = direction;
				mLastValues[k] = v;
			}

		}
	}
	//当传感器的经度发生变化时就会调用这个方法，在这里没有用
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

}
