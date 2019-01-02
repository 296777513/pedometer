package com.bit.pedometer.ui.fragment.PK;

import java.util.List;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.MyListAdapter;
import com.bit.pedometer.ui.fragment.tools.PictureUtil;
import com.bit.pedometer.ui.fragment.tools.ReFlashListView;
import com.bit.pedometer.ui.fragment.tools.ToRoundBitmap;
import com.bit.pedometer.ui.fragment.tools.ReFlashListView.IReflashListener;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.User;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentPK_1 extends Fragment implements OnItemClickListener,
		IReflashListener {

	private View view;
	private ReFlashListView listView;
	private MyListAdapter mAdapter;
	private PedometerDB pedometerDB;
	private List<User> users;
	private ToRoundBitmap toRoundBitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk_1, container, false);
		init();
		showList();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		users = pedometerDB.lodListUsers();
		showList();
	}

	@SuppressLint("SimpleDateFormat")
	private void init() {
		toRoundBitmap = ToRoundBitmap.getInstance(getActivity());
		listView = (ReFlashListView) view.findViewById(R.id.pk_1_listview);
		pedometerDB = PedometerDB.getInstance(getActivity());
		users = pedometerDB.lodListUsers();
		// Toast.makeText(getActivity(), users.get(0).getSex() + "",
		// Toast.LENGTH_LONG).show();
		// listView.setOnScrollListener(this);
	}

	private void showList() {
		if (mAdapter == null) {
			mAdapter = new MyListAdapter(getActivity(), users, listView);
			listView.setAdapter(mAdapter);
		} else {
			mAdapter.changeData(users);
		}
		listView.setInterface(this);
		listView.setOnItemClickListener(this);
	}

	@SuppressLint("InflateParams")
	public void onItemClick(AdapterView<?> arg0, View convrtView, int position,
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

		sex.setText(users.get(pos).getSex());
		Bitmap bitmap = toRoundBitmap.toRoundBitmap(PictureUtil
				.Byte2Bitmap(users.get(pos).getPicture()));
		picture.setImageBitmap(bitmap);

		steps.setText(users.get(pos).getToday_step() + "");
		number.setText((position) + "");

		dialog.setView(view);
		if (users.get(pos).getObjectId().equals(MainActivity.myObjectId)) {
			dialog.setPositiveButton("确认", null);
			name.setText(users.get(pos).getName() + "(自己)");
		} else {
			name.setText(users.get(pos).getName());
			dialog.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							pedometerDB.deleteUser(users.get(pos));
							Group group = pedometerDB.loadGroup(users.get(pos)
									.getGroupId());
							group.setMember_number(group.getMember_number() - 1);
							group.setTotal_number(group.getTotal_number()
									- users.get(pos).getToday_step());
							pedometerDB.updateGroup(group);
						}
					});
			dialog.setPositiveButton("确认", null);
		}
		dialog.setCancelable(false);
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
				users = pedometerDB.lodListUsers();
				// 通知界面显示
				showList();
				// 通知listview 刷新数据完毕；
				listView.reflashComplete();
			}
		}, 2000);
	}

}
