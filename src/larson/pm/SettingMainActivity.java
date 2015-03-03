package larson.pm;

import java.util.Date;

import larson.pm.bean.UserInfo;
import larson.pm.utils.ApkUpdateUtil;
import larson.pm.utils.App;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingMainActivity extends Activity {
	private RelativeLayout setting_guesture_ly;
	private Context context = this;
	private int howoften;// 多久备份一次(天)
	private RelativeLayout setting_backup_time_ly;
	private RelativeLayout update_ly;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main_page);

		initView();
		
	}
	
	
	/**
	 * 初始化布局控件
	 */
	private void initView() {
		/**
		 * 用户中心
		 */
		LinearLayout setting_user_ly = (LinearLayout) findViewById(R.id.setting_user_ly);
		setting_user_ly.setOnClickListener(listener);
		
		/**
		 * 观看教程
		 */
		RelativeLayout setting_teach_ly = (RelativeLayout) findViewById(R.id.setting_teach_ly);
		setting_teach_ly.setOnClickListener(listener);

		/**
		 * 联系我们
		 */
		RelativeLayout setting_contactus_ly = (RelativeLayout) findViewById(R.id.setting_contactus_ly);
		setting_contactus_ly.setOnClickListener(listener);

		/**
		 * 跳转到备份恢复页面
		 */
		RelativeLayout setting_backrec_ly = (RelativeLayout) findViewById(R.id.setting_backrec_ly);
		setting_backrec_ly.setOnClickListener(listener);
		/**
		 * 软件升级
		 */
		update_ly = (RelativeLayout) findViewById(R.id.setting_update_ly);
		update_ly.setOnClickListener(listener);

		/**
		 * 关于软件按钮，介绍软件使用，关于开发团队
		 */
		RelativeLayout setting_about_ly = (RelativeLayout) findViewById(R.id.setting_about_ly);
		setting_about_ly.setOnClickListener(listener);

		/**
		 * 软件内容设置
		 */
		RelativeLayout setting_software_ly = (RelativeLayout) findViewById(R.id.setting_software_ly);
		setting_software_ly.setOnClickListener(listener);
		/**
		 * 设置手势密码
		 */
		setting_guesture_ly = (RelativeLayout) findViewById(R.id.setting_guesture_ly);
		setting_guesture_ly.setOnClickListener(listener);
		/**
		 * 设置是否需要手势 当手势开关改变后，手势可设置随之改变
		 */
		CheckBox setting_isguesture = (CheckBox) findViewById(R.id.setting_isguesture);
		setting_isguesture.setOnCheckedChangeListener(checklistener);
		setting_isguesture.setChecked(App.getInstance().getIsPwdSetted());
		setting_guesture_ly.setClickable(App.getInstance().getIsPwdSetted());

		/**
		 * 备份恢复相关 如果开关打开则备份设置可点击，反之不可 如果超过一个月未备份则提示用户
		 * 同时用户可以选择是否备份到云端，如果是，在恢复的时候优先恢复本地内容，本地没有就会拿云端内容
		 */
		CheckBox setting_open_autobackup_cb = (CheckBox) findViewById(R.id.setting_open_autobackup_cb);
		setting_open_autobackup_cb.setOnCheckedChangeListener(checklistener);
		setting_backup_time_ly = (RelativeLayout) findViewById(R.id.setting_backup_time_ly);
		setting_backup_time_ly.setOnClickListener(listener);
		
		TextView backup_hint = (TextView) findViewById(R.id.backup_hint);
		if (App.getInstance().getIsOutTime() == true)
			backup_hint.setText("好久没有备份了");
		else {
			backup_hint.setTextColor(Color.GRAY);
			backup_hint.setText("上次备份："
					+ new Date(App.getInstance().getLastBackupTime())
							.toLocaleString());
		}

		CheckBox setting_backup_cludy_cb = (CheckBox) findViewById(R.id.setting_backup_cludy_cb);
		setting_backup_cludy_cb.setOnCheckedChangeListener(checklistener);
	}

	/**
	 * checkBox改变监听
	 */
	private OnCheckedChangeListener checklistener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			/**
			 * 设置手势密码
			 */
			case R.id.setting_isguesture:
				App.getInstance().setPwdSetted(isChecked);
				if (isChecked == false) {
					setting_guesture_ly.setClickable(false);
				} else {
					setting_guesture_ly.setClickable(true);
				}
				break;
			case R.id.setting_open_autobackup_cb:
				/**
				 * 设置自动备份恢复 让周期是否可以设置随着自动备份改变
				 */
				App.getInstance().setIsAutoBackup(isChecked);
				setting_backup_time_ly.setClickable(isChecked);
				break;
			/**
			 * 是否备份到云端
			 */
			case R.id.setting_backup_cludy_cb:
				App.getInstance().setIsBackup2Cloud(isChecked);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 点击监听器
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			System.out.println("click-------");
			switch (v.getId()) {
			case R.id.setting_user_ly:
				if(App.isUserLogin==true)
				startActivity(new Intent(SettingMainActivity.this,
						SettingAccountActivity.class));
				else
					startActivity(new Intent(SettingMainActivity.this,
							LoginActivity.class));
					
				break;
			case R.id.setting_backrec_ly:
				startActivity(new Intent(SettingMainActivity.this,
						SettingBackRecActivity.class));
				break;
			case R.id.setting_about_ly:
				startActivity(new Intent(SettingMainActivity.this,
						SettingAboutUsActivity.class));
				break;
			case R.id.setting_software_ly:
				startActivity(new Intent(SettingMainActivity.this,
						SettingSoftwareActivity.class));
				break;
			case R.id.setting_guesture_ly:
				startActivity(new Intent(SettingMainActivity.this,
						CreateGesturePasswordActivity.class));
				break;

			case R.id.setting_update_ly:
				update_ly.setClickable(false);
				ApkUpdateUtil.getInstance().update(context, update_ly);
				break;
			case R.id.setting_contactus_ly:
				startActivity(new Intent(SettingMainActivity.this,
						SettingContractUSActivity.class));
				break;
			case R.id.setting_teach_ly:
				App.isSettingMode = true;
				startActivity(new Intent(SettingMainActivity.this,
						WhatsNewActivity.class));
				break;
			/**
			 * 自动备份频率
			 */
			case R.id.setting_backup_time_ly:
				String[] items = new String[] { "每天", "每周", "每月" };
				AlertDialog.Builder builder = new Builder(context);
				final ItemClickListener choseListener = new ItemClickListener();

				builder.setSingleChoiceItems(items, -1, choseListener)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										int whichone = choseListener.getWhich();
										if (whichone == 0)
											howoften = 1;
										else if (whichone == 1)
											howoften = 7;
										else if (whichone == 2)
											howoften = 30;

										App.getInstance().setBackupFrequence(
												howoften);
									}
								});
				builder.create().show();
				break;
			default:
				break;
			}
		}
	};

	private class ItemClickListener implements DialogInterface.OnClickListener {
		private int which;

		@Override
		public void onClick(DialogInterface dialog, int which) {
			this.which = which;
		}

		public int getWhich() {
			return which;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TextView setting_uid = (TextView) findViewById(R.id.setting_uid);
		TextView setting_unick = (TextView) findViewById(R.id.setting_unick);
		UserInfo user = App.currentUser;
		if(user==null){
			setting_uid.setText("未登录");
			setting_unick.setText("游客");
			return;
		}
		setting_uid.setText(user.getUsername());
		setting_unick.setText(user.getNickname());
	}
	
}
