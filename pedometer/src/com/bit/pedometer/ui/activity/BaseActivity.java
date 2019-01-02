package com.bit.pedometer.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.pedometer.R;
import com.bit.pedometer.ui.view.MyDialog;


/**
 * Created by Android Studio. author: liyachao Date: 15/5/20 Time: 16:31
 */
public abstract class BaseActivity extends FragmentActivity {

    /**
     * 自定义的吐司
     */
    private Toast toast;

//    /**
//     * 吐司中显示的文字
//     */
//    private TextView toastTextView;

    /**
     * 使用handler发送吐司
     */
    private Handler handler = new Handler();

    /**
     * 显示对话框
     */
    private MyDialog dialog;
    /**
     * 现实进度条
     */
    private ProgressDialog progressDialog;

    /**
     * 软键盘管理工具
     */
    InputMethodManager imm;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView();
        initView();
        setTitle();
        initData();
        setEvent();
    }


    /**
     * 初始化布局
     */
    protected abstract void setContentView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置标头
     */
    protected abstract void setTitle();

    /**
     * 初始化值
     */
    protected abstract void initData();

    /**
     * 设置事件
     */
    protected abstract void setEvent();


    private void init() {
        context = this;
        /**
         * 初始化吐司和吐司的布局文件
         */
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view = inflater.inflate(R.layout.toast_layout, null);
//        toastTextView = (TextView) view.findViewById(R.id.toast_text);
//        toast = new Toast(getApplicationContext());
//        toast.setView(view);
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);


        /**
         * 初始化进度框
         */
        progressDialog = new ProgressDialog(this, R.style.ProgressDialog);
        /**
         * 初始化软键盘管理工具
         */

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


    }


    protected <T extends View> T getView(int viewId) {
        View view = findViewById(viewId);
        return (T) view;
    }

    /**
     * 设置标题栏
     *
     * @param str 标题栏文字
     */
    protected void setTitleText(String str) {
        TextView titleText = (TextView) findViewById(R.id.center_title);
        titleText.setText(str);
    }

    /**
     * 设置标题栏颜色
     *
     * @param colorStr 标题栏颜色
     */
    protected void setMyTitleColor(String colorStr) {
        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        titleLayout.setBackgroundColor(Color.parseColor(colorStr));
    }

    /**
     * 设置标题栏颜色
     *
     * @param color 标题栏颜色
     */
    protected void setMyTitleColor(int color) {
        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        titleLayout.setBackgroundColor(color);
    }

    /**
     * 显示标题栏左部图片
     *
     * @param resId         图片资源的ID
     * @param clickListener 点击事件
     */
    protected void showLeftWithImage(int resId,
                                     View.OnClickListener clickListener) {
        ImageView leftImage = (ImageView) findViewById(R.id.left_title_image);
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(resId);

        //设置点击区域
        LinearLayout leftClickRange = (LinearLayout) findViewById(R.id.left_title_layout);
        leftClickRange.setOnClickListener(clickListener);
    }

    /**
     * 显示左部的文字
     *
     * @param str           所要显示的文字
     * @param clickListener 点击事件
     */
    protected void showLeftWithText(String str,
                                    View.OnClickListener clickListener) {
        TextView leftText = (TextView) findViewById(R.id.left_title_text);
        leftText.setVisibility(View.VISIBLE);
        leftText.setText(str);

        //设置点击区域
        LinearLayout leftClickRange = (LinearLayout) findViewById(R.id.left_title_layout);
        leftClickRange.setOnClickListener(clickListener);
    }

    /**
     * 显示标题栏右部图片
     *
     * @param resId         图片资源的ID
     * @param clickListener 点击事件
     */
    protected void showRightWithImage(int resId,
                                      View.OnClickListener clickListener) {
        ImageView rightImage = (ImageView) findViewById(R.id.right_title_image);
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setImageResource(resId);

        //设置点击区域
        LinearLayout rightClickRange = (LinearLayout) findViewById(R.id.right_title_layout);
        rightClickRange.setOnClickListener(clickListener);
    }

    /**
     * 显示标题栏右部第二个图片
     *
     * @param resId         图片资源的ID
     * @param clickListener 点击事件
     */
    protected void showRightWithSecondImage(int resId,
                                            View.OnClickListener clickListener) {
        ImageView rightImage = (ImageView) findViewById(R.id.right_second_title_image);
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setImageResource(resId);

        //设置点击区域
        LinearLayout rightClickRange = (LinearLayout) findViewById(R.id.right_second_title_layout);
        rightClickRange.setOnClickListener(clickListener);
    }

    /**
     * 显示右部的文字
     *
     * @param str           所要显示的文字
     * @param clickListener 点击事件
     */
    protected void showRightWithText(String str,
                                     View.OnClickListener clickListener) {
        TextView rightText = (TextView) findViewById(R.id.right_title_text);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(str);

        //设置点击区域
        LinearLayout rightClickRange = (LinearLayout) findViewById(R.id.right_title_layout);
        rightClickRange.setOnClickListener(clickListener);
    }

    /**
     * 显示自定义的Toast，并在有软键盘和没有软键盘弹出时间不相同
     *
     * @param strId    在strings.xml对应的字符串
     * @param booleans
     */
    protected void showToast(int strId, boolean[] booleans) {
        String str = getResources().getString(strId);
        showToast(str, booleans);
    }

    /**
     * 显示自定义的Toast，并在有软键盘和没有软键盘弹出时间不相同
     *
     * @param str      吐司的内容
     * @param booleans 输入框是否有焦点
     */
    protected void showToast(final String str, final boolean[] booleans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.setText(str);
//        toast.setGravity(Gravity.TOP, 0, dp2px(60));
                boolean isAllFocused = false;//是否输入框一个都没有选中
                if (booleans != null) {
                    for (boolean b : booleans) {
                        if (b) {
                            isAllFocused = true;
                            break;
                        }
                    }
                }

                if (isAllFocused) {
                    closeSoftKeyboard();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
//                            Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
                        }
                    }, 300);
                } else {
                    toast.show();
//                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 关闭软键盘
     */
    protected void closeSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 开启软键盘
     */
    protected void showSoftKeyboard() {
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    protected void showToast(String str) {
        showToast(str, null);
    }

    protected void showToast(int strId) {
        showToast(strId, null);
    }


    /**
     * 显示进度框
     *
     * @param message 传入显示的文字
     */
    protected void showProgressDialog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if ((keyCode == KeyEvent.KEYCODE_BACK) && (progressDialog.isShowing())) {
                            progressDialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

    }

    /**
     * 显示进度框
     *
     * @param message
     * @param keyListener 监听点击返回按钮
     */
    protected void showProgressDialog(final String message, final DialogInterface.OnKeyListener keyListener) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setOnKeyListener(keyListener);
            }
        });
    }


    /**
     * 现实对话框
     *
     * @param message      输入显示的文字
     * @param sureListener 监听事件
     */
    protected void showDialog(final String message, final View.OnClickListener sureListener) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 初始化对话框
                 */
                dialog = new MyDialog(BaseActivity.this, R.style.TransparentDialog);
                dialog.setCancelable(false);
                dialog.setDialogTitle("提示");
                dialog.setDialogContent(message);
                dialog.setMyOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }, sureListener);
                dialog.show();
            }
        });

    }

    /**
     * 现实对话框
     *
     * @param message      输入显示的文字
     * @param sureListener 监听事件
     */
    protected void showDialog(final String message,
                              final String leftButtonStr, final String rightButtonStr,
                              final View.OnClickListener sureListener) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 初始化对话框
                 */
                dialog = new MyDialog(BaseActivity.this, R.style.TransparentDialog);
                dialog.setCancelable(false);
                dialog.setDialogTitle("提示");
                dialog.setButtonText(leftButtonStr, rightButtonStr);
                dialog.setDialogContent(message);
                dialog.setMyOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }, sureListener);
                dialog.show();
            }
        });

    }

    /**
     * 现实对话框
     *
     * @param message      输入显示的文字
     * @param sureListener 监听事件
     */
    protected void showDialog(final String message, final View.OnClickListener sureListener
            , final View.OnClickListener cancelListener) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 初始化对话框
                 */
                dialog = new MyDialog(BaseActivity.this, R.style.TransparentDialog);
                dialog.setCancelable(false);
                dialog.setDialogTitle("提示");
                dialog.setDialogContent(message);
                dialog.setMyOnclickListener(cancelListener, sureListener);
                dialog.show();
            }
        });

    }

    /**
     * 关闭对话框
     */
    protected void cancelProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
    }

    /**
     * 关闭进度框
     */
    protected void cancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * popupwindow关闭，显示正常
     */
    protected void onLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * popupwindow开启，显示暗色
     */
    protected void offLight() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
    }

}