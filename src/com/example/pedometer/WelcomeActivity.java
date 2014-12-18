package com.example.pedometer;

import java.util.Timer;
import java.util.TimerTask;

import com.example.pedometer.service.StepDetector;
import com.example.pedometer.service.StepService;
import com.example.pedometer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class WelcomeActivity extends Activity {
	//private Animation animation;

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
			
//			animation = AnimationUtils.loadAnimation(WelcomeActivity.this,
//					R.anim.animation_main);
//			this.findViewById(R.id.welcome).setAnimation(animation);
//			animation.setAnimationListener(new AnimationListener() {
//
//				@Override
//				public void onAnimationStart(Animation arg0) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onAnimationRepeat(Animation arg0) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onAnimationEnd(Animation arg0) {
//					Intent intent = new Intent(WelcomeActivity.this,
//							MainActivity.class);
//					startActivity(intent);
//					WelcomeActivity.this.finish();
//				}
//			});
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
