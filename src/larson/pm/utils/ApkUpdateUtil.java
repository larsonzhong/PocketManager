package larson.pm.utils;

import java.io.File;
import java.io.FileOutputStream;

import larson.pm.R;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApkUpdateUtil {
	private int progress = 0;
	private int total;

	private String path = Constant.SERVER_UPDATE_APK_PATH;
	private String url = Constant.SERVER_UPDATE_SERVLET_PATH;
	private ProgressDialog pd;


	/**
	 * 开始更新数据，请求服务器获取文件
	 * 
	 * @param context
	 *            上下文
	 * @param update_ly
	 */
	public void update(final Context context, final RelativeLayout update_ly) {

		// 1.打开浏览器
		AsyncHttpClient client = new AsyncHttpClient();
		// 2.设置参数
		RequestParams params = new RequestParams();
		params.put("version", getVersion(context));

		// 3.回车
		client.post(url, params, new AsyncHttpResponseHandler() {

			/**
			 * 开启下载
			 */
			@Override
			public void onStart() {
				super.onStart();
				pd = new ProgressDialog(context);
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.setCancelable(false);
				pd.setMax(100);
				pd.setTitle("正在下载。。");
				pd.show();
			}

			/**
			 * 下载完成后
			 */
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				/**
				 * 只要以完成就让按钮可点击
				 */
				update_ly.setClickable(true);
				if (statusCode == 200) {
					pd.dismiss();
					System.out.println(responseBody.length);
					// 5.写文件
					makeFile(responseBody);
					Toast.makeText(context, "软件下载成功", Toast.LENGTH_SHORT)
							.show();
					// 开始安装
//					updateApk(context, path);
				} else {
					Toast.makeText(context, "服务器可能未开放哦", Toast.LENGTH_SHORT)
							.show();
				}
			}

			/**
			 * 正在下载中
			 */
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				int value = (bytesWritten / totalSize) * 100;
				progress = value;
				total = totalSize;
				 pd.setProgress(value);
				 pd.setMessage("已完成" + value + totalSize / 1000 + "kb");
			}

			/**
			 * 下载失败
			 */
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Toast.makeText(context, "服务器未开放,软件下载失败", Toast.LENGTH_SHORT).show();
				/**
				 * 如果失败了，也让按钮可以点击
				 */
				update_ly.setClickable(true);
				pd.dismiss();
			}
		});
	}

	/**
	 * 把返回的数据写入到文件
	 */
	private void makeFile(final byte[] responseBody) {
		new Thread() {
			public void run() {
				try {
					File file = new File(path);
					if (file.exists())
						file.delete();
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(responseBody);
					fos.flush();
					fos.close();
					System.out.println("文件写入成功");
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	private String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return context.getString(R.string.can_not_find_version_name);
		}
	}

	/**
	 * 升级程序
	 * 
	 * @param context
	 *            安装界面的上下文
	 * @param path
	 *            安装包所在路径
	 */
	public void updateApk(Context context, String path) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(new File(path)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static ApkUpdateUtil getInstance() {
		return new ApkUpdateUtil();
	};

	private ApkUpdateUtil() {
	};
	
//	private Thread mThread = new Thread() {
//	public void run() {
//		while (progress != total) {
//			pd.setProgress(progress);
//			pd.setMessage("已下载：" + progress / 1000 + "kb/" + total / 1000
//					+ "kb");
//		}
//	};
//};
}