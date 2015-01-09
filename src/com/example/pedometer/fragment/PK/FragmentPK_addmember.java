package com.example.pedometer.fragment.PK;

import java.util.List;
import java.util.zip.Inflater;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.pedometer.MainActivity;
import com.example.pedometer.R;
import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.tools.MyAdapter;
import com.example.pedometer.fragment.tools.ReFlashListView;
import com.example.pedometer.fragment.tools.ReFlashListView.IReflashListener;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;

import android.R.animator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentPK_addmember extends FragmentActivity implements
		OnClickListener, IReflashListener, OnItemClickListener {
	private ImageView back;
	private ReFlashListView memberList;
	private MyAdapter myAdapter;
	private User user;
	private List<Step> steps;
	private PedometerDB pedometerdb;
	private ProgressDialog progressDialog;

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
		user = pedometerdb.lodListUsers().get(0);
		steps = pedometerdb.loadListSteps();
		showProgressDialog();
		if (user.getObjectId().equals("1")) {

			user.setObjectId(null);
			user.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					pedometerdb.changeObjectId(user);
					MainActivity.myObjectId = user.getObjectId();
					for (int i = 0; i < steps.size(); i++) {
						Step step = steps.get(i);
						step.setUserId(user.getObjectId());
						pedometerdb.changeuserId(step);
					}
				}

				@Override
				public void onFailure(int arg0, String arg1) {

				}
			});
			Toast.makeText(FragmentPK_addmember.this, user.getObjectId(),
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(this, "-----" + user.getObjectId(),
					Toast.LENGTH_LONG).show();
			user.update(this);
		}

		back.setOnClickListener(this);
		memberList.setInterface(this);

	}

	private void queryAll() {
		BmobQuery<User> query = new BmobQuery<User>();
		query.findObjects(FragmentPK_addmember.this, new FindListener<User>() {

			@Override
			public void onSuccess(List<User> user_list) {
				// Toast.makeText(FragmentPK_addmember.this,
				// user_list.get(0).getName() + "", Toast.LENGTH_SHORT)
				// .show();
				memberList.setOnItemClickListener(FragmentPK_addmember.this);
				myAdapter = new MyAdapter(FragmentPK_addmember.this, user_list,
						memberList);
				memberList.setAdapter(myAdapter);
				closeProgressDialog();

			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(FragmentPK_addmember.this, "连接不上服务器",
						Toast.LENGTH_SHORT).show();

			}
		});

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
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				queryAll();
				memberList.reflashComplete();
			}
		}, 2000);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		LayoutInflater inflater = LayoutInflater
				.from(FragmentPK_addmember.this);
		View convertView = inflater.inflate(R.layout.member_add_list, null);
		Button btn = (Button) convertView
				.findViewById(R.id.member_add_list_button);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

	}

}
