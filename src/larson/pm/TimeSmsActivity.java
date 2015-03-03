package larson.pm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import larson.pm.adapter.SmsAdapter;
import larson.pm.adapter.SmsContactAdapter;
import larson.pm.bean.Contact;
import larson.pm.bean.SmsBean;
import larson.pm.dao.SmsDao;
import larson.pm.receiver.SmsReceiver;
import larson.pm.view.SlideCutListView;
import larson.pm.view.SlideCutListView.RemoveDirection;
import larson.pm.view.SlideCutListView.RemoveListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 定时发短信
 * 
 * @author Larson
 * 
 */
public class TimeSmsActivity extends Activity {

	private static final int RESULT_CODE_CONTACTS = 10111;

	private int whichselect;

	/**
	 * 用户设置的定时时间
	 */
	private String time;
	/**
	 * 是否定时
	 */
	private boolean isSetTime;

	private TextView sms_contact_et;
	private EditText sms_content_et;
	private TextView sms_date_tv;
	private TextView sms_time_tv;
	private SlideCutListView sms_list;
	private ListView contact_list;

	private LinearLayout sms_setting_time_ly;

	private List<Contact> contact_datas = new ArrayList<Contact>();
	private List<SmsBean> sms_datas = new ArrayList<SmsBean>();

	private SmsAdapter smsadapter;
	private SmsContactAdapter contactadapter;
	/**
	 * 是否用户正在准备发短信
	 */
	private boolean isEditingMode;

