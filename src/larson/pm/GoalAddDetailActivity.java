package larson.pm;

import java.util.ArrayList;
import java.util.List;

import larson.pm.adapter.GoalPageAdapter;
import larson.pm.bean.Goal;
import larson.pm.dao.GoalDao;
import larson.pm.utils.Constant;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoalAddDetailActivity extends Activity {

	private ViewPager goalPager;
	private List<View> views;
	private View goal_detail_achieve;
	private View goal_detail_define;
	private View goal_detail_failed;
	private View goal_detail_motivation;
	private TextView goal_motivation;
	private TextView goal_achieve;
	private TextView goal_failed;
	private TextView goal_define;
	private TextView goal_add_detail_describe;
	private TextView goal_add_detial_title;
	private ImageView cursor;
	private int bmpWidth;
	private int currIndex = 0;// 当前页卡编号
	private Bundle bundle;
	private EditText goal_et_benefit = null;
	private EditText goal_et_damage = null;
	private EditText goal_et_define = null;
	private EditText goal_et_reason = null;

	private Intent intent;
	private int screenW;
	private LinearLayout goal_add_detail_tag_ly;// 这是指示小标

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_add_detail);
		intent = getIntent();
		bundle = intent.getExtras();
		init_widget();
	}

	public void init_widget() {
		initImageView();
		initTextView();
		initViewPage();

		initEditText();

		initButton();

		initlayout();
	}

	private void initlayout() {
		goal_add_detail_tag_ly = (LinearLayout) findViewById(R.id.goal_add_detail_tag_ly);
	}

	private void initEditText() {
		goal_et_benefit = (EditText) goal_detail_achieve
				.findViewById(R.id.goal_add_content_et);
		goal_et_damage = (EditText) goal_detail_failed
				.findViewById(R.id.goal_add_content_et);
		goal_et_define = (EditText) goal_detail_define
				.findViewById(R.id.goal_add_content_et);
		goal_et_reason = (EditText) goal_detail_motivation
				.findViewById(R.id.goal_add_content_et);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constant.SAVE_DATA:
				saveToSQL();
				break;
			case Constant.SAVE_DATA_SUCCEED:
				Toast.makeText(GoalAddDetailActivity.this, "数据保存成功",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	};
	

	/**
	 * 完成后，保存按钮监听
	 */
	public void initButton() {
		TextView goal_detail_save = (TextView) findViewById(R.id.goal_detail_save);
		goal_detail_save.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 点击就保存数据
				mHandler.sendEmptyMessage(Constant.SAVE_DATA);

				Intent intent = new Intent(GoalAddDetailActivity.this,
						GoalActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	/**
	 * 把数据保存到数据库中
	 * 
	 * @param goalName
	 * @param reason
	 * @param benefit
	 * @param damage
	 */
	public void saveToSQL() {

		String benefit = goal_et_benefit.getText().toString();
		String damage = goal_et_damage.getText().toString();
		String define = goal_et_define.getText().toString();
		String reason = goal_et_reason.getText().toString();

		String goalName = bundle.getString("goalName");

		Float challengeLevel = bundle.getFloat("challengeLevel");
		Float clearLevel = bundle.getFloat("clearLevel");
		Float emergencyLevel = bundle.getFloat("emergencyLevel");
		Float importanceLevel = bundle.getFloat("importanceLevel");
		Float publicLevel = bundle.getFloat("publicLevel");
		Float step1Degree = bundle.getFloat("step1Degree");
		Float step2Degree = bundle.getFloat("step2Degree");
		Float step3Degree = bundle.getFloat("step3Degree");

		String step1LimitTime = bundle.getString("step1LimitTime");
		String step2LimitTime = bundle.getString("step2LimitTime");
		String step3LimitTime = bundle.getString("step3LimitTime");

		String step1Describe = bundle.getString("step1Describe");
		String step2Describe = bundle.getString("step2Describe");
		String step3Describe = bundle.getString("step3Describe");

		GoalDao dao = new GoalDao(this);

		Goal goal = new Goal();
		goal.setGoalName(goalName);
		goal.setIsFinish(0);
		goal.setChallengeLevel(challengeLevel);
		goal.setEmergencyLevel(emergencyLevel);
		goal.setImportanceLevel(importanceLevel);
		goal.setPublicLevel(publicLevel);
		goal.setClearLevel(clearLevel);
		goal.setStep1Degree(step1Degree);
		goal.setStep2Degree(step2Degree);
		goal.setStep3Degree(step3Degree);
		goal.setStep1LimitTime(step1LimitTime);
		goal.setStep2LimitTime(step2LimitTime);
		goal.setStep3LimitTime(step3LimitTime);
		goal.setStep1Describe(step1Describe);
		goal.setStep2Describe(step2Describe);
		goal.setStep3Describe(step3Describe);
		goal.setDefine(define);
		goal.setReason(reason);
		goal.setBenefit(benefit);
		goal.setDamage(damage);

		boolean result = dao.insert(goal);
		if (result)
			mHandler.sendEmptyMessage(Constant.SAVE_DATA_SUCCEED);
	}

	/**
	 * 初始化页卡
	 */
	public void initViewPage() {
		goalPager = (ViewPager) findViewById(R.id.goal_detail_Pager);
		views = new ArrayList<View>();

		LayoutInflater inflater = getLayoutInflater();
		goal_detail_achieve = inflater.inflate(
				R.layout.goal_add_detail_content, null);
		goal_detail_define = inflater.inflate(R.layout.goal_add_detail_content,
				null);
		goal_detail_failed = inflater.inflate(R.layout.goal_add_detail_content,
				null);
		goal_detail_motivation = inflater.inflate(
				R.layout.goal_add_detail_content, null);

		views.add(goal_detail_achieve);
		views.add(goal_detail_define);
		views.add(goal_detail_failed);
		views.add(goal_detail_motivation);

		goalPager.setAdapter(new GoalPageAdapter(views));
		goalPager.setCurrentItem(0);
		goalPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	/**
	 * 这些是便于点击切换到指定页面的空白文本
	 */
	public void initTextView() {
		goal_define = (TextView) findViewById(R.id.goal_define);
		goal_define.setOnClickListener(new MyOnClickListener(0));
		goal_motivation = (TextView) findViewById(R.id.goal_motivation);
		goal_motivation.setOnClickListener(new MyOnClickListener(1));
		goal_achieve = (TextView) findViewById(R.id.goal_achieve);
		goal_achieve.setOnClickListener(new MyOnClickListener(2));
		goal_failed = (TextView) findViewById(R.id.goal_failed);
		goal_failed.setOnClickListener(new MyOnClickListener(3));

		goal_add_detail_describe = (TextView) findViewById(R.id.goal_add_detail_describe);
		goal_add_detial_title = (TextView)findViewById(R.id.goal_add_detial_title);
	}

	/**
	 * 初始化游标图片
	 */
	public void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.goal_add_detail_line).getWidth();
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		/**
		 * 设置游标图片的宽高从而设置图片的移动动画
		 */
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm.widthPixels;
		int offset = (screenW / 4 - bmpWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);

	}

	/**
	 * 点击小圆圈调到指定页面
	 * 
	 * @author Larson
	 * 
	 */
	public class MyOnClickListener implements OnClickListener {
		private int index;

		public MyOnClickListener(int index) {
			this.index = index;
		}

		public void onClick(View v) {
			goalPager.setCurrentItem(index);
		}

	}

	/**
	 * 页码改变监听
	 * 
	 * @author Larson
	 * 
	 */
	public class MyPageChangeListener implements OnPageChangeListener {

		// int one = offset * 2 + bmpWidth;// 页卡1 -> 页卡2 偏移量
		int one = screenW / 4;

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
			Toast.makeText(GoalAddDetailActivity.this, "您选择了" + arg0 + "页卡",
					Toast.LENGTH_SHORT).show();

			setIndicateBg(arg0);
			switch (arg0) {
			case 0:
				goal_add_detail_describe.setText("请对这个目标详细定义");
				break;
			case 1:
				goal_add_detail_describe.setText("什么动力让你有这个目标");
				break;
			case 2:
				goal_add_detail_describe.setText("达成目标最满意的结果");
				break;
			case 3:
				goal_add_detail_describe.setText("没达成目标最糟糕的后果");

				break;

			default:
				break;
			}
		}

	};

	/**
	 * 设置滑动切换时候的切换图片
	 * 
	 * @param id
	 */
	private void setIndicateBg(int id) {
		switch (id) {
		case 0:
			goal_add_detial_title.setText("定义");
			goal_add_detail_tag_ly
					.setBackgroundResource(R.drawable.goal_add_detail_tag1);
			break;
		case 1:
			goal_add_detial_title.setText("动机");
			goal_add_detail_tag_ly
					.setBackgroundResource(R.drawable.goal_add_detail_tag2);
			break;
		case 2:
			goal_add_detial_title.setText("最好");
			goal_add_detail_tag_ly
					.setBackgroundResource(R.drawable.goal_add_detail_tag3);
			break;
		case 3:
			goal_add_detial_title.setText("最差");
			goal_add_detail_tag_ly
					.setBackgroundResource(R.drawable.goal_add_detail_tag4);
			break;

		default:
			break;
		}
	}

}
