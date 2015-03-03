package larson.pm;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class GoalAddStepActivity extends Activity {
	// 目标名
	private EditText goal_add_name;

	// 各个阶段限定时间
	private final int STEP1TIME = 1;
	private final int STEP2TIME = 2;
	private final int STEP3TIME = 3;
	// 目标各个阶段完成度
	private float step1Degree = 0;
	private float step2Degree = 0;
	private float step3Degree = 0;

	// 第二步的东西
	private EditText goal_step1Time;
	private EditText goal_step2Time;
	private EditText goal_step3Time;
	private RatingBar goal_step1Degree;
	private RatingBar goal_step2Degree;
	private RatingBar goal_step3Degree;
	private Button goal_step1Time_btn;
	private Button goal_step2Time_btn;
	private Button goal_step3Time_btn;
	private EditText goal_step1Describe;
	private EditText goal_step2Describe;
	private EditText goal_step3Describe;

	private ImageView goal_next_btn;
	// bundle
	private Bundle b;

	// 那一个被选中
	private int oldChecked;

	private LinearLayout layout1;

	private LinearLayout layout2;

	private LinearLayout layout3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		b = intent.getExtras();
		setContentView(R.layout.goal_add_step);
		init_setp_widget();
		initLayout();
	}

	/**
	 * 初始化layout
	 */
	private void initLayout() {
		layout1 = (LinearLayout) findViewById(R.id.goal_level_step1_ly);
		layout2 = (LinearLayout) findViewById(R.id.goal_level_step2_ly);
		layout3 = (LinearLayout) findViewById(R.id.goal_level_step3_ly);
	}

	// -------以下为阶段设置的代码
	private void init_setp_widget() {

		goal_add_name = (EditText) findViewById(R.id.goal_add_name);

		goal_step1Time = (EditText) findViewById(R.id.goal_step1Time);
		goal_step2Time = (EditText) findViewById(R.id.goal_step2Time);
		goal_step3Time = (EditText) findViewById(R.id.goal_step3Time);

		goal_step1Degree = (RatingBar) findViewById(R.id.goal_step1Degree);
		goal_step1Degree.setOnRatingBarChangeListener(stepListener);
		goal_step2Degree = (RatingBar) findViewById(R.id.goal_step2Degree);
		goal_step2Degree.setOnRatingBarChangeListener(stepListener);
		goal_step3Degree = (RatingBar) findViewById(R.id.goal_step3Degree);
		goal_step3Degree.setOnRatingBarChangeListener(stepListener);

		goal_step1Time_btn = (Button) findViewById(R.id.goal_step1Time_btn);
		goal_step1Time_btn.setOnClickListener(clickListener);
		goal_step2Time_btn = (Button) findViewById(R.id.goal_step2Time_btn);
		goal_step2Time_btn.setOnClickListener(clickListener);
		goal_step3Time_btn = (Button) findViewById(R.id.goal_step3Time_btn);
		goal_step3Time_btn.setOnClickListener(clickListener);

		goal_step1Describe = (EditText) findViewById(R.id.goal_step1Describe);
		goal_step2Describe = (EditText) findViewById(R.id.goal_step2Describe);
		goal_step3Describe = (EditText) findViewById(R.id.goal_step3Describe);

		goal_next_btn = (ImageView) findViewById(R.id.goal_next_btn);
		goal_next_btn.setOnClickListener(clickListener);
		
		RadioGroup goal_step_rb_group = (RadioGroup) findViewById(R.id.goal_step_rb_group);
		goal_step_rb_group
				.setOnCheckedChangeListener(new CheckChangeListener());
	}

	/**
	 * RadioGroup选中监听
	 * 
	 * @author Larson
	 * 
	 */
	private class CheckChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.goal_step1_rb:
				startAnim(1);
				oldChecked = 1;
				break;
			case R.id.goal_step2_rb:
				startAnim(2);
				oldChecked = 2;
				break;
			case R.id.goal_step3_rb:
				startAnim(3);
				oldChecked = 3;
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 切换动画
	 */
	private void startAnim(int nowCheck) {
		switch (nowCheck) {
		case 1:
			Animation anim_enter = AnimationUtils.loadAnimation(this,
					R.anim.anim_enter);
			layout1.startAnimation(anim_enter);
			layout1.setVisibility(View.VISIBLE);
			if (oldChecked == 2) {
				layout2.setVisibility(View.GONE);
			} else if (oldChecked == 3) {
				layout3.setVisibility(View.GONE);
			}
			break;
		case 2:
			Animation anim_enter2 = AnimationUtils.loadAnimation(this,
					R.anim.anim_enter);
			layout2.startAnimation(anim_enter2);
			layout2.setVisibility(View.VISIBLE);
			if (oldChecked == 1) {
				layout1.setVisibility(View.GONE);
			} else if (oldChecked == 3) {
				layout3.setVisibility(View.GONE);
			}
			break;
		case 3:
			Animation anim_enter3 = AnimationUtils.loadAnimation(this,
					R.anim.anim_enter);
			layout3.startAnimation(anim_enter3);
			layout3.setVisibility(View.VISIBLE);
			if (oldChecked == 1) {
				layout1.setVisibility(View.GONE);
			} else if (oldChecked == 2) {
				layout2.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
	}

	/** 处理时间弹窗 */
	public Dialog onCreatDialog(int num) {
		Calendar calendar = Calendar.getInstance();
		Dialog dialog = null;
		switch (num) {
		case STEP1TIME:
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							monthOfYear++;
							goal_step1Time.setText(year + "-" + monthOfYear
									+ "-" + dayOfMonth);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			break;
		case STEP2TIME:
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							monthOfYear++;
							goal_step2Time.setText(year + "-" + monthOfYear
									+ "-" + dayOfMonth);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			break;
		case STEP3TIME:
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							monthOfYear++;
							goal_step3Time.setText(year + "-" + monthOfYear
									+ "-" + dayOfMonth);
						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			break;
		}
		return dialog;
	}

	/** 按钮点击监听 */
	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			if (v.getId() == goal_step1Time_btn.getId()) {
				onCreatDialog(STEP1TIME).show();
			}
			if (v.getId() == goal_step2Time_btn.getId()) {
				onCreatDialog(STEP2TIME).show();
			}
			if (v.getId() == goal_step3Time_btn.getId()) {
				onCreatDialog(STEP3TIME).show();
			}else if(v.getId()==R.id.goal_next_btn){
				b.putString("goalName", goal_add_name.getText().toString());

				b.putFloat("step1Degree", step1Degree);
				b.putFloat("step2Degree", step2Degree);
				b.putFloat("step3Degree", step3Degree);

				b.putString("step1LimitTime", goal_step1Time.getText()
						.toString());
				b.putString("step2LimitTime", goal_step2Time.getText()
						.toString());
				b.putString("step3LimitTime", goal_step3Time.getText()
						.toString());

				b.putString("step1Describe", goal_step1Describe.getText()
						.toString());
				b.putString("step2Describe", goal_step2Describe.getText()
						.toString());
				b.putString("step3Describe", goal_step3Describe.getText()
						.toString());

				Intent intent = new Intent(GoalAddStepActivity.this,
						GoalAddDetailActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				finish();// 用完记得释放内存
			}
		}
	};

	/** （step）当用户评级改变 */
	private OnRatingBarChangeListener stepListener = new OnRatingBarChangeListener() {
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			if (ratingBar == goal_step1Degree) {
				step1Degree = rating;
				step2Degree = step1Degree;
			}
			if (ratingBar == goal_step2Degree) {
				step2Degree = rating;
				step3Degree = step2Degree;
			}
			if (ratingBar == goal_step3Degree) {
				step3Degree = rating;
			}
		}
	};


}
