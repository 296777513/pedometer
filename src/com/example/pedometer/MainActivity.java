package com.example.pedometer;

import java.util.ArrayList;
import java.util.List;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.FragmentAdapter;
import com.example.pedometer.fragment.FragmentAnalysis;
import com.example.pedometer.fragment.FragmentHistory;
import com.example.pedometer.fragment.FragmentPK;
import com.example.pedometer.fragment.FragmentPedometer;
import com.example.pedometer.fragment.FragmentSet;
import com.example.pedometer.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.Fragment;

public class MainActivity extends FragmentActivity {
	private RadioGroup rgs;
	private RadioButton btn1;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_mian);
		btn1 = (RadioButton) findViewById(R.id.btn5);
		if (PedometerDB.getInstance(this).loadUser(1) == null) {
			btn1.setChecked(true);
		}
		rgs = (RadioGroup) findViewById(R.id.radioGroup);
		fragments.add(new FragmentHistory());
		fragments.add(new FragmentAnalysis());
		fragments.add(new FragmentPedometer());
		fragments.add(new FragmentPK());
		fragments.add(new FragmentSet());
		new FragmentAdapter(MainActivity.this, fragments, R.id.Fragment, rgs,
				this);

	}

}
