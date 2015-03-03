package larson.pm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 目标创建类
 * 
 * @author Larson
 * 
 */
public class GoalAddActivity extends Activity {

	// 目标评级
	private float challengeLevel = 0.0f;
	private float clearLevel = 0.0f;
	private float emergencyLevel = 0.0f;
	private float importanceLevel = 0.0f;
	private float publicLevel = 0.0f;

	private Bundle b;// 储存数据

	private RatingBar goal_challengeLevel;
	private RatingBar goal_clearLevel;
	private RatingBar goal_emergencyLevel;
	private RatingBar goal_importanceLevel;
	private RatingBar goal_publicLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_add_level);

		initView();
		initHint();
	}
	
	/**
	 * 初始化ratingbar拖动提示
	 */
	private void initHint(){
		goal_hint_challenge_title = (TextView) findViewById(R.id.goal_hint_challenge_title);
		goal_hint_clear_title = (TextView) findViewById(R.id.goal_hint_clear_title);
		goal_hint_emergency_title = (TextView) findViewById(R.id.goal_hint_emergency_title);
		goal_hint_importance_title = (TextView) findViewById(R.id.goal_hint_importance_title);
		goal_hint_public_title = (TextView) findViewById(R.id.goal_hint_public_title);
		
		goal_hint_challenge_content = (TextView) findViewById(R.id.goal_hint_challenge_content);
		goal_hint_clear_content = (TextView) findViewById(R.id.goal_hint_clear_content);
		goal_hint_emergency_content = (TextView) findViewById(R.id.goal_hint_emergency_content);
		goal_hint_importance_content = (TextView) findViewById(R.id.goal_hint_importance_content);
		goal_hint_public_content = (TextView) findViewById(R.id.goal_hint_public_content);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		/*--------------- 设置帮助监听------------*/
		ImageButton goal_challengeLevel_help = (ImageButton) findViewById(R.id.goal_challengeLevel_help);
		goal_challengeLevel_help.setOnClickListener(listener);
		ImageButton goal_emergencyLevel_help = (ImageButton) findViewById(R.id.goal_emergencyLevel_help);
		goal_emergencyLevel_help.setOnClickListener(listener);
		ImageButton goal_importanceLevel_help = (ImageButton) findViewById(R.id.goal_importanceLevel_help);
		goal_importanceLevel_help.setOnClickListener(listener);
		ImageButton goal_publicLevel_help = (ImageButton) findViewById(R.id.goal_publicLevel_help);
		goal_publicLevel_help.setOnClickListener(listener);
		ImageButton goal_clearLevel_help = (ImageButton) findViewById(R.id.goal_clearLevel_help);
		goal_clearLevel_help.setOnClickListener(listener);

		/*-------------初始化评分条---------------*/
		goal_challengeLevel = (RatingBar) findViewById(R.id.goal_challengeLevel);
		goal_challengeLevel.setOnRatingBarChangeListener(rating_listener);
		goal_clearLevel = (RatingBar) findViewById(R.id.goal_clearLevel);
		goal_clearLevel.setOnRatingBarChangeListener(rating_listener);
		goal_emergencyLevel = (RatingBar) findViewById(R.id.goal_emergencyLevel);
		goal_emergencyLevel.setOnRatingBarChangeListener(rating_listener);
		goal_importanceLevel = (RatingBar) findViewById(R.id.goal_importanceLevel);
		goal_importanceLevel.setOnRatingBarChangeListener(rating_listener);
		goal_publicLevel = (RatingBar) findViewById(R.id.goal_publicLevel);
		goal_publicLevel.setOnRatingBarChangeListener(rating_listener);

		ImageView add_btn_next = (ImageView) findViewById(R.id.add_btn_next);
		add_btn_next.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			// ----------以下为级别帮助------------
			case R.id.goal_challengeLevel_help:
				popDialog("挑战级别：\n你对本目标难度定义，难度级别从1星到5星，星级越高，难度越大。");
				break;
			case R.id.goal_clearLevel_help:
				popDialog("清楚级别：\n你是否很清楚你设定的目标，必须做到目标很明确有利于你目标更容易实现，尽量对目标量化以便阶段审核");
				break;
			case R.id.goal_emergencyLevel_help:
				popDialog("紧急级别：\n永远记住紧急的事情最先做，然后是重要的事，这样能避免错失良机");
				break;
			case R.id.goal_importanceLevel_help:
				popDialog("重要级别：\n你必须明白这个目标在你心中的位置，对你有多重要，先把重要的做完再去做不重要的");
				break;
			case R.id.goal_publicLevel_help:
				popDialog("公开级别：\n每个人韧性有限，要想更多的坚持，需要人监督，所以，把你的目标告诉你5-7个好友，让他们帮忙监督");
				break;

			// -------------后退
			case R.id.add_btn_next:

				b = new Bundle();

				b.putFloat("challengeLevel", challengeLevel);
				b.putFloat("clearLevel", clearLevel);
				b.putFloat("emergencyLevel", emergencyLevel);
				b.putFloat("importanceLevel", importanceLevel);
				b.putFloat("publicLevel", publicLevel);
				Intent intent = new Intent(GoalAddActivity.this,
						GoalAddStepActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 弹出提示对话框
	 * 
	 * @param id
	 *            用户按下的提示键
	 */
	private void popDialog(String str) {
		AlertDialog dialog = new AlertDialog.Builder(this).setMessage(str)
				.create();
		dialog.show();

		// 以下几句设置对话框的透明度
		Window window = dialog.getWindow();
		LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		window.setAttributes(lp);
	}

	/**
	 * 程度评分条拖动监听
	 */
	private OnRatingBarChangeListener rating_listener = new OnRatingBarChangeListener() {

		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			/**
			 * 难度级别
			 */
			if (ratingBar == goal_challengeLevel) {
				challengeLevel = rating;
				if(rating<2.5){
					goal_hint_challenge_content.setText("难度较低，适用于短期内可以完成的计划");
					goal_hint_challenge_title.setText("较低难度");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=2.5&&rating<4){
					goal_hint_challenge_content.setText("难度中等，适用于目前能力能够胜任，但不是很轻易就能完成的计划");
					goal_hint_challenge_title.setText("中等难度");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=4){
					goal_hint_challenge_content.setText("较高难度，不明确是否能够完成，但是愿意尝试，挑战自己");
					goal_hint_challenge_title.setText("较高难度");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			/**
			 * 明确级别
			 */
			if (ratingBar == goal_clearLevel) {
				clearLevel = rating;
				if(rating<2.5){
					goal_hint_clear_content.setText("目标不明确，如果不想后期修改的话，建议你先弄清楚目标再设定");
					goal_hint_clear_title.setText("不太明确");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}else if(rating>=2.5&&rating<4){
					goal_hint_clear_content.setText("一般明确，你对你的目标有了一定的了解，但是还不是很明确，弄清楚目标有利于实现");
					goal_hint_clear_title.setText("一般明确");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_clear_content.setText("很明确，你已经知道你要干什么了，也知道怎么去实现了，加油");
					goal_hint_clear_title.setText("非常明确");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}
			}
			if (ratingBar == goal_emergencyLevel) {
				emergencyLevel = rating;
				if(rating<2.5){
					goal_hint_emergency_content.setText("不紧急，这个可以放到最后做");
					goal_hint_emergency_title.setText("不用着急");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=2.5&&rating<4){
					goal_hint_emergency_content.setText("还是尽早实现比较好");
					goal_hint_emergency_title.setText("比较紧急");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_emergency_content.setText("立刻，马上，实现这个目标，刻不容缓");
					goal_hint_emergency_title.setText("较高难度");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			if (ratingBar == goal_importanceLevel) {
				importanceLevel = rating;
				if(rating<2.5){
					goal_hint_importance_content.setText("不太重要，设立这个目标仅仅是以备不时之需。或者闲着有空的时候实现");
					goal_hint_importance_title.setText("不太重要");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=2.5&&rating<4){
					goal_hint_importance_content.setText("比较重要，我知道这个目标对我有用，我应该尽可能实现");
					goal_hint_importance_title.setText("比较重要");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_importance_content.setText("很重要，这个目标非实现不可，不然寝食难安");
					goal_hint_importance_title.setText("非常重要");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			if (ratingBar == goal_publicLevel) {
				publicLevel = rating;
				if(rating<2.5){
					goal_hint_public_content.setText("公开程度太低，建议把你的目标告诉其他人，让他们监督你实行");
					goal_hint_public_title.setText("偏向保密");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}else if(rating>=2.5&&rating<4){
					goal_hint_public_content.setText("稍微公开，你是怕你不能完成吗");
					goal_hint_public_title.setText("一般公开");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_public_content.setText("很不错，你已经让其他人知道了你的目标");
					goal_hint_public_title.setText("完全公开");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
					
				}
			}
		}
	};

	/**
	 * 用户的按键监听
	 */

	private TextView goal_hint_challenge_title;
	private TextView goal_hint_clear_title;
	private TextView goal_hint_emergency_title;
	private TextView goal_hint_importance_title;
	private TextView goal_hint_public_title;
	private TextView goal_hint_challenge_content;
	private TextView goal_hint_clear_content;
	private TextView goal_hint_emergency_content;
	private TextView goal_hint_importance_content;
	private TextView goal_hint_public_content;	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(GoalAddActivity.this, GoalActivity.class));
			finish();
		}
		return true;
	}

}
