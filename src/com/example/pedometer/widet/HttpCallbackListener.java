package com.example.pedometer.widet;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
