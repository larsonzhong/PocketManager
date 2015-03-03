package larson.pm;

import java.util.Calendar;

import larson.pm.bean.Fond;
import larson.pm.dao.FondDao;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FondAddActivity extends Activity {
	private final int DATE = 3;
	private final int TIME = 4;
	/**
	 * �ʽ�����
	 */
	private int ifIn = -1;
	/**
	 * �������
	 */
	private String type;
	/**
	 * ����
	 */
	private String subject;
	private Button fond_add_BtnDate;
	private Button fond_add_BtnTime;
	private TextView fond_add_date_tv;
	/**
	 * ��ӵ�ʱ��
	 */
	private TextView fond_add_time_tv;
	/**
	 * ��ע���ı�
	 */
	private EditText fond_add_describe;
	/**
	 * ȡ����ť
	 */
	private Button fond_add_Cancel;
	/**
	 * ���水ť
	 */
	private Button fond_add_Save;
	/**
	 * �ʽ��С
	 */
	private EditText fond_add_amount;
	/**
	 * �ʽ������spinner
	 */
	private Spinner fond_ifIn_sp;
	/**
	 * �ʽ�����spinner
	 */
	private Spinner fond_type_sp;
	/**
	 * �ʽ��������⣬����������Ŀ
	 */
	private Spinner fond_add_subject_sp;
	/**
	 * �ʽ�����������adapter
	 */
	private ArrayAdapter<CharSequence> fond_type_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fond_add_page);

		initBtnAndTextView();
		initSpinner();
	}

	/**
	 * ��ʼ��spinner����Ϊspinner�Ƚ϶࣬дһ�鲻�ÿ���������ʼ��
	 */
	private void initSpinner() {
		// 1.��ʼ������֧����spinner
		String[] items = { "����", "֧��" };
		fond_ifIn_sp = (Spinner) findViewById(R.id.fond_ifIn_sp);
		ifInadapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item, items);
		ifInadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fond_ifIn_sp.setAdapter(ifInadapter);
		fond_ifIn_sp.setOnItemSelectedListener(sp_listener);

		// 2.��ʼ�����ѡ��spinner
		fond_type_sp = (Spinner) findViewById(R.id.fond_type_sp);
		fond_type_adapter = ArrayAdapter.createFromResource(this,
				R.array.TBL_EXPENDITURE_CATEGORY,
				android.R.layout.simple_spinner_item);
		fond_type_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fond_type_sp.setAdapter(fond_type_adapter);
		fond_type_sp.setOnItemSelectedListener(sp_listener);

		// 3.��ʼ�������spinner(���ݶ�̬��ʼ��)
		fond_add_subject_sp = (Spinner) findViewById(R.id.fond_add_subject_sp);
		fond_add_subject_sp.setOnItemSelectedListener(sp_listener);
	}

	/**
	 * ����type������������
	 * 
	 * @param ifIn
	 * if = 1������֧��
	 * Ϊ0����Ϊ����
	 */
	private void updateTypeSpinner(int ifIn) {
		if (ifIn == 1) {
			fond_type_adapter = ArrayAdapter.createFromResource(this,
					R.array.TBL_EXPENDITURE_CATEGORY,
					android.R.layout.simple_spinner_dropdown_item);
			fond_type_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_type_sp.setAdapter(fond_type_adapter);
		} else if (ifIn == 0) {
			fond_type_adapter = ArrayAdapter.createFromResource(this,
					R.array.TBL_INCOME_CATEGORY,
					android.R.layout.simple_spinner_dropdown_item);
			fond_type_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_type_sp.setAdapter(fond_type_adapter);
		}
	}

	/**
	 * ����������������subject����spinner������
	 * 
	 * @param type
	 *            �ʽ����
	 */
	private void updateSpiner(String type) {
		if (type.equalsIgnoreCase("��ѡ��")) {
			return;
		}

		if (type.equalsIgnoreCase("��������")) {
			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_1,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		}

		else if (type.equalsIgnoreCase("���ڱ���")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_2,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("ҽ�Ʊ���")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_3,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("��������")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_4,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("ѧϰ����")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_5,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("��������")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_6,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("����ͨѶ")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_7,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("�г���ͨ")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_8,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("�Ӽ���ҵ")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_9,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("ʳƷ��ˮ")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_10,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("�·���Ʒ")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_11,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("��������")) {
			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_INCOME_SUB_CATEGORY_1,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("ְҵ����")) {
			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_INCOME_SUB_CATEGORY_2,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		}
	}

	/**
	 * ��ʼ����ť���ı�����editText�����button��ӵ������ ��һ�ڶ������������ �ڡ� �������ĸ�ʱ���ı�v ���������ʱ��ѡ����
	 */
	public void initBtnAndTextView() {

		fond_add_amount = (EditText) findViewById(R.id.fond_add_amount);

		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		fond_add_date_tv = (TextView) findViewById(R.id.fond_add_date_tv);
		fond_add_date_tv.setText(c.get(Calendar.YEAR) + "-" + month + "-"
				+ c.get(Calendar.DAY_OF_MONTH));
		fond_add_time_tv = (TextView) findViewById(R.id.fond_add_time_tv);
		fond_add_time_tv.setText(c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE));

		fond_add_BtnDate = (Button) findViewById(R.id.fond_add_BtnDate);
		fond_add_BtnDate.setOnClickListener(listener_btn);

		fond_add_BtnTime = (Button) findViewById(R.id.fond_add_BtnTime);
		fond_add_BtnTime.setOnClickListener(listener_btn);

		fond_add_describe = (EditText) findViewById(R.id.fond_add_describe);

		fond_add_Cancel = (Button) findViewById(R.id.fond_add_Cancel);
		fond_add_Cancel.setOnClickListener(listener_btn);
		fond_add_Save = (Button) findViewById(R.id.fond_add_Save);
		fond_add_Save.setOnClickListener(listener_btn);

	}

	/**
	 * spinner�ļ�����
	 */
	private OnItemSelectedListener sp_listener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View view,
				int position, long arg3) {
			if (view == fond_type_sp.getSelectedView()) {
				type = (String) fond_type_sp.getItemAtPosition(position);
				updateSpiner(type);
			} else if (view == fond_add_subject_sp.getSelectedView()) {
				// ���������
				subject = (String) fond_add_subject_sp
						.getItemAtPosition(position);
			} else if (view == fond_ifIn_sp.getSelectedView()) {
				// ������ʽ�����
				ifIn = position;
				updateTypeSpinner(ifIn);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			Toast.makeText(FondAddActivity.this, "��û�������ʽ�����Ŷ",
					Toast.LENGTH_SHORT).show();
		}
	};

	/**
	 * ��ť����¼�������ʱ��ͱ��水ť��
	 */
	private OnClickListener listener_btn = new OnClickListener() {
		public void onClick(View v) {
			if (v.getId() == fond_add_Cancel.getId())
				finish();
			if (v.getId() == R.id.fond_add_Save) {
				// 1.�����ʱ��Ҫ��ȡspinner�ڵ�����

				String describe = fond_add_describe.getText().toString();
				String amount = fond_add_amount.getText().toString();
				String time = fond_add_date_tv.getText().toString() + " "
						+ fond_add_time_tv.getText().toString();

				// 2.���ݻ�ȡ�ɹ�Ҫ��У��
				if (subject == null || "".equals(subject) || subject == null
						|| "".equals(subject) || amount == null
						|| "".equals(amount) || time == null || "".equals(time)) {
					Toast.makeText(FondAddActivity.this, "����д����",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// 3.�������ݵ����ݿ�
				saveToSQL(subject, type, describe, amount, time);
				Toast.makeText(FondAddActivity.this, "��ӳɹ�����",
						Toast.LENGTH_SHORT).show();
				startActivity(new Intent(FondAddActivity.this,
						FondActivity.class));
				finish();
			}
			/**
			 * ��ȡ���ڵİ�ť
			 */
			if (v.getId() == fond_add_BtnDate.getId()) {
				Log.v("latson", "brnshow");
				onCreateDialog(DATE).show();
			}
			/**
			 * ��ȡʱ��İ�ť
			 */
			if (v.getId() == fond_add_BtnTime.getId()) {
				Log.v("latson", "brnshow");
				onCreateDialog(TIME).show();
			}

		}
	};
	private ArrayAdapter<CharSequence> ifInadapter;

	/**
	 * �������ݵ����ݿ�
	 * 
	 * @param subject2
	 *            ���⣨С�ࣩ
	 * @param type
	 *            �������ͣ����ࣩ
	 * @param describe
	 *            ��ע��Ϣ
	 * @param amount
	 *            ���
	 * @param time
	 *            ����ʱ��
	 */
	public void saveToSQL(String subject2, String type, String describe,
			String amount, String time) {
		FondDao dao = new FondDao(this);
		if (this.ifIn == -1) {
			new AlertDialog.Builder(this)
					.setTitle("�ʽ�����")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(new String[] { "����", "֧��" }, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									FondAddActivity.this.ifIn = which;
									dialog.dismiss();
								}
							}).setNegativeButton("ȡ��", null).show();
		}
		Fond f = null;
		if (time == null) {
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH) + 1;
			time = c.get(Calendar.YEAR) + "-" + month + "-"
					+ c.get(Calendar.DAY_OF_MONTH)
					+ c.get(Calendar.HOUR_OF_DAY) + ":"
					+ c.get(Calendar.MINUTE);
		}
		f = new Fond(type, subject2, amount, time, describe, ifIn);
		dao.insert(f);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		Calendar c = null;

		if (id == DATE) {
			c = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker view, int year,
								int month, int dayOfMonth) {
							// TODO Auto-generated method stub
							fond_add_date_tv.setText(year + "-" + (month + 1)
									+ "-" + dayOfMonth);
						}
					}, c.get(Calendar.YEAR), // �������
					c.get(Calendar.MONTH), // �����·�
					c.get(Calendar.DAY_OF_MONTH) // ��������
			);
		}
		if (id == TIME) {
			c = Calendar.getInstance();
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							fond_add_time_tv.setText(hourOfDay + ":" + minute);
						}
					}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
					false);
		}
		return dialog;
	}

	/**
	 * ���û��·��ؼ�
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(FondAddActivity.this, FondActivity.class));
		finish();
	}

}
