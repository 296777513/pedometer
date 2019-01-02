package com.bit.pedometer.common.utils;

import java.util.List;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bit.pedometer.data.bean.User;

public class BmobUtil {

	public static void saveBmob(final User user,
			final BmobSaveAndUpdataListener listener, final Context context) {

		new Thread(new Runnable() {
			public void run() {
				Bmob.initialize(context, "c153449e638703134b8fe75c52210bc7");
				if (user.getObjectId().equals("1")) {
					user.setObjectId(null);
//					Toast.makeText(context, "我正在存储", Toast.LENGTH_LONG).show();
					user.save(context, new SaveListener() {

						@Override
						public void onSuccess() {
							listener.onFinishedSave(user);
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							listener.onFailure(arg1);
						}
					});

				} else {
//					Toast.makeText(context, "我在更新", Toast.LENGTH_LONG).show();
					user.update(context,new UpdateListener() {
						
						public void onSuccess() {
							listener.onFinishedupdata(user);
						}
						
						public void onFailure(int arg0, String arg1) {
							listener.onFailure(arg1);
							
						}
					});
				}

			}
		}).start();
	}

	public static void queryBmob(final BmobQueryListener listener,
			final Context context) {

		new Thread(new Runnable() {
			public void run() {
				Bmob.initialize(context, "c153449e638703134b8fe75c52210bc7");
				BmobQuery<User> query = new BmobQuery<User>();
				query.findObjects(context, new FindListener<User>() {

					@Override
					public void onSuccess(List<User> user_list) {
						listener.onQuerySuccess(user_list);

					}

					@Override
					public void onError(int arg0, String arg1) {
						listener.onFailure(arg1);

					}
				});

			}
		}).start();

	}
	
	public interface BmobSaveAndUpdataListener{
		void onFinishedSave(User user);
		void onFailure(String str);
		void onFinishedupdata(User user);
		
	}
	
	public interface BmobQueryListener{
		void onQuerySuccess(List<User> users);
		void onFailure(String str);
	}
}
