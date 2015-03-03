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
	 * 资金流向
	 */
	private int ifIn = -1;
	/**
	 * 消费类别
	 */
	private String type;
	/**
	 * 主题
	 */
	private String subject;
	private Button fond_add_BtnDate;
	private Button fond_add_BtnTime;
	private TextView fond_add_date_tv;
	/**
	 * 添加的时间
	 */
	private TextView fond_add_time_tv;
	/**
	 * 备注的文本
	 */
	private EditText fond_add_describe;
	/**
	 * 取消按钮
	 */
	private Button fond_add_Cancel;
	/**
	 * 保存按钮
	 */
	private Button fond_add_Save;
	/**
	 * 资金大小
	 */
	private EditText fond_add_amount;
	/**
	 * 资金流向的spinner
	 */
	private Spinner fond_ifIn_sp;
	/**
	 * 资金类别的spinner
	 */
	private Spinner fond_type_sp;
	/**
	 * 资金消费主题，是类别的子条目
	 */
	private Spinner fond_add_subject_sp;
	/**
	 * 资金类别的适配器adapter
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
	 * 初始化spinner，因为spinner比较多，写一块不好看，单独初始化
	 */
	private void initSpinner() {
		// 1.初始化收入支出的spinner
		String[] items = { "收入", "支出" };
		fond_ifIn_sp = (Spinner) findViewById(R.id.fond_ifIn_sp);
		ifInadapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item, items);
		ifInadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fond_ifIn_sp.setAdapter(ifInadapter);
		fond_ifIn_sp.setOnItemSelectedListener(sp_listener);

		// 2.初始化类别选择spinner
		fond_type_sp = (Spinner) findViewById(R.id.fond_type_sp);
		fond_type_adapter = ArrayAdapter.createFromResource(this,
				R.array.TBL_EXPENDITURE_CATEGORY,
				android.R.layout.simple_spinner_item);
		fond_type_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fond_type_sp.setAdapter(fond_type_adapter);
		fond_type_sp.setOnItemSelectedListener(sp_listener);

		// 3.初始化主题的spinner(内容动态初始化)
		fond_add_subject_sp = (Spinner) findViewById(R.id.fond_add_subject_sp);
		fond_add_subject_sp.setOnItemSelectedListener(sp_listener);
	}

	/**
	 * 更新type消费类别的数据
	 * 
	 * @param ifIn
	 * if = 1代表是支出
	 * 为0代表为收入
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
	 * 用作二级联动更新subject主题spinner的数据
	 * 
	 * @param type
	 *            资金类别
	 */
	private void updateSpiner(String type) {
		if (type.equalsIgnoreCase("请选择")) {
			return;
		}

		if (type.equalsIgnoreCase("其他杂项")) {
			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_1,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		}

		else if (type.equalsIgnoreCase("金融保险")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_2,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("医疗保健")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_3,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("人情往来")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_4,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("学习进修")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_5,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("休闲娱乐")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_6,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("交流通讯")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_7,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("行车交通")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_8,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("居家物业")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_9,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("食品酒水")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_10,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("衣服饰品")) {

			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_EXPENDITURE_SUB_CATEGORY_11,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("其他收入")) {
			ArrayAdapter<CharSequence> fond_add_subject_adapter = ArrayAdapter
					.createFromResource(this,
							R.array.TBL_INCOME_SUB_CATEGORY_1,
							android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fond_add_subject_sp.setAdapter(fond_add_subject_adapter);
		} else if (type.equalsIgnoreCase("职业收入")) {
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
	 * 初始化按钮和文本，给editText后面的button添加点击监听 第一第二是主题和类型 第、 三个第四个时间文本v 第五第六是时间选择器
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
	 * spinner的监听器
	 */
	private OnItemSelectedListener sp_listener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View view,
				int position, long arg3) {
			if (view == fond_type_sp.getSelectedView()) {
				type = (String) fond_type_sp.getItemAtPosition(position);
				updateSpiner(type);
			} else if (view == fond_add_subject_sp.getSelectedView()) {
				// 如果是主题
				subject = (String) fond_add_subject_sp
						.getItemAtPosition(position);
			} else if (view == fond_ifIn_sp.getSelectedView()) {
				// 如果是资金流向
				ifIn = position;
				updateTypeSpinner(ifIn);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			Toast.makeText(FondAddActivity.this, "您没有设置资金流向哦",
					Toast.LENGTH_SHORT).show();
		}
	};

	/**
	 * 按钮点击事件（包括时间和保存按钮）
	 */
	private OnClickListener listener_btn = new OnClickListener() {
		public void onClick(View v) {
			if (v.getId() == fond_add_Cancel.getId())
				finish();
			if (v.getId() == R.id.fond_add_Save) {
				// 1.保存的时候要获取spinner内的数据

				String describe = fond_add_describe.getText().toString();
				String amount = fond_add_amount.getText().toString();
				String time = fond_add_date_tv.getText().toString() + " "
						+ fond_add_time_tv.getText().toString();

				// 2.数据获取成功要表单校验
				if (subject == null || "".equals(subject) || subject == null
						|| "".equals(subject) || amount == null
						|| "".equals(amount) || time == null || "".equals(time)) {
					Toast.makeText(FondAddActivity.this, "请填写完整",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// 3.保存数据到数据库
				saveToSQL(subject, type, describe, amount, time);
				Toast.makeText(FondAddActivity.this, "添加成功！！",
						Toast.LENGTH_SHORT).show();
				startActivity(new Intent(FondAddActivity.this,
						FondActivity.class));
				finish();
			}
			/**
			 * 获取日期的按钮
			 */
			if (v.getId() == fond_add_BtnDate.getId()) {
				Log.v("latson", "brnshow");
				onCreateDialog(DATE).show();
			}
			/**
			 * 获取时间的按钮
			 */
			if (v.getId() == fond_add_BtnTime.getId()) {
				Log.v("latson", "brnshow");
				onCreateDialog(TIME).show();
			}

		}
	};
	private ArrayAdapter<CharSequence> ifInadapter;

	/**
	 * 保存数据到数据库
	 * 
	 * @param subject2
	 *            主题（小类）
	 * @param type
	 *            消费类型（大类）
	 * @param describe
	 *            备注消息
	 * @param amount
	 *            金额
	 * @param time
	 *            消费时间
	 */
	public void saveToSQL(String subject2, String type, String describe,
			String amount, String time) {
		FondDao dao = new FondDao(this);
		if (this.ifIn == -1) {
			new AlertDialog.Builder(this)
					.setTitle("资金流向")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(new String[] { "收入", "支出" }, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									FondAddActivity.this.ifIn = which;
									dialog.dismiss();
								}
							}).setNegativeButton("取消", null).show();
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
					}, c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
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
	 * 当用换下返回键
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(FondAddActivity.this, FondActivity.class));
		finish();
	}

}
