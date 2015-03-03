package larson.pm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import larson.pm.bean.UserInfo;
import larson.pm.utils.App;
import larson.pm.utils.Constant;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * �û�ע��ҳ��
 * 
 * @author Larson
 * 
 */
public class RegisterActivity extends Activity {
	/**
	 * �û���
	 */
	private String username;
	/**
	 * ����
	 */
	private String password;
	/**
	 * ȷ������
	 */
	private String confirmpsw;
	/**
	 * �ǳ�
	 */
	private String nickname;
	/**
	 * �Ա�
	 */
	private String gender;

	private EditText username_et;
	private EditText password_et;
	private EditText confirmpsw_et;
	private EditText nikename_et;
	private Button register_btn;
	private RadioGroup gender_group_rb;

	/**
	 * ע���ʱ��Ľ�����
	 */
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initView();
	}

	/**
	 * ��ʼ����ͼ�ؼ�
	 */
	private void initView() {
		username_et = (EditText) findViewById(R.id.username);
		password_et = (EditText) findViewById(R.id.password);
		confirmpsw_et = (EditText) findViewById(R.id.confirmpsw);
		nikename_et = (EditText) findViewById(R.id.nikename);
		register_btn = (Button) findViewById(R.id.register_btn);
		register_btn.setOnClickListener(listener);
		gender_group_rb = (RadioGroup) findViewById(R.id.gender_group_rb);
		gender_group_rb.setOnCheckedChangeListener(checkListener);
	}

	/**
	 * ע�ᰴť�ĵ��
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			doRegister();
		}
	};

	/**
	 * ��ʼע��
	 */
	private void doRegister() {
		pd = ProgressDialog.show(RegisterActivity.this, "����ע��..",
				"����ע����..���Ժ�....", true, true);
		// 1.��ȡ����
		username = username_et.getText().toString();
		password = password_et.getText().toString();
		confirmpsw = confirmpsw_et.getText().toString();
		nickname = nikename_et.getText().toString();
		// 2.����У��,���Է��أ��������
		if (checkNull(username) || checkNull(password) || checkNull(confirmpsw)
				|| checkNull(gender) || checkNull(nickname)) {
			Toast.makeText(this, "����д��������", Toast.LENGTH_SHORT).show();
			// TODO ���ﱾӦ������У��ģ���ʱ͵��
			return;
		}
		// 3.��ʼ�������ע��
		Thread thread = new Thread(registerRunnable);
		thread.start();

	}

	/**
	 * ��ʼע����߳�
	 */
	Runnable registerRunnable = new Runnable() {
		@Override
		public void run() {
			// 1.ƴװ����
			StringBuilder sb = new StringBuilder();
			sb.append("<users>");
			sb.append("<user>");
			sb.append("<username>");
			sb.append(username);
			sb.append("</username>");

			sb.append("<password>");
			sb.append(password);
			sb.append("</password>");

			sb.append("<nickname>");
			sb.append(nickname);
			sb.append("</nickname>");

			sb.append("<gender>");
			sb.append(gender);
			sb.append("</gender>");

			sb.append("</user>");
			sb.append("</users>");
			// 2.��ʼд����
			byte content[] = sb.toString().getBytes();
			try {
				URL url = new URL(Constant.REGISTER_SERVLET);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "mutipart/form-data");
				conn.setRequestProperty("Content-Length", content.length + "");
				conn.getOutputStream().write(content);
				// 3.��ȡ���������ص�����
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream in = conn.getInputStream();

					RegisterResultTool resultTool = new RegisterResultTool();
					String result = resultTool.getResultFromInputStream(in);
					// 4.�ύ���
					Message msg = new Message();
					msg.obj = result;
					msg.what = 200;
					registerResultHandler.sendMessage(msg);

				}
			} catch (IOException e) {
				Message msg = new Message();
				msg.what = 400;
				registerResultHandler.sendMessage(msg);
				e.printStackTrace();
			}

			pd.cancel();
		}
	};

	/**
	 * �����ؽ����handler ������ص������Ƿ��������ĳɹ����ݣ�ע��ɹ���������Ƿ�����������
	 */
	private Handler registerResultHandler = new Handler() {
		public void handleMessage(Message msg) {
			System.out.println(msg.obj + "-------------" + msg.what);
			switch (msg.what) {
			case 400:
				Toast.makeText(RegisterActivity.this,
						"ע��ʧ��.\n1.������δ����\n2.����������", Toast.LENGTH_SHORT).show();
				break;
			case 200:
				String str = (String) msg.obj;
				if (str.contains("regist_ok")) {
					Toast.makeText(RegisterActivity.this, "ע��ɹ�.",
							Toast.LENGTH_SHORT).show();
					App.currentUser = new UserInfo(username, password, gender, nickname);
					startActivity(new Intent(RegisterActivity.this, SettingMainActivity.class));
					finish();
				} else
					Toast.makeText(RegisterActivity.this, "�������ڲ�����.",
							Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * У���Ƿ�������Ϊ��ֵ
	 * 
	 * @param str
	 * @return
	 */
	private boolean checkNull(String str) {
		if ("".equals(str) || str == null)
			return true;
		return false;
	}

	/**
	 * �Ա�radioGroup�ı����
	 */
	private OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.male_rb:
				gender = "��";
				break;
			case R.id.female_rb:
				gender = "Ů";
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ������������ؽ������
	 * 
	 * @author Larson
	 * 
	 */
	private class RegisterResultTool {
		/**
		 * �ӷ��������ص������ж�ȡ���ؽ��
		 * 
		 * @param in
		 * @return
		 */
		public String getResultFromInputStream(InputStream in) {
			String result = "";
			SAXParserFactory sf = SAXParserFactory.newInstance();

			try {
				XMLReader xr = sf.newSAXParser().getXMLReader();

				RegisterResultHandler rrh = new RegisterResultHandler();
				xr.setContentHandler(rrh);
				xr.parse(new InputSource(in));
				result = rrh.getResult();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

	}

	/**
	 * ��Ϊ���������ص���xml��ʽ��������Ҫ����
	 * 
	 * @author Larson
	 * 
	 */
	private class RegisterResultHandler extends DefaultHandler {
		/**
		 * ��õķ��������ؽ��
		 */
		private String result;
		/**
		 * ��ʱ��ȡ���ݵı���
		 */
		private String var = "";

		/**
		 * ��ȡ����ֵ
		 * 
		 * @return
		 */
		public String getResult() {
			return result;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			var += new String(ch, start, length);
			super.characters(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			/**
			 * ȡ��info��ǩ�����ݣ����������ص�ע������
			 */
			if (qName.equals("info")) {
				result = var.trim();
			}
			super.endElement(uri, localName, qName);
		}
	}
}
