package com.example.pedometer.widet;

import com.example.pedometer.widet.CircularProgressBar.onProgressChangeListener;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RateTextCircularProgressBar extends FrameLayout implements
		onProgressChangeListener {
	private CircularProgressBar mCircularProgressBar;
	private TextView mRateText;
	private CircleAnimation ani;
	private int type = 1;
	private int Progress1;
	private int Progress;

	public RateTextCircularProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RateTextCircularProgressBar(Context context) {
		super(context);
		init();
	}

	private void init() {
		mCircularProgressBar = new CircularProgressBar(getContext());
		this.addView(mCircularProgressBar);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp.gravity = Gravity.CENTER;
		mCircularProgressBar.setLayoutParams(lp);

		mRateText = new TextView(getContext());
		this.addView(mRateText);
		mRateText.setLayoutParams(lp);
		mRateText.setGravity(Gravity.CENTER);
		mRateText.setTextColor(Color.BLACK);
		mRateText.setTextSize(50);

		ani = new CircleAnimation();
		ani.setDuration(1000);

		mCircularProgressBar.setOnProgressChangeListener(this);
	}

	public void setMax(int max) {
		mCircularProgressBar.setMax(max);
	}

	public void setProgress(int progress, int type) {
		Progress1 = progress;
		if (type == 3) {
			mRateText.setVisibility(View.INVISIBLE);
		}else {
			mRateText.setVisibility(View.VISIBLE);
		}

		if (this.type != type) {
			if (type == 0) {
				this.startAnimation(ani);
			} else {
				
				this.startAnimation(ani);
				this.type = type;
			}

		} else {
			mCircularProgressBar.setProgress(progress);
			mRateText.setText(progress + "");
		}

	}

	public CircularProgressBar getCircularProgressBar() {
		return mCircularProgressBar;
	}

	public void setTextSize(float size) {
		mRateText.setTextSize(size);
	}

	public void setTextColor(int color) {
		mRateText.setTextColor(color);
	}

	@Override
	public void onChange(int duration, int progress, float rate) {
		// mRateText.setText(String.valueOf((int) (rate * 100) + "%"));
		switch (type) {
		case 1:
			mRateText.setText(progress + "");
			break;
		case 2:
			mRateText.setText(progress + "");
			break;

		default:
			break;
		}

	}

	private class CircleAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				Progress = (int) (Progress1 * interpolatedTime);
			} else {
				Progress = Progress1;
			}
			postInvalidate();
			
				mCircularProgressBar.setProgress(Progress);
				mRateText.setText(Progress + "");
			
			
		}
	}

}
