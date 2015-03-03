package larson.pm.utils;

import java.io.File;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * ϵͳ��Ϣ<br>
 * ����Ӳ����Ϣ�ͱ�����������Ϣ
 * @author Larson
 *
 */
public class SystemInfoUtil {
	
	public static int SYS_SCREEN_WIDTH;
	public static int SYS_SCREEN_HEIGHT;
	public static int CONTACT_GROUP_LABLE_WIDTH;
	
	/**
	 * ��ʼ��ʱ��ȡ��Ļ����
	 * @param activity
	 */
	public static void getSystemInfo(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		SYS_SCREEN_WIDTH = dm.widthPixels;
		SYS_SCREEN_HEIGHT = dm.heightPixels;
		//����group��ʾ�Ŀ��ռ��3/5����Ļ���
		CONTACT_GROUP_LABLE_WIDTH = SYS_SCREEN_WIDTH*3/5;
	}
	
	
	/**
	 * ��ʼ�����ݿⱸ��Ŀ¼����ֹĿ¼�Ҳ���
	 */
	public static void initExtraDBDir(){

		File sp = new File(Constant.SOFTWARE_PATH);
		if (!sp.exists()){
			sp.mkdir(); // ������Ŀ¼�ʹ���(���Ŀ¼)
		}
		
		File bup = new File(Constant.BUCKUP_PATH);
		if (!bup.exists())
			bup.mkdirs(); // ������Ŀ¼�ʹ��������ݱ���Ŀ¼��
		else
			bup.delete();//����������������
		
		Log.v("larson", "-------����sdĿ¼-------" );
	}
	
	/**
	 * ��ʼ��������ݿ�Ŀ¼���еĻ���ɾ������ֹ���ݻָ����ǳ���
	 * @param toPath 
	 */
	public static void initInnerDBDir(String toPath){

		File bup = new File(toPath);
		if (bup.exists())
			bup.delete();//�������������ݿ��˾���ɾ��������ݿ��ٱ���
		
		Log.v("larson", "-------ɾ���ڲ��洢���ݿ��ļ�-------" );
	}
}
