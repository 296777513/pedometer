package com.example.pedometer.fragment;

import com.example.pedometer.widet.HistogramView;
import com.example.test6.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FragmentAnalysis extends Fragment implements OnClickListener{
	private HistogramView hv1;
	private HistogramView hv2;
	private HistogramView hv3;
	private HistogramView hv4;
	private HistogramView hv5;
	private HistogramView hv6;
	private HistogramView hv7;
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view  = inflater.inflate(R.layout.analyze, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		
	}
	private void init()
	{
		hv1 = (HistogramView) view.findViewById(R.id.map1);
		hv2 = (HistogramView) view.findViewById(R.id.map2);
		hv3 = (HistogramView) view.findViewById(R.id.map3);
		hv4 = (HistogramView) view.findViewById(R.id.map4);
		hv5 = (HistogramView) view.findViewById(R.id.map5);
		hv6 = (HistogramView) view.findViewById(R.id.map6);
		hv7 = (HistogramView) view.findViewById(R.id.map7);
		hv1.setProgress(0.1);
		hv2.setProgress(0.9);
		hv3.setProgress(0.6);
		hv4.setProgress(0.8);
		hv5.setProgress(0.5);
		hv6.setProgress(0.6);
		hv7.setProgress(0.7);
		hv1.setOnClickListener(this);
		hv2.setOnClickListener(this);
		hv3.setOnClickListener(this);
		hv4.setOnClickListener(this);
		hv5.setOnClickListener(this);
		hv6.setOnClickListener(this);
		hv7.setOnClickListener(this);
		
		
		
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
			break;
		case R.id.map2:
			hv1.setText(false);
			hv2.setText(true);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			break;
		case R.id.map3:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(true);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			break;
		case R.id.map4:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(true);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			break;
		case R.id.map5:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(true);
			hv6.setText(false);
			hv7.setText(false);
			break;
		case R.id.map6:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(true);
			hv7.setText(false);
			break;
		case R.id.map7:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(true);
			break;

		default:
			break;
		}
		
	}

}
