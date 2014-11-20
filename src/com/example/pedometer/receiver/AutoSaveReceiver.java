package com.example.pedometer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoSaveReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, AutoSaveReceiver.class);
		context.startService(i);
	}

	

}
