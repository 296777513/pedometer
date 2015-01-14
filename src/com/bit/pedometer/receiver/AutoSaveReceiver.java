package com.bit.pedometer.receiver;

import com.bit.pedometer.service.AutoSaveService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AutoSaveReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent i = new Intent(context, AutoSaveService.class);
		Toast.makeText(context, "date changes", Toast.LENGTH_SHORT)
		.show();
		context.startService(i);
	}

	

}
