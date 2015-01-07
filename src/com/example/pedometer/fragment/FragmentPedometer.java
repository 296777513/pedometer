package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.onekeyshare.*;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;
import com.example.pedometer.model.Weather;
import com.example.pedometer.service.StepDetector;
import com.example.pedometer.service.StepService;
import com.example.pedometer.widet.CircleBar;
import com.example.pedometer.widet.HttpCallbackListener;
import com.example.pedometer.widet.HttpUtil;
import com.example.pedometer.R;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentPedometer extends Fragment implements OnClickListener {
	private View view;
	private CircleBar circleBar;
	private int total_step = 0;
	private Thread thread;
	private int Type = 1;
	private int calories = 0;
	private ImageView sharekey;
	private int step_length = 50;
	private int weight = 70;
	private Step step = null;
	private User user = null;
	private Group group = null;
	private Weather weather;
	private PedometerDB pedometerDB;
	private SimpleDateFormat sdf;
	private String today;
	private String test;
	private boolean flag = true;// 来判断第三个页面是否开启动画

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			total_step = StepDetector.CURRENT_SETP;
			if (Type == 1) {
				circleBar.setProgress(total_step, Type);
			} else if (Type == 2) {
				calories = (int) (weight * total_step * step_length * 0.01 * 0.01);
				circleBar.setProgress(calories, Type);
			} else if (Type == 3) {
				if (flag) {
					circleBar.startCustomAnimation();
					flag = false;
				}
				if (test != null || weather.getWeather() == null) {
					weather.setWeather("正在更新中...");
					weather.setPtime("");
					weather.setTemp1("");
					weather.setTemp2("");
					circleBar.setWeather(weather);
				} else {
					circleBar.setWeather(weather);
				}
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.pedometer, container, false);
		init();
		mThread();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		saveDate();
	}

	@Override
	public void onPause() {
		super.onPause();
		saveDate();
	}

	private void saveDate() {
		user = pedometerDB.loadUser(1);
		step = pedometerDB.loadSteps(1, today);
		group = pedometerDB.loadGroup(user.getGroupId());
		user.setToday_step(StepDetector.CURRENT_SETP);
		pedometerDB.updateUser(user);
		group.setTotal_number(group.getTotal_number()
				+ (user.getToday_step() - step.getNumber()));
		pedometerDB.updateGroup(group);
		step.setNumber(StepDetector.CURRENT_SETP);
		pedometerDB.updateStep(step);
		
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {

		sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(new Date());
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

		sharekey = (ImageView) view.findViewById(R.id.title_pedometer);
		weather = new Weather();
		circleBar = (CircleBar) view.findViewById(R.id.progress_pedometer);
		circleBar.setMax(10000);
		circleBar.setProgress(StepDetector.CURRENT_SETP, 1);
		circleBar.startCustomAnimation();
		circleBar.setOnClickListener(this);

		ShareSDK.initSDK(getActivity());
		sharekey.setOnClickListener(this);
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
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(response.toString());
					JSONObject weatherInfo = jsonObject
							.getJSONObject("weatherinfo");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					weather.setPtime(sdf.format(new Date()));
					weather.setTemp1(weatherInfo.getString("temp1"));
					weather.setTemp2(weatherInfo.getString("temp2"));
					weather.setWeather(weatherInfo.getString("weather"));
					test = null;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Exception e) {
				test = "同步失败";
			}
		});
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
				queryFromServer(address);
				flag = true;
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
