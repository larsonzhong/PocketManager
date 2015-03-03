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
	private ImageButton autoprofiles_help;// ��ײ��İ���ͼ��
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

		init();// -----------------------------����ɾ���ĳ�ʼ��

		// ������ť���
		autoprofiles_help = (ImageButton) findViewById(R.id.autoprofiles_help);
		autoprofiles_help.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Dialog dialog = new Dialog(ProfilesActivity.this);
				// ��������ContentView
				dialog.setContentView(R.layout.auto_profiles_help);
				WindowManager.LayoutParams lp = dialog.getWindow()
						.getAttributes();
				lp.alpha = 0.6f; // 0.0-1.0
				dialog.getWindow().setAttributes(lp);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
			}
		});

		// ������ݵİ�ť
		ImageButton auto_add_button = (ImageButton) findViewById(R.id.auto_addbtn);
		auto_add_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProfilesActivity.this,
						ProfilesAddActiivity.class);
				startActivity(intent);
				// ���뵭��
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

	}

	// ��������Ҫˢ�½��棬���»�ȡadapter
	@Override
	protected void onResume() {
		super.onResume();
		autoprofiles = dao.quertAll();
		adapter = new ProfilesAdapter(context, autoprofiles);
		autoprofiles_listview.setAdapter(adapter);
		autoprofiles = dao.quertAll();
	}

	/*--------------һ��Ϊremove��������----------------*/
	// private ArrayAdapter<String> adapter;
	private SlideCutListView autoprofiles_listview;//
	private ProfilesAdapter adapter;

	private void init() {
		// listview����
		autoprofiles_listview = (SlideCutListView) findViewById(R.id.autoprofiles_listview);
		autoprofiles_listview.setRemoveListener(this);

		/**
		 * ���˰�ť
		 */
		ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// �������������-------�����ݿ��ж�ȡ
		autoprofiles = dao.quertAll();
		ProfilesAdapter adapter = new ProfilesAdapter(context, autoprofiles);
		autoprofiles_listview.setAdapter(adapter);
	}

	// ??????????????????????????????????????? ȡ������
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

	// ����ɾ��֮��Ļص�����positionָҪɾ����λ�ú�
	public void removeItem(RemoveDirection direction, int position,View view) {
		// ִ��ɾ�����ݿ����ϸ���listview
		autoprofiles = dao.quertAll();
		Autoprofiles id_deletes = autoprofiles.get(position);
		String _delete = id_deletes.getTime();
		dao.delete(_delete);
		// ---����������������������������----------��������Ҫɾ��am������
		cancelAlarm(id_deletes);// ?????????????????????????????????????????????????????????ȡ������

		// dao.delete(autoprofiles.get(position).getId());
		autoprofiles = dao.quertAll();
		adapter = new ProfilesAdapter(context, autoprofiles);
		adapter.notifyDataSetChanged();// ��̬ˢ��
		autoprofiles_listview.setAdapter(adapter);

		switch (direction) {
		case RIGHT:
			Toast.makeText(this, "ȡ���ɹ�", Toast.LENGTH_SHORT)
					.show();
			break;
		case LEFT:
			Toast.makeText(this, "ȡ���趨  " + position, Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}
}