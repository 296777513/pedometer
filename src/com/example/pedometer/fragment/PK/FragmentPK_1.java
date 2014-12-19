package com.example.pedometer.fragment.PK;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pedometer.db.PedometerDB;

import com.example.pedometer.model.User;
import com.example.pedometer.R;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
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
	private List<User> list;

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
		list = pedometerDB.lodListUsers();
		simpleAdapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.member_list, new String[] { "pic", "name", "steps",
						"number" }, new int[] { R.id.pic, R.id.name,
						R.id.steps, R.id.number });
		simpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
	}

	private List<Map<String, Object>> getData() {

		for (int i = 0; i < list.size(); i++) {

			// try {
			Map<String, Object> map = new HashMap<String, Object>();

			user = pedometerDB.loadUser(i + 1);
			if (user.getPicture() != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(user.getPicture());
				map.put("pic", bitmap);
			} else {
				map.put("pic", R.drawable.logo_qq);
			}
			map.put("name", user.getName());
			map.put("steps", user.getToday_step());
			map.put("number", (i + 1) + "");
			dataList.add(map);
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

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
			// dataList.clear();
			// list = pedometerDB.loadListSteps(sdf.format(new Date()));
			// for (int i = 0; i < list.size(); i++) {
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("pic", R.drawable.logo_qq);
			// user = pedometerDB.loadUser(list.get(i).getUserId());
			// map.put("name", user.getName());
			// map.put("steps", list.get(i).getNumber());
			// map.put("number", (i + 1) + "");
			// dataList.add(map);
			// }
			// simpleAdapter.notifyDataSetChanged();
			// Log.i("tag", "正在滑动");
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
		user = pedometerDB.loadUser(position + 1);
		if (user == null) {
			user = new User();
		}

		Toast.makeText(getActivity(), user.getName() + "", Toast.LENGTH_SHORT)
				.show();

		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.userinfo, null);
		TextView name = (TextView) view.findViewById(R.id.user_name);
		TextView steps = (TextView) view.findViewById(R.id.user_steps);
		TextView number = (TextView) view.findViewById(R.id.user_number);
		TextView sex = (TextView) view.findViewById(R.id.user_sex);
		TextView weight = (TextView) view.findViewById(R.id.user_weight);
		ImageView picture = (ImageView) view.findViewById(R.id.user_picture);

		sex.setText(user.getSex());
		weight.setText(user.getWeight() + "");
		// height.setText(user.getPicture() + "");
		// try {
		if (user.getPicture() != null) {
			// Bitmap bitmap = ToRoundBitmap
			// .toRoundBitmap(BitmapFactory.decodeStream(getActivity()
			// .getContentResolver().openInputStream(
			// Uri.parse(user.getPicture()))));
			Bitmap bitmap = BitmapFactory.decodeFile(user.getPicture());
			picture.setImageBitmap(bitmap);
		} else {
			picture.setImageResource(R.drawable.logo_qq);
		}

		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		steps.setText(list.get(position).getToday_step() + "");
		number.setText((position + 1) + "");
		name.setText(user.getName());
		dialog.setView(view);
		dialog.show();
	}

}
