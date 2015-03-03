package larson.pm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AutoprofilesOpenHelper extends SQLiteOpenHelper {

	public AutoprofilesOpenHelper(Context context) {
		super(context, "autoprofiles.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE autoprofiles(id INTEGER PRIMARY KEY AUTOINCREMENT, time VARCHAR(50),profiles VARCHAR(50),isStart Integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("EROP TABLE IF EXISTS person");
		onCreate(db);
	}

}
