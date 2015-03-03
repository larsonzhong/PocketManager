package larson.pm.utils;

import android.os.Environment;

public class Constant {
	
	//--------��goalAdd��صĳ���----------
	public static final int GOAL_CHALLENGELEVEL_HELP = 11;
	public static final int GOAL_EMERGENCYLEVEL_HELP = 12;
	public static final int GOAL_IMPORTANCELEVEL_HELP = 13;
	public static final int GOAL_PUBLICLEVEL_HELP = 14;
	public static final int GOAL_CLEARLEVEL_HELP = 15;
	//---------------------------------------
	
	public static final int SAVE_DATA = 21;
	public static final int SAVE_DATA_SUCCEED = 22;
	
	//-----------------------------------------
	public static final int QUERY_BY_WEEK = 32;
	public static final int QUERY_BY_MONTH = 33;
	public static final int QUERY_THREE_MONTH = 34;
	
	
	public static final int DB_TYPE_GOAL = 47;
	public static final int DB_TYPE_FOND = 48;
	
	// ����ļ���SD���ϴ�ŵ�λ��
	public static final String SOFTWARE_PATH = Environment
			.getExternalStorageDirectory() + "/wlb";
	// ���ݵ����ݿ��ļ���ŵ�λ��
	public static final String BUCKUP_PATH = SOFTWARE_PATH + "/backup/";

	// �ʽ��������ݿⱸ��·��
	public static final String BACKUP_FOND_PATH = BUCKUP_PATH + "/fond.xml";
	// Ŀ���������ݿ��ļ�·��
	public static final String BACKUP_GOAL_PATH = BUCKUP_PATH + "goal.xml";
	
	
	public static final String PROFILES_OUTDOOR_NAME = "����ģʽ";
	public static final String PROFILES_INDOOR_NAME = "����ģʽ";
	public static final String PROFILES_MEETING_NAME = "����ģʽ";
	public static final String PROFILES_SILENT_NAME = "����ģʽ";
	public static final String PROFILES_OFFLINE_NAME = "����ģʽ";
	public static final String PROFILES_CALL_IN_NAME = "����ģʽ";
	
	
	public static final int PROFILES_NEW_ACTIVITY = 88;
	
	public static final int SETTING_IS_PASSWORDSEETED = 98;
	public static final int SETTING_IS_FIRSTRUN = 99;
	
	/**
	 * �жϴ����������������һ��activity
	 */
	public static final int WHICH_ACTIVITY_WELCOME = 101;
	public static final int WHICH_ACTIVITY_SETTINGMAIN = 102;
	
	/**
	 * �Զ�����Ƶ��
	 */
	public static final String HOWOFTEN_BACKUP = "how_often_update";
	public static final String BACKUP_FREQUENCE = "backup_frequence";
	public static final String LAST_BACKUP_TIME = "last_backup_time";
	public static final String IS_AUTO_BACKUP = "is_auto_backup";
	public static final String BACKUP_OUTOFDATE = "isbackup_outofdate";//����ʱ��̫��
	public static final String BACKUP_2CLOUD = "backup2cloud";
	public static final String IS_OUT_OF_TIME = "isoutoftime";
	
	/**
	 * ��serviceReceiver�йصĳ���
	 */
	public static String START_ACTION = "larson.pm.NoticeServiceStart";
	public static String STOP_ACTION = "larson.pm.NoticeServiceStop";
	public static String SERVICE_HOST = "192.168.0.1";
	public static int SERVICE_PORT = 199202;
	
	/**
	 * ������url�����ַ
	 */
	public static String SERVER_UPDATE_SERVLET_PATH = "http://192.168.0.118:8080/pm/servlet/UpdateServlet";
	/**
	 * ���غ��apk�ļ���ŵ�ַ
	 */
	public static String SERVER_UPDATE_APK_PATH = Constant.SOFTWARE_PATH+"/temp.apk";
	
	
	/**
	 * POPUP�����й�
	 */
	public static final int POPUP_BUTTON_ONE = 201;
	public static final int POPUP_BUTTON_TWO = 202;
	public static final int POPUP_BUTTON_THREE = 203;
	public static final int POPUP_BUTTON_FOUR = 204;
	public static final int POPUP_BUTTON_FIVE = 205;
	public static final int POPUP_BUTTON_SIX = 206;
	
	/**
	 * ��servlet���
	 */
	private static String HOST_URL = "http://192.168.0.101:8080/pm";
	public static final String LOGIN_SERVLET = HOST_URL+"/LoginValidate";
	public static final String REGISTER_SERVLET = HOST_URL+"/RegisterServlet";
	
}
