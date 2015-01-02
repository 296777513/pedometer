package com.example.pedometer.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.model.Step;
import com.example.pedometer.widet.HistogramView;
import com.example.pedometer.R;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author 李垭超  2015/1/1
 *
 */
public class FragmentAnalysis extends Fragment implements OnClickListener {
	
	private HistogramView hv1, hv2, hv3, hv4, hv5, hv6, hv7;//分析页面的7个条形统计图

	private PedometerDB pedometerDB;//对数据库进行操作

	private TextView day1,day2,day3,day4,day5,day6,day7;//条形统计图下的星期动态显示

	private TextView average_step;//7天的平均步数
	private TextView sum_step;//7天的总共步数

	private Step step = null;

	private int average = 0;
	private int sum = 0;
	private int average1 = 0;
	private int sum1 = 0;

	private Calendar calendar;
	private String day;

	private View view;

	private AllAnimation ani;//对条形统计图，和数字的动画显示

	/**
	 * 为碎片创建视图（加载布局）时调用
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.analyze, container, false);
		return view;
	}

	/**
	 * 确保与碎片相关联的活动一定已经创建完毕时候调用
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		setWeek();
		setProgress();
		view.startAnimation(ani);

	}

	/**
	 * 初始化当前页面
	 */
	private void init() {
		average_step = (TextView) view.findViewById(R.id.average_step);//实例化平均步数
		sum_step = (TextView) view.findViewById(R.id.sum_step);//实例化总共步数
		ani = new AllAnimation();//创建一个动画的对象
		ani.setDuration(1000);//设置动画完成的时间为1秒

		calendar = Calendar.getInstance();//实例化日历

		//实例化柱形统计图
		hv1 = (HistogramView) view.findViewById(R.id.map1);
		hv2 = (HistogramView) view.findViewById(R.id.map2);
		hv3 = (HistogramView) view.findViewById(R.id.map3);
		hv4 = (HistogramView) view.findViewById(R.id.map4);
		hv5 = (HistogramView) view.findViewById(R.id.map5);
		hv6 = (HistogramView) view.findViewById(R.id.map6);
		hv7 = (HistogramView) view.findViewById(R.id.map7);

		pedometerDB = PedometerDB.getInstance(getActivity());//实例化数据库的操作

		//对所有的条形柱状图设计点击时间，用来显示当前的柱状图所代表的步数
		hv1.setOnClickListener(this);
		hv2.setOnClickListener(this);
		hv3.setOnClickListener(this);
		hv4.setOnClickListener(this);
		hv5.setOnClickListener(this);
		hv6.setOnClickListener(this);
		hv7.setOnClickListener(this);

		//实例化星期
		day1 = (TextView) view.findViewById(R.id.Monday);
		day2 = (TextView) view.findViewById(R.id.Tuesday);
		day3 = (TextView) view.findViewById(R.id.Wednesday);
		day4 = (TextView) view.findViewById(R.id.Thursday);
		day5 = (TextView) view.findViewById(R.id.Friday);
		day6 = (TextView) view.findViewById(R.id.Saturday);
		day7 = (TextView) view.findViewById(R.id.Sunday);

	}

	/**
	 * 设置所有柱状图的大小，根据所代表的步数
	 */
	@SuppressLint("SimpleDateFormat")
	private void setProgress() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		day = sdf.format(calendar.getTime());//得到当天的日期
		// Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		step = pedometerDB.loadSteps(1, day);//从数据库中取出当天的步数
		//如果当天步数不为0，则设置相应的条形统计图，否则设置为0
		if (step != null) {
			hv1.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv1.setProgress(0);
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);//设置前一天的日期
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv2.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv2.setProgress(0);
			sum += 0;
		}
		// Toast.makeText(getActivity(), day+"", Toast.LENGTH_LONG).show();

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv3.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv3.setProgress(0);
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv4.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv4.setProgress(0);
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv5.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv5.setProgress(0);
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv6.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv6.setProgress(0);
			sum += 0;
		}

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		day = sdf.format(calendar.getTime());
		step = pedometerDB.loadSteps(1, day);
		if (step != null) {
			hv7.setProgress((step.getNumber() / 10000.0));
			sum += step.getNumber();
		} else {
			hv7.setProgress(0);
			sum += 0;
		}

	}

	/**
	 * 设置显示的星期数
	 */
	private void setWeek() {

		int day = calendar.get(Calendar.DAY_OF_WEEK);
		// Toast.makeText(getActivity(), day + "", Toast.LENGTH_LONG).show();
		day -= 1;
		day1.setText(week(day));
		day2.setText(week(day - 1));
		day3.setText(week(day - 2));
		day4.setText(week(day - 3));
		day5.setText(week(day - 4));
		day6.setText(week(day - 5));
		day7.setText(week(day - 6));
	}

	/**
	 * 实现条形同居图的点击事件
	 */
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.map1:
			hv1.setText(true);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map2:
			hv1.setText(false);
			hv2.setText(true);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map3:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(true);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map4:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(true);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map5:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(true);
			hv6.setText(false);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map6:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(true);
			hv7.setText(false);
			view.invalidate();
			break;
		case R.id.map7:
			hv1.setText(false);
			hv2.setText(false);
			hv3.setText(false);
			hv4.setText(false);
			hv5.setText(false);
			hv6.setText(false);
			hv7.setText(true);
			view.invalidate();
			break;

		default:
			break;
		}

	}

	private String week(int day) {
		if (day < 1) {
			day += 7;
		}
		switch (day) {
		case 1:
			return "周一";
		case 2:
			return "周二";
		case 3:
			return "周三";
		case 4:
			return "周四";
		case 5:
			return "周五";
		case 6:
			return "周六";
		case 7:
			return "周日";
		default:
			return "";
		}
	}

	/**
	 * 设置分析页面的动画类
	 * @author 李垭超
	 *
	 */
	private class AllAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				sum1 = (int) (sum * interpolatedTime);
				average1 = (int) (average * interpolatedTime);
			} else {
				sum1 = sum;
				average1 = average;
			}
			view.postInvalidate();
			sum_step.setText(sum1 + "");
			average = sum / 7;
			average_step.setText(average1 + "");

		}
	}

}
