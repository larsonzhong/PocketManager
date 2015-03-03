package larson.pm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FondOpenHelper extends SQLiteOpenHelper {

	public FondOpenHelper(Context context) {
		super(context, "fond.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE fond(id INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR(50),event VARCHAR(50),count VARCHAR(50),time VARCHAR(50),describe VARCHAR(50),ifIn INTEGER)");
		db.execSQL("CREATE TABLE matter(id INTEGER PRIMARY KEY AUTOINCREMENT, event VARCHAR(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("EROP TABLE IF EXISTS person");
		onCreate(db);
	}

}
