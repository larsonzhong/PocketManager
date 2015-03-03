package larson.pm.dao;

import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.Reminder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReminderDao extends ReminderOpenHelper {
	private Context context;

	public ReminderDao(Context context) {
		super(context);
		this.context = context;
	}

	public List<Reminder> quertAll() {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String cmd = "SELECT id,tagName, time,isClock,isNotify,ringType FROM reminder ORDER BY time";
		Cursor c = db.rawQuery(cmd, null);
		List<Reminder> reminders = new ArrayList<Reminder>();
		while (c.moveToNext()) {
			Reminder reminder = new Reminder();
			reminder.setId(c.getInt(0));
			reminder.setTagName(c.getString(1));
			reminder.setTime(c.getString(2));
			reminder.setIsClock(c.getInt(3));
			reminder.setIsNotify(c.getInt(4));
			reminder.setRingType(c.getInt(5));
			
			reminders.add(reminder);
		}
		db.close();
		return reminders;
	}

	// 插入数据
	public void insert(Reminder r) {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		String cmd = "INSERT INTO reminder(tagName,time,isClock,isNotify,ringType) VALUES('"
				+ r.getTagName() + "','" + r.getTime() + "'," + r.getIsClock()+","+r.getIsNotify()+","+r.getRingType()
				+ ")";
		db.execSQL(cmd);
		db.close();
	}

	// 是否更新成功---没用到--更新是否响铃
	public void update(int id, int isClock) {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE reminder SET isStart='" + isClock
				+ "' WHERE id=" + id);
		db.close();
	}

	// //删除成功-----int id
	public void delete(int id) {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM reminder WHERE id=" + id);
		db.close();
		Log.v("larson", "delete------------id" + id);
	}

	// //删除成功-----String time
	public void delete(String time) {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM reminder WHERE time='" + time + "'");
		db.close();
		Log.v("larson", "delere------------id" + time);
	}

	// 查询得到包括id的数据
	public List<Reminder> query(String query_key, String value) {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		Reminder a = new Reminder();

		String cmd = "SELECT id,tagName,time,isClock,ringType FROM reminder WHERE "
				+ query_key + "=?";
		Cursor c = db.rawQuery(cmd, new String[] { value });

		List<Reminder> reminders = new ArrayList<Reminder>();
		while (c.moveToNext()) {
			String tagName = c.getString(1);// 设定时间
			String time = c.getString(2);// 情景模式
			int isClock = c.getInt(3);// 是否开始
			int ringType = c.getInt(4);
			int id = c.getInt(0);

			a.setIsClock(isClock);
			a.setTagName(tagName);
			a.setTime(time);
			a.setId(id);
			a.setRingType(ringType);
			reminders.add(a);
		}
		return reminders;
	}
	
	//删除表
	public void deleteTable(){
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("DROP TABLE reminder");
		db.execSQL("CREATE TABLE reminder(id INTEGER PRIMARY KEY AUTOINCREMENT, tagName VARCHAR(50),time VARCHAR(50),isClock Integer,ringType VARCHAR(50))");
	}

	public void createTable() {
		ReminderOpenHelper helper = new ReminderOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL("CREATE TABLE reminder(id INTEGER PRIMARY KEY AUTOINCREMENT, tagName VARCHAR(50),time VARCHAR(50),isClock Integer,ringType VARCHAR(50))");
	}

}
