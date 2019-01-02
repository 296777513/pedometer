package com.bit.pedometer.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.data.bean.Step;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
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
	private SimpleDateFormat sdf;

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

		ani = new AllAnimation();
		ani.setDuration(2000);
		iView.setOnClickListener(this);

		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		pedometerDB = PedometerDB.getInstance(getActivity());

		sdf = new SimpleDateFormat("yyyyMMdd");
		date1 = sdf.format(new Date());

		step = pedometerDB.loadSteps(MainActivity.myObjectId, date1);
		if (step == null) {
			step = new Step();
		}
		view.startAnimation(ani);
		number.setText(count + "");
		progressBar.setProgress(progress);
		ratio.setText(ratio1 + "%");

	}

	@Override
	public void onClick(View arg0) {
		dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

				if (arg3 < 10 && arg2 < 10) {
					date = arg1 + "" + "0" + (arg2 + 1) + "0" + arg3;
				} else if (arg3 < 10) {
					date = arg1 + "" + (arg2 + 1) + "0" + arg3;
				} else if (arg2 < 10) {
					date = arg1 + "" + "0" + (arg2 + 1) + arg3;
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
		// 设置对话框背景
		// dPicker.setBackground(view.getResources().getDrawable(R.drawable.back_pressed));
		dPicker.setMaxDate(System.currentTimeMillis());
		// dialog.setTitle("你好");
		dialog.show();

	}

	// 这是用来测试数据的，正常使用的话，可以将这个方法删除。
	private void insert() {
		if (pedometerDB.lodListUsers().size() < 2) {
			int drawables[] = new int[] { R.drawable.head1, R.drawable.head2,
					R.drawable.head3, R.drawable.head4, R.drawable.head5,
					R.drawable.head6, R.drawable.head8, R.drawable.head9,
					R.drawable.head10 };

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Step step = new Step();
			step.setNumber(9000);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			step.setNumber(8754);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			step.setNumber(4213);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			step.setNumber(1234);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			step.setNumber(4523);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			step.setNumber(1342);
			step.setDate(sdf.format(calendar.getTime()));
			step.setUserId(MainActivity.myObjectId);
			pedometerDB.saveStep(step);

//			String temp = sdf.format(new Date());
//			Group group = pedometerDB.loadGroup(1);
//			group.setMember_number(group.getMember_number() + 2);
//			group.setTotal_number(group.getTotal_number() + 9834);
//			pedometerDB.updateGroup(group);
//
//			group = pedometerDB.loadGroup(2);
//			group.setMember_number(group.getMember_number() + 2);
//			group.setTotal_number(group.getTotal_number() + 11234);
//			pedometerDB.updateGroup(group);
//
//			group = pedometerDB.loadGroup(3);
//			group.setMember_number(group.getMember_number() + 3);
//			group.setTotal_number(group.getTotal_number() + 12013);
//			pedometerDB.updateGroup(group);

//			User user = new User();
//			user.setName("潘兆轩");
//			user.setObjectId("1");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[1]))));
//			user.setGroupId(1);
//			user.setSensitivity(5);
//			user.setSex("男");
//			user.setStep_length(35);
//			user.setWeight(63);
//			user.setToday_step(5213);
//			pedometerDB.saveUser(user);
//			step.setNumber(5213);
//			step.setDate(temp);
//			step.setUserId("1");
//			pedometerDB.saveStep(step);
//
//			user.setName("陶冶");
//			user.setObjectId("3");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[0]))));
//			user.setSensitivity(5);
//			user.setSex("女");
//			user.setGroupId(1);
//			user.setToday_step(4321);
//			user.setStep_length(30);
//			user.setWeight(53);
//			pedometerDB.saveUser(user);
//			step.setNumber(4321);
//			step.setDate(temp);
//			step.setUserId("3");
//			pedometerDB.saveStep(step);
//
//			user.setName("李楠");
//			user.setObjectId("4");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[2]))));
//			user.setGroupId(3);
//			user.setSensitivity(5);
//			user.setSex("男");
//			user.setStep_length(35);
//			user.setWeight(63);
//			user.setToday_step(3213);
//			pedometerDB.saveUser(user);
//			step.setNumber(3213);
//			step.setDate(temp);
//			step.setUserId("3");
//			pedometerDB.saveStep(step);
//
//			user.setName("李名扬");
//			user.setObjectId("6");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[3]))));
//			user.setGroupId(2);
//			user.setSensitivity(5);
//			user.setSex("男");
//			user.setStep_length(35);
//			user.setToday_step(6234);
//			user.setWeight(89);
//			pedometerDB.saveUser(user);
//			step.setNumber(6234);
//			step.setDate(temp);
//			step.setUserId("6");
//			pedometerDB.saveStep(step);
//
//			user.setName("李涛");
//			user.setObjectId("7");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[4]))));
//			user.setGroupId(2);
//			user.setSensitivity(5);
//			user.setToday_step(5000);
//			user.setSex("女");
//			user.setStep_length(30);
//			user.setWeight(53);
//			pedometerDB.saveUser(user);
//			step.setNumber(5000);
//			step.setDate(temp);
//			step.setUserId("7");
//			pedometerDB.saveStep(step);
//
//			user.setName("潘大轩");
//			user.setObjectId("8");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[5]))));
//			user.setGroupId(3);
//			user.setSensitivity(5);
//			user.setToday_step(6400);
//			user.setSex("女");
//			user.setStep_length(30);
//			user.setWeight(53);
//			pedometerDB.saveUser(user);
//			step.setNumber(6400);
//			step.setDate(temp);
//			step.setUserId("8");
//			pedometerDB.saveStep(step);
//
//			user.setName("潘小轩");
//			user.setObjectId("9");
//			user.setPicture(PictureUtil.Bitmap2Byte(PictureUtil
//					.drawable2Bitmap(getActivity().getResources().getDrawable(
//							drawables[6]))));
//			user.setGroupId(3);
//			user.setSensitivity(5);
//			user.setToday_step(2400);
//			user.setSex("女");
//			user.setStep_length(30);
//			user.setWeight(53);
//			pedometerDB.saveUser(user);
//			step.setNumber(2400);
//			step.setDate(temp);
//			step.setUserId("9");
//			pedometerDB.saveStep(step);

			// Group group = new Group();
			// group.setAverage_number(9534);
			// group.setMember_number(2);
			// pedometerDB.saveGroup(group);
			//
			// group.setAverage_number(12340);
			// group.setMember_number(2);
			// pedometerDB.saveGroup(group);
			//
			// group.setAverage_number(12013);
			// group.setMember_number(3);
			// pedometerDB.saveGroup(group);
		}
	}

	/**
	 * 查询选择日期所走的步数
	 */
	private void queryStep() {
		step = pedometerDB.loadSteps(MainActivity.myObjectId, date);
		if (step != null) {
			// count = 0;
			// Toast.makeText(getActivity(), step.getNumber() + "---"+date,
			// Toast.LENGTH_SHORT).show();
			progressBar.setProgress(0);
			number.setText(count + "");
			view.startAnimation(ani);
		} else {
			progressBar.setProgress(0);
			number.setText(0 + "");
			ratio.setText("0%");
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
