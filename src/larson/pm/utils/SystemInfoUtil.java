package larson.pm.utils;

import java.io.File;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 系统信息<br>
 * 包括硬件信息和本软件的软件信息
 * @author Larson
 *
 */
public class SystemInfoUtil {
	
	public static int SYS_SCREEN_WIDTH;
	public static int SYS_SCREEN_HEIGHT;
	public static int CONTACT_GROUP_LABLE_WIDTH;
	
	/**
	 * 初始化时获取屏幕数据
	 * @param activity
	 */
	public static void getSystemInfo(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		SYS_SCREEN_WIDTH = dm.widthPixels;
		SYS_SCREEN_HEIGHT = dm.heightPixels;
		//整个group显示的宽度占据3/5的屏幕宽度
		CONTACT_GROUP_LABLE_WIDTH = SYS_SCREEN_WIDTH*3/5;
	}
	
	
	/**
	 * 初始化数据库备份目录，防止目录找不到
	 */
	public static void initExtraDBDir(){

		File sp = new File(Constant.SOFTWARE_PATH);
		if (!sp.exists()){
			sp.mkdir(); // 不存在目录就创建(软件目录)
		}
		
		File bup = new File(Constant.BUCKUP_PATH);
		if (!bup.exists())
			bup.mkdirs(); // 不存在目录就创建（数据备份目录）
		else
			bup.delete();//否则清空里面的数据
		
		Log.v("larson", "-------创建sd目录-------" );
	}
	
	/**
	 * 初始化软件数据库目录，有的话就删除，防止数据恢复覆盖出错
	 * @param toPath 
	 */
	public static void initInnerDBDir(String toPath){

		File bup = new File(toPath);
		if (bup.exists())
			bup.delete();//如果存在这个数据库了就先删除这个数据库再备份
		
		Log.v("larson", "-------删除内部存储数据库文件-------" );
	}
}
