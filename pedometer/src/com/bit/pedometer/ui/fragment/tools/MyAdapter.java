package com.bit.pedometer.ui.fragment.tools;

import java.util.List;

import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.User;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {
	private List<User> user_list;
	private LayoutInflater inflater;
	private Context context;
	private PedometerDB pedometerDB;
	private User user;
	private int position;
	private ReFlashListView reFlashListView;
	private ToRoundBitmap toRoundBitmap;

	public MyAdapter(Context context, List<User> user_list,
			ReFlashListView reFlashListView) {

		this.user_list = user_list;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.reFlashListView = reFlashListView;
		toRoundBitmap = ToRoundBitmap.getInstance(context);
	}

	public void onDateChange(List<User> user_list) {
		this.user_list = user_list;
		this.notifyDataSetChanged();
	}

	public void setPosition(int position) {
		Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
		this.position = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.user_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return this.user_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		user = user_list.get(position);
		ViewHolder viewHolder;
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.member_add_list, null);
			viewHolder = new ViewHolder();
			viewHolder.picture = (ImageView) view
					.findViewById(R.id.member_add_list_picture);
			viewHolder.name = (TextView) view
					.findViewById(R.id.member_add_list_name);
			viewHolder.steps = (TextView) view
					.findViewById(R.id.member_add_list_steps);
			viewHolder.add = (TextView) view.findViewById(R.id.already_add);
			viewHolder.btn = (Button) view
					.findViewById(R.id.member_add_list_button);
			viewHolder.bitmap = toRoundBitmap.toRoundBitmap(PictureUtil
					.Byte2Bitmap(user.getPicture()));
			;
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.picture.setImageBitmap(viewHolder.bitmap);

		viewHolder.name.setText(user.getName());
		viewHolder.steps.setText(user.getToday_step() + "");

		pedometerDB = PedometerDB.getInstance(context);
		User user1 = pedometerDB.loadUser(user.getObjectId());

		if (user1 != null) {
			viewHolder.add.setVisibility(View.VISIBLE);
			viewHolder.btn.setVisibility(View.GONE);
		} else {
			viewHolder.add.setVisibility(View.GONE);
			viewHolder.btn.setVisibility(View.VISIBLE);
			user = user_list.get(this.position);

			viewHolder.btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					user = user_list.get(position);
					pedometerDB.saveUser(user);
					Group group = pedometerDB.loadGroup(user.getGroupId());
					if (group != null) {
						group.setTotal_number(group.getTotal_number()
								+ user.getToday_step());
						group.setMember_number(group.getMember_number() + 1);
						pedometerDB.updateGroup(group);
						MyAdapter.this.notifyDataSetChanged();
						reFlashListView.invalidate();
					} else {
						Toast.makeText(context, "group is null ",
								Toast.LENGTH_SHORT).show();
					}

				}
			});
		}

		return view;
	}

	private class ViewHolder {
		ImageView picture;
		TextView name;
		TextView steps;
		TextView add;
		Button btn;
		Bitmap bitmap;
	}
}
