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
 * ��ʱ������
 * 
 * @author Larson
 * 
 */
public class TimeSmsActivity extends Activity {

	private static final int RESULT_CODE_CONTACTS = 10111;

	private int whichselect;

	/**
	 * �û����õĶ�ʱʱ��
	 */
	private String time;
	/**
	 * �Ƿ�ʱ
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
	 * �Ƿ��û�����׼��������
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
	 * ��ʼ���ؼ�
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
	 * ����ɾ��,����id��ɾ�����ݿ��е����ݣ�Ȼ������б����û�����ݾͲ�ɾ����
	 */
	private RemoveListener removeListener = new RemoveListener() {
		@Override
		public void removeItem(RemoveDirection direction, int position,View view) {
			//1.�����СΪ0ֱ�ӷ���
			if(sms_datas.size()==0)
				return;
			//2.Ѱ�����������id��ɾ�����ݿ�
			TextView sms_id = (TextView) view.findViewById(R.id.sms_id);
			String _id = sms_id.getText().toString();
			int id = Integer.parseInt(_id);
			//3.�Ƴ�list��item
			sms_datas.remove(position);
			SmsDao dao = new SmsDao(TimeSmsActivity.this);
			dao.delete(id);
			//4.�����б�
			sms_datas = new SmsDao(TimeSmsActivity.this).queryAll();
			smsadapter.notifyDataSetChanged();
			//5.ȡ�����õ�alarms
			// �������ݹ�ȥ
			Intent intent = new Intent(SmsReceiver.SEND_SMS);
			PendingIntent smsIntent = PendingIntent.getBroadcast(
					TimeSmsActivity.this, id, intent, 0);
			alarms.cancel(smsIntent);
		}
	};

	/**
	 * ������ť�ĵ���¼�
	 */
	private OnClickListener listener = new OnClickListener() {

		private int RESULT_CODE_CONTACTS = 10111;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**
			 * ���ذ�ť
			 */
			case R.id.btn_return:
				finish();
				break;
			/**
			 * �����ϵ�˰�ť ���û������ӵ�ʱ�����༭��ϵ��ģʽ��listView�л�����ϵ��listView
			 * ����ϵͳ��ϵ�˽��沢����һѡ�е�����
			 */
			case R.id.add_contact_btn:
				isEditingMode = true;
				switchListView();

				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, RESULT_CODE_CONTACTS);

				break;
			/**
			 * ��Ӷ�ʱ��ť
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
			 * ���Ͷ��Ű�ť
			 */
			case R.id.sms_send_btn:
				String content = sms_content_et.getText().toString();
				if (content == null || "".equals(content)) {
					Toast.makeText(TimeSmsActivity.this, "���ܷ��Ϳ���ϢŶ��",
							Toast.LENGTH_SHORT).show();
					return;
				}

				isEditingMode = false;
				switchListView();
				saveToSQL();
				startSending();
				Toast.makeText(TimeSmsActivity.this, "��ʱ�������óɹ�",
						Toast.LENGTH_SHORT).show();
				break;
			/**
			 * �������ڰ�ť
			 */
			case R.id.sms_date_btn:
				Dialog dataDialog = createDialog(R.id.sms_date_btn);
				dataDialog.show();
				break;
			/**
			 * ����ʱ�䰴ť
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
	 * �����ݱ��浽���ݿ�
	 */
	private void saveToSQL() {
		time = sms_date_tv.getText() + " " + sms_time_tv.getText();
		String content = sms_content_et.getText().toString();

		String to = makeTo();

		SmsDao dao = new SmsDao(this);
		dao.insert(new SmsBean(to, content, time));
	}

	/**
	 * ��ʼ���Ͷ���
	 */
	private void startSending() {
		// �������ݹ�ȥ
		String content = sms_content_et.getText().toString();
		String to = makeTo();
		Intent intent = new Intent(SmsReceiver.SEND_SMS);
		intent.putExtra("content", content);
		intent.putExtra("to", to);

		PendingIntent smsIntent = PendingIntent.getBroadcast(
				TimeSmsActivity.this, readIdfromDB(), intent, 0);
		alarms.set(AlarmManager.RTC_WAKEUP, getTime(), smsIntent);
	}

	// ����ϵͳ��ϵ�˷��صĽ��
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_CODE_CONTACTS:
			// ���سɹ�
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
	 * ����ϵͳ��ϵ�˷��ص�����,��ת��contact_list
	 * 
	 * @param intent
	 */
	private void backContact(Intent intent) {
		Uri uri = intent.getData();
		// �õ�ContentResolver����
		ContentResolver cr = getContentResolver();
		// ȡ�õ绰���п�ʼһ��Ĺ��
		Cursor cursor = cr.query(uri, null, null, null, null);
		// �����ƶ����
		while (cursor.moveToNext()) {
			// ȡ����ϵ������
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			final String contactName = cursor.getString(nameFieldColumnIndex);
			// ȡ�õ绰����
			final String ContactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ ContactId, null, null);

			/**
			 * ����ֻһ���绰�����ʱ�����ǵ���һ����ѡ�����û�ѡ�� ѡ��������ת��contact_list
			 */
			List<String> templist = new ArrayList<String>();
			while (phone.moveToNext()) {
				String PhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				boolean isRepeat = false;
				// ��������Ѿ����˾Ͳ�����������ˣ��ͷ��ز���ʾ
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
			 * ����һ����ѡ�����û�ѡ��
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
					 * ����Ҫ�������û�Ⱥ���list
					 */
					sms_contact_et.setText(getAllContacts(contact_datas));
					contactadapter.notifyDataSetInvalidated();
				}
			};
			builder.setSingleChoiceItems(arr, 0, choselistener);
			builder.setPositiveButton("ȷ��", oklistener);
			builder.create().show();
		}
		cursor.close();

	}

	/**
	 * ��ȡ��ǰ��������������ӵ�Ŀ��
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
	 * �л�list��ʾģʽ ��������ϵ�˾ͽ�����ϵ��ģʽ��������ͻ���û�е����Ӿ���smsģʽ
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
	 * ����������
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
	 * ͨ��tp_time��ȡ����id��������ʶ��,���ص�ǰid---------���쳣��û������ʱ�䣩
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
			Toast.makeText(TimeSmsActivity.this, "��û��ѡ��ʱ��", Toast.LENGTH_SHORT)
					.show();
		}
		return id;
	}

	/**
	 * ��ȡ�趨��ʱ��
	 * 
	 * @return
	 */
	// �����л�ʱ��
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
			System.out.println("line410--ʱ��ת�������˰�");
		}
		return tm;
	}

	/**
	 * ��ȡ�Ի���
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
					}, c.get(Calendar.YEAR), // �������
					c.get(Calendar.MONTH), // �����·�
					c.get(Calendar.DAY_OF_MONTH) // ��������
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
