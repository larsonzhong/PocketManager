package larson.pm;

import java.io.DataInputStream;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �û���½����
 * 
 * @author Larson
 * 
 */
public class LoginActivity extends Activity {

	/**
	 * �Ƿ����粻����
	 */
	private boolean isNetError;

	/**
	 * �û���������
	 */
	private String username;
	private String password;

	/**
	 * ��½�Ի���
	 */
	private ProgressDialog proDialog;
	/**
	 * ���˰�ť
	 */
	private ImageView btn_back;
	/**
	 * �û��������
	 */
	private EditText login_user_edit;
	/**
	 * ���������
	 */
	private EditText login_passwd_edit;
	/**
	 * ע���ı�����
	 */
	private TextView register_link;
	private Button login_login_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		initView();
		setListener();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		login_user_edit = (EditText) findViewById(R.id.login_user_edit);
		login_passwd_edit = (EditText) findViewById(R.id.login_passwd_edit);
		register_link = (TextView) findViewById(R.id.register_link);
		login_login_btn = (Button) findViewById(R.id.login_login_btn);
	}

	/**
	 * Ϊ�ؼ����ü�����
	 */
	private void setListener() {
		btn_back.setOnClickListener(listener);
		register_link.setOnClickListener(listener);
		login_login_btn.setOnClickListener(listener);
	}

	/**
	 * ���������
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				finish();
				break;
			case R.id.register_link:
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
				break;
			case R.id.login_login_btn:
				proDialog = ProgressDialog.show(LoginActivity.this, "������..",
						"������..���Ժ�....", true, true);
				// ��һ���߳̽��е�¼��֤,��Ҫ������ʧ��,�ɹ�����ֱ��ͨ��startAcitivity(Intent)ת��
				Thread loginThread = new Thread(new LoginHandler());
				loginThread.start();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * �����û���½
	 * 
	 * @author Larson
	 * 
	 */
	class LoginHandler implements Runnable {
		@Override
		public void run() {
			username = login_user_edit.getText().toString();
			password = login_passwd_edit.getText().toString();
			// ���ﻻ�������֤��ַ
			String validateURL = Constant.LOGIN_SERVLET + "?userName="
					+ username + "&password=" + password;
			int loginState = validateLocalLogin(username, password, validateURL);
			Log.d(this.toString(), "validateLogin");

			/**
			 * ��½�ɹ�
			 */
			if (loginState == 200) {
				proDialog.dismiss();
				// ����ȫ�ֱ���
				App.isUserLogin = true;
				loginResultHandler.sendEmptyMessage(200);
				startActivity(new Intent(LoginActivity.this,
						SettingMainActivity.class));
				finish();
			} else if(loginState == 400){
				proDialog.dismiss();
				// ͨ������handler��֪ͨUI���̸߳���UI,
				Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("isNetError", isNetError);
				message.setData(bundle);
				message.what = loginState;
				loginResultHandler.sendMessage(message);
			}else if(loginState == 411){
				proDialog.dismiss();
				loginResultHandler.sendEmptyMessage(411);
			}
		}

	}

	/**
	 * ��¼��̨֪ͨ����UI�߳�,��Ҫ���ڵ�¼ʧ��,֪ͨUI�̸߳��½��� 410-------�û�������Ϊ�� 411-------�û��������벻��ȷ
	 * 200-------��½�ɹ� 400--------����ʧ��
	 * */
	Handler loginResultHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 400:
				isNetError = msg.getData().getBoolean("isNetError");
				if (proDialog != null) {
					proDialog.dismiss();
				}
				if (isNetError) {
					Toast.makeText(LoginActivity.this, "��½ʧ��:\n��������������.!",
							Toast.LENGTH_SHORT).show();
				}
				// �û������������
				else {
					Toast.makeText(LoginActivity.this, "��½ʧ��,��������ȷ���û���������!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 411:
				Toast.makeText(LoginActivity.this, "�û��������벻��ȷ",
						Toast.LENGTH_SHORT).show();
				break;
			case 200:
				Toast.makeText(LoginActivity.this,
						"��ӭ��" + App.currentUser.getNickname(),
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	/**
	 * ����û���½,������ͨ��DataOutputStream��dos.writeInt(int)�� ';���ж��Ƿ��¼�ɹ�<br>
	 * ����������200��½�ɹ�,����ʧ��),������ӷ���������5��,Ҳ������ʧ��.<br>
	 * ����400��½ʧ��<br>
	 * ����ɹ��򷵻�200��ʧ�ܷ���411;<br>
	 * 
	 * @param userName
	 *            �û���
	 * @param password
	 *            ����
	 * @param validateUrl
	 *            ����½�ĵ�ַ
	 * */
	private int validateLocalLogin(String userName, String password,
			String validateUrl) {
		// ���ڱ�ǵ�½״̬
		int loginState = 0;
		HttpURLConnection conn = null;
		DataInputStream dis = null;
		try {
			URL url = new URL(validateUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			dis = new DataInputStream(conn.getInputStream());
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Log.d(this.toString(),
						"getResponseCode() not HttpURLConnection.HTTP_OK");
				isNetError = true;
				loginState = 400;
			}
			loginState = handleReturn(dis);
		} catch (Exception e) {
			e.printStackTrace();
			isNetError = true;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return loginState;
	}

	/**
	 * ����ɹ��򷵻�200��ʧ�ܷ���411;
	 * 
	 * @param dis
	 * @return
	 */
	private int handleReturn(DataInputStream dis) {
		// 1.��ȡ��������
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader reader = null;
		try {
			// 2.�Զ����������
			reader = factory.newSAXParser().getXMLReader();
			LoginResultHandler handler = new LoginResultHandler();
			reader.setContentHandler(handler);

			// 3.��ʼ�����ͻ��˹�����������,�������ݷ�װ��userInfo��bean��
			reader.parse(new InputSource(dis));
			UserInfo user = handler.getUser();
			App.currentUser = user;
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
			return 411;
		}
	}

	/**
	 * ������������ص�����
	 * 
	 * @author Larson
	 * 
	 */
	private class LoginResultHandler extends DefaultHandler {
		private UserInfo user;
		private String var = "";

		public UserInfo getUser() {
			return user;
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
			if ("username".equals(qName))
				user.setUsername(var);
			if ("password".equals(qName))
				user.setPassword(var);
			if ("nickname".equals(qName))
				user.setNickname(var);
			if ("gender".equals(qName))
				user.setGender(var);

			// ��������ʱ�����ÿ�
			var = "";
			super.endElement(uri, localName, qName);
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equals("user"))
				user = new UserInfo();
			super.startElement(uri, localName, qName, attributes);
		}

	}
}
