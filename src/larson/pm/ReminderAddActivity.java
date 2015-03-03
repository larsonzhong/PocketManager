package larson.pm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import larson.pm.bean.Reminder;
import larson.pm.dao.ReminderDao;
import larson.pm.receiver.ReminderReceiver;
import larson.pm.reminder.vew.AbstractWheelTextAdapter;
import larson.pm.reminder.vew.ArrayWheelAdapter;
import larson.pm.reminder.vew.NumericWheelAdapter;
import larson.pm.reminder.vew.WheelView;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ReminderAddActivity extends Activity {
	/**
	 * 是否铃声提醒
	 */
	private boolean isRingtone;
	/**
	 * 是否通知栏提醒
	 */
	private int isNotify;

	private Context context = ReminderAddActivity.this;
	private AlarmManager alarm;
	private RelativeLayout reminder_select_ringtone;
	private CheckBox reminder_music_hint_cb;
	private CheckBox reminder_nitify_cb;
	private String event;
	private int music_id = 0;
	private EditText reminder_add_event;
	private WheelView hours;
	private WheelView mins;
	private WheelView ampm;
	private WheelView day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reminder_add_ly);

		alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		/**
		 * 是否音乐提示的开关
		 */
		reminder_music_hint_cb = (CheckBox) findViewById(R.id.reminder_music_hint_cb);
		reminder_music_hint_cb.setOnCheckedChangeListener(check_listener);
		/**
		 * 是否通知栏提醒的开关
		 */
		reminder_nitify_cb = (CheckBox) findViewById(R.id.reminder_nitify_cb);
		reminder_nitify_cb.setOnCheckedChangeListener(check_listener);
		/**
		 * 选择音乐的按钮
		 */
		reminder_select_ringtone = (RelativeLayout) findViewById(R.id.reminder_select_ringtone);
		reminder_select_ringtone.setOnClickListener(click_listener);

		reminder_add_event = (EditText) findViewById(R.id.reminder_add_event);
		event = reminder_add_event.getText().toString();

		hours = (WheelView) findViewById(R.id.hour);
		NumericWheelAdapter hourAdapter = new NumericWheelAdapter(this, 0, 23,
				"%02d");
		hourAdapter.setItemResource(R.layout.reminder_wheel_text_item);
		hourAdapter.setItemTextResource(R.id.text);
		hours.setViewAdapter(hourAdapter);

		mins = (WheelView) findViewById(R.id.mins);
		NumericWheelAdapter minAdapter = new NumericWheelAdapter(this, 0, 59,
				"%02d");
		minAdapter.setItemResource(R.layout.reminder_wheel_text_item);
		minAdapter.setItemTextResource(R.id.text);
		mins.setViewAdapter(minAdapter);
		mins.setCyclic(true);

		ampm = (WheelView) findViewById(R.id.ampm);
		ArrayWheelAdapter<String> ampmAdapter = new ArrayWheelAdapter<String>(
				this, new String[] { "AM", "PM" });
		ampmAdapter.setItemResource(R.layout.reminder_wheel_text_item);
		ampmAdapter.setItemTextResource(R.id.text);
		ampm.setViewAdapter(ampmAdapter);

		// 设置当前时间
		Calendar calendar = Calendar.getInstance(Locale.US);
		hours.setCurrentItem(calendar.get(Calendar.HOUR));
		mins.setCurrentItem(calendar.get(Calendar.MINUTE));
		ampm.setCurrentItem(calendar.get(Calendar.AM_PM));
		day = (WheelView) findViewById(R.id.day);
		day.setViewAdapter(new DayArrayAdapter(this, calendar));

		Button reminder_add_ok = (Button) findViewById(R.id.reminder_add_ok);
		reminder_add_ok.setOnClickListener(click_listener);

		Button reminder_add_cancel = (Button) findViewById(R.id.reminder_add_cancel);
		reminder_add_cancel.setOnClickListener(click_listener);
	}

	/**
	 * 开关改变监听
	 */
	private OnCheckedChangeListener check_listener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.reminder_music_hint_cb:
				isRingtone = isChecked;
				// 如果不设置音乐则不可选
				if (isRingtone == false)
					reminder_select_ringtone.setClickable(false);
				else
					reminder_select_ringtone.setClickable(true);
				break;
			case R.id.reminder_nitify_cb:
				if (isChecked)
					isNotify = 1;
				else
					isNotify = 0;
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 点击监听事件
	 */
	private OnClickListener click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.reminder_add_cancel:
				finish();

			case R.id.reminder_select_ringtone:
				selectMusic();  
				break;

			case R.id.reminder_add_ok:
				int day_curr = day.getCurrentItem()
						+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH);// 获取星期几（几号）---------------
				int ampm_curr = ampm.getCurrentItem();
				int hours_curr = hours.getCurrentItem();
				if (hours_curr >= 12) {
					hours_curr -= 12;
					ampm_curr = 1;
				}
				int mins_curr = mins.getCurrentItem();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH, day_curr);
				c.set(Calendar.HOUR, hours_curr);
				c.set(Calendar.MINUTE, mins_curr);
				c.set(Calendar.AM_PM, ampm_curr);
				c.set(Calendar.SECOND, 0); // -------设置第零秒开始
				String time = c.getTime().toLocaleString();
				event = reminder_add_event.getText().toString();
				// ???????????????????--写入数据库
				int isClock = 1;
				if (isRingtone == false) {
					music_id = 0;
					isClock = 0;
				}
				saveToSQL(event, time, isClock, isNotify, music_id + 1);

				/**
				 * 如果设置了响铃就会发送广播
				 */
				if (isRingtone == true) {
					Intent intent = new Intent(ReminderReceiver.ALARM_CLOCK);
					intent.putExtra("musicId", matchID(music_id));
					intent.putExtra("event", event);
					PendingIntent alarmIntent = PendingIntent
							.getBroadcast(ReminderAddActivity.this,
									readIdfromDB(), intent, 0);
					alarm.set(AlarmManager.RTC_WAKEUP, getTime(c), alarmIntent);
				}

				finish();
				break;
			default:
				break;
			}
		}

		/**
		 * 点击单选框选择歌曲
		 */
		private void selectMusic() {
			/**
			 * 处理和music_id相关的事情
			 */
			String[] items = { "清晨", "开心", "大风车", "滴滴声"};
			 Builder builder = new AlertDialog.Builder(context);  
			    builder.setIcon(R.drawable.reminder_about_ico);  
			    builder.setTitle("选择铃声");  
			    final ChoiceOnClickListener choiceListener =   
			        new ChoiceOnClickListener();  
			    builder.setSingleChoiceItems(items, -1, choiceListener);  
			      
			    DialogInterface.OnClickListener btnListener =   
			        new DialogInterface.OnClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialogInterface, int which) {  
			                music_id = choiceListener.getWhich();
			            }  
			        };  
			    builder.setPositiveButton("确定", btnListener);  
			    builder.create().show();
		}
	};
	
	/**
	 * 单选框点击事件
	 * @author Larson
	 *
	 */
	private class ChoiceOnClickListener implements DialogInterface.OnClickListener {  
		  
        private int which = 0;  
        @Override  
        public void onClick(DialogInterface dialogInterface, int which) {  
            this.which = which;  
        }  
          
        public int getWhich() {  
            return which;  
        }  
    }  

	// 通过选择项匹配歌曲id
	public int matchID(int id) {
		int _id = 0;
		switch (id) {
		case 1:
			_id = R.raw.alarm1;
			break;
		case 2:
			_id = R.raw.alarm2;
			break;
		case 3:
			_id = R.raw.alarm3;
			break;
		case 0:
			_id = R.raw.alarm0;
			break;
		default:
			break;
		}
		Log.v("larson", _id + "   ---   ");
		return _id;
	}

	// 获取时间发送广播---毫秒值
	public long getTime(Calendar c) {
		return c.getTimeInMillis();
	}

	// 读取id从数据库
	public int readIdfromDB() {
		ReminderDao dao;
		int id = -1;
		dao = new ReminderDao(ReminderAddActivity.this);
		List<Reminder> rd = dao.query("tagName", event);
		id = rd.get(0).getId();
		return id;
	}

	// 保存到数据库
	public void saveToSQL(String event2, String time, int isClock2,
			int isNotify, int music_id) {
		ReminderDao dao = new ReminderDao(context);
		Reminder reminder = new Reminder();
		reminder.setId(music_id);
		reminder.setIsClock(isClock2);
		reminder.setIsNotify(isNotify);
		reminder.setTagName(event2);
		reminder.setTime(time);
		dao.insert(reminder);
	}

	/**
	 * Day adapter
	 * 
	 */
	private class DayArrayAdapter extends AbstractWheelTextAdapter {
		// Count of days to be shown
		private final int daysCount = 20;

		// Calendar
		Calendar calendar;

		/**
		 * Constructor
		 */
		protected DayArrayAdapter(Context context, Calendar calendar) {
			super(context, R.layout.reminder_time2_day, NO_RESOURCE);
			this.calendar = calendar;

			setItemTextResource(R.id.time2_monthday);
		}

		public View getItem(int index, View cachedView, ViewGroup parent) {
			// int day = -daysCount / 2 + index;
			int day = index;
			Calendar newCalendar = (Calendar) calendar.clone();
			newCalendar.roll(Calendar.DAY_OF_YEAR, day);

			View view = super.getItem(index, cachedView, parent);
			TextView weekday = (TextView) view.findViewById(R.id.time2_weekday);
			if (day == 0) {
				weekday.setText("");
			} else {
				DateFormat format = new SimpleDateFormat("EEE");
				weekday.setText(format.format(newCalendar.getTime()));
			}

			TextView monthday = (TextView) view
					.findViewById(R.id.time2_monthday);
			if (day == 0) {
				monthday.setText("");
				monthday.setTextColor(0xFF0000F0);
			} else {
				DateFormat format = new SimpleDateFormat("MMM d");
				monthday.setText(format.format(newCalendar.getTime()));
				monthday.setTextColor(0xFF111111);
			}

			return view;
		}

		public int getItemsCount() {
			return daysCount + 1;
		}

		protected CharSequence getItemText(int index) {
			String time = "星期" + calendar.get(Calendar.DAY_OF_WEEK) + "  "
					+ calendar.get(Calendar.MONTH) + "月"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "日";
			return time;
		}
	}

	
	
}


