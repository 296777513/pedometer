package com.example.pedometer;

import java.util.ArrayList;
import java.util.List;

import com.example.pedometer.fragment.FragmentAdapter;
import com.example.pedometer.fragment.FragmentAnalysis;
import com.example.pedometer.fragment.FragmentHistory;
import com.example.pedometer.fragment.FragmentPedometer;
import com.example.test6.R;

import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.app.Activity;
import android.app.Fragment;

public class MainActivity extends Activity {
	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_mian);

		rgs = (RadioGroup) findViewById(R.id.radioGroup);
		fragments.add(new FragmentHistory());
		fragments.add(new FragmentAnalysis());
		fragments.add(new FragmentPedometer());
		new FragmentAdapter(this, fragments, R.id.Fragment, rgs);

	}

}
