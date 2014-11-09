package com.example.pedometer.widet;

import com.example.test6.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class HistogramView extends View implements Runnable {
	private boolean Text = false;
	private int Height;
	private int Width;
	private Bitmap bitmap;
	private int mHeight;
	private int AnimValue;
	private double Progress;
	private Handler handler = new Handler();
	private int SpeedRatio = 1;
	private int DelayTime = 1;
	private Canvas canvas;

	public void setText(boolean mText) {
		this.Text = mText;
	}

	public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Width = w;
		Height = h;
		mHeight = (int) (h * Progress);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		drawViewWithBitmap(paint);
	}

	private void drawViewWithBitmap(Paint paint) {
		RectF dst = null;
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.column);
		handler.postDelayed(this, DelayTime);
		dst = new RectF(0, Height - AnimValue, Width, Height);
		if (Text) {
			canvas.drawText((int)(Progress * 1000) + "", 0, (Height - AnimValue) - 2, paint);
		}
		canvas.drawBitmap(bitmap, null, dst, paint);
	}

	public void setProgress(double Progress) {
		this.Progress = Progress;
	}

	@Override
	public void run() {
		if (AnimValue <= mHeight) {
			AnimValue += SpeedRatio;
			invalidate();
		}
		else {
			invalidate();
		}

	}

}
