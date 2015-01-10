package com.example.pedometer.widet;

import java.util.List;

import android.content.Context;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.pedometer.fragment.PK.FragmentPK_addmember;
import com.example.pedometer.fragment.tools.MyAdapter;
import com.example.pedometer.model.User;

public class BmobUtil {

	public static void saveBmob(final User user,
			final BmobCallbackListener listener, final Context context) {

		new Thread(new Runnable() {
			public void run() {
				Bmob.initialize(context, "c153449e638703134b8fe75c52210bc7");
				if (user.getObjectId().equals("1")) {
					user.setObjectId(null);
					user.save(context, new SaveListener() {

						@Override
						public void onSuccess() {
							listener.onFinish(user);
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							listener.onFailure(arg1);
						}
					});

				} else {
					// Toast.makeText(this, "-----" + user.getObjectId(),
					// Toast.LENGTH_LONG).show();
					user.update(context);
				}

			}
		}).start();
	}

	public static void queryBmob(final BmobCallbackListener listener,
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
}
