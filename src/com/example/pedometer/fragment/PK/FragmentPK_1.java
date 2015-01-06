package com.example.pedometer.fragment.PK;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.tools.ReFlashListView;
import com.example.pedometer.fragment.tools.ToRoundBitmap;
import com.example.pedometer.fragment.tools.ReFlashListView.IReflashListener;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.User;
import com.example.pedometer.R;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentPK_1 extends Fragment implements OnItemClickListener,
		IReflashListener {

	private View view;
	private ReFlashListView listView;
	private SimpleAdapter simpleAdapter = null;
	private List<Map<String, Object>> dataList;
	private PedometerDB pedometerDB;
	private List<User> list;
	private int[] drawables;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk_1, container, false);
		init();
		setData();
		showList();
		return view;
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		listView = (ReFlashListView) view.findViewById(R.id.pk_1_listview);
		dataList = new ArrayList<Map<String, Object>>();
		pedometerDB = PedometerDB.getInstance(getActivity());
		drawables = new int[] { R.drawable.head1, R.drawable.head2,
				R.drawable.head3, R.drawable.head4, R.drawable.head5,
				R.drawable.head6, R.drawable.head8, R.drawable.head9,
				R.drawable.head10 };


		// listView.setOnScrollListener(this);
	}

	private void showList() {
		if (simpleAdapter == null) {
			simpleAdapter = new SimpleAdapter(getActivity(), dataList,
					R.layout.member_list, new String[] { "pic", "name",
							"steps", "number" }, new int[] { R.id.pic,
							R.id.name, R.id.steps, R.id.number });
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
		} else {
			simpleAdapter.notifyDataSetChanged();
		}
		listView.setInterface(this);
		listView.setOnItemClickListener(this);
	}

	private List<Map<String, Object>> setData() {
		if (list != null && dataList != null) {
			list.clear();
			dataList.clear();
		}

		list = pedometerDB.lodListUsers();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Bitmap bitmap;
			if (list.get(i).getPicture() != null) {

				try {
					bitmap = ToRoundBitmap
							.toRoundBitmap(BitmapFactory
									.decodeStream(getActivity()
											.getContentResolver()
											.openInputStream(
													Uri.parse(list.get(i)
															.getPicture()))));

					map.put("pic", bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory
						.decodeResource(getActivity().getResources(),
								drawables[i]));

				map.put("pic", bitmap);
			}
			map.put("name", list.get(i).getName());
			map.put("steps", list.get(i).getToday_step());
			map.put("number", (i + 1) + "");
			dataList.add(map);
		}

		return dataList;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		final int pos = position - 1;

		final AlertDialog.Builder dialog = new AlertDialog.Builder(
				getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.userinfo, null);
		TextView name = (TextView) view.findViewById(R.id.user_name);
		TextView steps = (TextView) view.findViewById(R.id.user_steps);
		TextView number = (TextView) view.findViewById(R.id.user_number);
		TextView sex = (TextView) view.findViewById(R.id.user_sex);
		ImageView picture = (ImageView) view.findViewById(R.id.user_picture);

		sex.setText(list.get(pos).getSex());
		Bitmap bitmap;
		if (list.get(pos).getPicture() != null) {

			try {
				bitmap = ToRoundBitmap
						.toRoundBitmap(BitmapFactory.decodeStream(getActivity()
								.getContentResolver().openInputStream(
										Uri.parse(list.get(pos).getPicture()))));
				picture.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Resources default_picture = getActivity().getResources();
			bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory.decodeResource(
					default_picture, drawables[pos]));
			picture.setImageBitmap(bitmap);
		}

		steps.setText(list.get(pos).getToday_step() + "");
		number.setText((position + 1) + "");

		dialog.setView(view);
		if (list.get(pos).getId() == 1) {
			dialog.setPositiveButton("确认", null);
			name.setText(list.get(pos).getName() + "(自己)");
		} else {
			name.setText(list.get(pos).getName());
			dialog.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							pedometerDB.deleteUser(list.get(pos));
						}
					});
			dialog.setPositiveButton("确认", null);
		}

		dialog.show();
	}

	@Override
	public void onReflash() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 获取最新数据
				setData();
				// 通知界面显示
				showList();
				// 通知listview 刷新数据完毕；
				listView.reflashComplete();
			}
		}, 2000);
	}

}
