package larson.pm.receiver;


import larson.pm.R;
import larson.pm.ReminderActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {

	public static final String ALARM_CLOCK = "larson.wlb.reminder.ALARM_CLOCK";
	private String TAG = "larson";

	@Override
	public void onReceive(Context context, Intent intent) {
		int musicId = intent.getIntExtra("musicId",1);
		String event = intent.getStringExtra("event");
		Log.v(TAG, musicId+"");
		MediaPlayer mediaPlayer = MediaPlayer.create(context, musicId);
		mediaPlayer.start();
		setNotification(event,context);
	}

	/**
	 * ����֪ͨ
	 * @param context 
	 */
	private void setNotification(String text, Context context) {

		// 1.Get a reference to the NotificationManager:
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		// 2.Instantiate the Notification:
		int icon = R.drawable.reminder_about_ico;
		CharSequence tickerText = "�����������������";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		// 3.Define the notification's message and PendingIntent:
		CharSequence contentTitle = "����";
		CharSequence contentText = text;
		Intent notificationIntent = new Intent(context, ReminderActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;//�Զ�ȡ��
		notification.vibrate = new long[]{0,100,200,300}; //0�����ʼ�񶯣���100�����ֹͣ���ٹ�200������ٴ���300����
		notification.number = 1; //number�ֶα�ʾ��֪ͨ����ĵ�ǰ�¼�����������������״̬��ͼ��Ķ���

		// 4.Pass the Notification to the NotificationManager:
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}
}
