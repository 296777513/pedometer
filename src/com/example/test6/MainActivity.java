package com.example.test6;

import java.util.ArrayList;
import java.util.List;

import com.example.test6.fragment.FragmentAdapter;
import com.example.test6.fragment.FragmentPedometer;
import com.example.test6.step.StepService;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

public class MainActivity extends Activity {
	private RadioGroup rgs;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_mian);
		Intent intent = new Intent(this, StepService.class);
		startService(intent);
		rgs = (RadioGroup) findViewById(R.id.radioGroup);
		fragments.add(new FragmentPedometer());
		new FragmentAdapter(this, fragments, R.id.Fragment, rgs);

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

}
