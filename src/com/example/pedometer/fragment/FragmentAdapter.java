package com.example.pedometer.fragment;

import java.util.List;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentAdapter implements OnCheckedChangeListener {
	private List<Fragment> fragments;
	private RadioGroup rGroup;
	private FragmentActivity activity;
	private int fgContentId;
	private int currentId;
	private User user;
	private FragmentTransaction fTransaction;

	public FragmentAdapter(FragmentActivity activity, final List<Fragment> fragments,
			final int fgContentId, RadioGroup rGroup, Context context) {
		user = PedometerDB.getInstance(context).loadUser(1);
		this.activity = activity;
		this.fragments = fragments;
		this.rGroup = rGroup;
		this.fgContentId = fgContentId;
		fTransaction = activity.getSupportFragmentManager()
				.beginTransaction();
		if (user != null) {
			fTransaction.add(fgContentId, fragments.get(2));
			fTransaction.commit();
		}else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("提示");
			dialog.setMessage("您还没有注册，需要注册！");
			dialog.setPositiveButton("确认", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					fTransaction.add(fgContentId, fragments.get(4));
					fTransaction.show(fragments.get(4));
					fTransaction.commit();
				}
			});
			dialog.show();
		}
		
		
		rGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		for (int i = 0; i < rGroup.getChildCount(); i++) {
			if (rGroup.getChildAt(i).getId() == arg1) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);
				getCurrentFragment().onPause();
				if (fragment.isAdded()) {
					fragment.onResume();
				} else {
					ft.add(fgContentId, fragment);
				}
				showFragment(i);
				ft.commit();
			}

		}

	}

	private void showFragment(int i) {
		for (int i1 = 0; i1 < fragments.size(); i1++) {
			Fragment fragment = fragments.get(i1);
			FragmentTransaction ft = obtainFragmentTransaction(i1);
			if (i == i1) {
				ft.show(fragment);
			} else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentId = i;
	}

	private Fragment getCurrentFragment() {
		return fragments.get(currentId);
	}

	private FragmentTransaction obtainFragmentTransaction(int i) {
		FragmentTransaction fg = activity.getSupportFragmentManager()
				.beginTransaction();
		return fg;
	}

}
