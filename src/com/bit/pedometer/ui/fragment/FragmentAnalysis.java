package com.bit.pedometer.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.DisplayUtil;
import com.bit.pedometer.data.bean.Step;
import com.bit.pedometer.ui.view.HistogramView;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentAnalysis extends Fragment implements OnTouchListener {
	private HistogramView hv;

	private PedometerDB pedometerDB;

	private String[] weeks;// 设置星期数目
	private int[] steps;// 设置7天的步数
	private int[] text;// 设置是否显示对应柱状图的数值

	private TextView average_step;
	private TextView sum_step;

	private Step step = null;

	private int average = 0;
	private int sum = 0;
	private int average1 = 0;
	private int sum1 = 0;

	private Calendar calendar;
	private String day;

	private View view;

	private AllAnimation ani;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.analyze, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		setWeek();
		setProgress();
		view.startAnimation(ani);

	}

	@SuppressLint("ClickableViewAccessibility")
	private void init() {
		weeks = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		steps = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		average_step = (TextView) view.findViewById(R.id.average_step);
		sum_step = (TextView) view.findViewById(R.id.sum_step);
		ani = new AllAnimation();
		ani.setDuration(2000);

		calendar = Calendar.getInstance();

		hv = (HistogramView) view.findViewById(R.id.histograms);

		pedometerDB = PedometerDB.getInstance(getActivity());

		hv.setOnTouchListener(this);

	}

	@SuppressLint("SimpleDateFormat")
	private void setProgress() {
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		day = sdf.format(calendar.getTime());
		// Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}
		// Toast.makeText(getActivity(), day+"", Toast.LENGTH_LONG).show();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(MainActivity.myObjectId, day);
		if (step != null) {
			steps[i++] = step.getNumber();
			sum += step.getNumber();
		} else {
			steps[i++] = 0;
			sum += 0;
		}

		hv.setWeekd(weeks);
		hv.setProgress(steps);

	}

	private void setWeek() {
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		// Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		day -= 1;
		for (int i = 0; i < weeks.length; i++) {
			weeks[i] = week(day - i);
		}
	}

	private String week(int day) {
		if (day < 1) {
			day += 7;
		}
		switch (day) {
		case 1:
			return "周一";
		case 2:
			return "周二";
		case 3:
			return "周三";
		case 4:
			return "周四";
		case 5:
			return "周五";
		case 6:
			return "周六";
		case 7:
			return "周日";
		default:
			return "";
		}
	}

	private class AllAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				sum1 = (int) (sum * interpolatedTime);
				average1 = (int) (average * interpolatedTime);
			} else {
				sum1 = sum;
				average1 = average;
			}
			view.postInvalidate();
			sum_step.setText(sum1 + "");
			average = sum / 7;
			average_step.setText(average1 + "");

		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int step = (v.getWidth() - dp2px(30)) / 8;
		int x = (int) event.getX();
		for (int i = 0; i < 7; i++) {
			if (x > (dp2px(15) + step * (i + 1) - dp2px(15))
					&& x < (dp2px(15) + step * (i + 1) + dp2px(15))) {
				text[i] = 1;
				for (int j = 0; j < 7; j++) {
					if (i != j) {
						text[j] = 0;
					}
				}
				hv.setText(text);
			}
		}

		return false;
	}

	private int dp2px(int value) {
		return DisplayUtil.dip2px(getActivity(), value);
	}

}
