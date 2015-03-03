package larson.pm.utils;


import larson.pm.bean.UserInfo;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class App extends Application {
	private static App mInstance;
	private LockPatternUtils mLockPatternUtils;
	
	
	
	/**
	 * ��ǰ��¼�û�
	 */
	public static UserInfo currentUser;
	/**
	 * �û��Ƿ��Ѿ���½
	 */
	public static boolean isUserLogin=false;
	
	public static boolean isSettingMode = false;
	/**
	 * �����Ƿ��趨
	 */
	private boolean isPwdSetted;
	/**
	 * �Ƿ��״�����
	 */
	private boolean isFirstRun;

	/**
	 * ��ȡӦ�ó����ʵ����������mainfest��application��������
	 * 
	 * @return
	 */
	public static App getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;// �Ķ� this->new App()
		mLockPatternUtils = new LockPatternUtils(this);
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

	public boolean getIsPwdSetted() {
		return isPwdSetted;
	}

	public boolean getIsFirstRun() {
		return isFirstRun;
	}

	public void setPwdSetted(boolean isPwdSetted) {
		this.isPwdSetted = isPwdSetted;
		writeSP(isPwdSetted,Constant.SETTING_IS_PASSWORDSEETED);
	}

	public void setFirstRun(boolean isFirstRun) {
		this.isFirstRun = isFirstRun;
		writeSP(isFirstRun,Constant.SETTING_IS_FIRSTRUN);
	}
	
	private void writeSP(boolean flag,int type){
		String key = null;
		if(type==Constant.SETTING_IS_PASSWORDSEETED)
			key = "isPwdSetted";
		if(type==Constant.SETTING_IS_FIRSTRUN)
			key = "isFirstRun";
		Editor editor = getEditor();
		editor.putBoolean(key, flag);
		editor.commit();
	}

	/**
	 * ��ȡsharepreference�༭��
	 * @return
	 */
	public Editor getEditor() {
		SharedPreferences sp = getMySharePreference();
		Editor editor = sp.edit();
		return editor;
	}

	private SharedPreferences getMySharePreference() {
		SharedPreferences sp = getSharedPreferences("wlb", MODE_PRIVATE);
		return sp;
	}
	
	/**
	 * 1.1�����Զ�����Ƶ��
	 */
	public void setBackupFrequence(int howoften){
		Editor editor = getEditor();
		editor.putInt(Constant.HOWOFTEN_BACKUP, 7);
		editor.commit();
	}
	
	/**
	 * 1.2�Ƿ��Զ�����
	 * @param flag
	 */
	public void setIsAutoBackup(boolean flag){
		Editor editor = getEditor();
		editor.putBoolean(Constant.IS_AUTO_BACKUP, flag);
		editor.commit();
	}
	/**
	 * 1.3
	 * @return
	 */
	public boolean getIsAutoBackup(){
		SharedPreferences sp = getMySharePreference();
		return sp.getBoolean(Constant.IS_AUTO_BACKUP, true);
	}
	/**
	 * 1.4
	 * @return
	 */
	public int getBackupFrequence(){
		SharedPreferences sp = getMySharePreference();
		return sp.getInt(Constant.BACKUP_FREQUENCE, 30);
	}
	/**
	 * 1.5
	 * @return
	 */
	public long getLastBackupTime(){
		SharedPreferences sp = getMySharePreference();
		return sp.getLong(Constant.LAST_BACKUP_TIME,0);
	}

	/**
	 * 1.6
	 * @param time
	 */
	public void setLastBackupTime(long time) {
		Editor editor = getEditor();
		editor.putLong(Constant.LAST_BACKUP_TIME, time);
		editor.apply();
	}
	
	public void setIsBackup2Cloud(boolean isBackup2Cloud){
		Editor editor = getEditor();
		editor.putBoolean(Constant.BACKUP_2CLOUD, isBackup2Cloud);
		editor.commit();
	}
	
	public boolean getIsBackup2Cloud(){
		SharedPreferences sp = getMySharePreference();
		return sp.getBoolean(Constant.BACKUP_2CLOUD, false);
	}

	/**
	 * �Ƿ�ܾ�û�б�����
	 * @return
	 */
	public boolean getIsOutTime() {
		boolean isouttime = getMySharePreference().getBoolean(Constant.IS_OUT_OF_TIME, false);
		return isouttime;
	}

	/**
	 * �����ǲ��Ǻܾ�û�б�����
	 * @param isOutTime
	 */
	public void setIsOutTime(boolean isOutTime) {
		Editor editor = getEditor();
		editor.putBoolean(Constant.IS_OUT_OF_TIME, isOutTime);
		editor.commit();
	}
	

}
