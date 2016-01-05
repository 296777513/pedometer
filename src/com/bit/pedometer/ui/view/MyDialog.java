package com.bit.pedometer.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bit.pedometer.R;


/**
 * Created by Android Studio. author: liyachao Date: 15/6/22 Time: 11:40
 */
public class MyDialog extends Dialog {
    Context context;

    /**
     * 提示框标题
     */
    private TextView dialogTitle;
    /**
     * 提示框内容
     */
    private TextView dialogContent;
    /**
     * 提示框同意按钮
     */
    private TextView dialogSure;
    /**
     * 提示框取消按钮
     */
    private TextView dialogCancel;

    private String leftStr = "取消";
    private String rightStr = "确认";

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogContent = (TextView) findViewById(R.id.dialog_content);
        dialogSure = (TextView) findViewById(R.id.dialog_sure);
        dialogCancel = (TextView) findViewById(R.id.dialog_cancel);

        dialogContent.setText(content);
        dialogTitle.setText(title);
        dialogCancel.setText(leftStr);
        dialogSure.setText(rightStr);
        dialogSure.setOnClickListener(sureListener);
        dialogCancel.setOnClickListener(cancelListener);
    }

    private String title;
    private String content;
    private View.OnClickListener cancelListener;
    private View.OnClickListener sureListener;

    public void setDialogTitle(String title) {
        this.title = title;

    }

    public void setButtonText(String leftBtn, String rightBtn) {
        this.leftStr = leftBtn;
        this.rightStr = rightBtn;
    }

    public void setDialogContent(String content) {
        this.content = content;
    }

    public void setMyOnclickListener(View.OnClickListener cancelListener
            , View.OnClickListener sureListener) {
        this.cancelListener = cancelListener;
        this.sureListener = sureListener;

    }
}
