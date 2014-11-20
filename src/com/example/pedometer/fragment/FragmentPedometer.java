package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;
import com.example.pedometer.service.StepDetector;
import com.example.pedometer.service.StepService;
import com.example.pedometer.widet.RateTextCircularProgressBar;
import com.example.test6.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentPedometer extends Fragment {
	View view;
	private RateTextCircularProgressBar mRateTextCircularProgressBar;
	private int total_step = 0;
	private Thread thread;
	private int Type = 1;
	private int calories = 0;
	private TextView tView1;
	private TextView tView2;
	private int step_length = 50;
	private int weight = 70;

	private Step step;
	private PedometerDB pedometerDB;

	private Calendar calendar;
	SimpleDateFormat sdf;
	private String today;

	public RateTextCircularProgressBar getmRateTextCircularProgressBar() {
		return mRateTextCircularProgressBar;
	}

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
				mRateTextCircularProgressBar.setProgress(total_step, Type);
			} else if (Type == 2) {
				tView1.setText("卡路里");
				tView2.setText("目标：");
				calories = (int) (weight * total_step * step_length * 0.01 * 0.01);
				mRateTextCircularProgressBar.setProgress(calories, Type);
			}

		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.view = inflater.inflate(R.layout.pedometer, container, false);
		init();
		if ((step = pedometerDB.loadSteps(1, today)) != null) {
			StepDetector.CURRENT_SETP = step.getNumber();
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mThread();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		step.setNumber(total_step);
		step.setDate(today);
		step.setUserId(1);
		pedometerDB.saveStep(step);
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		Intent intent = new Intent(getActivity(), StepService.class);
		getActivity().startService(intent);

		calendar = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(calendar.getTime());

		step = new Step();
		pedometerDB = PedometerDB.getInstance(getActivity());
		tView1 = (TextView) view.findViewById(R.id.pedometer_1);
		tView2 = (TextView) view.findViewById(R.id.pedometer_2);
		mRateTextCircularProgressBar = (RateTextCircularProgressBar) view
				.findViewById(R.id.progress_pedometer);
		mRateTextCircularProgressBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Type == 1) {

					Type = 2;
				} else if (Type == 2) {

					Type = 1;
				}
				Message msg = new Message();
				handler.sendMessage(msg);
			}
		});

		mRateTextCircularProgressBar.setMax(10000);
		mRateTextCircularProgressBar.getCircularProgressBar()
				.setCircleWidth(40);
		// countStep();
	}

	private void mThread() {
		if (thread == null) {

			thread = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(300);
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

}
