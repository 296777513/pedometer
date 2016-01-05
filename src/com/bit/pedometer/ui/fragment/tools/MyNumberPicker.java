package com.bit.pedometer.ui.fragment.tools;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MyNumberPicker extends NumberPicker {

	public MyNumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyNumberPicker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
		updateView(child);
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, index, params);
		updateView(child);
	}

	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, params);
		updateView(child);
	}

	public void updateView(View view) {
		if (view instanceof EditText) {
			// 这里修改字体的属性
			((EditText) view).setTextSize(20);
			((EditText) view).setTextColor(Color.BLUE);
		}
	}

}
