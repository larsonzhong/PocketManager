package larson.pm;

import larson.pm.listener.OnViewChangeListener;
import larson.pm.utils.App;
import larson.pm.view.MyScrollLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WhatsNewActivity extends Activity implements OnViewChangeListener {

	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private Button startBtn;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//2.如果不是首次运行，并且不是由设置界面进来的，就直接到welcome里面
		SharedPreferences sp = getSharedPreferences("wlb", MODE_PRIVATE);
		boolean isFirstRun = sp.getBoolean("isFirstRun", true);
		if(isFirstRun==false&&App.isSettingMode==false){
			startActivity(new Intent(WhatsNewActivity.this, WelcomeActivity.class));
			finish();
		}
		
		//否则进入展示页面
		setContentView(R.layout.whats_new_page);
		initView();
	}

	private void initView() {
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(onClick);
		animLayout = (LinearLayout) findViewById(R.id.animLayout);
		leftLayout = (LinearLayout) findViewById(R.id.leftLayout);
		rightLayout = (LinearLayout) findViewById(R.id.rightLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}

	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				mScrollLayout.setVisibility(View.GONE);
				pointLLayout.setVisibility(View.GONE);
				animLayout.setVisibility(View.VISIBLE);
				mainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);
				Animation leftOutAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.translate_left);
				Animation rightOutAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.translate_right);
				leftLayout.setAnimation(leftOutAnimation);
				rightLayout.setAnimation(rightOutAnimation);
				leftOutAnimation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						mainRLayout.setBackgroundColor(getResources().getColor(R.color.trans));
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						/**
						 * 播放完毕，判断是从哪里进来的，如果是开启软件就转到首页，否则转到设置界面
						 */
						leftLayout.setVisibility(View.GONE);
						rightLayout.setVisibility(View.GONE);
						Intent intent;
						if(App.isSettingMode){
							intent = new Intent(WhatsNewActivity.this,SettingMainActivity.class);
						}else{
							intent = new Intent(WhatsNewActivity.this,WelcomeActivity.class);
						}
								
						WhatsNewActivity.this.startActivity(intent);
						WhatsNewActivity.this.finish();
						overridePendingTransition(R.anim.anim_enter,
								R.anim.anim_exit);
					}
				});
				break;
			}
		}
	};

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}
}