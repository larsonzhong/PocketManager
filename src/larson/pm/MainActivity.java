package larson.pm;


import larson.pm.utils.SystemInfoUtil;
import larson.pm.view.MyImageView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private MyImageView fuction_fond_iv;
	private MyImageView fuction_goal_iv;
	private MyImageView fuction_time_iv;
	private MyImageView fuction_setting_iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initPixels();

		initWidget();
	}

	

	/**
	 * 初始化屏幕像素
	 */
	private void initPixels() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		SystemInfoUtil.SYS_SCREEN_WIDTH = dm.widthPixels;
		SystemInfoUtil.SYS_SCREEN_HEIGHT = dm.heightPixels;
	}

	/**
	 * 初始化系统控件
	 */
	private void initWidget() {
		fuction_fond_iv = (MyImageView) findViewById(R.id.fuction_fond_iv);
		fuction_fond_iv.setOnClickListener(this);

		fuction_goal_iv = (MyImageView) findViewById(R.id.fuction_goal_iv);
		fuction_goal_iv.setOnClickListener(this);

		fuction_time_iv = (MyImageView) findViewById(R.id.fuction_time_iv);
		fuction_time_iv.setOnClickListener(this);

		fuction_setting_iv = (MyImageView) findViewById(R.id.fuction_setting_iv);
		fuction_setting_iv.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fuction_fond_iv:
			openActivity(FondActivity.class);
			break;
		case R.id.fuction_goal_iv:
			openActivity(GoalActivity.class);
			break;
		case R.id.fuction_time_iv:
			openActivity(TimeMainActivity.class);
			break;
		case R.id.fuction_setting_iv:
			openActivity(SettingMainActivity.class);
			break;
		default:
			break;
		}
	}
	/**
//	 * 敬请期待
//	 */
//	private void showDialog(){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("不好意思,尚未开放，敬请期待")
//		       .setCancelable(false)
//		       .setIcon(R.drawable.logo)
//		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		        	   dialog.cancel();
//		           }
//		       });
//		AlertDialog alert = builder.create();
//		alert.show();
//	}

	private <T> void openActivity(Class<T> class1) {
		Intent intent = new Intent(MainActivity.this, class1);
		startActivity(intent);
	}

}
