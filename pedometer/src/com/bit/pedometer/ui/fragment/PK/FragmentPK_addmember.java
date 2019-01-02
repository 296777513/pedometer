package com.bit.pedometer.ui.fragment.PK;

import java.util.List;

import cn.bmob.v3.Bmob;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.MyAdapter;
import com.bit.pedometer.ui.fragment.tools.ReFlashListView;
import com.bit.pedometer.ui.fragment.tools.ReFlashListView.IReflashListener;
import com.bit.pedometer.data.bean.Step;
import com.bit.pedometer.data.bean.User;
import com.bit.pedometer.common.utils.BmobUtil;
import com.bit.pedometer.common.utils.BmobUtil.BmobQueryListener;
import com.bit.pedometer.common.utils.BmobUtil.BmobSaveAndUpdataListener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentPK_addmember extends FragmentActivity implements
		OnClickListener, IReflashListener {
	private ImageView back;
	private ReFlashListView memberList;
	private MyAdapter myAdapter;
	private User user;
	private List<Step> steps;
	private PedometerDB pedometerdb;
	private ProgressDialog progressDialog;
	private boolean flag = false;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.member_add);
		init();
		queryAll();
	}

	private void init() {
		Bmob.initialize(this, "c153449e638703134b8fe75c52210bc7");
		back = (ImageView) findViewById(R.id.member_add_back);
		memberList = (ReFlashListView) findViewById(R.id.member_add_list);
		pedometerdb = PedometerDB.getInstance(this);
		user = pedometerdb.loadFirstUser();
		steps = pedometerdb.loadListSteps();
		showProgressDialog();

		BmobUtil.saveBmob(user, new BmobSaveAndUpdataListener() {
			@Override
			public void onFinishedupdata(User user) {
				Toast.makeText(FragmentPK_addmember.this, "向服务器更新自己信息",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFinishedSave(User user) {

				Toast.makeText(FragmentPK_addmember.this, "成功连接服务器",
						Toast.LENGTH_SHORT).show();
				pedometerdb.changeObjectId(user);
				MainActivity.myObjectId = user.getObjectId();
				for (int i = 0; i < steps.size(); i++) {
					Step step = steps.get(i);
					step.setUserId(user.getObjectId());
					pedometerdb.changeuserId(step);
				}

			}

			@Override
			public void onFailure(String str) {
				Toast.makeText(FragmentPK_addmember.this, str,
						Toast.LENGTH_SHORT).show();

			}
		}, this);
		back.setOnClickListener(this);
		memberList.setInterface(this);

	}

	private void queryAll() {
		BmobUtil.queryBmob(new BmobQueryListener() {

			@Override
			public void onQuerySuccess(List<User> users) {
				// Toast.makeText(FragmentPK_addmember.this,
				// user_list.get(0).getName() + "", Toast.LENGTH_SHORT)
				// .show();
				myAdapter = new MyAdapter(FragmentPK_addmember.this, users,
						memberList);
				memberList.setAdapter(myAdapter);
				closeProgressDialog();
				if (flag) {
					memberList.reflashComplete();
					flag = false;
				}

			}

			@Override
			public void onFailure(String str) {
				Toast.makeText(FragmentPK_addmember.this, "连接不上服务器",
						Toast.LENGTH_SHORT).show();
				closeProgressDialog();
				FragmentPK_addmember.this.finish();

			}
		}, this);

	}

	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.member_add_back:
			this.finish();
			break;
		case R.id.member_add_list_button:

			break;
		default:
			break;
		}
	}

	@Override
	public void onReflash() {

		queryAll();
		flag = true;

	}

}
