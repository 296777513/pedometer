package com.example.pedometer.widet;

import com.example.pedometer.R;
import com.example.pedometer.fragment.tools.DisplayUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class HistogramView extends View {
	private boolean Text = false;
	private int Height;
	private int Width;
	private Bitmap bitmap;
	private int mHeight;
	private int AnimValue;
	private double Progress;
	private double Progress1 = 0;

	private Canvas canvas;
	HistogramAnimation ani;

	public void setText(boolean mText) {
		this.Text = mText;
		invalidate();

	}

	public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		ani = new HistogramAnimation();
		ani.setDuration(1000);
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ani = new HistogramAnimation();
		ani.setDuration(1000);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Width = w;
		Height = h;
		mHeight = (int) (h * Progress * 0.9);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(DisplayUtil.sp2px(getContext(), 12));
		paint.setColor(Color.parseColor("#6DCAEC"));
		RectF dst = new RectF(0, Height - AnimValue, Width, Height);
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.column);

		this.canvas.drawBitmap(bitmap, null, dst, paint);
		if (Text) {
			if (Progress1 != 0) {
				this.canvas.drawText((int) (Progress1 * 10000) + "", 0,
						(Height - AnimValue) - 10, paint);
			} else {
				this.canvas.drawText((int) (Progress * 10000) + "", 0,
						(Height - AnimValue) - 10, paint);
			}

		}
	}

	public void setProgress(double Progress) {
		if (Progress < 0.03 && Progress != 0) {
			this.Progress1 = Progress;
			Progress = 0.03;
		}
		this.Progress = Progress;
		this.startAnimation(ani);
	}

	private class HistogramAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				AnimValue = (int) (mHeight * interpolatedTime);
			} else {
				AnimValue = mHeight;
			}
			postInvalidate();
		}
	}

	

}
