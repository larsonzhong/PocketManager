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
	 * ��ʼ�������ݣ������������ȡ�ļ�
	 * 
	 * @param context
	 *            ������
	 * @param update_ly
	 */
	public void update(final Context context, final RelativeLayout update_ly) {

		// 1.�������
		AsyncHttpClient client = new AsyncHttpClient();
		// 2.���ò���
		RequestParams params = new RequestParams();
		params.put("version", getVersion(context));

		// 3.�س�
		client.post(url, params, new AsyncHttpResponseHandler() {

			/**
			 * ��������
			 */
			@Override
			public void onStart() {
				super.onStart();
				pd = new ProgressDialog(context);
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.setCancelable(false);
				pd.setMax(100);
				pd.setTitle("�������ء���");
				pd.show();
			}

			/**
			 * ������ɺ�
			 */
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				/**
				 * ֻҪ����ɾ��ð�ť�ɵ��
				 */
				update_ly.setClickable(true);
				if (statusCode == 200) {
					pd.dismiss();
					System.out.println(responseBody.length);
					// 5.д�ļ�
					makeFile(responseBody);
					Toast.makeText(context, "������سɹ�", Toast.LENGTH_SHORT)
							.show();
					// ��ʼ��װ
//					updateApk(context, path);
				} else {
					Toast.makeText(context, "����������δ����Ŷ", Toast.LENGTH_SHORT)
							.show();
				}
			}

			/**
			 * ����������
			 */
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				int value = (bytesWritten / totalSize) * 100;
				progress = value;
				total = totalSize;
				 pd.setProgress(value);
				 pd.setMessage("�����" + value + totalSize / 1000 + "kb");
			}

			/**
			 * ����ʧ��
			 */
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Toast.makeText(context, "������δ����,�������ʧ��", Toast.LENGTH_SHORT).show();
				/**
				 * ���ʧ���ˣ�Ҳ�ð�ť���Ե��
				 */
				update_ly.setClickable(true);
				pd.dismiss();
			}
		});
	}

	/**
	 * �ѷ��ص�����д�뵽�ļ�
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
					System.out.println("�ļ�д��ɹ�");
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return ��ǰӦ�õİ汾��
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
	 * ��������
	 * 
	 * @param context
	 *            ��װ�����������
	 * @param path
	 *            ��װ������·��
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
//			pd.setMessage("�����أ�" + progress / 1000 + "kb/" + total / 1000
//					+ "kb");
//		}
//	};
//};
}