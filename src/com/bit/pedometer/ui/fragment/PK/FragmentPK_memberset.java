package com.bit.pedometer.ui.fragment.PK;

import com.bit.pedometer.R;
import com.bit.pedometer.data.db.PedometerDB;
import com.bit.pedometer.ui.fragment.tools.MyNumberPicker;
import com.bit.pedometer.ui.fragment.tools.PictureUtil;
import com.bit.pedometer.ui.fragment.tools.ToRoundBitmap;
import com.bit.pedometer.data.bean.Group;
import com.bit.pedometer.data.bean.User;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
	private Button btn1;//OK按钮，确定
	private Button btn2;//NO按钮，取消当前操作
	private Group group;
	private PedometerDB pedometerDB;
	private int groNum;
	private ToRoundBitmap toRoundBitmap;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.membet_set);
		init();

	}

	private void init() {
		toRoundBitmap = ToRoundBitmap.getInstance(this);
		picture = (ImageView) findViewById(R.id.member_picture);
		name = (TextView) findViewById(R.id.member_name);
		sexImage = (ImageView) findViewById(R.id.member_sexpic);
		weight = (TextView) findViewById(R.id.member_weight);
		steps = (TextView) findViewById(R.id.member_steps);
		nPicker = (MyNumberPicker) findViewById(R.id.member_numberPicker);
		btn1 = (Button) findViewById(R.id.member_button1);
		btn2 = (Button) findViewById(R.id.member_button2);

		user = (User) getIntent().getSerializableExtra("user_data");
		Bitmap bitmap = toRoundBitmap.toRoundBitmap(PictureUtil
				.Byte2Bitmap(user.getPicture()));
		picture.setImageBitmap(bitmap);
//		if (user.getPicture() != null) {
//			
//			try {
//				bitmap = ToRoundBitmap
//						.toRoundBitmap(BitmapFactory.decodeStream(this
//								.getContentResolver().openInputStream(
//										Uri.parse(user.getPicture()))));
//				picture.setImageBitmap(bitmap);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			Resources default_picture = this.getResources();
//			bitmap = ToRoundBitmap.toRoundBitmap(BitmapFactory.decodeResource(
//					default_picture, R.drawable.default_picture));
//			picture.setImageBitmap(bitmap);
//		}
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
			pedometerDB = PedometerDB.getInstance(this);//实例化数据库操作类
			group = pedometerDB.loadGroup(user.getGroupId());//取出当前成员所在的组，方便随后的修改
			groNum = nPicker.getValue();//选择移动的组数
			//如果移动组数则执行下列的语句
			if (groNum != user.getGroupId()) {
				
				group.setMember_number(group.getMember_number() - 1);
				group.setTotal_number(group.getTotal_number()
						- user.getToday_step());
				pedometerDB.updateGroup(group);
				
				Group group1 = pedometerDB.loadGroup(groNum);
				group1.setMember_number(group1.getMember_number() + 1);
				group1.setTotal_number(group1.getTotal_number()
						+ user.getToday_step());
				pedometerDB.updateGroup(group1);
				
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
