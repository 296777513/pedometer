package com.bit.pedometer.common.utils;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
