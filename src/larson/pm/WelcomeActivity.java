package larson.pm;

import java.util.Date;

import larson.pm.utils.App;
import larson.pm.utils.Constant;
import larson.pm.utils.SaveDB;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		checkRun();
		checkConfig();
		finish();
	}
	
	/**
	 * 检测系统配置
	 */
	private void checkConfig(){
		/**
		 * 1.检查备份频率（lastBackupTime）
		 * 备份所有数据
		 * 如果超时了就记录下
		 */
		boolean isauto = App.getInstance().getIsAutoBackup();
		long currentTime = new Date().getTime()/(24*3600*1000);
		long lastBackupTime = App.getInstance().getLastBackupTime()/(24*3600*1000);
		long howoften = App.getInstance().getBackupFrequence();
		boolean isOutTime = (currentTime-lastBackupTime-howoften)>=0?true:false;
		System.out.println("最后一次备份"+lastBackupTime+"---------"+"现在时间"+currentTime+"中间间隔"+howoften);
		App.getInstance().setIsOutTime(isOutTime);
		if(isauto==true){
			if(isOutTime==true){
				new SaveDB().backupAll2File(this);
				Toast.makeText(this, "自动备份成功", Toast.LENGTH_SHORT).show();
			}
		}
		/**
		 * 2. 检查自动更新
		 */
	}

	/**
	 * 检测运行环境
	 */
	private void checkRun() {
		SharedPreferences sp = getSharedPreferences("wlb", MODE_PRIVATE);
		boolean isFirstRun = sp.getBoolean("isFirstRun", true);
		App.getInstance().setFirstRun(isFirstRun);
		boolean isPwdSetted = sp.getBoolean("isPwdSetted", false);
		App.getInstance().setPwdSetted(isPwdSetted);
		System.out.println(isPwdSetted+"--------------------------"+isFirstRun);
		
		if (isFirstRun == true) {
			/**
			 * 首次运行创建快捷方式
			 */
			createShut();
			
			/**
			 * 第1步：设置首次运行为false
			 */
			Editor editor = sp.edit();
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			/**
			 * 第2步：提示设置密码
			 */
			startActivity(new Intent(WelcomeActivity.this,
					GuideGesturePasswordActivity.class));
		} else {
			/**
			 * 否则需要检查密码
			 */
			if (isPwdSetted == true){
				Intent intent = new Intent(WelcomeActivity.this,
						UnlockGesturePasswordActivity.class);
				intent.putExtra("whichActivity", Constant.WHICH_ACTIVITY_WELCOME);
				startActivity(intent);
				
			}

			else
				startActivity(new Intent(WelcomeActivity.this,
						MainActivity.class));

		}
	}
	
	/**
	 * 创建桌面快捷方式
	 */
	public void createShut() {
		// 创建添加快捷方式的Intent
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		String title = getResources().getString(R.string.app_name);
		// 加载快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				WelcomeActivity.this, R.drawable.logo);
		// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
		Intent myIntent = new Intent(WelcomeActivity.this,
				WelcomeActivity.class);
		// 设置快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 设置快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 设置快捷方式对应的Intent
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// 发送广播添加快捷方式
		sendBroadcast(addIntent);
		
		Toast.makeText(this, "创建桌面快捷方式", Toast.LENGTH_SHORT).show();
	}
}
