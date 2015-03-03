package larson.pm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReminderOpenHelper extends SQLiteOpenHelper {

	public ReminderOpenHelper(Context context) {
		super(context, "reminder.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS reminder(id INTEGER PRIMARY KEY AUTOINCREMENT, tagName VARCHAR(50),time VARCHAR(50),isClock Integer,isNotify Integer,ringType VARCHAR(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("EROP TABLE IF EXISTS person");
		onCreate(db);
	}

}
