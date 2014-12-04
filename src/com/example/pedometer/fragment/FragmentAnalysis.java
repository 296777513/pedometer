package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;
import com.example.pedometer.widet.HistogramView;
import com.example.test6.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentAnalysis extends Fragment implements OnClickListener {
	private HistogramView hv1;
	private HistogramView hv2;
	private HistogramView hv3;
	private HistogramView hv4;
	private HistogramView hv5;
	private HistogramView hv6;
	private HistogramView hv7;

	private PedometerDB pedometerDB;

	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView day5;
	private TextView day6;
	private TextView day7;

	private TextView average_step;
	private TextView sum_step;

	private Step step;

	private int average = 0;
	private int sum = 0;

	private Calendar calendar;
	private String day;

	private View view;

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
		calculate();

	}

	private void init() {
		average_step = (TextView) view.findViewById(R.id.average_step);
		sum_step = (TextView) view.findViewById(R.id.sum_step);

		calendar = Calendar.getInstance();

		hv1 = (HistogramView) view.findViewById(R.id.map1);
		hv2 = (HistogramView) view.findViewById(R.id.map2);
		hv3 = (HistogramView) view.findViewById(R.id.map3);
		hv4 = (HistogramView) view.findViewById(R.id.map4);
		hv5 = (HistogramView) view.findViewById(R.id.map5);
		hv6 = (HistogramView) view.findViewById(R.id.map6);
		hv7 = (HistogramView) view.findViewById(R.id.map7);

		pedometerDB = PedometerDB.getInstance(getActivity());

		hv1.setOnClickListener(this);
		hv2.setOnClickListener(this);
		hv3.setOnClickListener(this);
		hv4.setOnClickListener(this);
		hv5.setOnClickListener(this);
		hv6.setOnClickListener(this);
		hv7.setOnClickListener(this);

		day1 = (TextView) view.findViewById(R.id.Monday);
		day2 = (TextView) view.findViewById(R.id.Tuesday);
		day3 = (TextView) view.findViewById(R.id.Wednesday);
		day4 = (TextView) view.findViewById(R.id.Thursday);
		day5 = (TextView) view.findViewById(R.id.Friday);
		day6 = (TextView) view.findViewById(R.id.Saturday);
		day7 = (TextView) view.findViewById(R.id.Sunday);

	}

	private void calculate() {
		sum_step.setText(sum + "");
		average = sum / 7;
		average_step.setText(average + "");
	}

	@SuppressLint("SimpleDateFormat")
	private void setProgress() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		day = sdf.format(calendar.getTime());
		Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		step = pedometerDB.loadSteps(1, day);
		hv1.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv2.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();
		// Toast.makeText(getActivity(), day+"", Toast.LENGTH_LONG).show();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv3.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv4.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv5.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv6.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		hv7.setProgress((step.getNumber() / 10000.0));
		sum += step.getNumber();

	}

	private void setWeek() {

		int day = calendar.get(Calendar.DAY_OF_WEEK);
		Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		day -= 1; 
		day1.setText(week(day));
		day2.setText(week(day - 1));
		day3.setText(week(day - 2));
		day4.setText(week(day - 3));
		day5.setText(week(day - 4));
		day6.setText(week(day - 5));
		day7.setText(week(day - 6));
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.map1:
			hv1.setText(true);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map2:
			hv1.setText(false);
			hv2.setText(true);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map3:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(true);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map4:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(true);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map5:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(true);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map6:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(true);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map7:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(true);
			view.invalidate();
			break;

		default:
			break;
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

}
