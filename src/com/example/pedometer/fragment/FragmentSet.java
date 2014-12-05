package com.example.pedometer.fragment;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.User;
import com.example.test6.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class FragmentSet extends Fragment implements OnClickListener {
	private View view;
//	private LinearLayout sexLayout;
	private LinearLayout birthdayLayout;
	private LinearLayout weightLayout;
	private LinearLayout heightLayout;
	private LinearLayout sensitivyLayout;
	private LinearLayout lengthLayout;

	private TextView birthdayText;
	private TextView weightText;
	private TextView heightText;
	private TextView sensitivyText;
	private TextView lengthText;

	private AlertDialog.Builder dialog;
	private NumberPicker numberPicker;

	private PedometerDB pedometerDB;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.set, container, false);
		init();
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		user.setName("李垭超");
		user.setSex("男");
		pedometerDB.saveUser(user);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		user.setName("李垭超");
		user.setSex("男");
		pedometerDB.saveUser(user);
	}

	private void init() {
//		sexLayout = (LinearLayout) view.findViewById(R.id.sex);
		birthdayLayout = (LinearLayout) view.findViewById(R.id.birthday);
		weightLayout = (LinearLayout) view.findViewById(R.id.weight);
		heightLayout = (LinearLayout) view.findViewById(R.id.height);
		sensitivyLayout = (LinearLayout) view.findViewById(R.id.sensitivy);
		lengthLayout = (LinearLayout) view.findViewById(R.id.lengh_step);

		birthdayText = (TextView) view.findViewById(R.id.birthday_);
		weightText = (TextView) view.findViewById(R.id.weight_);
		heightText = (TextView) view.findViewById(R.id.height_);
		sensitivyText = (TextView) view.findViewById(R.id.sensitivy_);
		lengthText = (TextView) view.findViewById(R.id.lengh_step_);

		pedometerDB = PedometerDB.getInstance(getActivity());
		user = new User();

		birthdayLayout.setOnClickListener(this);
		weightLayout.setOnClickListener(this);
		heightLayout.setOnClickListener(this);
		sensitivyLayout.setOnClickListener(this);
		lengthLayout.setOnClickListener(this);
		
		user = pedometerDB.loadUser("李垭超");
		if (user.getWeight() != 0 && user.getStep_length() != 0
				&& user.getSensitivity() != 0) {
			setSensitivity(user.getSensitivity());
			weightText.setText(String.valueOf(user.getWeight()));
			heightText.setText(String.valueOf(user.getHeight()));
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.birthday:
			dialog = new AlertDialog.Builder(getActivity());
			numberPicker = new NumberPicker(getActivity());
			numberPicker.setFocusable(true);
			numberPicker.setFocusableInTouchMode(true);
			numberPicker.setMaxValue(2010);
			numberPicker.setValue(Integer.parseInt(birthdayText.getText()
					.toString()));
			numberPicker.setMinValue(1960);
			dialog.setView(numberPicker);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							birthdayText.setText(numberPicker.getValue() + "");
							user.setBirthday(numberPicker.getValue());
						}
					});
			dialog.show();

			break;
		case R.id.weight:
			dialog = new AlertDialog.Builder(getActivity());
			numberPicker = new NumberPicker(getActivity());
			numberPicker.setFocusable(true);
			numberPicker.setFocusableInTouchMode(true);
			numberPicker.setMaxValue(200);
			numberPicker.setValue(Integer.parseInt(weightText.getText()
					.toString()));
			numberPicker.setMinValue(30);
			dialog.setView(numberPicker);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							weightText.setText(numberPicker.getValue() + "");
							user.setWeight(numberPicker.getValue());
						}
					});
			dialog.show();
			break;
		case R.id.height:
			dialog = new AlertDialog.Builder(getActivity());
			numberPicker = new NumberPicker(getActivity());
			numberPicker.setFocusable(true);
			numberPicker.setFocusableInTouchMode(true);
			numberPicker.setMaxValue(200);
			numberPicker.setValue(Integer.parseInt(heightText.getText()
					.toString()));
			numberPicker.setMinValue(100);
			dialog.setView(numberPicker);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							heightText.setText(numberPicker.getValue() + "");
							user.setHeight(numberPicker.getValue());
						}
					});
			dialog.show();
			break;
		case R.id.sensitivy:
			dialog = new AlertDialog.Builder(getActivity());
			numberPicker = new NumberPicker(getActivity());
			numberPicker.setFocusable(true);
			numberPicker.setFocusableInTouchMode(true);
			numberPicker.setMaxValue(10);
			numberPicker.setMinValue(1);
			dialog.setView(numberPicker);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							user.setSensitivity(numberPicker.getValue());
							setSensitivity(numberPicker.getValue());

						}
					});
			dialog.show();
			break;
		case R.id.lengh_step:
			dialog = new AlertDialog.Builder(getActivity());
			numberPicker = new NumberPicker(getActivity());
			numberPicker.setFocusable(true);
			numberPicker.setFocusableInTouchMode(true);
			numberPicker.setMaxValue(100);
			numberPicker.setValue(Integer.parseInt(lengthText.getText()
					.toString()));
			numberPicker.setMinValue(15);
			dialog.setView(numberPicker);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							lengthText.setText(numberPicker.getValue() + "");
							user.setStep_length(numberPicker.getValue());
						}
					});
			dialog.show();
			break;

		default:
			break;
		}

	}
	private void setSensitivity(int value){
		switch (value) {
		case 1:
			sensitivyText.setText("一级");
			break;
		case 2:
			sensitivyText.setText("二级");
			break;
		case 3:
			sensitivyText.setText("三级");
			break;
		case 4:
			sensitivyText.setText("四级");
			break;
		case 5:
			sensitivyText.setText("五级");
			break;
		case 6:
			sensitivyText.setText("六级");
			break;
		case 7:
			sensitivyText.setText("七级");
			break;
		case 8:
			sensitivyText.setText("八级");
			break;
		case 9:
			sensitivyText.setText("九级");
			break;
		case 10:
			sensitivyText.setText("十级");
			break;

		default:
			break;
		}
	}

}
