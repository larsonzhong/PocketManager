package larson.pm.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.Autoprofiles;
import larson.pm.utils.TimeTools;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AutoprofilesDao extends AutoprofilesOpenHelper {

	private Context context;

	public AutoprofilesDao(Context context) {
		super(context);
		this.context = context;
	}

	// time VARCHAR(50),profiles VARCHAR(50),isStart
	// ��ѯȫ������profiles����(���ص���ɾ����ʱ��profiles)
	public List<Autoprofiles> quertAll() {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String cmd = "SELECT id,time, profiles,isStart FROM autoprofiles ";
		Cursor c = db.rawQuery(cmd, null);
		List<Autoprofiles> profiles = new ArrayList<Autoprofiles>();
		while (c.moveToNext()) {
			Autoprofiles profile = new Autoprofiles(c.getInt(0),c.getString(1),
					c.getString(2), c.getInt(3));
			try {
				if(TimeTools.isNotDepressed(profile.getTime()))
				profiles.add(profile);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		db.close();
		return profiles;
	}

	// ��������
	public void insert(Autoprofiles a) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		String cmd = "INSERT INTO autoprofiles(time,profiles,isStart) VALUES('"
				+ a.getTime()
				+ "','"
				+ a.getProfiles()
				+ "',"
				+ a.getIsStart()
				+")";
		db.execSQL(cmd);
		db.close();
	}

	// �Ƿ���³ɹ�
	public void update(int id, int isStart) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE autoprofiles SET isStart='" + isStart + "' WHERE id=" + id);
		db.close();
	}

	// //ɾ���ɹ�-----int id
	public void delete(int id) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM autoprofiles WHERE id=" + id);
		db.close();
		Log.v("larson", "delere------------id"+id);
	}
	// //ɾ���ɹ�-----String time
	public void delete(String time) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM autoprofiles WHERE time='" + time+"'");
		db.close();
		Log.v("larson", "delere------------id"+time);
	}

	//��ѯ�õ�����id������
	public List<Autoprofiles> query(String query_key, String value) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Autoprofiles a = new Autoprofiles();

		String cmd = "SELECT id,time,profiles,isStart FROM autoprofiles WHERE "
				+ query_key + "=?";
		Cursor c = db.rawQuery(cmd, new String[] { value });

		List<Autoprofiles> autoprofiles = new ArrayList<Autoprofiles>();
		while (c.moveToNext()) {
			String time = c.getString(1);// �趨ʱ��
			String profiles = c.getString(2);// �龰ģʽ
			int isStart = c.getInt(3);// �Ƿ�ʼ
			int id = c.getInt(0);

			a.setIsStart(isStart);
			a.setProfiles(profiles);
			a.setTime(time);
			a.setId(id);
			autoprofiles.add(a);
		}
		return autoprofiles;
	}

}
