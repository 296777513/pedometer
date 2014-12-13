package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;
import com.example.test6.R;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentPK_1 extends Fragment implements OnItemClickListener,
		OnScrollListener {

	private View view;
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private PedometerDB pedometerDB;
	private User user = null;
	private SimpleDateFormat sdf;
	private List<Step> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk_1, container, false);
		init();
		return view;
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		listView = (ListView) view.findViewById(R.id.pk_1_listview);
		dataList = new ArrayList<Map<String, Object>>();

		pedometerDB = PedometerDB.getInstance(getActivity());
		sdf = new SimpleDateFormat("yyyMMdd");
		list = pedometerDB.loadListSteps(sdf.format(new Date()));
		simpleAdapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.item,
				new String[] { "pic", "name", "steps", "number" }, new int[] {
						R.id.pic, R.id.name, R.id.steps, R.id.number });
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
	}

	private List<Map<String, Object>> getData() {

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", R.drawable.logo_qq);
			map.put("name", list.get(i).getName());
			map.put("steps", list.get(i).getNumber());
			map.put("number", (i + 1) + "");
			dataList.add(map);
		}

		return dataList;
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		switch (scrollState) {
		case SCROLL_STATE_FLING:
			
			
			Log.i("tag", "继续滑动");
			break;
		case SCROLL_STATE_IDLE:
			Log.i("tag", "已经停止滑动");
			
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			list = pedometerDB.loadListSteps(sdf.format(new Date()));
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pic", R.drawable.logo_qq);
				map.put("name", list.get(i).getName());
				map.put("steps", list.get(i).getNumber());
				map.put("number", (i + 1) + "");
				dataList.add(map);
			}
			simpleAdapter.notifyDataSetChanged();
			Log.i("tag", "正在滑动");
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// String str = listView.getItemAtPosition(position) + "";
		// Toast.makeText(getActivity(), "position=" + position + "text: " +
		// str,
		// Toast.LENGTH_SHORT).show();
		user = pedometerDB.loadUser();
		if (user == null) {
			user = new User();
		}

		Toast.makeText(getActivity(), list.get(position).getName() + "",
				Toast.LENGTH_SHORT).show();
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.userinfo, null);
		TextView name = (TextView) view.findViewById(R.id.user_name);
		TextView steps = (TextView) view.findViewById(R.id.user_steps);
		TextView number = (TextView) view.findViewById(R.id.user_number);
		TextView sex = (TextView) view.findViewById(R.id.user_sex);
		TextView weight = (TextView) view.findViewById(R.id.user_weight);
		TextView height = (TextView) view.findViewById(R.id.user_height);
		sex.setText(user.getSex());
		weight.setText(user.getWeight() + "");
		height.setText(user.getHeight() + "");
		steps.setText(list.get(position).getNumber() + "");
		number.setText((position + 1) + "");
		name.setText(list.get(position).getName());
		dialog.setView(view);
		dialog.show();

	}

}
