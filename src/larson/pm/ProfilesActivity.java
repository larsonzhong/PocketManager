package larson.pm;

import java.util.List;

import larson.pm.adapter.ProfilesAdapter;
import larson.pm.bean.Autoprofiles;
import larson.pm.dao.AutoprofilesDao;
import larson.pm.receiver.AlarmBroadcastReceiver;
import larson.pm.utils.Constant;
import larson.pm.view.SlideCutListView;
import larson.pm.view.SlideCutListView.RemoveDirection;
import larson.pm.view.SlideCutListView.RemoveListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfilesActivity extends Activity implements RemoveListener {
	private ImageButton autoprofiles_help;// 最底部的帮助图标
	private Context context = this;
	private List<Autoprofiles> autoprofiles;
	private AutoprofilesDao dao = new AutoprofilesDao(context);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.auto_profiles);
		dao = new AutoprofilesDao(context);

		init();// -----------------------------滑动删除的初始化

		// 帮助按钮点击
		autoprofiles_help = (ImageButton) findViewById(R.id.autoprofiles_help);
		autoprofiles_help.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Dialog dialog = new Dialog(ProfilesActivity.this);
				// 设置它的ContentView
				dialog.setContentView(R.layout.auto_profiles_help);
				WindowManager.LayoutParams lp = dialog.getWindow()
						.getAttributes();
				lp.alpha = 0.6f; // 0.0-1.0
				dialog.getWindow().setAttributes(lp);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
			}
		});

		// 添加数据的按钮
		ImageButton auto_add_button = (ImageButton) findViewById(R.id.auto_addbtn);
		auto_add_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProfilesActivity.this,
						ProfilesAddActiivity.class);
				startActivity(intent);
				// 淡入淡出
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

	}

	// 返回来就要刷新界面，重新获取adapter
	@Override
	protected void onResume() {
		super.onResume();
		autoprofiles = dao.quertAll();
		adapter = new ProfilesAdapter(context, autoprofiles);
		autoprofiles_listview.setAdapter(adapter);
		autoprofiles = dao.quertAll();
	}

	/*--------------一下为remove动画代码----------------*/
	// private ArrayAdapter<String> adapter;
	private SlideCutListView autoprofiles_listview;//
	private ProfilesAdapter adapter;

	private void init() {
		// listview配置
		autoprofiles_listview = (SlideCutListView) findViewById(R.id.autoprofiles_listview);
		autoprofiles_listview.setRemoveListener(this);

		/**
		 * 后退按钮
		 */
		ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 在这里添加数据-------从数据库中读取
		autoprofiles = dao.quertAll();
		ProfilesAdapter adapter = new ProfilesAdapter(context, autoprofiles);
		autoprofiles_listview.setAdapter(adapter);
	}

	// ??????????????????????????????????????? 取消设置
	public void cancelAlarm(Autoprofiles apf) {
		AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = null;
		int id = apf.getId();
		String mode = apf.getProfiles();
		Intent intent = new Intent();
		if (mode.equals(Constant.PROFILES_INDOOR_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.RING_CHANGED);
			intent.putExtra("checkedId", R.id.ring);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
		}
		if (mode.equals(Constant.PROFILES_OUTDOOR_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.RV_CHANGED);
			intent.putExtra("checkedId", R.id.ring_and_vibrate);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);
		}
		if (mode.equals(Constant.PROFILES_MEETING_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.VIBRATE_CHANGED);
			intent.putExtra("checkedId", R.id.vibrate);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);
		}
		if (mode.equals(Constant.PROFILES_SILENT_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.SILENT_CHANGED);
			intent.putExtra("checkedId", R.id.silent);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);
		}
		if (mode.equals(Constant.PROFILES_OFFLINE_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.OFFLINE_CHANGED);
			intent.putExtra("checkedId", R.id.offline);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);
		}
		if (mode.equals(Constant.PROFILES_CALL_IN_NAME)) {
			intent = new Intent(AlarmBroadcastReceiver.CALL_IN_CHANGED);
			intent.putExtra("checkedId", R.id.call_in);
			alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);
		}
		alarms.cancel(alarmIntent);
	}

	// 滑动删除之后的回调方法position指要删除的位置号
	public void removeItem(RemoveDirection direction, int position,View view) {
		// 执行删除数据库马上更新listview
		autoprofiles = dao.quertAll();
		Autoprofiles id_deletes = autoprofiles.get(position);
		String _delete = id_deletes.getTime();
		dao.delete(_delete);
		// ---？？？？？？？？？？？？？？----------？？？还要删除am的闹钟
		cancelAlarm(id_deletes);// ?????????????????????????????????????????????????????????取消设置

		// dao.delete(autoprofiles.get(position).getId());
		autoprofiles = dao.quertAll();
		adapter = new ProfilesAdapter(context, autoprofiles);
		adapter.notifyDataSetChanged();// 动态刷新
		autoprofiles_listview.setAdapter(adapter);

		switch (direction) {
		case RIGHT:
			Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT)
					.show();
			break;
		case LEFT:
			Toast.makeText(this, "取消设定  " + position, Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}
}