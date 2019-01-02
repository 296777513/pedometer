package com.bit.pedometer.ui.view;

import com.bit.pedometer.R;
import com.bit.pedometer.ui.fragment.tools.DisplayUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class HistogramView extends View {

	private Paint xLinePaint;// 坐标轴 轴线 画笔：
	private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
	private Paint titlePaint;// 绘制文本的画笔
	private Paint paint;// 矩形画笔 柱状图的样式信息
	private int[] progress;// 7 条
	private int[] aniProgress;// 实现动画的值
	private final int TRUE = 1;// 在柱状图上显示数字
	private int[] text;
	private Bitmap bitmap;
	// 坐标轴左侧的数标
	private String[] ySteps;
	// 坐标轴底部的星期数
	private String[] xWeeks;

	private HistogramAnimation ani;

	public HistogramView(Context context) {
		super(context);
		init(context, null);
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		ySteps = new String[] { "10k", "7.5k", "5k", "2.5k", "0" };
		xWeeks = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
		text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		aniProgress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		ani = new HistogramAnimation();
		ani.setDuration(2000);

		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();

		xLinePaint.setColor(Color.DKGRAY);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
	}

	public void setWeekd(String[] xWeeks) {
		this.xWeeks = xWeeks;
		this.postInvalidate();
	}

	public void setText(int[] text) {

		this.text = text;

		this.postInvalidate();// 可以子线程 更新视图的方法调用。
	}

	public void setProgress(int[] progress) {
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.column);
		this.progress = progress;
		// this.invalidate(); //失效的意思。
		// this.postInvalidate(); // 可以子线程 更新视图的方法调用。
		this.startAnimation(ani);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight() - dp2px(50);

		// 1 绘制坐标线：startX, startY, stopX, stopY, paint
		// canvas.drawLine(50, 10, 50, height, xLinePaint);

		canvas.drawLine(dp2px(30), height + dp2px(3), width - dp2px(30), height
				+ dp2px(3), xLinePaint);

		// 2 绘制坐标内部的水平线

		int leftHeight = height - dp2px(5);// 左侧外周的 需要划分的高度：

		int hPerHeight = leftHeight / 4;// 分成四部分

		hLinePaint.setTextAlign(Align.CENTER);
		for (int i = 0; i < 4; i++) {
			canvas.drawLine(dp2px(30), dp2px(10) + i * hPerHeight, width
					- dp2px(30), dp2px(10) + i * hPerHeight, hLinePaint);
		}

		// 3 绘制 Y 周坐标

		titlePaint.setTextAlign(Align.RIGHT);
		titlePaint.setTextSize(sp2px(12));
		titlePaint.setAntiAlias(true);
		titlePaint.setStyle(Paint.Style.FILL);
		for (int i = 0; i < ySteps.length; i++) {
			canvas.drawText(ySteps[i], dp2px(25), dp2px(13) + i * hPerHeight,
					titlePaint);
		}

		// 4 绘制 X 周 做坐标
		int xAxisLength = width - dp2px(30);
		int columCount = xWeeks.length + 1;
		int step = xAxisLength / columCount;

		for (int i = 0; i < columCount - 1; i++) {
			// text, baseX, baseY, textPaint
			canvas.drawText(xWeeks[i], dp2px(25) + step * (i + 1),
					height + dp2px(20), titlePaint);
		}

		// 5 绘制矩形

		if (aniProgress != null && aniProgress.length > 0) {
			for (int i = 0; i < aniProgress.length; i++) {// 循环遍历将7条柱状图形画出来
				int value = aniProgress[i];
				paint.setAntiAlias(true);// 抗锯齿效果
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(sp2px(15));// 字体大小
				paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
				Rect rect = new Rect();// 柱状图的形状

				rect.left = step * (i + 1);
				rect.right = dp2px(30) + step * (i + 1);
				int rh = (int) (leftHeight - leftHeight * (value / 10000.0));
				rect.top = rh + dp2px(10);
				rect.bottom = height;

				

				canvas.drawBitmap(bitmap, null, rect, paint);

				if (this.text[i] == TRUE) {
					canvas.drawText(value + "", dp2px(15) + step * (i + 1)
							- dp2px(15), rh + dp2px(5), paint);
				}

			}
		}

	}

	private int dp2px(int value) {
		return DisplayUtil.dip2px(getContext(), value);
	}

	private int sp2px(int value) {
		return DisplayUtil.sp2px(getContext(), value);
	}

	/**
	 * 集成animation的一个动画类
	 * 
	 * @author 李垭超
	 *
	 */
	private class HistogramAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = (int) (progress[i] * interpolatedTime);
				}
			} else {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = progress[i];
				}
			}
			invalidate();
		}
	}

}