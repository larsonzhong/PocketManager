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
 * 用户登陆处理
 * 
 * @author Larson
 * 
 */
public class LoginActivity extends Activity {

	/**
	 * 是否网络不可用
	 */
	private boolean isNetError;

	/**
	 * 用户名和密码
	 */
	private String username;
	private String password;

	/**
	 * 登陆对话框
	 */
	private ProgressDialog proDialog;
	/**
	 * 回退按钮
	 */
	private ImageView btn_back;
	/**
	 * 用户名输入框
	 */
	private EditText login_user_edit;
	/**
	 * 密码输入框
	 */
	private EditText login_passwd_edit;
	/**
	 * 注册文本链接
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
	 * 初始化控件
	 */
	private void initView() {
		btn_back = (ImageView) findViewById(R.id.btn_back);
		login_user_edit = (EditText) findViewById(R.id.login_user_edit);
		login_passwd_edit = (EditText) findViewById(R.id.login_passwd_edit);
		register_link = (TextView) findViewById(R.id.register_link);
		login_login_btn = (Button) findViewById(R.id.login_login_btn);
	}

	/**
	 * 为控件设置监听器
	 */
	private void setListener() {
		btn_back.setOnClickListener(listener);
		register_link.setOnClickListener(listener);
		login_login_btn.setOnClickListener(listener);
	}

	/**
	 * 点击监听器
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
				proDialog = ProgressDialog.show(LoginActivity.this, "连接中..",
						"连接中..请稍后....", true, true);
				// 开一个线程进行登录验证,主要是用于失败,成功可以直接通过startAcitivity(Intent)转向
				Thread loginThread = new Thread(new LoginHandler());
				loginThread.start();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 处理用户登陆
	 * 
	 * @author Larson
	 * 
	 */
	class LoginHandler implements Runnable {
		@Override
		public void run() {
			username = login_user_edit.getText().toString();
			password = login_passwd_edit.getText().toString();
			// 这里换成你的验证地址
			String validateURL = Constant.LOGIN_SERVLET + "?userName="
					+ username + "&password=" + password;
			int loginState = validateLocalLogin(username, password, validateURL);
			Log.d(this.toString(), "validateLogin");

			/**
			 * 登陆成功
			 */
			if (loginState == 200) {
				proDialog.dismiss();
				// 更新全局变量
				App.isUserLogin = true;
				loginResultHandler.sendEmptyMessage(200);
				startActivity(new Intent(LoginActivity.this,
						SettingMainActivity.class));
				finish();
			} else if(loginState == 400){
				proDialog.dismiss();
				// 通过调用handler来通知UI主线程更新UI,
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
	 * 登录后台通知更新UI线程,主要用于登录失败,通知UI线程更新界面 410-------用户名不能为空 411-------用户名或密码不正确
	 * 200-------登陆成功 400--------连接失败
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
					Toast.makeText(LoginActivity.this, "登陆失败:\n请检查您网络连接.!",
							Toast.LENGTH_SHORT).show();
				}
				// 用户名和密码错误
				else {
					Toast.makeText(LoginActivity.this, "登陆失败,请输入正确的用户名和密码!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 411:
				Toast.makeText(LoginActivity.this, "用户名或密码不正确",
						Toast.LENGTH_SHORT).show();
				break;
			case 200:
				Toast.makeText(LoginActivity.this,
						"欢迎你" + App.currentUser.getNickname(),
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};

	/**
	 * 检查用户登陆,服务器通过DataOutputStream的dos.writeInt(int)， ';来判断是否登录成功<br>
	 * 服务器返回200登陆成功,否则失败),如果连接服务器超过5秒,也算连接失败.<br>
	 * 返回400登陆失败<br>
	 * 处理成功则返回200，失败返回411;<br>
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param validateUrl
	 *            检查登陆的地址
	 * */
	private int validateLocalLogin(String userName, String password,
			String validateUrl) {
		// 用于标记登陆状态
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
	 * 处理成功则返回200，失败返回411;
	 * 
	 * @param dis
	 * @return
	 */
	private int handleReturn(DataInputStream dis) {
		// 1.获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader reader = null;
		try {
			// 2.自定义解析过程
			reader = factory.newSAXParser().getXMLReader();
			LoginResultHandler handler = new LoginResultHandler();
			reader.setContentHandler(handler);

			// 3.开始解析客户端过来的输入流,并把数据封装到userInfo的bean中
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
	 * 处理服务器返回的数据
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

			// 设置完临时属性置空
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
