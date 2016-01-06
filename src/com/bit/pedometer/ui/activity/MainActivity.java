package com.bit.pedometer.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.FragmentAdapter;
import com.bit.pedometer.ui.fragment.FragmentAnalysis;
import com.bit.pedometer.ui.fragment.FragmentHistory;
import com.bit.pedometer.ui.fragment.FragmentPK;
import com.bit.pedometer.ui.fragment.FragmentPedometer;
import com.bit.pedometer.ui.fragment.FragmentSet;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.Fragment;

/**
 * 这是pedometer的主页面
 *
 * @author 李垭超
 */
public class MainActivity extends FragmentActivity {
    public static String myObjectId = null;
    private RadioGroup rgs;//用来切换各个页面
    private RadioButton btn1;//如果是用户第一次进入这个app没有进行注册，则会跳转到注册页面
    public List<Fragment> fragments = new ArrayList<Fragment>();//将5个fragment添加到这个list里
    private PedometerDB pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page_mian);
        btn1 = (RadioButton) findViewById(R.id.btn5);
        pd = PedometerDB.getInstance(this);
        //判断用户是否进行注册过，如果没有进行注册则选中注册页面的radiobutton
        if (pd.loadFirstUser() == null) {
            btn1.setChecked(true);
        } else {
            myObjectId = pd.loadFirstUser().getObjectId();
        }

        rgs = (RadioGroup) findViewById(R.id.radioGroup);//实例化RadioGroup
        fragments.add(new FragmentHistory());
        fragments.add(new FragmentAnalysis());
        fragments.add(new FragmentPedometer());
        fragments.add(new FragmentPK());
        fragments.add(new FragmentSet());
        //自己写的一个fragment的适配器，进行几个页面的逻辑跳转
        new FragmentAdapter(MainActivity.this, fragments, R.id.Fragment, rgs,
                this);

    }

}
