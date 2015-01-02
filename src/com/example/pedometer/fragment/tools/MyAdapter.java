package com.example.pedometer.fragment.tools;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.example.pedometer.R;
import com.example.pedometer.R.id;
import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.User;

import android.R.drawable;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
	private myOnclickListener mListener;
	private ReFlashListView reFlashListView;

	public MyAdapter(Context context, List<User> user_list,
			ReFlashListView reFlashListView) {

		this.user_list = user_list;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.reFlashListView = reFlashListView;

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

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.member_add_list, null);
		}
		ImageView picture = (ImageView) convertView
				.findViewById(R.id.member_add_list_picture);
		TextView name = (TextView) convertView
				.findViewById(R.id.member_add_list_name);
		TextView steps = (TextView) convertView
				.findViewById(R.id.member_add_list_steps);
		TextView add = (TextView) convertView.findViewById(R.id.already_add);
		Button btn = (Button) convertView
				.findViewById(R.id.member_add_list_button);

		Bitmap bitmap;
		if (user.getPicture() != null) {

			try {
				bitmap = ToRoundBitmap
						.toRoundBitmap(BitmapFactory.decodeStream(context
								.getContentResolver().openInputStream(
										Uri.parse(user.getPicture()))));

				picture.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Resources default_picture = context.getResources();
			bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory.decodeResource(
					default_picture, R.drawable.default_picture));
			picture.setImageBitmap(bitmap);

		}

		name.setText(user.getName());
		steps.setText(user.getToday_step() + "");

		pedometerDB = PedometerDB.getInstance(context);
		User user1 = pedometerDB.loadUser(user.getId());

		if (user1 != null) {
			add.setVisibility(View.VISIBLE);
			btn.setVisibility(View.GONE);
		} else {
			add.setVisibility(View.GONE);
			btn.setVisibility(View.VISIBLE);
			user = user_list.get(this.position);

			btn.setOnClickListener(new OnClickListener() {

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

		return convertView;
	}

	public void setMyOnclickListener(myOnclickListener mListener) {
		this.mListener = mListener;
	}

	public interface myOnclickListener {
		public void onclick();
	}

}
