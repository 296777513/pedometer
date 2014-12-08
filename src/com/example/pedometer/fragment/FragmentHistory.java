package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;
import com.example.test6.R;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentHistory extends Fragment implements OnClickListener {
	private AllAnimation ani;
	private View view;
	private ImageView iView;
	private TextView tView;
	private TextView number;
	private TextView ratio;
	private ProgressBar progressBar;

	private DatePicker dPicker;
	private DatePickerDialog dialog;

	private Calendar calendar;
	private int year;
	private int month;
	private int day;
	private String date;
	private String date1;

	private PedometerDB pedometerDB;
	private Step step;

	private int count;
	private int progress;
	private int ratio1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.history, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		insert();
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		iView = (ImageView) view.findViewById(R.id.date_image);
		tView = (TextView) view.findViewById(R.id.date_text);
		number = (TextView) view.findViewById(R.id.number);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		ratio = (TextView) view.findViewById(R.id.ratio);
		step = new Step();

		ani = new AllAnimation();
		ani.setDuration(2000);
		iView.setOnClickListener(this);

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		pedometerDB = PedometerDB.getInstance(getActivity());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		date1 = sdf.format(calendar.getTime());

		step = pedometerDB.loadSteps(1, date1);
		view.startAnimation(ani);
		number.setText(count + "");
		progressBar.setProgress(progress);
		ratio.setText(ratio1 + "%");

	}

	@Override
	public void onClick(View arg0) {
		dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				if (arg3 < 10) {
					date = arg1 + "" + (arg2 + 1) + "0" + arg3;
				} else {
					date = arg1 + "" + (arg2 + 1) + "" + arg3;
				}
				if (date.equals(date1)) {
					tView.setText("今天");
				} else {
					tView.setText(arg1 + "/" + (arg2 + 1) + "/" + arg3);
				}
				queryStep();
			}
		}, year, month, day);
		dPicker = dialog.getDatePicker();
		dPicker.setSpinnersShown(false);
		dPicker.setCalendarViewShown(true);
		dPicker.setMaxDate(calendar.getTimeInMillis());

		dialog.show();

	}

	private void insert() {
		Step step = new Step();
		step.setNumber(10000);
		step.setDate("20141204");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(6234);
		step.setDate("20141206");
		step.setUserId(1);
		pedometerDB.saveStep(step);
		step.setNumber(5213);
		step.setDate("20141205");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(3213);
		step.setDate("20141201");
		step.setUserId(1);
		pedometerDB.saveStep(step);
		
		step.setNumber(4321);
		step.setDate("20141202");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(5000);
		step.setDate("20141203");
		step.setUserId(1);
		pedometerDB.saveStep(step);
	}

	/**
	 * 查询选择日期所走的步数
	 */
	private void queryStep() {
		step = pedometerDB.loadSteps(1, date);
		if (step != null) {
			// count = 0;
			// Toast.makeText(getActivity(), step.getNumber() + "---"+date,
			// Toast.LENGTH_SHORT).show();
			progressBar.setProgress(0);
			number.setText(count + "");
			view.startAnimation(ani);

		}
	}

	private class AllAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				count = (int) (step.getNumber() * interpolatedTime);
				progress = (int) ((step.getNumber() / (double) 10000)
						* progressBar.getMax() * interpolatedTime);
				ratio1 = (int) ((step.getNumber() / (double) 10000) * 100 * interpolatedTime);
			} else {
				count = step.getNumber();
				progress = (int) ((step.getNumber() / (double) 10000) * progressBar
						.getMax());
				ratio1 = (int) ((step.getNumber() / (double) 10000) * 100);
			}
			view.postInvalidate();
			progressBar.setProgress(progress);
			number.setText(count + "");
			ratio.setText(ratio1 + "%");
		}
	}

}
