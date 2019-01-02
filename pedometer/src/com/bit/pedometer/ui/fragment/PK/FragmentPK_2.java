package com.bit.pedometer.ui.fragment.PK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.ExpandableListViewAdapter;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.User;

public class FragmentPK_2 extends Fragment implements OnChildClickListener {

	private View view;
	private ExpandableListView listView;
	private ExpandableListViewAdapter eAdapter;
	private List<Group> list;
	private HashMap<Group, List<User>> userMap;
	private List<User> userList;
	private PedometerDB pedometerDB;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.pk_2, container, false);
		init();
		prepareData();
		showData();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		prepareData();
		showData();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void showData() {
		if (eAdapter == null) {
			eAdapter = new ExpandableListViewAdapter(getActivity(), list,
					userMap, listView);
			listView.setAdapter(eAdapter);
		} else {
			eAdapter.changeExpandableList(list, userMap);
		}
	}

	private void init() {
		listView = (ExpandableListView) view.findViewById(R.id.pk_2_listview);
		userMap = new HashMap<Group, List<User>>();
		pedometerDB = PedometerDB.getInstance(getActivity());
		list = new ArrayList<Group>();
		userList = new ArrayList<User>();
		listView.setOnChildClickListener(this);
	}

	private void prepareData() {
		list = pedometerDB.loadListGroup();
		userList = pedometerDB.lodListUsers();
		for (int i = 0; i < list.size(); i++) {
			List<User> mUser = new ArrayList<User>();
			for (int j = 0; j < userList.size(); j++) {
				if (userList.get(j).getGroupId() == list.get(i).getID()) {
					mUser.add(userList.get(j));
					userMap.put(list.get(i), mUser);
				}
			}
		}

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		User user = userMap.get(list.get(groupPosition)).get(childPosition);
		Intent intent = new Intent(getActivity(), FragmentPK_memberset.class);
		// Toast.makeText(getActivity(), user.getName(),
		// Toast.LENGTH_SHORT).show();
		intent.putExtra("user_data", user);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_bottom_in,
				R.anim.slide_top_out);
		return false;
	}

}
