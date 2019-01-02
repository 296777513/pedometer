package com.bit.pedometer.ui.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bit.pedometer.ui.activity.MainActivity;
import com.bit.pedometer.R;
import com.bit.pedometer.ui.activity.SetToTakePicActivity;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.PictureUtil;
import com.bit.pedometer.ui.fragment.tools.ToRoundBitmap;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.Step;
import com.bit.pedometer.data.bean.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSet extends Fragment implements OnClickListener {
    public static final int REQUEST_CODE_TO_TACK_PICTURE = 2201;
    public static final int REQUEST_CODE_TACK_PICTURE = 2202;
    public static final int REQUEST_CODE_TACK_PICTURE_ZOOM = 2204;
    public static final int REQUEST_CODE_FROM_ALBUM = 2203;
    public static final int REQUEST_CODE_FROM_ALBUM_ZOOM = 2205;


    private File file;
    private View view;
    // private LinearLayout sexLayout;


    private LinearLayout weightLayout;
    private LinearLayout sensitivyLayout;
    private LinearLayout lengthLayout;
    private LinearLayout nameLayout;
    private RadioButton rButton1;
    private RadioButton rButton2;

    private TextView weightText;
    private ImageView pictureImage;
    private TextView sensitivyText;
    private TextView lengthText;
    private TextView nameText;
    private EditText editText;

    private AlertDialog.Builder dialog;
    private NumberPicker numberPicker;

    private PedometerDB pedometerDB;
    private User user = null;
    private Group group = null;
    private ToRoundBitmap toRoundBitmap;

    // private Intent pictureIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.set, container, false);
        init();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        pedometerDB.updateUser(user);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pedometerDB.updateUser(user);
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        toRoundBitmap = ToRoundBitmap.getInstance(getActivity());
        // sexLayout = (LinearLayout) view.findViewById(R.id.sex);
        weightLayout = (LinearLayout) view.findViewById(R.id.weight);
        sensitivyLayout = (LinearLayout) view.findViewById(R.id.sensitivy);
        lengthLayout = (LinearLayout) view.findViewById(R.id.lengh_step);
        nameLayout = (LinearLayout) view.findViewById(R.id.set_name);

        weightText = (TextView) view.findViewById(R.id.weight_);

        sensitivyText = (TextView) view.findViewById(R.id.sensitivy_);
        lengthText = (TextView) view.findViewById(R.id.lengh_step_);
        nameText = (TextView) view.findViewById(R.id.name_);
        pictureImage = (ImageView) view.findViewById(R.id.picture);
        rButton1 = (RadioButton) view.findViewById(R.id.male);
        rButton2 = (RadioButton) view.findViewById(R.id.female);

        pedometerDB = PedometerDB.getInstance(getActivity());

        weightLayout.setOnClickListener(this);
        sensitivyLayout.setOnClickListener(this);
        nameLayout.setOnClickListener(this);
        lengthLayout.setOnClickListener(this);
        rButton1.setOnClickListener(this);
        rButton2.setOnClickListener(this);
        pictureImage.setOnClickListener(this);

        if (MainActivity.myObjectId != null) {
            user = pedometerDB.loadUser(MainActivity.myObjectId);
            nameText.setText(user.getName());
            setSensitivity(user.getSensitivity());
            weightText.setText(String.valueOf(user.getWeight()));

            Bitmap picture = PictureUtil.Byte2Bitmap(user.getPicture());
            pictureImage.setImageBitmap(toRoundBitmap.toRoundBitmap(picture));

            lengthText.setText(String.valueOf(user.getStep_length()));

            if (user.getSex().equals("男")) {
                rButton1.setChecked(true);
            } else {
                rButton2.setChecked(true);
            }
        } else {
            pictureImage.setImageBitmap(toRoundBitmap
                    .toRoundBitmap(BitmapFactory.decodeResource(getActivity()
                            .getResources(), R.drawable.default_picture)));
            user = new User();
            user.setName(nameText.getText().toString());
            user.setWeight(Integer.valueOf(weightText.getText().toString()));
            user.setSensitivity(10);
            user.setSex("男");
            Bitmap picture = PictureUtil.drawable2Bitmap(getActivity()
                    .getResources().getDrawable(R.drawable.default_picture));
            user.setPicture(PictureUtil.Bitmap2Byte(picture));
            user.setStep_length(Integer
                    .valueOf(lengthText.getText().toString()));
            user.setGroupId(1);
            group = pedometerDB.loadGroup(1);
            group.setMember_number(1);
            pedometerDB.updateGroup(group);
            user.setObjectId("1");
            MainActivity.myObjectId = user.getObjectId();
            pedometerDB.saveUser(user);
            // 将当前日期格式化为：yyyyMMdd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Step step = new Step();
            // 得到当天的日期
            step.setDate(sdf.format(new Date()));
            step.setUserId(MainActivity.myObjectId);
            step.setNumber(0);
            pedometerDB.saveStep(step);

        }

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.picture:
                Intent intent = new Intent(getActivity(),
                        SetToTakePicActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TO_TACK_PICTURE);

                pedometerDB.updateUser(user);
                break;
            case R.id.set_name:
                dialog = new AlertDialog.Builder(getActivity());
                editText = new EditText(getActivity());
                editText.setSingleLine(true);
                dialog.setView(editText);

                dialog.setTitle("填写姓名");
                dialog.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                nameText.setText(editText.getText().toString());
                                user.setName(editText.getText().toString());
                                pedometerDB.updateUser(user);
                            }
                        });
                dialog.show();
                break;

            case R.id.weight:
                dialog = new AlertDialog.Builder(getActivity());
                numberPicker = new NumberPicker(getActivity());
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                numberPicker.setMaxValue(200);
                numberPicker.setValue(Integer.parseInt(weightText.getText()
                        .toString()));
                numberPicker.setMinValue(30);
                dialog.setView(numberPicker);
                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                weightText.setText(numberPicker.getValue() + "");
                                user.setWeight(numberPicker.getValue());
                            }
                        });
                dialog.show();
                break;

            case R.id.sensitivy:
                dialog = new AlertDialog.Builder(getActivity());
                numberPicker = new NumberPicker(getActivity());
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                numberPicker.setMaxValue(10);
                numberPicker.setMinValue(1);
                dialog.setView(numberPicker);
                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                user.setSensitivity(numberPicker.getValue());
                                setSensitivity(numberPicker.getValue());

                            }
                        });
                dialog.show();
                break;
            case R.id.lengh_step:
                dialog = new AlertDialog.Builder(getActivity());
                numberPicker = new NumberPicker(getActivity());
                numberPicker.setFocusable(true);
                numberPicker.setFocusableInTouchMode(true);
                numberPicker.setMaxValue(100);
                numberPicker.setValue(Integer.parseInt(lengthText.getText()
                        .toString()));
                numberPicker.setMinValue(15);
                dialog.setView(numberPicker);
                dialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                lengthText.setText(numberPicker.getValue() + "");
                                user.setStep_length(numberPicker.getValue());
                            }
                        });
                dialog.show();
                break;
            case R.id.male:

                user.setSex("男");

                break;
            case R.id.female:
                user.setSex("女");
                break;
        }

    }

    private void setSensitivity(int value) {
        switch (value) {
            case 1:
                sensitivyText.setText("一级");
                break;
            case 2:
                sensitivyText.setText("二级");
                break;
            case 3:
                sensitivyText.setText("三级");
                break;
            case 4:
                sensitivyText.setText("四级");
                break;
            case 5:
                sensitivyText.setText("五级");
                break;
            case 6:
                sensitivyText.setText("六级");
                break;
            case 7:
                sensitivyText.setText("七级");
                break;
            case 8:
                sensitivyText.setText("八级");
                break;
            case 9:
                sensitivyText.setText("九级");
                break;
            case 10:
                sensitivyText.setText("十级");
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_CODE_TO_TACK_PICTURE:
                    Intent i = new Intent();
                    i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 两个参数:外部存储设备目录,存储的名字
                    file = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");
                    // 往意图中放额外的数据
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 设置拍照的照片存储在哪个位置。
                    String state = Environment.getExternalStorageState();
                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
                        Toast.makeText(getActivity(),
                                "SD Card Error! Please check it!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 头像获取方式获取，并调用对应系统API
                    String buttonType = (String) data.getExtras().get(
                            SetToTakePicActivity.INTENT_KEY_BUTTON_TYPE);
                    if (SetToTakePicActivity.BUTTON_TYPE_TAKE_PICTURES
                            .equals(buttonType)) {
                        startActivityForResult(i, REQUEST_CODE_TACK_PICTURE_ZOOM);
                    } else if (SetToTakePicActivity.BUTTON_TYPE_FROM_ALBUM
                            .equals(buttonType)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM_ZOOM);
                    }
                    break;
                case REQUEST_CODE_TACK_PICTURE_ZOOM:
                    Uri uri_tack = Uri.fromFile(file);
                    if (uri_tack != null) {
                        startPhotoZoom(uri_tack, REQUEST_CODE_TACK_PICTURE);
                    }
                    break;
                case REQUEST_CODE_TACK_PICTURE:
                    Bitmap photo = null;
                    if (photo == null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            photo = (Bitmap) bundle.get("data");
                        }
                    }
                    if (photo != null) {
                        pictureImage.setImageBitmap(toRoundBitmap.toRoundBitmap(photo));
                    } else {
                        Toast.makeText(getActivity(),
                                "添加头像失败，请重新操作",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case REQUEST_CODE_FROM_ALBUM_ZOOM:
                    Uri originalUri = data.getData();
                    if (originalUri != null) {
                        startPhotoZoom(originalUri, REQUEST_CODE_FROM_ALBUM);
                    }
                    break;
                case REQUEST_CODE_FROM_ALBUM:
                    Bitmap image = null;
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        image = (Bitmap) bundle.get("data");
                    }
                    if (image != null) {
                        pictureImage.setImageBitmap(toRoundBitmap.toRoundBitmap(image));
                    } else {
                        Toast.makeText(getActivity(),
                                "添加头像失败，请重新操作",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri, int witch) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 系统的裁剪图片默认对图片进行人脸识别，当识别到有人脸时，
        // 会按aspectX和aspectY为1来处理，如果想设置成自定义的裁剪比例，
        // 需要设置noFaceDetection为true。
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, witch);
    }
}
