package larson.pm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingAboutUsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_aboutus_page);
		
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		//������ť
		case R.id.setting_aboutus_help:
			startActivity(new Intent(SettingAboutUsActivity.this, SettingAboutHelpActivity.class));
			break;
			//�ƹ㰴ť
		case R.id.setting_share_iv:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(
					Intent.EXTRA_SUBJECT,
					"�������á��ڴ�������ͦ���õģ���Ҳ���԰ɣ����ص�ַ��http://192.168.0.118:8080/pm/UpDateServlet");
			intent.putExtra(
					Intent.EXTRA_TEXT,
					"�������á��ڴ�������ͦ���õģ���Ҳ���԰ɣ����ص�ַ��http://192.168.0.118:8080/pm/UpDateServlet");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getResources().getString(R.string.about_share)));
			break;
		default:
			break;
		}
	}
}
