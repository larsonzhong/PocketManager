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
	private int howoften;// ��ñ���һ��(��)
	private RelativeLayout setting_backup_time_ly;
	private RelativeLayout update_ly;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main_page);

		initView();
		
	}
	
	
	/**
	 * ��ʼ�����ֿؼ�
	 */
	private void initView() {
		/**
		 * �û�����
		 */
		LinearLayout setting_user_ly = (LinearLayout) findViewById(R.id.setting_user_ly);
		setting_user_ly.setOnClickListener(listener);
		
		/**
		 * �ۿ��̳�
		 */
		RelativeLayout setting_teach_ly = (RelativeLayout) findViewById(R.id.setting_teach_ly);
		setting_teach_ly.setOnClickListener(listener);

		/**
		 * ��ϵ����
		 */
		RelativeLayout setting_contactus_ly = (RelativeLayout) findViewById(R.id.setting_contactus_ly);
		setting_contactus_ly.setOnClickListener(listener);

		/**
		 * ��ת�����ݻָ�ҳ��
		 */
		RelativeLayout setting_backrec_ly = (RelativeLayout) findViewById(R.id.setting_backrec_ly);
		setting_backrec_ly.setOnClickListener(listener);
		/**
		 * �������
		 */
		update_ly = (RelativeLayout) findViewById(R.id.setting_update_ly);
		update_ly.setOnClickListener(listener);

		/**
		 * ���������ť���������ʹ�ã����ڿ����Ŷ�
		 */
		RelativeLayout setting_about_ly = (RelativeLayout) findViewById(R.id.setting_about_ly);
		setting_about_ly.setOnClickListener(listener);

		/**
		 * �����������
		 */
		RelativeLayout setting_software_ly = (RelativeLayout) findViewById(R.id.setting_software_ly);
		setting_software_ly.setOnClickListener(listener);
		/**
		 * ������������
		 */
		setting_guesture_ly = (RelativeLayout) findViewById(R.id.setting_guesture_ly);
		setting_guesture_ly.setOnClickListener(listener);
		/**
		 * �����Ƿ���Ҫ���� �����ƿ��ظı�����ƿ�������֮�ı�
		 */
		CheckBox setting_isguesture = (CheckBox) findViewById(R.id.setting_isguesture);
		setting_isguesture.setOnCheckedChangeListener(checklistener);
		setting_isguesture.setChecked(App.getInstance().getIsPwdSetted());
		setting_guesture_ly.setClickable(App.getInstance().getIsPwdSetted());

		/**
		 * ���ݻָ���� ������ش��򱸷����ÿɵ������֮���� �������һ����δ��������ʾ�û�
		 * ͬʱ�û�����ѡ���Ƿ񱸷ݵ��ƶˣ�����ǣ��ڻָ���ʱ�����Ȼָ��������ݣ�����û�оͻ����ƶ�����
		 */
		CheckBox setting_open_autobackup_cb = (CheckBox) findViewById(R.id.setting_open_autobackup_cb);
		setting_open_autobackup_cb.setOnCheckedChangeListener(checklistener);
		setting_backup_time_ly = (RelativeLayout) findViewById(R.id.setting_backup_time_ly);
		setting_backup_time_ly.setOnClickListener(listener);
		
		TextView backup_hint = (TextView) findViewById(R.id.backup_hint);
		if (App.getInstance().getIsOutTime() == true)
			backup_hint.setText("�þ�û�б�����");
		else {
			backup_hint.setTextColor(Color.GRAY);
			backup_hint.setText("�ϴα��ݣ�"
					+ new Date(App.getInstance().getLastBackupTime())
							.toLocaleString());
		}

		CheckBox setting_backup_cludy_cb = (CheckBox) findViewById(R.id.setting_backup_cludy_cb);
		setting_backup_cludy_cb.setOnCheckedChangeListener(checklistener);
	}

	/**
	 * checkBox�ı����
	 */
	private OnCheckedChangeListener checklistener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			/**
			 * ������������
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
				 * �����Զ����ݻָ� �������Ƿ�������������Զ����ݸı�
				 */
				App.getInstance().setIsAutoBackup(isChecked);
				setting_backup_time_ly.setClickable(isChecked);
				break;
			/**
			 * �Ƿ񱸷ݵ��ƶ�
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
	 * ���������
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
			 * �Զ�����Ƶ��
			 */
			case R.id.setting_backup_time_ly:
				String[] items = new String[] { "ÿ��", "ÿ��", "ÿ��" };
				AlertDialog.Builder builder = new Builder(context);
				final ItemClickListener choseListener = new ItemClickListener();

				builder.setSingleChoiceItems(items, -1, choseListener)
						.setPositiveButton("ȷ��",
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
			setting_uid.setText("δ��¼");
			setting_unick.setText("�ο�");
			return;
		}
		setting_uid.setText(user.getUsername());
		setting_unick.setText(user.getNickname());
	}
	
}
