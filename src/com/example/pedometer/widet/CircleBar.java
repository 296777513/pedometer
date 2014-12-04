package com.example.pedometer.widet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleBar extends View {
	
	private static int flag = 0;
	
	private Paint mPaint = new Paint();
	private RectF mColorWheelRectangle = new RectF();//圆圈的矩形范围
	private Paint mDefaultWheelPaint;//绘制底部灰色圆圈的画笔
	private Paint mColorWheelPaint;//绘制蓝色扇形的画笔
	private Paint textPaint;//中间文字的画笔
	private float mColorWheelRadius;// 圆圈普通状态下的半径
	private float circleStrokeWidth;// 圆圈的线条粗细 
	private float pressExtraStrokeWidth;//按下状态下增加的圆圈线条增加的粗细   
	private int progress;//中间文字内容
	private int mCount;//为了达到数字增加效果而添加的变量，他和mText其实代表一个意思
	private float mSweepAnglePer;//为了达到蓝色扇形增加效果而添加的变量，他和mSweepAngle其实代表一个意思  
	private float mSweepAngle;//扇形弧度
	private int mTextSize;//文字颜色
	BarAnimation1 anim;//动画类
	public CircleBar(Context context) {
		super(context);
		init(null, 0);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	
	private void init(AttributeSet attrs, int defStyle) {
		
		circleStrokeWidth = dip2px(getContext(), 10);// 圆圈的线条粗细 
		pressExtraStrokeWidth = dip2px(getContext(), 2);//按下状态下增加的圆圈线条增加的粗细 
		mTextSize = dip2px(getContext(), 40);//文字大小
		
		//绘制蓝色扇形的画笔
		mColorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mColorWheelPaint.setColor(0xFF29a6f6);
		mColorWheelPaint.setStyle(Paint.Style.STROKE);
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
		
		//绘制底部灰色圆圈的画笔
		mDefaultWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDefaultWheelPaint.setColor(0xFFeeefef);
		mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);

		//中间文字的画笔
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
		textPaint.setColor(0xFF333333);
		textPaint.setStyle(Style.FILL_AND_STROKE);
		textPaint.setTextAlign(Align.LEFT);
		textPaint.setTextSize(mTextSize);
		
		//中间文字内容
		progress = 0;
		//扇形弧度
		mSweepAngle = 0;
		
		//动画类
		anim = new BarAnimation1();
		anim.setDuration(2000);
		
		
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int halfHeight = getHeight() / 2;
		int halfWidth = getWidth() / 2;
		int radius = halfHeight < halfWidth ? halfHeight : halfWidth;
		canvas.drawCircle(halfWidth, halfHeight, radius - 20f,
				mDefaultWheelPaint);
		//圆圈的矩形范围//绘制底部灰色圆圈的画笔
	//	canvas.drawArc(mColorWheelRectangle, -90, 360, false, mDefaultWheelPaint);
		//为了达到蓝色扇形增加效果而添加的变量，他和mSweepAngle其实代表一个意思 //绘制蓝色扇形的画笔
		
		mColorWheelRectangle.top = halfHeight - radius + 20f;
		mColorWheelRectangle.bottom = halfHeight + radius - 20f;
		mColorWheelRectangle.left = halfWidth - radius + 20f;
		mColorWheelRectangle.right = halfWidth + radius - 20f;
		canvas.drawArc(mColorWheelRectangle, -90, mSweepAnglePer, false, mColorWheelPaint);
		Rect bounds = new Rect();
		String textstr=mCount+"";
		//中间文字的画笔
		textPaint.getTextBounds(textstr, 0, textstr.length(), bounds);
		canvas.drawText(
				mCount+"",
				(mColorWheelRectangle.centerX())
						- (textPaint.measureText(textstr) / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2,
				textPaint);
		canvas.drawText(
				"目标：10000",
				(mColorWheelRectangle.centerX())
						- (textPaint.measureText("目标：10000") / 2),
				mColorWheelRectangle.centerY() + bounds.height() / 2 +100,
				textPaint);
		canvas.save();
		
	}
	
	
//	@Override
//    public void setPressed(boolean pressed) {
//		
//		Log.i(TAG,"call setPressed ");
//
//        if (pressed) {
//        	mColorWheelPaint.setColor(0xFF165da6);
//    		textPaint.setColor(0xFF070707);
//    		mColorWheelPaint.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
//    		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
//    		textPaint.setTextSize(mTextSize-pressExtraStrokeWidth);
//        } else {
//        	mColorWheelPaint.setColor(0xFF29a6f6);
//    		textPaint.setColor(0xFF333333);
//    		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
//    		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
//    		textPaint.setTextSize(mTextSize);
//        }
//        super.setPressed(pressed);
//        this.invalidate();
//    }
	
	public void startCustomAnimation(){
		this.startAnimation(anim);
	}
    
	public void setProgress(int progress){
		this.progress = progress;
		this.invalidate();
	}
	
	public void setSweepAngle(float sweepAngle){
		mSweepAngle = sweepAngle;
		this.invalidate();
		
	}
	
    
    public class BarAnimation1 extends Animation {

    	@Override
    	protected void applyTransformation(float interpolatedTime, Transformation t) {
    		super.applyTransformation(interpolatedTime, t);
    		if (interpolatedTime < 1.0f) {
    			mSweepAnglePer =  interpolatedTime * mSweepAngle;
    			mCount = (int)(interpolatedTime * progress);
    		} else {
    			mSweepAnglePer = mSweepAngle;
    			mCount = progress;
    		}
    		postInvalidate();  
    		
    	}
    }
    
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }


}
