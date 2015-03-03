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
 * 用户注册页面
 * 
 * @author Larson
 * 
 */
public class RegisterActivity extends Activity {
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 确认密码
	 */
	private String confirmpsw;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别
	 */
	private String gender;

	private EditText username_et;
	private EditText password_et;
	private EditText confirmpsw_et;
	private EditText nikename_et;
	private Button register_btn;
	private RadioGroup gender_group_rb;

	/**
	 * 注册的时候的进度条
	 */
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		initView();
	}

	/**
	 * 初始化视图控件
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
	 * 注册按钮的点击
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			doRegister();
		}
	};

	/**
	 * 开始注册
	 */
	private void doRegister() {
		pd = ProgressDialog.show(RegisterActivity.this, "正在注册..",
				"正在注册中..请稍后....", true, true);
		// 1.获取数据
		username = username_et.getText().toString();
		password = password_et.getText().toString();
		confirmpsw = confirmpsw_et.getText().toString();
		nickname = nikename_et.getText().toString();
		// 2.进行校验,不对返回，否则继续
		if (checkNull(username) || checkNull(password) || checkNull(confirmpsw)
				|| checkNull(gender) || checkNull(nickname)) {
			Toast.makeText(this, "请填写完整数据", Toast.LENGTH_SHORT).show();
			// TODO 这里本应该完整校验的，暂时偷懒
			return;
		}
		// 3.开始向服务器注册
		Thread thread = new Thread(registerRunnable);
		thread.start();

	}

	/**
	 * 开始注册的线程
	 */
	Runnable registerRunnable = new Runnable() {
		@Override
		public void run() {
			// 1.拼装数据
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
			// 2.开始写数据
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
				// 3.获取服务器返回的数据
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream in = conn.getInputStream();

					RegisterResultTool resultTool = new RegisterResultTool();
					String result = resultTool.getResultFromInputStream(in);
					// 4.提交结果
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
	 * 处理返回结果的handler 如果返回的数据是服务器给的成功数据，注册成功，否则就是服务器有问题
	 */
	private Handler registerResultHandler = new Handler() {
		public void handleMessage(Message msg) {
			System.out.println(msg.obj + "-------------" + msg.what);
			switch (msg.what) {
			case 400:
				Toast.makeText(RegisterActivity.this,
						"注册失败.\n1.服务器未开放\n2.网络有问题", Toast.LENGTH_SHORT).show();
				break;
			case 200:
				String str = (String) msg.obj;
				if (str.contains("regist_ok")) {
					Toast.makeText(RegisterActivity.this, "注册成功.",
							Toast.LENGTH_SHORT).show();
					App.currentUser = new UserInfo(username, password, gender, nickname);
					startActivity(new Intent(RegisterActivity.this, SettingMainActivity.class));
					finish();
				} else
					Toast.makeText(RegisterActivity.this, "服务器内部错误.",
							Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 校验是否传入数据为空值
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
	 * 性别radioGroup改变监听
	 */
	private OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.male_rb:
				gender = "男";
				break;
			case R.id.female_rb:
				gender = "女";
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 处理服务器返回结果的类
	 * 
	 * @author Larson
	 * 
	 */
	private class RegisterResultTool {
		/**
		 * 从服务器返回的数据中读取返回结果
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
	 * 因为服务器返回的是xml形式，所以需要解析
	 * 
	 * @author Larson
	 * 
	 */
	private class RegisterResultHandler extends DefaultHandler {
		/**
		 * 获得的服务器返回结果
		 */
		private String result;
		/**
		 * 临时存取数据的变量
		 */
		private String var = "";

		/**
		 * 获取返回值
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
			 * 取出info标签的数据（服务器返回的注册结果）
			 */
			if (qName.equals("info")) {
				result = var.trim();
			}
			super.endElement(uri, localName, qName);
		}
	}
}
