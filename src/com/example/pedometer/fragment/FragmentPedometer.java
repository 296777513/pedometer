package com.example.pedometer.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.onekeyshare.*;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;
import com.example.pedometer.model.Weather;
import com.example.pedometer.service.StepDetector;
import com.example.pedometer.service.StepService;
import com.example.pedometer.widet.RateTextCircularProgressBar;
import com.example.pedometer.R;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPedometer extends Fragment implements OnClickListener {
	private View view;
	private RateTextCircularProgressBar mRateTextCircularProgressBar;
	private int total_step = 0;
	private Thread thread;
	private int Type = 1;
	private int calories = 0;
	private TextView tView1;
	private TextView tView2;
	private TextView tView3;
	private ImageView sharekey;
	private int step_length = 50;
	private int weight = 70;

	private Step step = null;
	private User user = null;
	private Weather weather;
	private PedometerDB pedometerDB;

	private Calendar calendar;
	private SimpleDateFormat sdf;
	private String today;
	private String test;

	@SuppressLint("HandlerLeak")
	public int getTotal_step() {
		return total_step;
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			total_step = StepDetector.CURRENT_SETP;
			if (Type == 1) {
				tView1.setText("步数");
				tView2.setText("目标：10000");
				tView1.setVisibility(View.VISIBLE);
				tView2.setVisibility(View.VISIBLE);
				tView3.setVisibility(View.INVISIBLE);
				tView1.setTextColor(Color.parseColor("#a1a3a6"));
				tView2.setTextColor(Color.parseColor("#a1a3a6"));
				tView3.setTextColor(Color.parseColor("#a1a3a6"));
				mRateTextCircularProgressBar.setProgress(total_step, Type);
			} else if (Type == 2) {
				tView1.setText("卡路里");
				tView2.setText("目标：");
				tView1.setVisibility(View.VISIBLE);
				tView2.setVisibility(View.VISIBLE);
				tView3.setVisibility(View.INVISIBLE);
				tView1.setTextColor(Color.parseColor("#a1a3a6"));
				tView2.setTextColor(Color.parseColor("#a1a3a6"));
				tView3.setTextColor(Color.parseColor("#a1a3a6"));
				calories = (int) (weight * total_step * step_length * 0.01 * 0.01);
				mRateTextCircularProgressBar.setProgress(calories, Type);
			} else if (Type == 3) {
				mRateTextCircularProgressBar.setProgress(10000, Type);
				if (test != null) {
					tView1.setVisibility(View.INVISIBLE);
					tView2.setVisibility(View.INVISIBLE);
					tView3.setVisibility(View.VISIBLE);
					tView3.setTextColor(Color.parseColor("#6DCAEC"));
					tView3.setText(test);
				} else {
					tView1.setVisibility(View.VISIBLE);
					tView2.setVisibility(View.VISIBLE);
					tView3.setVisibility(View.VISIBLE);
					tView1.setTextColor(Color.parseColor("#6DCAEC"));
					tView2.setTextColor(Color.parseColor("#6DCAEC"));
					tView3.setTextColor(Color.parseColor("#6DCAEC"));
					tView1.setText(weather.getPtime());
					tView2.setText(weather.getTemp1() + " ~ "
							+ weather.getTemp2());
					tView3.setText(weather.getWeather());

				}

			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.pedometer, container, false);

		init();
		mRateTextCircularProgressBar.setProgress(StepDetector.CURRENT_SETP, 0);
		mThread();

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		user = pedometerDB.loadUser(1);
		step = pedometerDB.loadSteps(1, today);
		
		user.setToday_step(total_step);
		pedometerDB.updateUser(user);
		step.setNumber(total_step);
		pedometerDB.updateStep(step);

	}

	@Override
	public void onPause() {
		super.onPause();
		user = pedometerDB.loadUser(1);
		step = pedometerDB.loadSteps(1, today);
		
		user.setToday_step(total_step);
		pedometerDB.updateUser(user);
		step.setNumber(total_step);
		pedometerDB.updateStep(step);
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		pedometerDB = PedometerDB.getInstance(getActivity());
		user = pedometerDB.loadUser(1);
		if (user != null) {
			step_length = user.getStep_length();
			weight = user.getWeight();
			StepDetector.SENSITIVITY = user.getSensitivity();
			StepDetector.CURRENT_SETP = user.getToday_step();
		} else {
			Toast.makeText(getActivity(), "this is my", Toast.LENGTH_SHORT)
					.show();

		}
		Intent intent = new Intent(getActivity(), StepService.class);
		getActivity().startService(intent);

		calendar = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(calendar.getTime());

		weather = new Weather();

		tView1 = (TextView) view.findViewById(R.id.pedometer_1);
		tView2 = (TextView) view.findViewById(R.id.pedometer_2);
		tView3 = (TextView) view.findViewById(R.id.pedometer_3);
		sharekey = (ImageView) view.findViewById(R.id.title_pedometer);
		mRateTextCircularProgressBar = (RateTextCircularProgressBar) view
				.findViewById(R.id.progress_pedometer);
		mRateTextCircularProgressBar.setOnClickListener(this);

		mRateTextCircularProgressBar.setMax(10000);
		mRateTextCircularProgressBar.getCircularProgressBar()
				.setCircleWidth(40);

		ShareSDK.initSDK(getActivity());
		sharekey.setOnClickListener(this);

		// Toast.makeText(getActivity(), today, Toast.LENGTH_SHORT).show();
//		if ((step = pedometerDB.loadSteps(1, today)) != null) {
//			StepDetector.CURRENT_SETP = step.getNumber();
//		} else {
//			step = new Step();
//			step.setNumber(total_step);
//			step.setDate(today);
//			step.setUserId(1);
//			pedometerDB.saveStep(step);
//		}

	}

	private void mThread() {
		if (thread == null) {

			thread = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (StepService.flag) {
							Message msg = new Message();
							handler.sendMessage(msg);
						}
					}
				}
			});
			thread.start();
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void queryFromServer(final String address) {
		new Thread(new Runnable() {

			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					JSONObject jsonObject = new JSONObject(response.toString());
					JSONObject weatherInfo = jsonObject
							.getJSONObject("weatherinfo");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					weather.setPtime(sdf.format(new Date()));
					weather.setTemp1(weatherInfo.getString("temp1"));
					weather.setTemp2(weatherInfo.getString("temp2"));
					weather.setWeather(weatherInfo.getString("weather"));
					test = null;
				} catch (Exception e) {
					test = "同步失败";
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.title_pedometer:
			OnekeyShare oks = new OnekeyShare();
			oks.setNotification(R.drawable.ic_launcher,
					"ShareSDK notification content");
			oks.setText("今天已经走了" + total_step + "步");
			oks.setSilent(false);
			oks.disableSSOWhenAuthorize();
			oks.setDialogMode();
			// 显示
			oks.show(getActivity());
			break;
		case R.id.progress_pedometer:
			if (Type == 1) {

				Type = 2;
			} else if (Type == 2) {
				String address = "http://www.weather.com.cn/data/cityinfo"
						+ "/101010100.html";
				tView3.setText("同步中.....");
				queryFromServer(address);
				Type = 3;
			} else if (Type == 3) {

				Type = 1;
			}
			Message msg = new Message();
			handler.sendMessage(msg);
			break;

		default:
			break;
		}
	}

}
