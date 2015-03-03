package larson.pm;

import java.util.Calendar;
import java.util.List;

import larson.pm.bean.Autoprofiles;
import larson.pm.dao.AutoprofilesDao;
import larson.pm.receiver.AlarmBroadcastReceiver;
import larson.pm.utils.Constant;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

//------------Ϊ������⣬��ʱ�������⣬ɾ����¼���⼰ɾ��ʱ�������⣬���Ӷ�������
public class ProfilesAddActiivity extends Activity {
	String TAG = "RingToggle";
	protected boolean isChange;
	private String tp_time;
	private AlarmManager alarms;
	private TimePicker tp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_add);

		alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		tp = (TimePicker) findViewById(R.id.timePkr);
		tp.setIs24HourView(true);
		checkTime(tp);
		tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				tp_time = hourOfDay + ":" + minute;
			}
		});

		// ���onChangeListener
		RadioGroup group = (RadioGroup) findViewById(R.id.menu);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (isChange)
					return;

				switch (checkedId) {
				case R.id.ring_and_vibrate:
					ringAndVibrate();
					break;
				case R.id.ring:
					ring();
					break;
				case R.id.vibrate:
					vibrate();
					break;
				case R.id.silent:
					silent();
					break;
				case R.id.offline:
					offline();
					break;
				case R.id.call_in:
					call_in();
					break;
				}

				RadioButton radio = (RadioButton) findViewById(checkedId);
				if (radio != null)
					radio.setTextSize(30);

				ProfilesAddActiivity.this.waitClosing();
			}
		});
		// RadioButton��Ӽ�������������ֱ���Ч��
		for (int i = 0, l = group.getChildCount(); i < l; i++) {
			RadioButton radio = (RadioButton) group.getChildAt(i);
			radio.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					RadioButton radio = (RadioButton) v;
					if (!radio.isChecked())
						return false;

					radio.setTextSize(30);
					ProfilesAddActiivity.this.waitClosing();

					return false;
				}
			});
		}
	}
	
	/**
	 * У׼timepicker��ʱ��
	 * @param tp
	 */
	private void checkTime(TimePicker tp){
		Calendar calendar = Calendar.getInstance();
		tp.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateRadioGroup();
	}

	// �����龰ģʽ
	protected void updateRadioGroup() {
		// int checkedId = currentMode();
		RadioButton checked = (RadioButton) findViewById(R.id.auto_default_select);
		// RadioButton checked = (RadioButton) findViewById(checkedId);
		isChange = true;
		checked.setChecked(true);
		isChange = false;
	}

	// ȡ�õ�ǰ�龰ģʽ-------(û����)
	protected int currentMode() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		switch (audio.getRingerMode()) {
		case AudioManager.RINGER_MODE_SILENT:
			return R.id.silent;
		case AudioManager.RINGER_MODE_VIBRATE:
			return R.id.vibrate;
		}

		if (audio.shouldVibrate(AudioManager.VIBRATE_TYPE_RINGER))
			return R.id.ring_and_vibrate;

		return R.id.ring;
	}

	// ��������
	protected void ringAndVibrate() {
		Log.e(TAG, "" + R.id.ring_and_vibrate);
		saveToSQL(Constant.PROFILES_OUTDOOR_NAME);// ��д�ٲ��ȡ��ʱ��Ȼ���������壬��ʶΪtp_time
		Intent intent = new Intent(AlarmBroadcastReceiver.RV_CHANGED);
		intent.putExtra("checkedId", R.id.ring_and_vibrate);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, 0);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// д�����ݿ�
	private void saveToSQL(String profiles) {
		if (tp_time == null)
			Toast.makeText(ProfilesAddActiivity.this, "��û���趨ʱ�䣬����������Ч", Toast.LENGTH_SHORT)
					.show();
		else {
			AutoprofilesDao dao = new AutoprofilesDao(ProfilesAddActiivity.this);
			Autoprofiles a = new Autoprofiles(tp_time, profiles, 1);
			dao.insert(a);
		}
	}

	// ͨ��tp_time��ȡ����id��������ʶ��,���ص�ǰid---------���쳣��û������ʱ�䣩
	public int readIdfromDB() {
		AutoprofilesDao dao;
		int id = -1;
		try {
			dao = new AutoprofilesDao(ProfilesAddActiivity.this);
			List<Autoprofiles> aps = dao.query("time", tp_time);
			id = aps.get(0).getId();
		} catch (Exception e) {
			Toast.makeText(ProfilesAddActiivity.this, "��û��ѡ��ʱ��", Toast.LENGTH_SHORT).show();
		}
		return id;
	}

	// ����
	protected void ring() {
		Log.e(TAG, "" + R.id.ring);
		saveToSQL(Constant.PROFILES_INDOOR_NAME);
		Intent intent = new Intent(AlarmBroadcastReceiver.RING_CHANGED);
		intent.putExtra("checkedId", R.id.ring);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// ��
	protected void vibrate() {
		Log.e(TAG, "" + R.id.vibrate);
		saveToSQL(Constant.PROFILES_MEETING_NAME);
		Intent intent = new Intent(AlarmBroadcastReceiver.VIBRATE_CHANGED);
		intent.putExtra("checkedId", R.id.vibrate);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, 0);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// ����
	protected void silent() {
		Log.e(TAG, "" + R.id.silent);
		saveToSQL(Constant.PROFILES_SILENT_NAME);
		Intent intent = new Intent(AlarmBroadcastReceiver.SILENT_CHANGED);
		intent.putExtra("checkedId", R.id.silent);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, 0);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// ����ģʽ
	protected void offline() {
		saveToSQL(Constant.PROFILES_OFFLINE_NAME);
		Intent intent = new Intent(AlarmBroadcastReceiver.OFFLINE_CHANGED);
		intent.putExtra("checkedId", R.id.offline);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, 0);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// ����ģʽ
	protected void call_in() {
		saveToSQL(Constant.PROFILES_CALL_IN_NAME);
		Intent intent = new Intent(AlarmBroadcastReceiver.CALL_IN_CHANGED);
		intent.putExtra("checkedId", R.id.call_in);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
				readIdfromDB(), intent, 0);
		Log.e(TAG, "" + intent);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), alarmIntent);
	}

	// �رճ���
	protected void waitClosing() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				ProfilesAddActiivity.this.finish();
			}
		}.start();
	}

	// �����л�ʱ��
	private long getTime() {
		Calendar currentTime = Calendar.getInstance();
		int hour = tp.getCurrentHour() - currentTime.get(Calendar.HOUR_OF_DAY);
		if (hour < 0) {
			hour += 24;// ---------------������õ�ʱ���Ǹ��ľ���ʾ
			Toast.makeText(this, "�ף������õ��������ʱ��Ŷ", Toast.LENGTH_SHORT).show();
		}
		long min = tp.getCurrentMinute() - currentTime.get(Calendar.MINUTE);
		long second = currentTime.get(Calendar.SECOND);
		return System.currentTimeMillis() + (hour * 60 + min) * 60 * 1000
				- second * 1000;
	}

}
