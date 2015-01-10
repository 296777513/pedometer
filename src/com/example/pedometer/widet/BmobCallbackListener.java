package com.example.pedometer.widet;

import java.util.List;

import com.example.pedometer.model.User;

public interface BmobCallbackListener {

	void onFinish(User user);
	
	void onQuerySuccess(List<User> users);

	void onFailure(String e);

}
