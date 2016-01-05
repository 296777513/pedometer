package com.bit.pedometer.ui.fragment.tools;

import java.util.List;

import com.bit.pedometer.R;
import com.bit.pedometer.data.bean.User;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 这是为多人PK页面写的一个适配器。
 * 
 * @author Administrator
 *
 */
public class MyListAdapter extends BaseAdapter {
	private Context context;
	private List<User> users;
	private ReFlashListView reFlashListView;
	private ToRoundBitmap toRoundBitmap;

	public MyListAdapter(Context context, List<User> users,
			ReFlashListView reFlashListView) {
		this.context = context;
		this.users = users;
		this.reFlashListView = reFlashListView;
		toRoundBitmap = ToRoundBitmap.getInstance(context);
	}

	public void changeData(List<User> users) {
		this.users = users;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public User getItem(int position) {
		// TODO Auto-generated method stub
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		User user = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.member_list,
					null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view.findViewById(R.id.name);
			viewHolder.number = (TextView) view.findViewById(R.id.number);
			viewHolder.steps = (TextView) view.findViewById(R.id.steps);
			viewHolder.picture = (ImageView) view.findViewById(R.id.pic);

			view.setTag(viewHolder);

		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.bitmap = toRoundBitmap.toRoundBitmap(PictureUtil
				.Byte2Bitmap(user.getPicture()));
		viewHolder.name.setText(user.getName());
		viewHolder.steps.setText(user.getToday_step() + "");
		viewHolder.picture.setImageBitmap(viewHolder.bitmap);
		viewHolder.number.setText((position + 1) + "");
		return view;
	}

	private class ViewHolder {
		ImageView picture;
		TextView name;
		TextView steps;
		TextView number;
		Bitmap bitmap;
	}

}
