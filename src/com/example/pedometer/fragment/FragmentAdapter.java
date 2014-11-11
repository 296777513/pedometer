package com.example.pedometer.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentAdapter implements OnCheckedChangeListener {
	private List<Fragment> fragments;
	private RadioGroup rGroup;
	private Activity activity;
	private int fgContentId;
	private int currentId;

	public FragmentAdapter(Activity activity,
			List<Fragment> fragments, int fgContentId, RadioGroup rGroup) {
		this.activity = activity;
		this.fragments = fragments;
		this.rGroup = rGroup;
		this.fgContentId = fgContentId;

		FragmentTransaction fTransaction = activity.getFragmentManager()
				.beginTransaction();
		fTransaction.add(fgContentId, fragments.get(2));
		fTransaction.commit();
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
				}else {
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
			  if(i == i1){
	                ft.show(fragment);
	            }else{
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
		FragmentTransaction fg = activity.getFragmentManager().beginTransaction();
		return fg;
	}

}
