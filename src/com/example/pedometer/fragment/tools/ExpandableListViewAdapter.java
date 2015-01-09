package com.example.pedometer.fragment.tools;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.pedometer.R;
import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Group> group;
	private HashMap<Group, List<User>> user;
	private ExpandableListView accordion;
	private int last;
	private int[] drawables = new int[] { R.drawable.head1, R.drawable.head2,
			R.drawable.head3, R.drawable.head4, R.drawable.head5,
			R.drawable.head6, R.drawable.head8, R.drawable.head9,
			R.drawable.head10 };

	public ExpandableListViewAdapter(Context context, List<Group> group,
			HashMap<Group, List<User>> user, ExpandableListView accordion) {
		this.context = context;
		this.group = group;
		this.user = user;
		this.accordion = accordion;

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

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final User childUser = (User) getChild(groupPosition, childPosition);
		// Log.d("tag1", "Childview Displayed");
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_member_list,
					null);
		}

		PedometerDB pd = PedometerDB.getInstance(context);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Step step = pd.loadSteps(childUser.getObjectId(), sdf.format(new Date()));
		TextView nameTextView = (TextView) convertView
				.findViewById(R.id.group_member_name);
		TextView stepsTextView = (TextView) convertView
				.findViewById(R.id.group_memeber_number);
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.group_member_pic);

		nameTextView.setText(childUser.getName());
		stepsTextView.setText(childUser.getToday_step() + "");
		Bitmap bitmap = ToRoundBitmap.toRoundBitmap(PictureUtil
				.Byte2Bitmap(childUser.getPicture()));
		imageView.setImageBitmap(bitmap);
//		if (childUser.getPicture() != null) {
//			
//			try {
//				bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory.decodeStream(context
//						.getContentResolver().openInputStream(
//								Uri.parse(childUser.getPicture()))));
//				imageView.setImageBitmap(bitmap);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			Resources default_picture = context.getResources();
//			bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory.decodeResource(
//					default_picture, drawables[childPosition]));
//			imageView.setImageBitmap(bitmap);
//		}
		// TextView txtListChild = (TextView) convertView
		// .findViewById(R.id.lblListItem);
		//
		// txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// Log.d("tag1",
		// "childview called"
		// + this.user.get(
		// this.group.get(groupPosition)).size());
		List<User> j = this.user.get(group.get(groupPosition));
		if (j != null ) {
			return this.user.get(group.get(groupPosition)).size();
		}else {
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
		Group headerTitle = (Group) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_list, null);
		}

		TextView nameTextView = (TextView) convertView
				.findViewById(R.id.group_number);

		TextView stepsTextView = (TextView) convertView
				.findViewById(R.id.average_number);

		nameTextView.setText(headerTitle.getID() + "");
		if (headerTitle.getMember_number() == 0) {
			stepsTextView.setText("0");
		}else {
			stepsTextView.setText(headerTitle.getTotal_number()
					/ headerTitle.getMember_number() + "");
		}
		

		return convertView;
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

		if (groupPosition != last) {
			accordion.collapseGroup(last);
		}

		super.onGroupExpanded(groupPosition);

		last = groupPosition;
	}
}