package com.bit.pedometer.widet;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
