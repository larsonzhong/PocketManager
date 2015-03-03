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
 * 数据的备份与恢复
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
		 * 测试网络先,网络不可用就打开网络设置界面
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
				Toast.makeText(this, "备份成功", Toast.LENGTH_SHORT).show();
				App.getInstance().setLastBackupTime(new Date().getTime());
			} else
				Toast.makeText(this, "备份失败,麻烦反馈我们", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.setting_data_recovery:
			boolean isRecOK = new SaveDB().recoveryAllFromFile(this);
			if(isRecOK==true){
				Toast.makeText(this, "数据库恢复成功", Toast.LENGTH_SHORT).show();
			}else
				Toast.makeText(this, "数据库恢复失败~~呃呃", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 转到打开网络的界面
	 */
	private void openNetwork() {
		new AlertDialog.Builder(this)
				.setTitle("温馨提示")
				.setMessage("亲！您的网络连接未打开哦")
				.setPositiveButton("前往打开",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						}).setNegativeButton("取消", null).create().show();
	}
}
