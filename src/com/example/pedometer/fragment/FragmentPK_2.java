package com.example.pedometer.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.pedometer.db.PedometerDB;
import com.example.test6.R;

public class FragmentPK_2 extends Fragment {

	private View view;
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private PedometerDB pedometerDB;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk_2, container, false);
		init();
		return view;
	}

	private void init() {
		listView = (ListView) view.findViewById(R.layout.pk_2);
		dataList = new ArrayList<Map<String,Object>>();
		
		pedometerDB = PedometerDB.getInstance(getActivity());
		
	}

}
