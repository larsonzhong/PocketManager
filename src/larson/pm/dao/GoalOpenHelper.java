package larson.pm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoalOpenHelper extends SQLiteOpenHelper {

	public GoalOpenHelper(Context context) {
		super(context, "goal.db", null, 1);
	}

	/**
	 * int isSet, string goalName, int challengeLevel, int emergencyLevel, int
	 * importanceLevel, int publicLevel, int clearLevel, int step1Degree, int
	 * step2Degree, int step3Degree, String step1LimitTime, String
	 * step2LimitTime, String step3LimitTime, String goalName, String reason,
	 * String benefit, String damage
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// String cmd =
		// "CREATE TABLE goal(id INTEGER PRIMARY KEY AUTOINCREMENT, isSet INTEGER,isFinish INTEGER,challengeLevel INTEGER,emergencyLevel INTEGER,importanceLevel INTEGER,publicLevel INTEGER,clearLevel INTEGER,step1Degree INTEGER,step2Degree INTEGER,step3Degree INTEGER,step1LimitTime VARCHAR(50),step2LimitTime VARCHAR(50),step3LimitTime VARCHAR(50),goalName VARCHAR(50),reason VARCHAR(50),benefit VARCHAR(50),damage VARCHAR(50))";
		String cmd = "CREATE TABLE goal(id INTEGER PRIMARY KEY AUTOINCREMENT,isFinish INTEGER,goalName VARCHAR(50),challengeLevel FLOAT,emergencyLevel FLOAT,importanceLevel FLOAT,publicLevel FLOAT,clearLevel FLOAT,step1Degree FLOAT,step2Degree FLOAT,step3Degree FLOAT,step1LimitTime VARCHAR(50),step2LimitTime VARCHAR(50),step3LimitTime VARCHAR(50),step1Describe VARCHAR(500),step2Describe VARCHAR(500),step3Describe VARCHAR(500),define VARCHAR(500),reason VARCHAR(500),benefit VARCHAR(500),damage VARCHAR(500))";
		db.execSQL(cmd);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("EROP TABLE IF EXISTS person");
		onCreate(db);
	}

}
