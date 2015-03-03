package larson.pm.receiver;

import static android.provider.Settings.System.AIRPLANE_MODE_ON;
import larson.pm.CallInActivity;
import larson.pm.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {


	public static final String VIBRATE_CHANGED = "larson.wlb.autoprofiles.VIBRATE_CHANGED";
	public static final String SILENT_CHANGED = "larson.wlb.autoprofiles.SILENT_CHANGED";
	public static final String RV_CHANGED = "larson.wlb.autoprofiles.RV_CHANGED";
	public static final String RING_CHANGED = "larson.wlb.autoprofiles.RING_CHANGED";
	public static final String OFFLINE_CHANGED = "larson.wlb.autoprofiles.OFFLINE_CHANGED";
	public static final String CALL_IN_CHANGED = "larson.wlb.autoprofiles.CALL_IN_CHANGED";

	// public static int REQUEST_CODE ;//���������룬���Զ�Ӧ���ݿ���Ψһ��id

	String TAG = "AlarmBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		AudioManager audio = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);

		int checkedId = intent.getIntExtra("checkedId", 0);

		Log.e(TAG, checkedId + intent.getAction());
		// �л��龰ģʽ
		switch (checkedId) {
		case R.id.ring_and_vibrate:
			ringAndVibrate(audio);
			break;
		case R.id.vibrate:
			vibrate(audio);
			break;
		case R.id.silent:
			silent(audio);
			break;
		case R.id.offline:
			// ����Ѿ��������оͲ����ظ�������
			if (!getAirplaneMode(context))
				offLine(context, true);
			break;
		case R.id.call_in:
			call_in(context, intent);
			break;
		default:
			ring(audio);
			break;
		}

	}

	// ��������
	protected void ringAndVibrate(AudioManager audio) {
		audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
				AudioManager.VIBRATE_SETTING_ON);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
				AudioManager.VIBRATE_SETTING_ON);
	}

	// ����
	protected void ring(AudioManager audio) {
		audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
				AudioManager.VIBRATE_SETTING_OFF);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
				AudioManager.VIBRATE_SETTING_OFF);

	}

	// ��
	protected void vibrate(AudioManager audio) {
		audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
				AudioManager.VIBRATE_SETTING_ON);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
				AudioManager.VIBRATE_SETTING_ON);

	}

	// ����
	protected void silent(AudioManager audio) {
		audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
				AudioManager.VIBRATE_SETTING_OFF);
		audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
				AudioManager.VIBRATE_SETTING_OFF);
		Log.v(TAG, "����ģʽ����");
	}

	// ����
	/**
	 * �����ֻ�����ģʽ
	 * 
	 * @param context
	 * @param enabling
	 *            true:����Ϊ����ģʽ false:ȡ������ģʽ
	 */
	protected void offLine(Context context, boolean enabling) {

		Settings.System.putInt(context.getContentResolver(), AIRPLANE_MODE_ON,
				enabling ? 1 : 0);
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enabling);
		context.sendBroadcast(intent);
	}

	/**
	 * ���� ת������ҳ�棬����ϵͳ�Դ�������
	 * 
	 * @param context
	 */
	protected void call_in(Context context, Intent intent) {
		intent.setClass(context, CallInActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * �ж��ֻ��Ƿ��Ƿ���ģʽ
	 * 
	 * @param context
	 * @return
	 */
	public boolean getAirplaneMode(Context context) {
		int isAirplaneMode = Settings.System.getInt(
				context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON,
				0);
		return (isAirplaneMode == 1) ? true : false;
	}


}
