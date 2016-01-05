package com.bit.pedometer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bit.pedometer.R;

/**
 * Created with Android Studio. User: liyachao Date: 15-4-14 Time: 下午2:33
 * 头像获取方式选择弹出框
 */
public class SetToTakePicActivity extends Activity {

    public static final String INTENT_KEY_BUTTON_TYPE = "ButtonType";
    public static final String BUTTON_TYPE_TAKE_PICTURES = "TAKE_PICTURES";
    public static final String BUTTON_TYPE_FROM_ALBUM = "FROM_ALBUM";
    public static final String BUTTON_TYPE_NUM = "NUM";
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_headpic);

        View btnTakePics = findViewById(R.id.btn_take_pictures);
        View btnFromAlbum = findViewById(R.id.btn_from_album);
        View btnCancel = findViewById(R.id.btn_cancel);

        btnTakePics.setOnClickListener(new mOnClickListener());
        btnFromAlbum.setOnClickListener(new mOnClickListener());
        btnCancel.setOnClickListener(new mOnClickListener());

        num = getIntent().getStringExtra("num");
    }

    class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_take_pictures:
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_KEY_BUTTON_TYPE,
                            BUTTON_TYPE_TAKE_PICTURES);
                    intent.putExtra(BUTTON_TYPE_NUM, num);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
                case R.id.btn_from_album:
                    intent = new Intent();
                    intent.putExtra(INTENT_KEY_BUTTON_TYPE, BUTTON_TYPE_FROM_ALBUM);
                    intent.putExtra(BUTTON_TYPE_NUM, num);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
                case R.id.btn_cancel:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

}
