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
		//帮助按钮
		case R.id.setting_aboutus_help:
			startActivity(new Intent(SettingAboutUsActivity.this, SettingAboutHelpActivity.class));
			break;
			//推广按钮
		case R.id.setting_share_iv:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(
					Intent.EXTRA_SUBJECT,
					"我正在用《口袋管理》，挺好用的，你也试试吧！下载地址：http://192.168.0.118:8080/pm/UpDateServlet");
			intent.putExtra(
					Intent.EXTRA_TEXT,
					"我正在用《口袋管理》，挺好用的，你也试试吧！下载地址：http://192.168.0.118:8080/pm/UpDateServlet");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getResources().getString(R.string.about_share)));
			break;
		default:
			break;
		}
	}
}
