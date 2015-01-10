package com.example.pedometer.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.tools.PictureUtil;
import com.example.pedometer.fragment.tools.ToRoundBitmap;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.Step;
import com.example.pedometer.model.User;
import com.example.pedometer.MainActivity;
import com.example.pedometer.R;

import android.annotation.SuppressLint;
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

public class FragmentSet extends Fragment implements OnClickListener {
	private View view;
	// private LinearLayout sexLayout;

	private Uri imageUri;
	public static final int TAKE_PHOTO = 0;
	public static final int TAKE_PHOTO1 = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CROP_PHOTO1 = 3;

	private LinearLayout weightLayout;
	private LinearLayout pictureLayout;
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
	private Uri originalUri;

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
		// sexLayout = (LinearLayout) view.findViewById(R.id.sex);
		weightLayout = (LinearLayout) view.findViewById(R.id.weight);
		sensitivyLayout = (LinearLayout) view.findViewById(R.id.sensitivy);
		lengthLayout = (LinearLayout) view.findViewById(R.id.lengh_step);
		nameLayout = (LinearLayout) view.findViewById(R.id.set_name);
		pictureLayout = (LinearLayout) view.findViewById(R.id.picture);

		weightText = (TextView) view.findViewById(R.id.weight_);

		sensitivyText = (TextView) view.findViewById(R.id.sensitivy_);
		lengthText = (TextView) view.findViewById(R.id.lengh_step_);
		nameText = (TextView) view.findViewById(R.id.name_);
		pictureImage = (ImageView) view.findViewById(R.id.picture_);
		rButton1 = (RadioButton) view.findViewById(R.id.male);
		rButton2 = (RadioButton) view.findViewById(R.id.female);

		pedometerDB = PedometerDB.getInstance(getActivity());

		weightLayout.setOnClickListener(this);
		sensitivyLayout.setOnClickListener(this);
		nameLayout.setOnClickListener(this);
		lengthLayout.setOnClickListener(this);
		rButton1.setOnClickListener(this);
		rButton2.setOnClickListener(this);
		pictureLayout.setOnClickListener(this);

		if (MainActivity.myObjectId != null) {
			user = pedometerDB.loadUser(MainActivity.myObjectId);
			nameText.setText(user.getName());
			setSensitivity(user.getSensitivity());
			weightText.setText(String.valueOf(user.getWeight()));

			Bitmap picture = PictureUtil.Byte2Bitmap(user.getPicture());
			pictureImage.setImageBitmap(ToRoundBitmap.toRoundBitmap(picture));

			lengthText.setText(String.valueOf(user.getStep_length()));

			if (user.getSex().equals("男")) {
				rButton1.setChecked(true);
			} else {
				rButton2.setChecked(true);
			}
		} else {
			pictureImage.setImageBitmap(ToRoundBitmap
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
			dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle("图片来源");
			dialog.setNegativeButton("取消", null);
			dialog.setItems(new String[] { "拍照", "相册" },
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							switch (arg1) {
							case 0:
								File outputImage = new File(Environment
										.getExternalStorageDirectory(),
										"picture.jpg");
								try {
									if (outputImage.exists()) {
										outputImage.delete();
									}
									outputImage.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
								imageUri = Uri.fromFile(outputImage);
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);

								intent.putExtra(MediaStore.EXTRA_OUTPUT,
										imageUri);
								startActivityForResult(intent, TAKE_PHOTO1);

								break;
							case 1:

								Intent intent1 = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								// intent1.putExtra(MediaStore.EXTRA_OUTPUT,
								// imageUri);
								startActivityForResult(intent1, CROP_PHOTO1);

								break;
							}
							// 照片的原始资源地址

						}
					});
			dialog.show();

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
		super.onActivityResult(requestCode, resultCode, data);
		// this.pictureIntent = data;

		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == getActivity().RESULT_OK) {

				Bitmap bitmap;
				try {
					bitmap = BitmapFactory.decodeStream(getActivity()
							.getContentResolver().openInputStream(imageUri));
					pictureImage.setImageBitmap(bitmap);
					user.setPicture(PictureUtil.Bitmap2Byte(bitmap));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		case TAKE_PHOTO1:
			if (resultCode == getActivity().RESULT_OK) {

				Intent intent = new Intent("com.android.camera.action.CROP");

				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);

			}
			break;
		case CROP_PHOTO:
			if (resultCode == getActivity().RESULT_OK) {
				try {

					Bitmap bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory
							.decodeStream(getActivity().getContentResolver()
									.openInputStream(originalUri)));
					pictureImage.setImageBitmap(bitmap);
					user.setPicture(PictureUtil.Bitmap2Byte(bitmap));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case CROP_PHOTO1:
			if (resultCode == getActivity().RESULT_OK) {

				originalUri = data.getData();
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(originalUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra("crop", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		default:
			break;
		}
	}
}
