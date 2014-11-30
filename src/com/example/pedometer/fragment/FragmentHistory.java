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
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentHistory extends Fragment implements OnClickListener {
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

	private PedometerDB pedometerDB;
	private Step step;

	private int pNumber = 0;
	private int rNumber = 0;

	private Thread thread;
	private int count;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setNumber();
			setProgressbar();
			setRatio();
		}
	};

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

		iView.setOnClickListener(this);

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		pedometerDB = PedometerDB.getInstance(getActivity());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date1 = sdf.format(calendar.getTime());
		Toast.makeText(getActivity(), date1, Toast.LENGTH_SHORT).show();
		step = pedometerDB.loadSteps(1, date1);
		number.setText(step.getNumber() + "");
		progressBar
				.setProgress((int) ((step.getNumber() / (double) 10000) * progressBar
						.getMax()));
		ratio.setText(((int) ((step.getNumber() / (double) 10000) * 100)) + "%");

	}

	@Override
	public void onClick(View arg0) {
		dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				tView.setText(arg1 + "/" + (arg2 + 1) + "/" + arg3);
				date = arg1 + "" + (arg2 + 1) + "" + arg3;

				queryStep();
			}
		}, year, month, day);
		dPicker = dialog.getDatePicker();
		dPicker.setSpinnersShown(false);
		dPicker.setCalendarViewShown(true);
		dialog.show();

	}

	private void insert() {
		Step step = new Step();
		step.setNumber(1000);
		step.setDate("20141129");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(1234);
		step.setDate("20141128");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(4321);
		step.setDate("20141127");
		step.setUserId(1);
		pedometerDB.saveStep(step);

		step.setNumber(5421);
		step.setDate("20141126");
		step.setUserId(1);
		pedometerDB.saveStep(step);
	}

	/**
	 * 查询选择日期所走的步数
	 */
	private void queryStep() {
		step = pedometerDB.loadSteps(1, date);
		if (step != null) {
			count = 0;
			pNumber = 0;
			rNumber = 0;
			progressBar.setProgress(0);
			number.setText(count + "");
			mThread();
		}
	}

	/**
	 * 设置progressbar
	 */
	private void setProgressbar() {

		double ratio = (step.getNumber() / (double) 10000);

		int progress = (int) (ratio * progressBar.getMax());

		if (pNumber < progress) {
			pNumber++;
			progressBar.incrementProgressBy(1);
		}
	}

	/**
	 * 设置显示的步数number
	 */
	private void setNumber() {
		if (count < step.getNumber()) {
			count += 2;
			number.setText(count + "");
		}
	}

	/**
	 * 设置显示的比率
	 */
	private void setRatio() {
		int ratio = (int) ((step.getNumber() / (double) 10000) * 100);
		if (rNumber <= ratio) {
			this.ratio.setText((rNumber++) + "%");
		}
	}

	private void mThread() {
		if (thread == null) {
			thread = new Thread(new Runnable() {
				public void run() {

					while (true) {
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message msg = new Message();
						handler.sendMessage(msg);
					}
				}
			});
			thread.start();
		}
	}

}
