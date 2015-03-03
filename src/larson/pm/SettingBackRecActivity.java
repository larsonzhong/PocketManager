package larson.pm;

import java.util.Date;

import larson.pm.utils.App;
import larson.pm.utils.NetUtil;
import larson.pm.utils.SaveDB;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���ݵı�����ָ�
 * 
 * @author Larson
 * 
 */
public class SettingBackRecActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_bak_rec);

		TextView setting_data_backup = (TextView) findViewById(R.id.setting_data_backup);
		setting_data_backup.setOnClickListener(this);
		TextView setting_data_recovery = (TextView) findViewById(R.id.setting_data_recovery);
		setting_data_recovery.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		/**
		 * ����������,���粻���þʹ��������ý���
		 */
		if (App.getInstance().getIsBackup2Cloud()) {
			boolean isnetAviliable = new NetUtil().isNetworkAvailable(this);
			if (!isnetAviliable)
				openNetwork();
		}
		switch (v.getId()) {
		case R.id.setting_data_backup:
			boolean isBakOK = new SaveDB().backupAll2File(this);
			if (isBakOK == true) {
				Toast.makeText(this, "���ݳɹ�", Toast.LENGTH_SHORT).show();
				App.getInstance().setLastBackupTime(new Date().getTime());
			} else
				Toast.makeText(this, "����ʧ��,�鷳��������", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.setting_data_recovery:
			boolean isRecOK = new SaveDB().recoveryAllFromFile(this);
			if(isRecOK==true){
				Toast.makeText(this, "���ݿ�ָ��ɹ�", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(this, "���ݿ�ָ�ʧ��~~����", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * ת��������Ľ���
	 */
	private void openNetwork() {
		new AlertDialog.Builder(this)
				.setTitle("��ܰ��ʾ")
				.setMessage("�ף�������������δ��Ŷ")
				.setPositiveButton("ǰ����",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						}).setNegativeButton("ȡ��", null).create().show();
	}
}
