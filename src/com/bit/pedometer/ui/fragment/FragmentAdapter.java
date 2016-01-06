package com.bit.pedometer.ui.fragment;


import java.util.List;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.data.bean.Group;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 5个fragment切换的适配器
 * 
 * @author 李垭超
 * 
 */
public class FragmentAdapter implements OnCheckedChangeListener {
	private List<Fragment> fragments;// 一个tab页面对应一个Fragment
	private RadioGroup rGroup;// 用于切换tab
	private FragmentActivity activity;// Fragment所属的Activity
	private int fgContentId;// Activity中所要被替换的区域的id
	private int currentId; // 当前Tab页面索引
	private FragmentTransaction fTransaction;// 用于让调用者在切换tab时候增加新的功能
	@SuppressLint("SimpleDateFormat")
	public FragmentAdapter(FragmentActivity activity,
			final List<Fragment> fragments, final int fgContentId,
			RadioGroup rGroup, Context context) {
		PedometerDB pedometerDB = PedometerDB.getInstance(context);
		this.activity = activity;
		this.fragments = fragments;
		this.rGroup = rGroup;
		this.fgContentId = fgContentId;
		fTransaction = activity.getSupportFragmentManager().beginTransaction();
		// 判断是否存在用户，如果存在则显示第三个页面，如果不存在则显示第5个页面
		if (MainActivity.myObjectId != null) {
			fTransaction.add(fgContentId, fragments.get(2));
			fTransaction.commit();
		} else {
			//如果是第一次登陆则自动初始化三个小组，进行PK
			Group group = new Group();
			group.setTotal_number(0);
			group.setMember_number(0);
			pedometerDB.saveGroup(group);

			group.setTotal_number(0);
			group.setMember_number(0);
			pedometerDB.saveGroup(group);

			group.setTotal_number(0);
			group.setMember_number(0);
			pedometerDB.saveGroup(group);

			//进行对话框提示，需要进行注册
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
			dialog.setCancelable(false);
			dialog.show();
		}
		rGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * 改变Tab键，进行更换页面
	 */
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		for (int i = 0; i < rGroup.getChildCount(); i++) {
			if (rGroup.getChildAt(i).getId() == arg1) {
				Fragment fragment = fragments.get(i);
				FragmentTransaction ft = obtainFragmentTransaction(i);
				getCurrentFragment().onPause();// 暂停当前页面
				if (fragment.isAdded()) {
					fragment.onResume();// 启动目标tab的onResume()
				} else {
					ft.add(fgContentId, fragment);
				}
				showFragment(i);// 显示目标tab
				ft.commit();
			}

		}

	}

	/**
	 * 显示切换的页面
	 * @param i
	 */
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
		currentId = i;// 更新目标tab为当前tab
	}

	/**
	 * 得到当前的页面
	 * @return
	 */
	private Fragment getCurrentFragment() {
		return fragments.get(currentId);
	}

	/**
	 * 得到当前页面的事务管理
	 * @param i
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int i) {
		FragmentTransaction fg = activity.getSupportFragmentManager()
				.beginTransaction();
		return fg;
	}

}
