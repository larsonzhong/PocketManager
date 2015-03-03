package larson.pm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsOpenHelper extends SQLiteOpenHelper {

	public SmsOpenHelper(Context context) {
		super(context, "autosms.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS autosms(id INTEGER PRIMARY KEY AUTOINCREMENT,receiver VARCHAR(500),content VARCHAR(500),time VARCHAR(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
