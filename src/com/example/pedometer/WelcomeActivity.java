package com.example.pedometer;



import java.util.Timer;
import java.util.TimerTask;




import com.example.pedometer.service.StepDetector;
import com.example.pedometer.service.StepService;
import com.example.test6.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (StepService.flag || StepDetector.CURRENT_SETP > 0) {
			Intent intent = new Intent(WelcomeActivity.this, MainActivity.class); 																				
			startActivity(intent);												
			this.finish();
		} else {
			setContentView(R.layout.welcome);
			(new Timer()).schedule(new TimerTask() {
				public void run() {
					Intent intent = new Intent(WelcomeActivity.this, 
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}, 3000);
		}
	}

}
