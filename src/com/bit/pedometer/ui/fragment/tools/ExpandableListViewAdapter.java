package com.bit.pedometer.ui.fragment.tools;

import java.util.HashMap;
import java.util.List;

import com.bit.pedometer.R;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Group> group;
	private HashMap<Group, List<User>> user;
	private ExpandableListView accordion;
	private int last;
	private ToRoundBitmap toRoundBitmap;

	// private int[] drawables = new int[] { R.drawable.head1, R.drawable.head2,
	// R.drawable.head3, R.drawable.head4, R.drawable.head5,
	// R.drawable.head6, R.drawable.head8, R.drawable.head9,
	// R.drawable.head10 };

	public ExpandableListViewAdapter(Context context, List<Group> group,
			HashMap<Group, List<User>> user, ExpandableListView accordion) {
		this.context = context;
		this.group = group;
		this.user = user;
		this.accordion = accordion;
		toRoundBitmap = ToRoundBitmap.getInstance(context);

	}
	public void changeExpandableList(List<Group> group,
			HashMap<Group, List<User>> user){
		this.group = group;
		this.user = user;
		this.notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		// Log.d("tag1", "in child get ");
		return this.user.get(this.group.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	private class ViewChildHolder {
		TextView nameTextView;
		TextView stepsTextView;
		ImageView imageView;
		Bitmap bitmap;
		
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final User childUser = (User) getChild(groupPosition, childPosition);
		// Log.d("tag1", "Childview Displayed");
		View view;
		ViewChildHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.group_member_list, null);
			viewHolder = new ViewChildHolder();
			viewHolder.nameTextView = (TextView) view
					.findViewById(R.id.group_member_name);
			viewHolder.stepsTextView = (TextView) view
					.findViewById(R.id.group_memeber_number);
			viewHolder.imageView = (ImageView) view
					.findViewById(R.id.group_member_pic);
			viewHolder.bitmap = toRoundBitmap.toRoundBitmap(PictureUtil
					.Byte2Bitmap(childUser.getPicture()));
			view.setTag(viewHolder);
			
		}else {
			view = convertView;
			viewHolder = (ViewChildHolder) view.getTag();
		}
		viewHolder.nameTextView.setText(childUser.getName());
		viewHolder.stepsTextView.setText(childUser.getToday_step() + "");
		viewHolder.imageView.setImageBitmap(viewHolder.bitmap);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		List<User> j = this.user.get(group.get(groupPosition));
		if (j != null) {
			return this.user.get(group.get(groupPosition)).size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		// Log.d("tag1", "打开-----------");
		return this.group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// Log.d("tag1", "in grp count");
		return this.group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// Log.d("tag1", "in grp Id");
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		Group group = (Group) getGroup(groupPosition);
		View view;
		ViewGroupHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(R.layout.group_list,
					null);
			viewHolder = new ViewGroupHolder();
			viewHolder.nameTextView = (TextView) view
					.findViewById(R.id.group_number);

			viewHolder.stepsTextView = (TextView) view
					.findViewById(R.id.average_number);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewGroupHolder) view.getTag();
		}

		viewHolder.nameTextView.setText(group.getID() + "");
		if (group.getMember_number() == 0) {
			viewHolder.stepsTextView.setText("0");
		} else {
			viewHolder.stepsTextView.setText(group.getTotal_number()
					/ group.getMember_number() + "");
		}

		return view;
	}

	private class ViewGroupHolder {
		TextView nameTextView;
		TextView stepsTextView;
	}

	@Override
	public boolean hasStableIds() {
		// Log.d("tag1", "111111111");
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// Log.d("tag1", "22222222222");
		return true;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
		if (groupPosition != last) {
			accordion.collapseGroup(last);
		}

		last = groupPosition;
	}
}