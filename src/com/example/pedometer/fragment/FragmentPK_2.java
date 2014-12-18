package com.example.pedometer.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.tools.ExpandableListViewAdapter;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.User;
import com.example.pedometer.R;

public class FragmentPK_2 extends Fragment {

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
		return view;
	}

	private void init() {
		listView = (ExpandableListView) view.findViewById(R.id.pk_2_listview);

		userMap = new HashMap<Group, List<User>>();
		pedometerDB = PedometerDB.getInstance(getActivity());
		list = new ArrayList<Group>();
		list = pedometerDB.loadListGroup();
		userList = pedometerDB.lodListUsers();
		prepareData();
		eAdapter = new ExpandableListViewAdapter(getActivity(), list, userMap,
				listView);
		listView.setAdapter(eAdapter);

	}

	private void prepareData() {
		for (int i = 0; i < list.size(); i++) {
			List<User> mUser = new ArrayList<User>();
			for (int j = 0; j < userList.size(); j++) {
				if (userList.get(j).getGroupId() == list.get(i).getID()) {
					mUser.add(userList.get(j));
//					Toast.makeText(getActivity(), mUser.get(0).getName() + "",
//							Toast.LENGTH_SHORT).show();
					userMap.put(list.get(i), mUser);
				}
			}
		}

	}

}