	private AlarmManager alarms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_sms);

		initView();
		alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		Button btn_return = (Button) findViewById(R.id.btn_return);
		btn_return.setOnClickListener(listener);

		Button add_contact_btn = (Button) findViewById(R.id.add_contact_btn);
		add_contact_btn.setOnClickListener(listener);

		Button sms_more_btn = (Button) findViewById(R.id.sms_more_btn);
		sms_more_btn.setOnClickListener(listener);

		Button sms_send_btn = (Button) findViewById(R.id.sms_send_btn);
		sms_send_btn.setOnClickListener(listener);

		ImageView sms_date_btn = (ImageView) findViewById(R.id.sms_date_btn);
		sms_date_btn.setOnClickListener(listener);

		ImageView sms_time_btn = (ImageView) findViewById(R.id.sms_time_btn);
		sms_time_btn.setOnClickListener(listener);

		sms_content_et = (EditText) findViewById(R.id.sms_content_et);
		sms_contact_et = (TextView) findViewById(R.id.sms_contact_et);
		sms_date_tv = (TextView) findViewById(R.id.sms_date_tv);
		sms_time_tv = (TextView) findViewById(R.id.sms_time_tv);

		sms_list = (SlideCutListView) findViewById(R.id.sms_list);
		sms_datas = new SmsDao(this).queryAll();
		smsadapter = new SmsAdapter(this, sms_datas);
		sms_list.setAdapter(smsadapter);
		sms_list.setRemoveListener(removeListener);

		contact_list = (ListView) findViewById(R.id.contact_list);
		contactadapter = new SmsContactAdapter(this, contact_datas);
		contact_list.setAdapter(contactadapter);

		sms_setting_time_ly = (LinearLayout) findViewById(R.id.sms_setting_time_ly);
	}
	
	/**
	 * 滑动删除,根据id号删除数据库中的数据，然后更新列表，如果没有数据就不删除了
	 */
	private RemoveListener removeListener = new RemoveListener() {
		@Override
		public void removeItem(RemoveDirection direction, int position,View view) {
			//1.如果大小为0直接返回
			if(sms_datas.size()==0)
				return;
			//2.寻找这个东西的id好删除数据库
			TextView sms_id = (TextView) view.findViewById(R.id.sms_id);
			String _id = sms_id.getText().toString();
			int id = Integer.parseInt(_id);
			//3.移除list的item
			sms_datas.remove(position);
			SmsDao dao = new SmsDao(TimeSmsActivity.this);
			dao.delete(id);
			//4.更新列表
			sms_datas = new SmsDao(TimeSmsActivity.this).queryAll();
			smsadapter.notifyDataSetChanged();
			//5.取消设置的alarms
			// 发送数据过去
			Intent intent = new Intent(SmsReceiver.SEND_SMS);
			PendingIntent smsIntent = PendingIntent.getBroadcast(
					TimeSmsActivity.this, id, intent, 0);
			alarms.cancel(smsIntent);
		}
	};

	/**
	 * 各个按钮的点击事件
	 */
	private OnClickListener listener = new OnClickListener() {

		private int RESULT_CODE_CONTACTS = 10111;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**
			 * 返回按钮
			 */
			case R.id.btn_return:
				finish();
				break;
			/**
			 * 添加联系人按钮 当用户点击添加的时候进入编辑联系人模式，listView切换成联系人listView
			 * 调用系统联系人界面并返回一选中的数据
			 */
			case R.id.add_contact_btn:
				isEditingMode = true;
				switchListView();

				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, RESULT_CODE_CONTACTS);

				break;
			/**
			 * 添加定时按钮
			 */
			case R.id.sms_more_btn:
				if (isSetTime) {
					sms_setting_time_ly.setVisibility(View.GONE);
					isSetTime = false;
				} else {
					sms_setting_time_ly.setVisibility(View.VISIBLE);
					isSetTime = true;
				}
				break;
			/**
			 * 发送短信按钮
			 */
			case R.id.sms_send_btn:
				String content = sms_content_et.getText().toString();
				if (content == null || "".equals(content)) {
					Toast.makeText(TimeSmsActivity.this, "不能发送空消息哦！",
							Toast.LENGTH_SHORT).show();
					return;
				}

				isEditingMode = false;
				switchListView();
				saveToSQL();
				startSending();
				Toast.makeText(TimeSmsActivity.this, "定时短信设置成功",
						Toast.LENGTH_SHORT).show();
				break;
			/**
			 * 设置日期按钮
			 */
			case R.id.sms_date_btn:
				Dialog dataDialog = createDialog(R.id.sms_date_btn);
				dataDialog.show();
				break;
			/**
			 * 设置时间按钮
			 */
			case R.id.sms_time_btn:
				Dialog timeDialog = createDialog(R.id.sms_time_btn);
				timeDialog.show();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 将数据保存到数据库
	 */
	private void saveToSQL() {
		time = sms_date_tv.getText() + " " + sms_time_tv.getText();
		String content = sms_content_et.getText().toString();

		String to = makeTo();

		SmsDao dao = new SmsDao(this);
		dao.insert(new SmsBean(to, content, time));
	}

	/**
	 * 开始发送短信
	 */
	private void startSending() {
		// 发送数据过去
		String content = sms_content_et.getText().toString();
		String to = makeTo();
		Intent intent = new Intent(SmsReceiver.SEND_SMS);
		intent.putExtra("content", content);
		intent.putExtra("to", to);

		PendingIntent smsIntent = PendingIntent.getBroadcast(
				TimeSmsActivity.this, readIdfromDB(), intent, 0);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), smsIntent);
	}

	// 调用系统联系人返回的结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_CODE_CONTACTS:
			// 返回成功
			if (resultCode == Activity.RESULT_OK) {

				if (data == null) {
					return;
				}
				backContact(data);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 调用系统联系人返回的数据,并转入contact_list
	 * 
	 * @param intent
	 */
	private void backContact(Intent intent) {
		Uri uri = intent.getData();
		// 得到ContentResolver对象
		ContentResolver cr = getContentResolver();
		// 取得电话本中开始一项的光标
		Cursor cursor = cr.query(uri, null, null, null, null);
		// 向下移动光标
		while (cursor.moveToNext()) {
			// 取得联系人名字
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			final String contactName = cursor.getString(nameFieldColumnIndex);
			// 取得电话号码
			final String ContactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ ContactId, null, null);

			/**
			 * 当不只一个电话号码的时候，我们弹出一个多选框让用户选择， 选择后的数据转入contact_list
			 */
			List<String> templist = new ArrayList<String>();
			while (phone.moveToNext()) {
				String PhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				boolean isRepeat = false;
				// 如果里面已经有了就不能往里面加了，就返回并提示
				for (Contact contact : contact_datas) {
					if (contact.getPhoneNumber().equalsIgnoreCase(PhoneNumber))
						isRepeat = true;
				}
				if (isRepeat == true)
					continue;
				else
					templist.add(PhoneNumber);
			}
			/**
			 * 弹出一个多选框让用户选择
			 */
			final int size = templist.size();
			final String[] arr = (String[]) templist.toArray(new String[size]);
			Builder builder = new AlertDialog.Builder(TimeSmsActivity.this);
			DialogInterface.OnClickListener choselistener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					whichselect = which;
				}
			};
			DialogInterface.OnClickListener oklistener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					contact_datas.add(new Contact(ContactId, contactName,
							arr[whichselect]));
					/**
					 * 更新要发给的用户群体的list
					 */
					sms_contact_et.setText(getAllContacts(contact_datas));
					contactadapter.notifyDataSetInvalidated();
				}
			};
			builder.setSingleChoiceItems(arr, 0, choselistener);
			builder.setPositiveButton("确定", oklistener);
			builder.create().show();
		}
		cursor.close();

	}

	/**
	 * 获取当前这条短信所有添加的目标
	 * 
	 * @param contact_datas
	 * @return
	 */
	private String getAllContacts(List<Contact> contact_datas) {
		StringBuilder sb = new StringBuilder();
		for (Contact c : contact_datas)
			sb.append(c.getContactName() + ",");
		return sb.toString();
	}

	/**
	 * 切换list显示模式 点击添加联系人就进入联系人模式，点击发送或者没有点击添加就是sms模式
	 */
	private void switchListView() {
		if (isEditingMode == true) {
			contact_list.setVisibility(View.VISIBLE);
			sms_list.setVisibility(View.GONE);
		} else {
			contact_list.setVisibility(View.GONE);
			sms_list.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 整理收信人
	 */
	private String makeTo() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < contact_datas.size(); i++) {
			if (i == (contact_datas.size() - 1))
				sb.append(contact_datas.get(i).getPhoneNumber());
			else
				sb.append(contact_datas.get(i).getPhoneNumber() + "$");
		}
		return sb.toString();
	}

	/**
	 * 通过tp_time读取数据id，生产标识码,返回当前id---------抛异常（没有设置时间）
	 * 
	 * @return
	 */
	public int readIdfromDB() {
		SmsDao dao;
		int id = -1;
		try {
			dao = new SmsDao(TimeSmsActivity.this);
			List<SmsBean> aps = dao.query("time", time);
			id = aps.get(0).getId();
		} catch (Exception e) {
			Toast.makeText(TimeSmsActivity.this, "您没有选择时间", Toast.LENGTH_SHORT)
					.show();
		}
		return id;
	}

	/**
	 * 获取设定的时间
	 * 
	 * @return
	 */
	// 计算切换时间
	private long getTime() {
		Date currentTime = new Date(System.currentTimeMillis());
		Date date = null;
		long tm = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = sdf.parse(time);
			tm = date.getTime() - currentTime.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("line410--时间转换出错了啊");
		}
		return tm;
	}

	/**
	 * 获取对话框
	 * 
	 * @param id
	 * @return
	 */
	protected Dialog createDialog(int id) {
		Dialog dialog = null;
		Calendar c = null;

		if (id == R.id.sms_date_btn) {
			c = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int month, int dayOfMonth) {
							// TODO Auto-generated method stub
							sms_date_tv.setText(year + "-" + (month + 1) + "-"
									+ dayOfMonth);
						}
					}, c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
		}
		if (id == R.id.sms_time_btn) {
			c = Calendar.getInstance();
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							sms_time_tv.setText(hourOfDay + ":" + minute);
						}
					}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
					false);
		}
		return dialog;
	}

	@Override
	protected void onResume() {
		smsadapter.notifyDataSetChanged();
		super.onResume();
	}

}
