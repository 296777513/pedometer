package com.example.pedometer.fragment.PK;

import com.example.pedometer.R;
import com.example.pedometer.db.PedometerDB;
import com.example.pedometer.fragment.tools.MyNumberPicker;
import com.example.pedometer.model.Group;
import com.example.pedometer.model.User;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class FragmentPK_memberset extends FragmentActivity implements
		OnClickListener {

	private User user;
	private ImageView picture;
	private TextView name;
	private ImageView sexImage;
	private TextView weight;
	private TextView steps;
	private MyNumberPicker nPicker;
	private Button btn1;
	private Button btn2;
	private Group group;
	private PedometerDB pedometerDB;
	private int groNum;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.membet_set);
		init();

	}

	private void init() {
		picture = (ImageView) findViewById(R.id.member_picture);
		name = (TextView) findViewById(R.id.member_name);
		sexImage = (ImageView) findViewById(R.id.member_sexpic);
		weight = (TextView) findViewById(R.id.member_weight);
		steps = (TextView) findViewById(R.id.member_steps);
		nPicker = (MyNumberPicker) findViewById(R.id.member_numberPicker);
		btn1 = (Button) findViewById(R.id.member_button1);
		btn2 = (Button) findViewById(R.id.member_button2);

		user = (User) getIntent().getSerializableExtra("user_data");
		if (user.getPicture() != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(user.getPicture());
			picture.setImageBitmap(bitmap);
		} else {
			picture.setImageResource(R.drawable.logo_qq);
		}
		name.setText(user.getName());
		if (user.getSex().equals("男")) {
			sexImage.setImageResource(R.drawable.male_set);
		} else {
			sexImage.setImageResource(R.drawable.female_set);
		}
		weight.setText(user.getWeight() + "");
		steps.setText(user.getToday_step() + "");

		nPicker.setFocusable(true);
		nPicker.setFocusableInTouchMode(true);
		nPicker.setMaxValue(3);
		nPicker.setValue(user.getGroupId());
		nPicker.setMinValue(1);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.member_button1:
			pedometerDB = PedometerDB.getInstance(this);
			group = pedometerDB.loadGroup(user.getGroupId());
			groNum = nPicker.getValue();
			if (groNum != user.getGroupId()) {
				group.setMember_number(group.getMember_number() - 1);
				group.setAverage_number(group.getAverage_number()
						- user.getToday_step());
				pedometerDB.updateGroup(group);
				user.setGroupId(groNum);
				pedometerDB.updateUser(user);
			}
			this.finish();

			break;
		case R.id.member_button2:

			this.finish();
			break;

		default:
			break;
		}

	}

}
