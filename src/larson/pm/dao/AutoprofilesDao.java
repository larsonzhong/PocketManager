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
	// 查询全部返回profiles集合(返回的是删除过时的profiles)
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

	// 插入数据
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

	// 是否更新成功
	public void update(int id, int isStart) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE autoprofiles SET isStart='" + isStart + "' WHERE id=" + id);
		db.close();
	}

	// //删除成功-----int id
	public void delete(int id) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM autoprofiles WHERE id=" + id);
		db.close();
		Log.v("larson", "delere------------id"+id);
	}
	// //删除成功-----String time
	public void delete(String time) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM autoprofiles WHERE time='" + time+"'");
		db.close();
		Log.v("larson", "delere------------id"+time);
	}

	//查询得到包括id的数据
	public List<Autoprofiles> query(String query_key, String value) {
		AutoprofilesOpenHelper helper = new AutoprofilesOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Autoprofiles a = new Autoprofiles();

		String cmd = "SELECT id,time,profiles,isStart FROM autoprofiles WHERE "
				+ query_key + "=?";
		Cursor c = db.rawQuery(cmd, new String[] { value });

		List<Autoprofiles> autoprofiles = new ArrayList<Autoprofiles>();
		while (c.moveToNext()) {
			String time = c.getString(1);// 设定时间
			String profiles = c.getString(2);// 情景模式
			int isStart = c.getInt(3);// 是否开始
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
