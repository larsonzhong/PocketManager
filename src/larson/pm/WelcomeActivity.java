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
	 * ���ϵͳ����
	 */
	private void checkConfig(){
		/**
		 * 1.��鱸��Ƶ�ʣ�lastBackupTime��
		 * ������������
		 * �����ʱ�˾ͼ�¼��
		 */
		boolean isauto = App.getInstance().getIsAutoBackup();
		long currentTime = new Date().getTime()/(24*3600*1000);
		long lastBackupTime = App.getInstance().getLastBackupTime()/(24*3600*1000);
		long howoften = App.getInstance().getBackupFrequence();
		boolean isOutTime = (currentTime-lastBackupTime-howoften)>=0?true:false;
		System.out.println("���һ�α���"+lastBackupTime+"---------"+"����ʱ��"+currentTime+"�м���"+howoften);
		App.getInstance().setIsOutTime(isOutTime);
		if(isauto==true){
			if(isOutTime==true){
				new SaveDB().backupAll2File(this);
				Toast.makeText(this, "�Զ����ݳɹ�", Toast.LENGTH_SHORT).show();
			}
		}
		/**
		 * 2. ����Զ�����
		 */
	}

	/**
	 * ������л���
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
			 * �״����д�����ݷ�ʽ
			 */
			createShut();
			
			/**
			 * ��1���������״�����Ϊfalse
			 */
			Editor editor = sp.edit();
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			/**
			 * ��2������ʾ��������
			 */
			startActivity(new Intent(WelcomeActivity.this,
					GuideGesturePasswordActivity.class));
		} else {
			/**
			 * ������Ҫ�������
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
	 * ���������ݷ�ʽ
	 */
	public void createShut() {
		// ������ӿ�ݷ�ʽ��Intent
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		String title = getResources().getString(R.string.app_name);
		// ���ؿ�ݷ�ʽ��ͼ��
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				WelcomeActivity.this, R.drawable.logo);
		// ���������ݷ�ʽ�����Intent,�ô�����������Ŀ�ݷ�ʽ���ٴ������ó���
		Intent myIntent = new Intent(WelcomeActivity.this,
				WelcomeActivity.class);
		// ���ÿ�ݷ�ʽ�ı���
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// ���ÿ�ݷ�ʽ��ͼ��
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// ���ÿ�ݷ�ʽ��Ӧ��Intent
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// ���͹㲥��ӿ�ݷ�ʽ
		sendBroadcast(addIntent);
		
		Toast.makeText(this, "���������ݷ�ʽ", Toast.LENGTH_SHORT).show();
	}
}
