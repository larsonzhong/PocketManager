package larson.pm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class TimeMainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_main_page);

		initView();

	}

	/**
	 * 初始化系统组件
	 */
	private void initView() {
		/**
		 * 定时器
		 */
		ImageButton time_timer_btn = (ImageButton) findViewById(R.id.time_timer_btn);
		time_timer_btn.setOnClickListener(listener);
		/**
		 * 情景模式管理
		 */
		ImageButton time_profiles_btn = (ImageButton) findViewById(R.id.time_profiles_btn);
		time_profiles_btn.setOnClickListener(listener);
		/**
		 * 事务管理
		 */
		ImageButton time_reminder_btn = (ImageButton) findViewById(R.id.time_reminder_btn);
		time_reminder_btn.setOnClickListener(listener);
		/**
		 * 时间设置界面
		 */
		ImageButton time_setting_btn = (ImageButton) findViewById(R.id.time_setting_btn);
		time_setting_btn.setOnClickListener(listener);
		
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.time_timer_btn:
				openActivity(TimeTimerActivity.class);
				break;
			case R.id.time_profiles_btn:
				openActivity(ProfilesActivity.class);
				break;
			case R.id.time_reminder_btn:
				openActivity(ReminderActivity.class);
				break;
			case R.id.time_setting_btn:
				openActivity(TimeSmsActivity.class);
				break;
			default:
				break;
			}
		}
	};

	private <T>void openActivity(Class<T> clazz) {
		startActivity(new Intent(TimeMainActivity.this, clazz));
	}
}
