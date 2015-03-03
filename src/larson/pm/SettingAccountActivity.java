package larson.pm;

import larson.pm.utils.App;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingAccountActivity extends Activity {

	private TextView nickname_tv;
	private TextView username_tv;
	private TextView moto_tv;
	private TextView gender_tv;
	private TextView gold_num_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_page);

		initTextView();
		initButton();
	}

	/**
	 * ��ʼ���ı��ؼ�
	 */
	private void initTextView() {
		nickname_tv = (TextView) findViewById(R.id.nickname_tv);
		username_tv = (TextView) findViewById(R.id.username_tv);
		moto_tv = (TextView) findViewById(R.id.moto_tv);

		gold_num_tv = (TextView) findViewById(R.id.gold_num_tv);

		gender_tv = (TextView) findViewById(R.id.gender_tv);

	}

	/**
	 * ��ʼ����ť
	 */
	private void initButton() {
		Button logout_btn = (Button) findViewById(R.id.logout_btn);
		logout_btn.setOnClickListener(listener);

		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(listener);

		ImageView nickname_editor = (ImageView) findViewById(R.id.nickname_editor);
		nickname_editor.setOnClickListener(listener);

		ImageView username_editor = (ImageView) findViewById(R.id.username_editor);
		username_editor.setOnClickListener(listener);

		ImageView moto_editor = (ImageView) findViewById(R.id.moto_editor);
		moto_editor.setOnClickListener(listener);

		ImageView gender_editor = (ImageView) findViewById(R.id.gender_editor);
		gender_editor.setOnClickListener(listener);
	}

	/**
	 * ������ť�ļ�����
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**
			 * �˳���ť
			 */
			case R.id.logout_btn:
				App.currentUser = null;
				App.isUserLogin=false;
				finish();
				break;
			/**
			 * ���ذ�ť
			 */
			case R.id.btn_back:
				finish();
				break;
			/**
			 * �޸��ǳƵİ�ť
			 */
			case R.id.nickname_editor:
				new AlertDialog.Builder(SettingAccountActivity.this)
				.setTitle("������")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(new EditText(SettingAccountActivity.this))
				.setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null)
				.show();
				break;
			/**
			 * �޸�username�İ�ť
			 */
			case R.id.username_editor:

				break;
			/**
			 * �޸��������İ�ť
			 */
			case R.id.moto_editor:

				break;
			/**
			 * �޸��Ա�İ�ť
			 */
			case R.id.gender_editor:

				break;

			default:
				break;
			}
		}
	};
}
