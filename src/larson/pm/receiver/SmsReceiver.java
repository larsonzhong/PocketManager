package larson.pm.receiver;

import java.util.List;

import larson.pm.R;
import larson.pm.ReminderActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SmsReceiver extends BroadcastReceiver {

	public static final String SEND_SMS = "larson.pm.SEND_SMS";

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("hahhahahha 到了smsReceiver------");
		
		String content = intent.getStringExtra("content");
		String to = intent.getStringExtra("to");
		String[] arr = to.split("$");

		SmsManager manager = SmsManager.getDefault();

		for (int i = 0; i < arr.length; i++) {
			if (content.length() > 70) {
				List<String> contents = manager.divideMessage(content);
				for (String text : contents) {
					manager.sendTextMessage(arr[i], null, text,
							null, null);
				}
			}
			System.out.println(arr[i]+"----------"+content);
		}
		setNotification(content,context);
	}
	
	/**
	 * 设置通知
	 * @param context 
	 */
	private void setNotification(String text, Context context) {

		// 1.Get a reference to the NotificationManager:
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		// 2.Instantiate the Notification:
		int icon = R.drawable.reminder_about_ico;
		CharSequence tickerText = "往来薄事务管理推送";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		// 3.Define the notification's message and PendingIntent:
//		Context context = getApplicationContext();
		CharSequence contentTitle = "提醒";
		CharSequence contentText = text;
		Intent notificationIntent = new Intent(context, ReminderActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 自动取消
		notification.vibrate = new long[] { 0, 100, 200, 300 }; // 0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒
		notification.number = 1; // number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部

		// 4.Pass the Notification to the NotificationManager:
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}

}
