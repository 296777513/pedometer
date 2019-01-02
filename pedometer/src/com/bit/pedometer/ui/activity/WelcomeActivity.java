package com.bit.pedometer.ui.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.bit.pedometer.R;
import com.bit.pedometer.service.StepDetector;
import com.bit.pedometer.service.StepService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * 这是开启程序的第一个启动页面
 * @author liyachao
 *
 */
public class WelcomeActivity extends Activity {
	//private Animation animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//页面设置为无标题
		//如果StepServer是启动的话或者CURRENT_SETP不为0，则直接进入到主页面，否则进入欢迎页面
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
			//设置一个计时器，在此页面上停留3秒然后跳转到主页面,
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
